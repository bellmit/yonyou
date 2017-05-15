package com.infoeai.eai.action.ctcai;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.dao.ctcai.SI03Dao;
import com.infoeai.eai.po.TiRebatePO;
import com.infoeai.eai.vo.Dcs2CtcaiVO;

/**
* 功能说明：经销商返利明细导入(DCS_CTCAI)
* 创建者：ZRM
* 创建时间：2013-05-21
*/
@Service
public class SI03Impl extends BaseService implements SI03{
	private static final Logger logger = LoggerFactory.getLogger(SI03Impl.class);
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Autowired
	SI03Dao dao;
	
	public String execute() throws Exception {
		logger.info("====SI03 is begin====");
		try{
			Calendar sysDate = Calendar.getInstance();
			/******************************开启事物*********************/
			beginDbService();
			
			//查找返利明细结果集
			List<Dcs2CtcaiVO> list = dao.getSI01Info();
			
			//对查找的结果集进行校验，封装组织成txt文件上传到服务器
			List<Dcs2CtcaiVO> dataList = new ArrayList<Dcs2CtcaiVO>();
			for(int i=0;i<list.size();i++){
				Dcs2CtcaiVO tempPO = new Dcs2CtcaiVO();
				tempPO = list.get(i);
				
				if(null==tempPO.getActionCode()||"".equals(tempPO.getActionCode())){
					logger.info("============="+tempPO.getSequenceId()+"的交易代码为空！=================");
					continue;
				}else if(tempPO.getActionCode().length()!=4){
					logger.info("============="+tempPO.getSequenceId()+"的交易代码长度与接口定义不一致！=================");
					continue;
				}
				
				/*if(null==tempPO.getActionDate()||"".equals(tempPO.getActionDate())){
					logger.info("============="+tempPO.getSequenceId()+"的交易日期为空！=================");
					continue;
				}else if(tempPO.getActionDate().length()!=8){
					logger.info("============="+tempPO.getSequenceId()+"的交易日期长度与接口定义不一致！=================");
					continue;
				}
				
				if(null==tempPO.getActionTime()||"".equals(tempPO.getActionTime())){
					logger.info("============="+tempPO.getSequenceId()+"的交易时间为空！=================");
					continue;
				}else if(tempPO.getActionTime().length()!=6){
					logger.info("============="+tempPO.getSequenceId()+"的交易时间长度与接口定义不一致！=================");
					continue;
				}
				
				if(null==tempPO.getDealerCode()||"".equals(tempPO.getDealerCode())){
					logger.info("============="+tempPO.getSequenceId()+"的经销商代码为空！=================");
					continue;
				}else if(tempPO.getDealerCode().length()>10){
					logger.info("============="+tempPO.getSequenceId()+"的经销商代码长度与接口定义不一致！=================");
					continue;
				}
				
				if(null==tempPO.getRebateCode()||"".equals(tempPO.getRebateCode())){
					logger.info("============="+tempPO.getSequenceId()+"的返利代码为空！=================");
					continue;
				}else if(tempPO.getRebateCode().length()>8){
					logger.info("============="+tempPO.getSequenceId()+"的返利代码长度与接口定义不一致！=================");
					continue;
				}
				
				if(null==tempPO.getRebateType()||"".equals(tempPO.getRebateType())){
					logger.info("============="+tempPO.getSequenceId()+"的返利类型为空！=================");
					continue;
				}else if(tempPO.getRebateType().length()>20){
					logger.info("============="+tempPO.getSequenceId()+"的返利类型长度与接口定义不一致！=================");
					continue;
				}
				
				if(null==tempPO.getRebateAmount()||"".equals(tempPO.getRebateAmount())){
					logger.info("============="+tempPO.getSequenceId()+"的返利金额为空！=================");
					continue;
				}else if(tempPO.getRebateAmount().length()>10){
					logger.info("============="+tempPO.getSequenceId()+"的返利金额长度与接口定义不一致！=================");
					continue;
				}
				
				if(null==tempPO.getOperator()||"".equals(tempPO.getOperator())){
					logger.info("============="+tempPO.getSequenceId()+"的操作人为空！=================");
					continue;
				}else if(tempPO.getOperator().length()>20){
					logger.info("============="+tempPO.getSequenceId()+"的操作人长度与接口定义不一致！=================");
					continue;
				}
				
				if(tempPO.getRemark().length()>100){
					logger.info("============="+tempPO.getSequenceId()+"的备注长度与接口定义不一致！=================");
					continue;
				}*/
				
				//逐个消息检查通过修改扫描状态，同时重新封装list
				
				TiRebatePO PO2 = TiRebatePO.findById(tempPO.getSequenceId());
				PO2.setString("SCAN_STATUS","1");
				PO2.setLong("UPDATE_BY",new Long(80000001));
				PO2.setTimestamp("UPDATE_DATE",sysDate.getTime());
				PO2.saveIt();
				dataList.add(tempPO);
			}
			
			if(dataList.size()>0){
				//将整理后的list封装成文件
				CTCAIParse01 parse = new CTCAIParse01();
				int flag = parse.writeTxt("FLRT",dataList);
				if(flag == 0){
					logger.info("=============文件封装成功=================");
				}else if(flag ==1){
					logger.info("=============文件封装失败=================");
				}
				
				//将对应目录下的文件备份到备份文件夹
				File srcFolder=new File("E:/Interface/ctcai/in/FLRT");
				File[] filesList=srcFolder.listFiles();  //获取文件夹下面的所有文件
				for(int j=0;j<filesList.length;j++){
					//剔除包含备份文件夹的结果集
					if(filesList[j].getName().equals("Archive")){
						continue;
					}else{
						rdt.copyFile("E:/Interface/ctcai/in/FLRT/"+filesList[j].getName(), "E:/Interface/ctcai/in/FLRT/Archive/"+filesList[j].getName());
					}
				}
			};
			dbService.endTxn(true);
			/******************************结束事物*********************/
		}catch (Throwable e){
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		}finally{
			logger.info("====SI03 is finish====");
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
		// TODO Auto-generated method stub
//		ContextUtil.loadConf();
		SI03Impl action = new SI03Impl();
		action.execute();
	}
}
