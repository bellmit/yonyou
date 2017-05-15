package com.infoeai.eai.action.ctcai;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.DTO.Ctcai2DcsDJZFDTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.parsetxt.CTCAIDJZFParse;
import com.yonyou.dms.common.domains.PO.basedata.TiNodeDetialPO;
import com.yonyou.dms.common.domains.PO.basedata.TtResourceRemarkPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * FTP
 * CTCAI->DCS
 * DJZF-经销商定金支付状态同步至DCS系统
 * @author luoyang
 *
 */
@Service
public class SI28Impl extends BaseService implements SI28 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI28Impl.class);
	String srcPath="E:/Interface/ctcai/out/DJZF";      //接口对应的文件路径
	ReadFileTxt rdt = new ReadFileTxt();

	@Override
	public String execute() throws Exception {
		logger.info("====SI28 is begin====");
		CTCAIDJZFParse djzfParse = new CTCAIDJZFParse();
		try {
			/******************************开启事物*********************/
			beginDbService();
			
			//扫描目标文件夹文件
			File srcFolder=new File(srcPath);
			if(!srcFolder.exists()){
				logger.info("=============文件夹不存在不执行任何操作=================");
				return "1"; 
			}
			File[] filesList=srcFolder.listFiles();  //获取文件夹下面的所有文件
			if(null!=filesList && filesList.length>0){
				for(int j=0;j<filesList.length;j++){
					//剔除包含备份文件夹的结果集
					if(filesList[j].getName().equals("Archive")){
						continue;
					}
					//传入文件路径获取解析后的结果集
					List<Ctcai2DcsDJZFDTO> list = djzfParse.readTxt(srcPath+"/"+filesList[j].getName(),"",filesList[j].getName());
					//对接收的PO集合进行处理
					if(null==list){
						logger.info("=============文件解析错误！=================");
						continue;
					}
					for(int i=0;i<list.size();i++){
						Ctcai2DcsDJZFDTO tempPO = new Ctcai2DcsDJZFDTO();
						tempPO = list.get(i);
						if(null==tempPO.getVin()||"".equals(tempPO.getVin())){
							logger.info("=============回传vin码为空！=================");
							continue;
						}else if(tempPO.getVin().length()!=17){
							logger.info("============="+tempPO.getVin()+"的vin码长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getActionCode()||"".equals(tempPO.getActionCode())){
							logger.info("============="+tempPO.getVin()+"的交易代码为空！=================");
							continue;
						}else if(!"DJZF".equals(tempPO.getActionCode())){
							logger.info("============="+tempPO.getVin()+"的交易代码与定义不匹配！=================");
							continue;
						}
						
						if(null==tempPO.getActionDate()||"".equals(tempPO.getActionDate())){
							logger.info("============="+tempPO.getVin()+"的交易日期为空！=================");
							continue;
						}else if(tempPO.getActionDate().length()!=8){
							logger.info("============="+tempPO.getVin()+"的交易日期长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getActionTime()||"".equals(tempPO.getActionTime())){
							logger.info("============="+tempPO.getVin()+"的交易时间为空！=================");
							continue;
						}else if(tempPO.getActionTime().length()!=6){
							logger.info("============="+tempPO.getVin()+"的交易时间长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getDealerCode()||"".equals(tempPO.getDealerCode())){
							logger.info("============="+tempPO.getVin()+"的经销商代码为空！=================");
							continue;
						}else if(tempPO.getDealerCode().length()>10){
							logger.info("============="+tempPO.getVin()+"的经销商代码长度与接口定义不一致！=================");
							continue;
						}
						
						//写入接口日志
						insertNodeDetail(tempPO);
						
						/* 2.更新订单表 */
						String isPay=tempPO.getIsPay();
						if("1".equals(isPay)){
							TtVsOrderPO.update("ORDER_STATUS = ?,UPDATE_DATE = ?,UPDATE_BY = ?", "VIN = ? AND ORDER_STATUS = ?", 
									OemDictCodeConstants.ORDER_STATUS_05,new Date(),new Long(80000002),tempPO.getVin(),OemDictCodeConstants.ORDER_STATUS_04);
							
							//期货订单定金收款后订单默认锁定
							List<TtResourceRemarkPO> checklist = TtResourceRemarkPO.find("VIN = ?", tempPO.getVin());
							if(checklist != null && !checklist.isEmpty()){								
								TtResourceRemarkPO.update("IS_LOCK = ?,UPDATE_DATE = ?,UPDATE_BY = ?", "VIN = ?", 1,new Date(),new Long(80000002),tempPO.getVin());
							}else{
								TtResourceRemarkPO resourceRemark = new TtResourceRemarkPO();
								resourceRemark.setInteger("REMARK", 0);
								resourceRemark.setInteger("IS_LOCK", 1);
								resourceRemark.setTimestamp("CREATE_DATE", new Date());
								resourceRemark.setLong("CREATE_BY", new Long(80000002));
								resourceRemark.insert();
							}
						}else{
							logger.info("============="+tempPO.getVin()+"定金支付状态为0，执行撤单操作=================");
							AutoScanBookedFuturesResource.processFutureResource(tempPO.getVin());
						}
					}
					rdt.DelFile(srcPath+"/"+filesList[j].getName());
				}
			}else{
				logger.info("=============指定文件夹为空=================");
			}
			
			dbService.endTxn(true);
			/******************************结束事物*********************/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
		} finally{
			logger.info("====SI28 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}

	private Long insertNodeDetail(Ctcai2DcsDJZFDTO tempPO) {
		// 在校验数据之前，将数据插入记录表(插入的时候默认记录成功导入，后面更新的时候修改未导入原因)
		TiNodeDetialPO nodePO = new TiNodeDetialPO();
		nodePO.setString("ACTION_CODE", CommonUtils.checkNull(tempPO.getActionCode()));
		nodePO.setString("ACTION_DATE", CommonUtils.checkNull(tempPO.getActionDate()));
		nodePO.setString("ACTION_TIME", CommonUtils.checkNull(tempPO.getActionTime()));
		nodePO.setString("VIN", CommonUtils.checkNull(tempPO.getVin()));
		nodePO.setString("DEALER_RECEIVE", tempPO.getDealerCode());
		nodePO.setString("UPDATE_STATUS", tempPO.getIsPay());
		nodePO.setTimestamp("SCAN_DATE", new Date());
		nodePO.insert();
		return nodePO.getLongId();
	}

}
