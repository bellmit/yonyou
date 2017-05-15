package com.infoeai.eai.action.ctcai;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.DTO.Ctcai2Dcs02DTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.parsetxt.CTCAIParse02;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.infoeai.eai.po.TtVsRebateEmployPO;

/**
 * FTP
 * CTCAI->DCS
 * 红票返利使用结果回传
 * @author luoyang
 *
 */
@Service
public class SI05Impl extends BaseService implements SI05 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI05Impl.class);	
	String srcPath="E:/Interface/ctcai/out/RTUR";      //接口对应的文件路径
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Autowired
	SICommonDao sidao;
	
	@Override
	public String execute() throws Exception {
		logger.info("====SI05 is begin====");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Calendar sysdate = Calendar.getInstance();
		CTCAIParse02 parse = new CTCAIParse02();
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
					List<Ctcai2Dcs02DTO> list = parse.readTxt(srcPath+"/"+filesList[j].getName(),"",filesList[j].getName());   //需要补充循环
					//对接收的PO集合进行处理
					for(int i=0;i<list.size();i++){
						Ctcai2Dcs02DTO tempPO = new Ctcai2Dcs02DTO();
						tempPO = list.get(i);
						if(null==tempPO.getActionCode()||"".equals(tempPO.getActionCode())){
							logger.info("=============交易代码为空！=================");
							continue;
						}else if(!"RTUR".equals(tempPO.getActionCode())){
							logger.info("=============交易代码与定义不匹配！=================");
							continue;
						}
						
						if(null==tempPO.getActionDate()||"".equals(tempPO.getActionDate())){
							logger.info("=============交易日期为空！=================");
							continue;
						}else if(tempPO.getActionDate().length()!=8){
							logger.info("=============交易日期长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getActionTime()||"".equals(tempPO.getActionTime())){
							logger.info("=============交易时间为空！=================");
							continue;
						}else if(tempPO.getActionTime().length()!=6){
							logger.info("=============交易时间长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getDealerCode()||"".equals(tempPO.getDealerCode())){
							logger.info("=============经销商代码为空！=================");
							continue;
						}else if(tempPO.getDealerCode().length()>10){
							logger.info("=============经销商代码长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getRedTicketNumber()||"".equals(tempPO.getRedTicketNumber())){
							logger.info("=============红票号为空！=================");
							continue;
						}else if(tempPO.getRedTicketNumber().length()>10){
							logger.info("=============红票号长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getRedTicketAmount()||"".equals(tempPO.getRedTicketAmount())){
							logger.info("=============红票金额为空！=================");
							continue;
						}else if(tempPO.getRedTicketAmount().length()>10){
							logger.info("=============红票金额长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getRebateCode()||"".equals(tempPO.getRebateCode())){
							logger.info("=============返利代码为空！=================");
							continue;
						}else if(tempPO.getRebateCode().length()>8){
							logger.info("=============返利代码长度与接口定义不一致！=================");
							continue;
						}
						
						if(null==tempPO.getUseDate()||"".equals(tempPO.getUseDate())){
							logger.info("=============返利使用日期为空！=================");
							continue;
						}else if(tempPO.getUseDate().length()>10){
							logger.info("=============返利使用日期长度与接口定义不一致！=================");
							continue;
						}
						
						if(tempPO.getRemark().length()>100){
							logger.info("=============备注长度与接口定义不一致！=================");
							continue;
						}
						
						//对满足要求的记录插入到业务表
						TtVsRebateEmployPO PO1 = new TtVsRebateEmployPO();
						String dealerCode = sidao.getDealerCodeByInfo( tempPO.getDealerCode(),"ctcai");
						PO1.setString("DEALER_CODE", dealerCode);
						PO1.setString("REBATE_CODE", tempPO.getRebateCode());
						PO1.setString("TICKET_NO", tempPO.getRedTicketNumber());
						PO1.setString("TICKET_AMOUNT", tempPO.getRedTicketNumber());
						PO1.setTimestamp("REBATE_EMPLOY_DATE", sdf1.parse(tempPO.getUseDate()) );
						PO1.setLong("CREATE_BY", new Long(80000001));
						PO1.setTimestamp("CREATE_DATE", sysdate.getTime());
						PO1.setString("REMARK", tempPO.getRemark());
						boolean flag = PO1.insert();
					}
					rdt.DelFile(srcPath+"/"+filesList[j].getName());
				}
			}
			
			dbService.endTxn(true);
			/******************************结束事物*********************/		
		} catch (ParseException e){
			e.printStackTrace();
		} catch (Throwable e){
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		} finally{
			logger.info("====SI05 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}
	

}
