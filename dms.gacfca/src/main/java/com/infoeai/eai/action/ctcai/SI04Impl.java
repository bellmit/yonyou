package com.infoeai.eai.action.ctcai;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.infoeai.eai.po.TtVsRebateEmployPO;
import com.infoeai.eai.vo.Ctcai2Dcs01VO;

/**
* 功能说明：车辆返利使用结果回传(CTCAI_DCS)
* 创建者：ZRM
* 创建时间：2013-05-21
*/
@Service
public class SI04Impl extends BaseService implements SI04{
	private static final Logger logger = LoggerFactory.getLogger(SI04Impl.class);
	String srcPath="E:/Interface/ctcai/out/FLUR";      //接口对应的文件路径
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Autowired
	SICommonDao dao;
	
	public String execute() throws Exception{
		logger.info("====SI04 is begin====");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar sysdate = Calendar.getInstance();
		CTCAIParse01 parse01 = new CTCAIParse01();
		try{
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
					List<Ctcai2Dcs01VO> list = parse01.readTxt(srcPath+"/"+filesList[j].getName(),"",filesList[j].getName());
					//对接收的PO集合进行处理
					if(null==list){
						logger.info("=============文件解析错误！=================");
						continue;
					}
					for(int i=0;i<list.size();i++){
						Ctcai2Dcs01VO tempPO = new Ctcai2Dcs01VO();
						tempPO = list.get(i);
						if(null==tempPO.getVin()||"".equals(tempPO.getVin())){
							logger.info("=============返利使用的回传vin码为空！=================");
							continue;
						}else if(tempPO.getVin().length()!=17){
							logger.info("============="+tempPO.getVin()+"的vin码长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getActionCode()||"".equals(tempPO.getActionCode())){
							logger.info("============="+tempPO.getVin()+"的交易代码为空！=================");
							continue;
						}else if(!"FLUR".equals(tempPO.getActionCode())){
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
						
						if(null==tempPO.getRebateCode()||"".equals(tempPO.getRebateCode())){
							logger.info("============="+tempPO.getVin()+"的返利代码为空！=================");
							continue;
						}else if(tempPO.getRebateCode().length()>8){
							logger.info("============="+tempPO.getVin()+"的返利代码长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getRebateAmount()||"".equals(tempPO.getRebateAmount())){
							logger.info("============="+tempPO.getVin()+"的返利金额为空！=================");
							continue;
						}else if(tempPO.getRebateAmount().length()>10){
							logger.info("============="+tempPO.getVin()+"的返利金额长度与接口定义不一致！=================");
							continue;
						}
						
						if(tempPO.getRemark().length()>100){
							logger.info("============="+tempPO.getVin()+"的备注长度与接口定义不一致！=================");
							continue;
						}
						
						//对满足要求的记录插入到业务表
						TtVsRebateEmployPO PO1 = new TtVsRebateEmployPO();
//						String id = SequenceManager.getSequence(null);
//						PO1.setId(id);
						PO1.setString("VIN",tempPO.getVin());
						//将外面经销商代码转换成本地代码
						String dealerCode = dao.getDealerCodeByInfo(tempPO.getDealerCode(),"ctcai");
						PO1.setString("DEALER_CODE",dealerCode);
						PO1.setString("REBATE_CODE",tempPO.getRebateCode());
						PO1.setString("REBATE_AMOUNT",tempPO.getRebateAmount());
						PO1.setTimestamp("REBATE_EMPLOY_DATE",sdf.parse(tempPO.getActionDate()+tempPO.getActionTime()));
						PO1.setLong("CREATE_BY",new Long(80000001));
						PO1.setTimestamp("CREATE_DATE",sysdate.getTime());
						PO1.setString("REMARK",tempPO.getRemark());
						
						PO1.saveIt();
					}
					rdt.DelFile(srcPath+"/"+filesList[j].getName());
				}
		    }else{
		    	logger.info("=============指定文件夹为空=================");
		    }
			dbService.endTxn(true);
			/******************************结束事物*********************/
		}catch (Throwable e){
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		}finally{
			logger.info("====SI04 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}
	
	
	/**
	 * 功能说明:手动发送消息包
	 * 创建人: ZRM
	 * 最后修改日期: 2013-05-21
	 */
	public static void main(String[] args) throws Throwable{
//		ContextUtil.loadConf();
		SI04Impl action = new SI04Impl();
		action.execute();
	}
}
