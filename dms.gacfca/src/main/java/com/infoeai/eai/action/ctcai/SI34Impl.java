package com.infoeai.eai.action.ctcai;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.DTO.Ctcai2DcsFPXXDTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.parsetxt.CTCAIFPXXParse;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dms.common.domains.PO.basedata.TiNodeDetialPO;
import com.yonyou.dms.common.domains.PO.basedata.TmCtcaiVehiclePO;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * FTP
 * CTCAI->DCS
 * FPXX--CTCAI将发票信息发送给DCS
 * @author luoyang
 *
 */
@Service
public class SI34Impl extends BaseService implements SI34 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI34Impl.class);
	String srcPath = "E:/Interface/ctcai/out/FPXX"; // 接口对应的文件路径
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Autowired
	SaleVehicleSaleDao saleDao;
	@Autowired
	SICommonDao dao;

	@Override
	public String execute() throws Exception {
		logger.info("====SI34 is begin====");
		CTCAIFPXXParse fpxxParse = new CTCAIFPXXParse();
		// 扫描目标文件夹文件
		File srcFolder = new File(srcPath);

		if (!srcFolder.exists()) {
			logger.info("=============文件夹不存在不执行任何操作=================");
			return "1";
		}
		File[] filesList = srcFolder.listFiles(); // 获取文件夹下面的所有文件
		if (null != filesList && filesList.length > 0) {
			for (int j = 0; j < filesList.length; j++) {
				// 剔除包含备份文件夹的结果集
				if (filesList[j].getName().equals("Archive")) {
					continue;
				}
				// 传入文件路径获取解析后的结果集
				List<Ctcai2DcsFPXXDTO> list = fpxxParse.readTxt(srcPath + "/"
						+ filesList[j].getName(), "", filesList[j].getName());
				// 对接收的PO集合进行处理
				if (null == list) {
					logger.info("=============文件解析错误！=================");
					continue;
				}
				try {
					/****************************** 开启事物 *********************/
					beginDbService();
					
					for (int i = 0; i < list.size(); i++) {
						Ctcai2DcsFPXXDTO tempPO = new Ctcai2DcsFPXXDTO();
						tempPO = list.get(i);
						if (null == tempPO.getVin()
								|| "".equals(tempPO.getVin())) {
							logger.info("=============回传vin码为空！=================");
							continue;
						} else if (tempPO.getVin().length() != 17) {
							logger.info("=============" + tempPO.getVin()
									+ "的vin码长度与接口定义不一致！=================");
							continue;
						}

						if (null == tempPO.getDealerCode()
								|| "".equals(tempPO.getDealerCode())) {
							logger.info("=============" + tempPO.getVin()
									+ "的经销商代码为空！=================");
							continue;
						} else if (tempPO.getDealerCode().length() > 10) {
							logger.info("=============" + tempPO.getVin()
									+ "的经销商代码长度与接口定义不一致！=================");
							continue;
						}
						
						List<TmCtcaiVehiclePO> resultPoList = TmCtcaiVehiclePO.find("VIN = ?", tempPO.getVin());
						Date currentTime = new Date();
						// 如果存在则更新数据
						if (resultPoList != null && resultPoList.size() > 0) {
							String dealerCode = dao.getDealerCodeByInfo(tempPO.getDealerCode(), "ctcai");
							TmCtcaiVehiclePO.update("DEALER_NO = ?,INVOICE_NO = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "VIN = ?",
									dealerCode,tempPO.getInvoiceNumber(),new Long(80000002),currentTime,tempPO.getVin());
						}else{
							// 如果找不到相关的vin信息，存在则新建数据
							TmCtcaiVehiclePO vehiclePO = new TmCtcaiVehiclePO();
							vehiclePO.setString("VIN", tempPO.getVin());
							Map vehicleIdMap = saleDao.getVehicleIdByVin(tempPO.getVin());
							if (vehicleIdMap != null) {
								String vehicleId = CommonUtils.checkNull(vehicleIdMap.get("VEHICLE_ID"));
								if (vehicleId != null && vehicleId.length() > 0) {
									vehiclePO.setLong("VEHICLE_ID",Long.valueOf(vehicleId));
								}
							}else{
								logger.debug("=============" + tempPO.getVin() + "在系统中不存在，忽略该车相关发票信息！=================");
								continue;
							}
							String dealerCode = dao.getDealerCodeByInfo(tempPO.getDealerCode(), "ctcai");
							vehiclePO.setString("DEALER_NO",dealerCode);
							vehiclePO.setString("INVOICE_NO",tempPO.getInvoiceNumber());
							vehiclePO.setLong("CREATE_BY",new Long(80000002));
							vehiclePO.setTimestamp("CREATE_DATE",currentTime);
							vehiclePO.saveIt();
						}
						// 写入接口日志
						insertNodeDetail(tempPO);
						if (i % 100 == 0) {
							logger.info("=============已处理" + i + "条发票信息=================");
						}
					}
					
					dbService.endTxn(true);
					rdt.DelFile(srcPath + "/" + filesList[j].getName());
					/****************************** 结束事物 *********************/					
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
					dbService.endTxn(false);
				} finally {
					Base.detach();
					dbService.clean();
				}
			}
			
		}else{
			logger.info("=============指定文件夹为空=================");
		}
		logger.info("====SI34 is finish====");
		return null;
	}

	private Long insertNodeDetail(Ctcai2DcsFPXXDTO tempPO) {
		// 在校验数据之前，将数据插入记录表(插入的时候默认记录成功导入，后面更新的时候修改未导入原因)
		TiNodeDetialPO nodePO = new TiNodeDetialPO();
		nodePO.setString("ACTION_CODE", "FPXX");
		nodePO.setString("VIN", CommonUtils.checkNull(tempPO.getVin()));
		nodePO.setString("DEALER_RECEIVE", tempPO.getDealerCode());
		nodePO.setTimestamp("SCAN_DATE", new Date());
		nodePO.insert();
		// 返回成功标识
		return nodePO.getLongId();
		
	}

}
