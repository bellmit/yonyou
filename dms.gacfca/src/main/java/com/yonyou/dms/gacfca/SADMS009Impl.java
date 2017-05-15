package com.yonyou.dms.gacfca;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SA009Dto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：发票多次扫描上报
 * 
 * @author Benzc
 * @date 2017年1月13日
 * 
 */
@Service
public class SADMS009Impl implements SADMS009{

	@Override
	public LinkedList<SA009Dto> getSADMS009() throws ServiceBizException {
		LinkedList<SA009Dto> resultList = new LinkedList<SA009Dto>();
		try{
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			String fileName=null;
			InputStream inputS=null;
			String input=null;
			
			SA009Dto dto = new SA009Dto();
			fileName="20170113.txt";
			File file = new File("D:"+File.separator+"saomiao"+File.separator+"11.jpg"); 
			
			inputS = new FileInputStream(file);
			inputS = new FileInputStream(file);
			InputStream inputS2=null;
			inputS2 = new FileInputStream(file);
			
//			下面	 这些是把这个输入流 转成输出保存在D盘的aa文件
			
			byte[] buf = new byte[2048];
			int len = -1;
			FileOutputStream out= new FileOutputStream("D:"+File.separator+"11.jpg");
			while ((len = inputS.read(buf)) != -1) {
				out.write(buf, 0, len);
				out.flush();
			}
			if(out!=null)
			out.close();
			
			if (inputS2 != null){
				int le = -1;
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				while ((le = inputS2.read(buf)) != -1) {
			 		outStream.write(buf, 0, le);
			 		outStream.flush();
				}//以这种格式 转成string  利用下ByteArrayOutputStream
				input= new String(outStream.toByteArray(),"ISO-8859-1");
				InputStream inputS3 = new ByteArrayInputStream(input.getBytes(("ISO-8859-1")));
				FileOutputStream out2= new FileOutputStream("D:"+File.separator+"12.jpg");
				while ((len = inputS3.read(buf)) != -1) {
					out2.write(buf, 0, len);
					out2.flush();
				}
				if(out2!=null)
					out2.close();
				dto.setDealerCode(dealerCode);
				dto.setFileName(fileName);
				dto.setInput(input);
				resultList.add(dto);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
		
		
	}

}
