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

import com.infoeai.eai.DTO.Dcs2Ctcai02DTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.parsetxt.DDCXImportParse;
import com.infoeai.eai.dao.ctcai.SI31Dao;
import com.yonyou.dms.common.domains.PO.basedata.TiFutureorderCancelImportDcsPO;

/**
 * FTP
 * DCS->CTCAI
 * 撤销未支付全款的期货订单
 * @author luoyang
 *
 */
@Service
public class SI31Impl extends BaseService implements SI31 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI31Impl.class);
	Calendar sysDate = Calendar.getInstance();
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Autowired
	SI31Dao dao;

	@Override
	public String execute() throws Exception {
		logger.info("====SI31 is begin====");
		try {
			/******************************开启事物*********************/
			beginDbService();
			
			List<Dcs2Ctcai02DTO> list_ddcx = SI31Dao.getSI31Info("DDCX");  //QHDD
			if(list_ddcx != null && list_ddcx.size()>0){
				doMethod(list_ddcx, "DDCX");
			}
			
			dbService.endTxn(true);
			/******************************结束事物*********************/
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		} finally{
			logger.info("====SI31 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}

	private String doMethod(List<Dcs2Ctcai02DTO> list, String type) {
		try {
			//对查找的结果集进行校验，封装组织成TXT文件上传到服务器
			List<Dcs2Ctcai02DTO> dataList = new ArrayList<Dcs2Ctcai02DTO>();
			for(int i=0;i<list.size();i++){
				Dcs2Ctcai02DTO tempPO = new Dcs2Ctcai02DTO();
				tempPO = list.get(i);
				
				if(null==tempPO.getVin()||"".equals(tempPO.getVin())){
					logger.info("============="+tempPO.getSequenceId()+"的VIN码为空！=================");
					continue;
				}else if(tempPO.getVin().length()>17){
					logger.info("============="+tempPO.getSequenceId()+"的VIN码长度与接口定义不一致！=================");
					continue;
				}
				
				//逐个消息检查通过修改扫描状态，同时重新封装list
				TiFutureorderCancelImportDcsPO.update("IS_SCAN = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "SEQUENCE_ID = ?", 
						"1",new Long(80000002),sysDate.getTime(),tempPO.getSequenceId());
				
				dataList.add(tempPO);
			}
			if(dataList.size()>0){
				DDCXImportParse parse = new DDCXImportParse();
				//将整理后的list封装成文件
				if("DDCX".equals(type)){
					parse.writeTxt("DDCX",dataList);//将接口对象写入TXT,该文件的生成应该慢10秒钟。
					
					//将对应目录下的文件备份到备份文件夹
					File srcFolder=new File("E:/Interface/ctcai/in/DDCX");
					File[] filesList=srcFolder.listFiles();  //获取文件夹下面的所有文件
					for(int j=0;j<filesList.length;j++){
						//剔除包含备份文件夹的结果集
						if(filesList[j].getName().equals("Archive")){
							continue;
						}else{
							rdt.copyFile("E:/Interface/ctcai/in/DDCX/"+filesList[j].getName(), "E:/Interface/ctcai/in/DDCX/Archive/"+filesList[j].getName());
						}
					}
				}
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
