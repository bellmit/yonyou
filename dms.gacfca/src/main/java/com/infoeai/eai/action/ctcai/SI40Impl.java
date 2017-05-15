package com.infoeai.eai.action.ctcai;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.DTO.Ctcai2DcsXLHHCDTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.parsetxt.CTCAIXLHHCParse;
import com.yonyou.dms.common.domains.PO.basedata.TiNodeDetialPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.ProOrderSerialPO;

/**
 * FTP
 * CTCAI->DCS
 * 生产订单中进至DCS系统
 * @author luoyang
 *
 */
@Service
public class SI40Impl extends BaseService implements SI40 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI40Impl.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String srcPath = "E:/Interface/ctcai/out/XLHHC"; // 接口对应的文件路径
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Override
	public String execute() throws Exception {
		logger.info("====SI40 is start====");
		CTCAIXLHHCParse xlhhcParse = new CTCAIXLHHCParse();
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
				List<Ctcai2DcsXLHHCDTO> list = xlhhcParse.readTxt(srcPath + "/"
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
						Ctcai2DcsXLHHCDTO tempPO = new Ctcai2DcsXLHHCDTO();
						tempPO = list.get(i);
						if (null == tempPO.getSerialNumber() || "".equals(tempPO.getSerialNumber())) {
							logger.info("=============回传序列号为空！=================");
							continue;
						} else if (tempPO.getSerialNumber().length() != 21) {
							logger.info("=============" + tempPO.getSerialNumber() + "的序列号长度与接口定义不一致！=================");
							continue;
						}

						if (null == tempPO.getActionDate() || "".equals(tempPO.getActionDate())) {
							logger.info("=============" + tempPO.getSerialNumber() + "的交易日期为空！=================");
							continue;
						} else if (tempPO.getActionDate().length() != 8) {
							logger.info("=============" + tempPO.getSerialNumber() + "的交易日期长度与接口定义不一致！=================");
							continue;
						}
						
						if (null == tempPO.getResult()|| "".equals(tempPO.getResult())) {
							logger.info("=============" + tempPO.getSerialNumber() + "的返回结果为空！=================");
							continue;
						}
						
						List<ProOrderSerialPO> resultPoList = ProOrderSerialPO.find("SERIAL_NUMBER = ?", tempPO.getSerialNumber());
						Date currentTime = new Date();
						// 如果存在则更新数据
						if (resultPoList != null && resultPoList.size() > 0) {
							Integer result = null;
							if("1".equals(tempPO.getResult())){
								//中进定金确认
								result = OemDictCodeConstants.ZHONGJIN_ORDER_CONFIRM;
							}else if("2".equals(tempPO.getResult())){
								//逾期未付定金撤销
								result = OemDictCodeConstants.ZHONGJIN_TIMEOUT_CANCEL;
							}
							ProOrderSerialPO.update("STATUS = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "SERIAL_NUMBER = ?",
									result,new Long(80000002),currentTime,tempPO.getSerialNumber());
						}else{
							logger.debug("=============" + tempPO.getSerialNumber() + "在系统中不存在，忽略该序列号信息！=================");
							continue;
						}
						// 写入接口日志
						insertNodeDetail(tempPO);
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
		}else {
			logger.info("=============指定文件夹为空=================");
		}
		logger.info("====SI40 is finish====");
		return null;
	}

	private Long insertNodeDetail(Ctcai2DcsXLHHCDTO tempPO) {
		// 在校验数据之前，将数据插入记录表(插入的时候默认记录成功导入，后面更新的时候修改未导入原因)
		TiNodeDetialPO nodePO = new TiNodeDetialPO();
		nodePO.setString("ACTION_CODE", "PON");
		nodePO.setString("ACTION_DATE", CommonUtils.checkNull(tempPO.getActionDate()));
		nodePO.setString("SERIAL_NUMBER", CommonUtils.checkNull(tempPO.getSerialNumber()));
		nodePO.setString("DEALER_RECEIVE", tempPO.getDealerCode());
		nodePO.setTimestamp("SCAN_DATE", new Date());
		nodePO.insert();
		// 返回成功标识
		return nodePO.getLongId();
		
	}

}
