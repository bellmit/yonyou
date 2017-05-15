package com.infoeai.eai.common.parsetxt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.infoeai.eai.DTO.Dcs2Ctcai02DTO;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.TXTFactory;
import com.infoeai.eai.dao.ctcai.SICommonDao;

public class DDCXImportParse extends TXTFactory {
	public static Logger logger = Logger.getLogger(SICommonDao.class);
	ReadFileTxt rdt = new ReadFileTxt();


	/**
	 * 输入：filePath文件路径 生产DCS->SWT文件，销售订单导入(节点状态2个,ZRL1和ZDRR) 生产成功或失败：0-成功，1-失败
	 * Dcs2SwtPO.java
	 */
	@Override
	public int writeTxt(String Uname, List obj) {
		List<Dcs2Ctcai02DTO> list = obj;
		DDCXImportParse parse =  new DDCXImportParse();
		String fileName = parse.fileFullName(Uname);
		StringBuffer str = null;
		if (list.size() != -1) {
			rdt.append(fileName, parse.timeFile(Uname), "GBK");// 添加内容头信息
			for (Dcs2Ctcai02DTO dcp : list) {
				str = new StringBuffer();
				str.append(dcp.getActionCode()).append("\\");
				str.append(dcp.getVin()).append("\\");;
				str.append(dcp.getDealerCode()).append("\\");
				str.append(dcp.getActionDate()).append("\\");
				str.append(dcp.getActionTime()).append("\\");;
				str.append(dcp.getModel()).append("\\");;
				String log = str.toString();
				rdt.append(fileName, log, "GBK");
			}
			rdt.append(fileName, "TRL\\" + (list.size()+2), "GBK");// 添加内容尾信息
		} else {
			return EAIConstant.WRITE_FAIL;
		}

		return EAIConstant.WRITE_SUCCESS;
	}
	
	SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat df2 = new SimpleDateFormat("HHmmss");
	
	/**
	 * fileFullName 文件全名, 包括全路径
	 * **/
	public  String fileFullName(String Uname){
		String contentTime = df.format(new Date());
		String contentTime2 = df2.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("E:/Interface/ctcai/in/"+Uname+"/");
		str.append("IN").append(Uname).append(contentTime).append(contentTime2).append(".txt");
		return str.toString();
	}
	
	/**
	 * txt文件名拼接公共方法
	 * **/
	public  String timeFile(String Uname){
		// new Date()为获取当前系统时间
		String contentTime = df.format(new Date());
		String contentTime2 = df2.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("DCS\\CTCAI\\").append(Uname).append("\\IN\\").append(contentTime).append("\\").append(contentTime2);
		return str.toString();
	}

	@Override
	protected List readTxt(String filePath, String childFile, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
