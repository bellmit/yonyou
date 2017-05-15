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
import com.infoeai.eai.common.parsetxt.QHDDImportParse;
import com.infoeai.eai.dao.ctcai.SI27Dao;
import com.infoeai.eai.po.TiFutureorderImportDcsPO;

/**
 * DCS->CTCAI
 * FTP
 * QHDD-期货订单同步至CATC系统
 * @author luoyang
 *
 */
@Service
public class SI27Impl extends BaseService implements SI27 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI27Impl.class);
	Calendar sysDate = Calendar.getInstance();
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Autowired
	SI27Dao dao;

	@Override
	public String execute() throws Exception {
		logger.info("====SI27 is begin====");		
		try {
			/******************************开启事物*********************/
			beginDbService();
			
			List<Dcs2Ctcai02DTO> list_qhdd = SI27Dao.getSI27Info("QHDD");  //QHDD
			if(list_qhdd != null && list_qhdd.size()>0){
				doMethod(list_qhdd, "QHDD");
			}
			
			dbService.endTxn(true);
			/******************************结束事物*********************/
		} catch (Throwable e){
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		} finally{
			logger.info("====SI27 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}

	/**
	 * 根据传递的类型
	 * @param type
	 * @return
	 */
	private void doMethod(List<Dcs2Ctcai02DTO> list, String type) {
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
			TiFutureorderImportDcsPO.update("IS_SCAN = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "SEQUENCE_ID = ?", "1",new Long(80000002),sysDate.getTime(),tempPO.getSequenceId());
			dataList.add(tempPO);
			if(dataList.size()>0){
				QHDDImportParse parse = new QHDDImportParse();
				//将整理后的list封装成文件
				if("QHDD".equals(type)){
					parse.writeTxt("QHDD",dataList);//将接口对象写入TXT,该文件的生成应该慢10秒钟。
					
					//将对应目录下的文件备份到备份文件夹
					File srcFolder=new File("E:/Interface/ctcai/in/QHDD");
					File[] filesList=srcFolder.listFiles();  //获取文件夹下面的所有文件
					for(int j=0;j<filesList.length;j++){
						//剔除包含备份文件夹的结果集
						if(filesList[j].getName().equals("Archive")){
							continue;
						}else{
							rdt.copyFile("E:/Interface/ctcai/in/QHDD/"+filesList[j].getName(), "E:/Interface/ctcai/in/QHDD/Archive/"+filesList[j].getName());
						}
					}
				}
			}
		}
		
	}

}
