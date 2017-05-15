package com.infoeai.eai.action.ncserp.transmit;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.ncserp.impl.ZPODSAPExecutor;
import com.infoeai.eai.action.ncserp.impl.ZRGSSAPExecutor;
import com.infoeai.eai.vo.SAPInboundVO;
import com.thoughtworks.xstream.XStream;

@Service
public class DCS2EAIServletImpl extends BaseService implements DCS2EAIServlet {
	private static final Logger logger = LoggerFactory.getLogger(DCS2EAIServletImpl.class);
	
	@Autowired
	ZRGSSAPExecutor zrgssap;
	@Autowired
	ZPODSAPExecutor zpodsap;
	//第二步：覆盖doGet()方法
	 public void doGet(ServletRequest request,
	  ServletResponse response)throws IOException,ServletException{
		//第三步：获取HTTP请求中的参数信息
		  String actionName=request.getParameter("actionName");
		  //actionName = request.getParameter("actionName");
		  if(actionName!=null)
			  actionName=new String(actionName.getBytes("ISO-8859-1"),"GB2312");
		  else
			  actionName="ZRGSSAPExecutor";//默认零售上报
		  logger.info("=================处理业务类："+actionName+"===================");
//		  Class executor;
		  Object obj = null;
		  List<SAPInboundVO> resultObj = null;
		  //自定义返回值类型
		try {
//			executor = Class.forName("com.infoeai.eai.action.ncserp.impl."+actionName);
			if("ZRGSSAPExecutor".equals(actionName)){
				resultObj = zrgssap.getInfo();
			}else if("ZPODSAPExecutor".equals(actionName)){
				resultObj = zpodsap.getInfo();
				
			}
//			obj = (Object) executor.newInstance();
			
			logger.info("执行的executor："+actionName);
//			Method mth = executor.getMethod("getInfo");
			
//			resultObj = mth.invoke(obj);
			logger.info("=================上传数据查询结束===================");
			
			XStream stream = new XStream();
			StringBuffer xmlOut = new StringBuffer("");
			
			if(resultObj instanceof List){
//			  List<SAPInboundVO> list = (List<SAPInboundVO>)resultObj;
			  logger.info("返回的Obj对象：" + obj + ";及其长度：" + resultObj.size());
			  if(resultObj !=null && resultObj.size() >0){
				xmlOut.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				xmlOut.append(stream.toXML(resultObj));
				logger.info("输出xml内容如下：\n" + xmlOut.toString());
			  }
			}
			
			//第四步：生成HTTP响应结果	
			response.setContentType("application/xml;charset=utf-8");
//			response.setCharacterEncoding("UTF-8");
			OutputStream out = response.getOutputStream();
			logger.info("=================执行"+actionName+",返回xmlOut输出："+xmlOut.toString()+"===================");
			out.write(xmlOut.toString().getBytes("UTF-8"));
			out.flush();
			out.close();
			//第五步：上传数据成功修改状态
			if("ZRGSSAPExecutor".equals(actionName)){
				resultObj = zrgssap.updateVoMethod(resultObj);
			}else if("ZPODSAPExecutor".equals(actionName)){
				resultObj = zpodsap.updateVoMethod(resultObj);
				
			}
//            Method updateMth = executor.getMethod("updateVoMethod",new Class[]{List.class});
//			resultObj = updateMth.invoke(obj,new Object[]{resultObj});
			logger.info("=================上传XML文件结束===================");
		}  catch (Throwable e) {
			logger.error("执行上报到SAP数据的Action出错："+actionName,e.getCause());
		}
		  

		
	 }
	 
	 public static void main(String[] args) {

	}
	 

	 public void doPost(ServletRequest request,
			  ServletResponse response)throws IOException,ServletException{
		 doGet(request, response);
	 } 
}
