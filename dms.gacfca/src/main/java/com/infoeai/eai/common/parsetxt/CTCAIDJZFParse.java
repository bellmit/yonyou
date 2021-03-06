package com.infoeai.eai.common.parsetxt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.infoeai.eai.DTO.Ctcai2DcsDJZFDTO;
import com.infoeai.eai.DTO.Dcs2CtcaiDTO;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.TXTFactory;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.yonyou.dms.function.utils.common.CommonUtils;


public class CTCAIDJZFParse extends TXTFactory {
	public static Logger logger = Logger.getLogger(SICommonDao.class);
	ReadFileTxt rdt = new ReadFileTxt();
	/**
	 * 输入：filePath文件路径 解析CTCAI->DCS文件，定金支付信息
	 */
	@Override
	public List<Ctcai2DcsDJZFDTO> readTxt(String filePath,String childFile,String fileName) {
		
		rdt.copyFile(filePath, "E:/Interface/ctcai/out/DJZF/Archive/"+fileName);
		List<Ctcai2DcsDJZFDTO> dcp = new ArrayList<Ctcai2DcsDJZFDTO>();
		// 获取txt信息
		String str = rdt.readFile(filePath, "GBK");
		if (str != null && str.length() != -1) {
			// row获取txt行数
			String[] row = str.split("\n");
			try {
				// 获取数据从第2行开始，到倒数第2行
				for (int i = 1; i < row.length - 1; i++) {
					// 截取数据值
					String[] str2 = row[i].replace("\\", " , ").split(",");
					Ctcai2DcsDJZFDTO po = new Ctcai2DcsDJZFDTO();
					po.setActionCode(CommonUtils.checkNull(str2[0].trim()));
					po.setVin(CommonUtils.checkNull(str2[1].trim()));
					po.setActionDate(CommonUtils.checkNull(str2[2].trim()));
					po.setActionTime(CommonUtils.checkNull(str2[3].trim()));
					po.setDealerCode(CommonUtils.checkNull(str2[4].trim()));
					po.setIsPay(CommonUtils.checkNull(str2[5].trim()));
					dcp.add(po);
				}
			} catch (Exception e) {
				logger.info("txt内部数据错误,获取失败：请检查txt数据是否丢失！" + e);
				dcp = null;
			}
		} else {
			System.out.println("请检查txt文件是否有数据！！");
		}
		
		return dcp;
	}

	/**
	 * 输入：filePath文件路径 生产DCS->CTCAI文件，经销商返利明细导入 生产成功或失败：0-成功，1-失败
	 * Dcs2CtcaiPO.java
	 */
	@Override
	public int writeTxt(String Uname,List obj) {
		List<Dcs2CtcaiDTO> list = obj;
		StringBuffer str = null;
		CTCAIDJZFParse parse = new CTCAIDJZFParse();
		String fileName = parse.fileFullName(Uname);
		
		if(list.size()!=-1){	
			rdt.append(fileName, parse.timeFile(Uname), "GBK");//添加内容头信息
			for(Dcs2CtcaiDTO dcp:list){
				str = new StringBuffer();
				str.append(dcp.getActionCode()).append("\\");
				str.append(dcp.getActionDate()).append("\\").append(dcp.getActionTime()).append("\\");
				str.append(dcp.getDealerCode()).append("\\").append(dcp.getRebateType()).append("\\");
				str.append(dcp.getRebateCode()).append("\\").append(dcp.getRebateAmount()).append("\\");
				str.append(dcp.getOperator()).append("\\").append(dcp.getRemark());
				String log = str.toString();
				rdt.append(fileName, log, "GBK");
			}
			rdt.append(fileName, "TRL\\" + (list.size()+2), "GBK");//添加内容尾信息
		}else{
			return EAIConstant.WRITE_FAIL;
		}
		return EAIConstant.WRITE_SUCCESS;
	}
	
	/**
	 * fileFullName 文件全名, 包括全路径
	 * **/
	public  String fileFullName(String Uname){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//设置日期格式
		String fileFullDate = df.format(new Date());// new Date()为获取当前系统时间
		StringBuffer str = new StringBuffer();
		str.append("X:/Interface/ctcai/in/"+Uname+"/");
		str.append("IN").append(Uname).append(fileFullDate).append(".txt");
		return str.toString();
	}
	
	/**
	 * txt文件名拼接公共方法
	 * **/
	public  String timeFile(String Uname){
		SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df2 = new SimpleDateFormat("HHmmss");
		// new Date()为获取当前系统时间
		String contentTime = df.format(new Date());
		String contentTime2 = df2.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("DCS\\CTCAI\\").append(Uname).append("\\IN\\").append(contentTime).append("\\").append(contentTime2);
	    
		return str.toString();
	}

}
