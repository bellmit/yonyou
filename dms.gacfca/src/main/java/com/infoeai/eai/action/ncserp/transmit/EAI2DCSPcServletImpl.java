package com.infoeai.eai.action.ncserp.transmit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.ncserp.impl.PCExecutorImpl;
import com.infoeai.eai.common.EAIConstant;

@Service
public class EAI2DCSPcServletImpl extends BaseService implements EAI2DCSPcServlet {
	private static Logger logger = LoggerFactory.getLogger(EAI2DCSPcServletImpl.class);

	//第二步：覆盖doGet()方法
	 public void doGet(ServletRequest request,
	  ServletResponse response)throws IOException,ServletException{
		logger.info("========进入EAI2DCSPcServlet.doGet方法=======>>");
		//String reMapping = request.getParameter("RE_MAPPING");
		//if(reMapping == null || !reMapping.equalsIgnoreCase("RE_MAPPING")){
		//第三步：获取HTTP请求中的参数信息
		 String returnResult = EAIConstant.DEAL_FAIL;
		 PCExecutorImpl pcImpl = null;
		
			ByteArrayOutputStream baos = null;
			InputStream in = null;
			try{
				in = request.getInputStream();
				byte[] temp = new byte[1024];
				baos = new ByteArrayOutputStream();
				int count = 0;
				while(true) {
					count = in.read(temp);
					if(count == -1) {
						break;
					}
					baos.write(temp, 0, count);
				}
				logger.info("=========EAI传输的TXT文件======>>" + new String(baos.toByteArray(),"UTF-8"));
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				InputStreamReader isr = new InputStreamReader(bais);
				BufferedReader br = new BufferedReader(isr);
				pcImpl = new PCExecutorImpl();
	            String row = null;
	 			beginDbService();
				logger.info("====================传输数据事物创建===================");
	             while ((row = br.readLine()) != null) {
	            	 String[] rowData = row.replace("|", " @ ").split("@");
	            	 returnResult = pcImpl.storePcData(rowData,0L);
		            logger.info("===========解析每行传输数据："+rowData[0]+"======= " + rowData );
				  }
	 			dbService.endTxn(true);
				logger.info("====================传输数据事物提交===================");
				logger.info("===========业务数据操作返回结果： " + returnResult );
				
			}catch (Throwable t) {
				returnResult = EAIConstant.DEAL_FAIL;
				logger.error("===========返回Servlet结果状态：========= " + returnResult ,t);
				try {
					dbService.endTxn(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}finally{
				dbService.clean();
				if(baos != null){
					baos.close();
				}
				if(in != null){
					in.close();
				}
			}
			
			if(EAIConstant.DEAL_SUCCESS.equals(returnResult)){
				//开始PC映射DCS
				Thread pcMappingThread = new PCMappingThread("PCMappingThread");
				pcMappingThread.start();
			}
			
			OutputStream out = null;
			try{
				//生成HTTP响应结果
				response.setContentType("application/xml;charset=utf-8");
//				response.setCharacterEncoding("UTF-8");
				out = response.getOutputStream();
				StringBuffer result = new StringBuffer();
				result.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> <item> <ReturnResult>").append(returnResult).toString();
				result.append(" </ReturnResult></item>");
				out.write(result.toString().getBytes("UTF-8"));
				logger.info("===========返回结果状态：========= " + result );
				out.flush();
			}catch (Throwable t) {
				logger.error("EAI2DCS PC interface return error.",t);
			}finally{
				if(out != null){
					out.close();
				}
			}
/*		}else{
		logger.info("====================手工调用RE_MAPPING程序开始...===================");
			//开始PC映射DCS
		mapping();
		response.setContentType("application/xml;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		OutputStream out = response.getOutputStream();
		out.write("重新执行PC <-> DCS Mapping 成功.".getBytes("UTF-8"));
		out.flush();
		out.close();
		}*/
		
	 }

	 public void doPost(ServletRequest request,
			  ServletResponse response)throws IOException,ServletException{
		 doGet(request, response);
	 } 
	 
}
