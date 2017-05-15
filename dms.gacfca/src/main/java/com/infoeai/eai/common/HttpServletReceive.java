package com.infoeai.eai.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infoeai.eai.action.boldseas.ob.ReceiveBoldseasMsg;
import com.infoeai.eai.action.k4.k4DcsToSapImpl;
import com.infoeai.eai.action.ncserp.SI01v2;
import com.infoeai.eai.action.ncserp.transmit.DCS2EAIServlet;
import com.infoeai.eai.action.ncserp.transmit.EAI2DCSClaimsServlet;
import com.infoeai.eai.action.ncserp.transmit.EAI2DCSPcServlet;
import com.infoeai.eai.action.ncserp.transmit.EAI2DCSServlet;
import com.infoeai.eai.action.saleOfTool.SOTDCSEAI001;
import com.infoeai.eai.action.saleOfTool.SOTDCSEAI002;
import com.infoeai.eai.action.saleOfTool.SOTDCSEAI003;
import com.infoeai.eai.action.saleOfTool.SOTEAIDCS001;
import com.infoeai.eai.action.saleOfTool.SOTEAIDCS002;
import com.infoeai.eai.action.saleOfTool.SOTEAIDCS003;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI001;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI002;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI003;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI004;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI005;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI006;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI007;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI008;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI009;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI010;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI013;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI014;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI015;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI016;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI017;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI018;
import com.infoeai.eai.action.saleOfTool.WSSOTEAI019;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({"serial","unchecked"})
public class HttpServletReceive extends GenericServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpServletReceive.class);
	
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		ResultMsgVO msg = new ResultMsgVO();
		logger.info("============http servlet 请求开始===============");
		try {
			HttpServletRequest  request = (HttpServletRequest) req;
			String path = request.getRequestURI();//获取路径
			logger.info("============http请求路径："+path);
			String method = path.substring(path.lastIndexOf("/")+1);
			logger.info("============http请求方法："+method);
			Map<String, String> messageMap = autoBindRequestParam(req);
			if(!"".equals(method)){
				switch (method) {
				case "Resevice":
					req.getParameterNames();
					ReceiveBoldseasMsg receiveBoldseasMsg = (ReceiveBoldseasMsg)ApplicationContextHelper.getBeanByType(ReceiveBoldseasMsg.class);
					msg = receiveBoldseasMsg.receiveMsg(messageMap);
					break;
				case "WSSOTEAI001":
					WSSOTEAI001 wssoteai001 = (WSSOTEAI001)ApplicationContextHelper.getBeanByType(WSSOTEAI001.class);
					wssoteai001.doPost(req, res);
					break;
				case "WSSOTEAI002":
					WSSOTEAI002 wssoteai002 = (WSSOTEAI002)ApplicationContextHelper.getBeanByType(WSSOTEAI002.class);
					wssoteai002.doPost(req, res);
					break;
				case "WSSOTEAI003":
					WSSOTEAI003 wssoteai003 = (WSSOTEAI003)ApplicationContextHelper.getBeanByType(WSSOTEAI003.class);
					wssoteai003.doPost(req, res);
					break;
				case "WSSOTEAI004":
					WSSOTEAI004 wssoteai004 = (WSSOTEAI004)ApplicationContextHelper.getBeanByType(WSSOTEAI004.class);
					wssoteai004.doPost(req, res);
					break;
				case "WSSOTEAI005":
					WSSOTEAI005 wssoteai005 = (WSSOTEAI005)ApplicationContextHelper.getBeanByType(WSSOTEAI005.class);
					wssoteai005.doPost(req, res);
					break;
				case "WSSOTEAI006":
					WSSOTEAI006 wssoteai006 = (WSSOTEAI006)ApplicationContextHelper.getBeanByType(WSSOTEAI006.class);
					wssoteai006.doGet(req, res);
					break;
				case "WSSOTEAI007":
					WSSOTEAI007 wssoteai007 = (WSSOTEAI007)ApplicationContextHelper.getBeanByType(WSSOTEAI007.class);
					wssoteai007.doPost(req, res);
					break;
				case "WSSOTEAI008":
					WSSOTEAI008 wssoteai008 = (WSSOTEAI008)ApplicationContextHelper.getBeanByType(WSSOTEAI008.class);
					wssoteai008.doPost(req, res);
					break;
				case "WSSOTEAI009":
					WSSOTEAI009 wssoteai009 = (WSSOTEAI009)ApplicationContextHelper.getBeanByType(WSSOTEAI009.class);
					wssoteai009.doPost(req, res);
					break;
				case "WSSOTEAI013":
					WSSOTEAI013 wssoteai013 = (WSSOTEAI013)ApplicationContextHelper.getBeanByType(WSSOTEAI013.class);
					wssoteai013.doPost(req, res);
					break;
				case "WSSOTEAI014":
					WSSOTEAI014 wssoteai014 = (WSSOTEAI014)ApplicationContextHelper.getBeanByType(WSSOTEAI014.class);
					wssoteai014.doPost(req, res);
					break;
				case "WSSOTEAI015":
					WSSOTEAI015 wssoteai015 = (WSSOTEAI015)ApplicationContextHelper.getBeanByType(WSSOTEAI015.class);
					wssoteai015.doPost(req, res);
					break;
				case "WSSOTEAI016":
					WSSOTEAI016 wssoteai016 = (WSSOTEAI016)ApplicationContextHelper.getBeanByType(WSSOTEAI016.class);
					wssoteai016.doPost(req, res);
					break;
				case "WSSOTEAI017":
					WSSOTEAI017 wssoteai017 = (WSSOTEAI017)ApplicationContextHelper.getBeanByType(WSSOTEAI017.class);
					wssoteai017.doPost(req, res);
					break;
				case "WSSOTEAI018":
					WSSOTEAI018 wssoteai018 = (WSSOTEAI018)ApplicationContextHelper.getBeanByType(WSSOTEAI018.class);
					wssoteai018.doPost(req, res);
					break;
				case "WSSOTEAI019":
					WSSOTEAI019 wssoteai019 = (WSSOTEAI019)ApplicationContextHelper.getBeanByType(WSSOTEAI019.class);
					wssoteai019.doPost(req, res);
					break;
				case "SOTDCSEAI001":
					SOTDCSEAI001 sotdcseai001 = (SOTDCSEAI001)ApplicationContextHelper.getBeanByType(SOTDCSEAI001.class);
					sotdcseai001.doPost(req, res);
					break;
				case "SOTDCSEAI002":
					SOTDCSEAI002 sotdcseai002 = (SOTDCSEAI002)ApplicationContextHelper.getBeanByType(SOTDCSEAI002.class);
					sotdcseai002.doPost(req, res);
					break;
				case "SOTDCSEAI003":
					SOTDCSEAI003 sotdcseai003 = (SOTDCSEAI003)ApplicationContextHelper.getBeanByType(SOTDCSEAI003.class);
					sotdcseai003.doPost(req, res);
					break;
				case "SOTEAIDCS001":
					SOTEAIDCS001 soteaidcs001 = (SOTEAIDCS001)ApplicationContextHelper.getBeanByType(SOTEAIDCS001.class);
					soteaidcs001.doGet(req, res);
					break;
				case "SOTEAIDCS002":
					SOTEAIDCS002 soteaidcs002 = (SOTEAIDCS002)ApplicationContextHelper.getBeanByType(SOTEAIDCS002.class);
					soteaidcs002.doGet(req, res);
					break;
				case "SOTEAIDCS003":
					SOTEAIDCS003 soteaidcs003 = (SOTEAIDCS003)ApplicationContextHelper.getBeanByType(SOTEAIDCS003.class);
					soteaidcs003.doGet(req, res);
					break;
				case "dcs2eai":
					DCS2EAIServlet dcs2eai = (DCS2EAIServlet)ApplicationContextHelper.getBeanByType(DCS2EAIServlet.class);
					dcs2eai.doPost(req, res);
					break;
				case "eai2claim":
					EAI2DCSClaimsServlet eai2claim = (EAI2DCSClaimsServlet)ApplicationContextHelper.getBeanByType(EAI2DCSClaimsServlet.class);
					eai2claim.doPost(req, res);
					break;
				case "eai2dcspc":
					EAI2DCSPcServlet eai2dcspc = (EAI2DCSPcServlet)ApplicationContextHelper.getBeanByType(EAI2DCSPcServlet.class);
					eai2dcspc.doPost(req, res);
					break;
				case "eai2dcs":
					EAI2DCSServlet eai2dcs = (EAI2DCSServlet)ApplicationContextHelper.getBeanByType(EAI2DCSServlet.class);
					eai2dcs.doPost(req, res);
					break;
				case "ServletTest":
					SI01v2 si01v2 = (SI01v2)ApplicationContextHelper.getBeanByType(SI01v2.class);
					si01v2.testSi01v2(messageMap);
					break;
				case "k4dcs2eai":
					k4DcsToSapImpl k4DcsToSap = (k4DcsToSapImpl)ApplicationContextHelper.getBeanByType(k4DcsToSapImpl.class);
					k4DcsToSap.doPost(req, res);
					break;
				default:
					msg.setResultMsg(ResultMessage.NOT_FIND_REQUEST_METHOD+"["+method+"]");
					break;
				}
			}else{
				msg.setResultMsg(ResultMessage.REQUEST_METHOD_NULL);
			}
		} catch (Exception e) {
			logger.info("============http servlet 请求异常===============");
			e.printStackTrace();
			msg.setResultMsg(ResultMessage.ERROR);
			throw new ServiceBizException("请求失败！");
		}finally {
			try {
				if(!"".equals(CommonUtils.checkNull(msg.getResultMsg()))){
					doPoress(res,msg);
				}
			} catch (Exception e2) {
				res.setContentType("text/html;charset=utf-8");
				PrintWriter out = res.getWriter();
				out.write(ResultMessage.ERROR);
				out.close();
			}
			logger.info("============http servlet 请求结束===============");
		}
	}
	
	/**
	 * 返回消息
	 * @param req
	 * @param res
	 * @param msg
	 */
	private void doPoress(ServletResponse res, ResultMsgVO msg) {
		PrintWriter out = null;
		try {
			logger.info("============http请求返回结果："+msg.getResultMsg());
			if(msg.getResultType()==0){//返回纯文本消息
				res.setContentType("text/html;charset=utf-8");
			}else if(msg.getResultType()==1){//返回JSON 字符串
				res.setContentType("application/json;charset=utf-8");
			}else if(msg.getResultType()==2){//返回xml 字符串
				res.setContentType("application/xml;charset=utf-8");
			}
			out = res.getWriter();
			out.write(msg.getResultMsg());
		} catch (Exception e) {
			throw new ServiceBizException("请求返回异常！");
		}finally{
			out.close();
		}
	}

	/**
	 * 打印出请求参数信息
	 * @param req
	 */
	public Map<String,String> autoBindRequestParam(ServletRequest req) {
      Enumeration<String> names = req.getParameterNames();
      Map<String,String> paramsMap = new HashMap<String, String>();
      String val = "";
      while (names.hasMoreElements()) {
          String name = names.nextElement();
          // 防止file类型值提交
          try {
          	val = java.net.URLDecoder.decode(req.getParameter(name), "UTF-8");
          	logger.info("接收到的参数:("+name+":"+val+")");
          	paramsMap.put(name,val);
          } catch (Exception e) {
              e.printStackTrace();
              continue;
          }
      }
      return paramsMap;
  }

}
