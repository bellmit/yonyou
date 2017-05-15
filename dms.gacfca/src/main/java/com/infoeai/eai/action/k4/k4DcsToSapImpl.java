package com.infoeai.eai.action.k4;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.vo.S0002XmlVO;
import com.infoeai.eai.vo.S0008XmlVO;
import com.infoeai.eai.vo.S0009XmlVO;
import com.thoughtworks.xstream.XStream;

@Service
public class k4DcsToSapImpl extends BaseService implements k4DcsToSap {
	private static final Logger logger = LoggerFactory.getLogger(k4DcsToSapImpl.class);

	@Autowired
	S0008 s0008;
	@Autowired
	S0009 s0009;
	
	@Override
	public void doGet(ServletRequest request, ServletResponse response) throws IOException, ServletException {

		// 第三步：获取HTTP请求中的参数信息
		String actionName = request.getParameter("actionName");

		if (null != actionName && actionName.length() > 0) {

			actionName = new String(actionName.getBytes("ISO-8859-1"), "GB2312");

			logger.info("========== 处理业务类：" + actionName + " ==========");

//			Class<?> executor;
			Object obj = null;
			List resultObj = null;
			actionName = actionName.toUpperCase();
			// 自定义返回值类型
			try {
				if ("S0008".equals(actionName)) {
					resultObj = s0008.getInfo();
				} else if ("S0009".equals(actionName)) {
					resultObj = s0009.getInfo();
				}
//				executor = Class.forName("com.infoeai.eai.action.k4." + actionName);
//				obj = (Object) executor.newInstance();

				logger.info("执行的executor：" + actionName);
//				Method mth = executor.getMethod("getInfo");

//				resultObj = mth.invoke(obj);
				logger.info("========== 上传数据查询结束 ==========");

				StringBuffer xmlOut = new StringBuffer("");
				if (null != resultObj) {
					xmlOut = getXmlOut(resultObj, actionName);
				}

				// 第四步：生成HTTP响应结果
				response.setContentType("application/xml;charset=utf-8");
//				((ServletRequest) response).setCharacterEncoding("UTF-8");
				OutputStream out = response.getOutputStream();
				logger.info("========== 执行" + actionName + ",返回xmlOut输出：" + xmlOut.toString() + " ==========");
				out.write(xmlOut.toString().getBytes("UTF-8"));
				out.flush();
				out.close();
				// 第五步：上传数据成功修改状态
				if("S0008".equals(actionName)){
					resultObj = s0008.updateVoMethod(resultObj);
				}else if("S0009".equals(actionName)){
					resultObj = s0009.updateVoMethod(resultObj);
				}
				logger.info("========== 上传XML文件结束 ==========");
			} catch (Throwable e) {
				logger.error("执行上报到SAP数据的Action出错：" + actionName, e.getCause());
			}
		} else {
			// 第四步：生成HTTP响应结果
			response.setContentType("application/xml;charset=utf-8");
//			((ServletRequest) response).setCharacterEncoding("UTF-8");
			OutputStream out = response.getOutputStream();
			StringBuffer xmlOut = new StringBuffer("");
			xmlOut.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			xmlOut.append("<IF_SEND>");
			xmlOut.append("<INTERFACE_CODE>");
			xmlOut.append("接口名称返回为空");
			xmlOut.append("</INTERFACE_CODE>");
			xmlOut.append("</IF_SEND>");
			logger.info("========== 执行" + actionName + ",返回xmlOut输出：" + xmlOut.toString() + " ==========");
			out.write(xmlOut.toString().getBytes("UTF-8"));
			out.flush();
			out.close();

		}

	}

	private StringBuffer getXmlOut(Object resultObj, String actionName) {
		StringBuffer xmlOut = new StringBuffer("");
		String strOut = "";
		StringBuffer voOut = new StringBuffer("");
		if (null != resultObj) {
			XStream stream = new XStream();
			xmlOut.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			xmlOut.append("<IF_SEND>");
			xmlOut.append("<INTERFACE_CODE>");
			xmlOut.append(actionName);
			xmlOut.append("</INTERFACE_CODE>");
			if (resultObj instanceof List) {
				if ("S0002".equals(actionName)) {
					List<S0002XmlVO> list = (List<S0002XmlVO>) resultObj;
					logger.info("返回的Obj对象：" + actionName + ";及其长度：" + list.size());
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							S0002XmlVO s2Xml = list.get(i);
							// xmlOut.append("<ITEM>");
							voOut.append(stream.toXML(s2Xml));
							// xmlOut.append("</ITEM>");
						}
					}
					strOut = voOut.toString().replaceAll("<com.infoeai.eai.vo.S0002XmlVO>", "<ITEM>")
							.replaceAll("</com.infoeai.eai.vo.S0002XmlVO>", "</ITEM>");
				}
				if ("S0008".equals(actionName)) {
					List<S0008XmlVO> list = (List<S0008XmlVO>) resultObj;
					logger.info("返回的Obj对象：" + actionName + ";及其长度：" + list.size());
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							S0008XmlVO s8Xml = list.get(i);
							// xmlOut.append("ITEM");
							voOut.append(stream.toXML(s8Xml));
							// xmlOut.append("/ITEM");
						}
					}
					strOut = voOut.toString().replaceAll("<com.infoeai.eai.vo.S0008XmlVO>", "<ITEM>")
							.replaceAll("</com.infoeai.eai.vo.S0008XmlVO>", "</ITEM>");
				}
				if ("S0009".equals(actionName)) {
					List<S0009XmlVO> list = (List<S0009XmlVO>) resultObj;
					logger.info("返回的Obj对象：" + actionName + ";及其长度：" + list.size());
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							S0009XmlVO s9Xml = list.get(i);
							// xmlOut.append("ITEM");
							voOut.append(stream.toXML(s9Xml));
							// xmlOut.append("/ITEM");
						}
					}
					strOut = voOut.toString().replaceAll("<com.infoeai.eai.vo.S0009XmlVO>", "<ITEM>")
							.replaceAll("</com.infoeai.eai.vo.S0009XmlVO>", "</ITEM>");
				}

			}
			xmlOut.append(strOut.replaceAll("__", "_"));
			xmlOut.append("</IF_SEND>");
			logger.info("输出xml内容如下：\n" + xmlOut.toString());
		}
		return xmlOut;
	}

	@Override
	public void doPost(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		doGet(request, response);

	}

}
