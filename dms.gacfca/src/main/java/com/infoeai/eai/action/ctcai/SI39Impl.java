package com.infoeai.eai.action.ctcai;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.DTO.SI39DTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.dao.ctcai.SI39Dao;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.ProOrderSerialPO;

/**
 * FTP
 * DCS->CTCAI
 * 生产订单
 * @author luoyang
 *
 */
@Service
public class SI39Impl extends BaseService implements SI39 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI39Impl.class);
	Calendar sysDate = Calendar.getInstance();
	ReadFileTxt rdt = new ReadFileTxt();
	private String fileName;	
	SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat df2 = new SimpleDateFormat("HHmmss");
	
	@Autowired
	SI39Dao dao;
	
	@Override
	public String execute() throws Exception {
		logger.info("====SI39 is begin====");
		String si39FileName=null;
		try {
			/******************************开启事物*********************/
			beginDbService();
			
			List<SI39DTO> list = SI39Dao.getSI39Info(); 
			if(list.size()>0){
				si39FileName = doMethod(list);
			}
			
			dbService.endTxn(true);
			/******************************结束事物*********************/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
			//如果发生异常，数据库回滚，删除si39.doMethod生成的文件
			if(si39FileName!=null && si39FileName.length()>0)
			{
				rdt.DelFile(si39FileName);
			}
		} finally {
			logger.info("====SI39 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}

	/**
	 * 数据进行处理
	 * @param list
	 * @return
	 */
	private String doMethod(List<SI39DTO> list) {
		String genFileName=null;
		genFileName=getFileName();
		int flag = writeTxt("SCXLH",list);
		if (flag==0) {
			//将对应目录下的文件备份到备份文件夹
			File srcFolder=new File("E:/Interface/ctcai/in/SCXLH");
			File[] filesList=srcFolder.listFiles();  //获取文件夹下面的所有文件
			for(int j=0;j<filesList.length;j++){
				//剔除包含备份文件夹的结果集
				if(filesList[j].getName().equals("Archive")){
					continue;
				}else{
					rdt.copyFile("E:/Interface/ctcai/in/SCXLH/"+filesList[j].getName(), "E:/Interface/ctcai/in/SCXLH/Archive/"+filesList[j].getName());
				}
			}
			//IsSend 修改成1
			for (int i = 0;i<list.size();i++) {
				ProOrderSerialPO.update("IS_SEND = ?", "ORDER_SERIAL_NUMBER_ID = ?", 1,list.get(i).getOrderSerialNumberId());
			}
		}
		return genFileName;
	}
	
	/**
	 * 生成文件
	 * @param list
	 */
	private int writeTxt(String Uname, List<SI39DTO> list) {
		String fileName = fileFullName(Uname);
		StringBuffer str = null;
		if (list.size() != -1) {
			rdt.append(fileName,timeFile(Uname), "GBK");// 添加内容头信息
			for (SI39DTO si39 : list) {
				str = new StringBuffer();
				str.append(si39.getSerialNumber()).append("\\");
				str.append(si39.getTaskId()).append("\\");
				str.append(si39.getCreateDate()).append("\\");
				str.append(si39.getCtcaiCode()).append("\\");
				str.append(si39.getCompanyName()).append("\\");
				str.append(si39.getForecastYear()).append("\\");
				if (Integer.parseInt(si39.getForecastMonth())<10) {
					str.append("0"+si39.getForecastMonth()).append("\\");
				} else {
					str.append(si39.getForecastMonth()).append("\\");
				}
				str.append(si39.getModelCode()).append("\\");
				str.append(si39.getColorCode()).append("\\");
				str.append(si39.getTrimCode());
				String log = str.toString();
				rdt.append(fileName, log, "GBK");
				this.setFileName(fileName);
			}
			rdt.append(fileName, "TRL\\" + (list.size()+2), "GBK");// 添加内容尾信息
		}else {
			return EAIConstant.WRITE_FAIL;
		}
		return EAIConstant.WRITE_SUCCESS;
	}

	/**
	 * txt文件名拼接公共方法
	 * **/
	private String timeFile(String Uname) {
		// new Date()为获取当前系统时间
		String contentTime = df.format(new Date());
		String contentTime2 = df2.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("DCS\\CTCAI\\").append("IN\\"+Uname).append("\\").append(contentTime).append("\\").append(contentTime2);
		return str.toString();
	}

	/**
	 * fileFullName 文件全名, 包括全路径
	 * **/
	private String fileFullName(String Uname) {
		String contentTime = df.format(new Date());
		String contentTime2 = df2.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("E:/Interface/ctcai/in/"+Uname+"/");
		str.append("INDCS").append(Uname+"").append(contentTime).append(contentTime2).append(".txt");
		return str.toString();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
