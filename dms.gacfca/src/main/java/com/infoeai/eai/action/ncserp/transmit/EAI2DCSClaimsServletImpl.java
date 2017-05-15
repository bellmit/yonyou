package com.infoeai.eai.action.ncserp.transmit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.ncserp.SI01v2;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.parsexml.DocumentXml;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

@Service
public class EAI2DCSClaimsServletImpl extends BaseService implements EAI2DCSClaimsServlet {
	private static Logger logger = LoggerFactory.getLogger(EAI2DCSClaimsServletImpl.class);
	//第二步：覆盖doGet()方法
	 public void doGet(ServletRequest request,
	  ServletResponse response)throws IOException,ServletException{
		//第三步：获取HTTP请求中的参数信息
		 String returnResult = EAIConstant.DEAL_FAIL;
	
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
				logger.info("=========EAI传输的XML文件======>>" + new String(baos.toByteArray(),"UTF-8"));
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				DocumentXml docXML = new DocumentXml();
				//List<Map<String, String>> xmlList= docXML.parserXml(bais);
				Document doc= docXML.getDocument(bais);
					SI01v2 si01 = (SI01v2)ApplicationContextHelper.getBeanByType(SI01v2.class);
					if(doc != null){
						returnResult = si01.setClaimsXMLToVO(doc);
					}
					logger.info("===========业务数据操作返回结果： " + returnResult );

			}catch (Throwable t) {
				logger.info("===========返回Servlet结果状态：========= " + returnResult );
				t.printStackTrace();
			}finally{
				if(baos != null){
					baos.close();
				}
				if(in != null){
					in.close();
				}
			}
		 
		  
		//生成HTTP响应结果	
			
		response.setContentType("application/xml;charset=utf-8");
//		response.setCharacterEncoding("UTF-8");
		OutputStream out = response.getOutputStream();
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> <item> <ReturnResult>").append(returnResult).toString();
		result.append(" </ReturnResult></item>");
		out.write(result.toString().getBytes("UTF-8"));
		logger.info("===========返回结果状态：========= " + result );
		out.flush();
		out.close();
		
	 }
	 
	 public static void main(String[] args) throws  IOException {
	 File  xmlFile = new File("D:/doc.xml");  
		 String returnResult = EAIConstant.DEAL_FAIL;
		try {
			
			
			
			DocumentXml  docXml = new DocumentXml();
			List<Map<String, String>> xmlList = docXml.parserXml(xmlFile);
			logger.info("===========返回结果：========= " + xmlList.toString() );
			if (xmlList != null && xmlList.size()>0){
				SI01v2 si01 = (SI01v2)ApplicationContextHelper.getBeanByType(SI01v2.class);
			    returnResult = si01.setXMLToVO(xmlList);
				logger.info("===========业务数据操作返回结果： " + returnResult );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
           

	}
	 public void doPost(ServletRequest request,
			  ServletResponse response)throws IOException,ServletException{
		 doGet(request, response);
	 } 
	 
}
