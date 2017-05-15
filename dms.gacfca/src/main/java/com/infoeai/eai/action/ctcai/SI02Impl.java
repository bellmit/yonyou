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

import com.infoeai.eai.DTO.Dcs2SwtDTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.parsetxt.SWTParse01;
import com.infoeai.eai.dao.ctcai.SI02Dao;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesorderImportPO;

/**
 * FTP
 * DCS->CTCAI
 * 销售订单信息导入-ZDRR
 * @author luoyang
 *
 */
@Service
public class SI02Impl extends BaseService implements SI02  {
	
	private static final Logger logger = LoggerFactory.getLogger(SI02Impl.class);
	Calendar sysDate = Calendar.getInstance();
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Autowired
	SI02Dao dao;

	@Override
	public String execute() throws Exception {
		logger.info("====SI02 is begin====");
		String si02FileName=null;
		try {
			/******************************开启事物*********************/
			beginDbService();
			
			List<Dcs2SwtDTO> list_zdrr = dao.getSI02Info("ZDRR"); //ZDRR
			if(list_zdrr != null && !list_zdrr.isEmpty()){
				si02FileName = doMethod(list_zdrr, "ZDRR");
			}
			
			dbService.endTxn(true);
			/******************************结束事物*********************/
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
			
			//如果发生异常，数据库回滚，删除si02.doMethod生成的文件
			if(si02FileName!=null && si02FileName.length()>0)
			{
				rdt.DelFile(si02FileName);
			}
		} finally {
			logger.info("====SI02 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}

	/**
	 * 根据传递的类型
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	private String doMethod(List<Dcs2SwtDTO> list, String type) {
		String genFileName=null;
		//对查找的结果集进行校验，封装组织成TXT文件上传到服务器
		List<Dcs2SwtDTO> dataList = new ArrayList<Dcs2SwtDTO>();
		for(int i=0;i<list.size();i++){
			Dcs2SwtDTO tempPO = new Dcs2SwtDTO();
			tempPO = list.get(i);
			
			if(null==tempPO.getVin()||"".equals(tempPO.getVin())){
				logger.info("============="+tempPO.getSequenceId()+"的VIN码为空！=================");
				continue;
			}else if(tempPO.getVin().length()>17){
				logger.info("============="+tempPO.getSequenceId()+"的VIN码长度与接口定义不一致！=================");
				continue;
			}			
			//逐个消息检查通过修改扫描状态，同时重新封装list
			TiSalesorderImportPO.update("IS_SCAN = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "SEQUENCE_ID = ?", "1",new Long(80000002),sysDate.getTime(),tempPO.getSequenceId());
			dataList.add(tempPO);
		}
		if(dataList.size()>0){
			SWTParse01 parse = new SWTParse01();
			//将整理后的list封装成文件
			if("ZDRR".equals(type)){
				parse.writeTxt("ZDRR",dataList);//将接口对象写入TXT,该文件的生成应该慢10秒钟。
				genFileName = parse.getFileName();
				//将对应目录下的文件备份到备份文件夹
				File srcFolder=new File("E:/Interface/ctcai/in/ZDRR-1");
				File[] filesList=srcFolder.listFiles();  //获取文件夹下面的所有文件
				for(int j=0;j<filesList.length;j++){
					//剔除包含备份文件夹的结果集
					if(filesList[j].getName().equals("Archive")){
						continue;
					}else{
						rdt.copyFile("E:/Interface/ctcai/in/ZDRR-1/"+filesList[j].getName(), "E:/Interface/ctcai/in/ZDRR-1/Archive/"+filesList[j].getName());
					}
				}
			}
		}
		return genFileName;
	}

}
