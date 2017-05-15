package com.infoeai.eai.common.wx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class WxUtil {
	public static final String WX_ACTION_BASE_URL="http://carowner.boldseas.com/cow/dms.";
	private static Logger logger = Logger.getLogger(WxUtil.class);
	/**
	 * 输入：XML的字符串
	 * 解析微信平台反馈结果
	 * 输出：
	 */
	public static synchronized Map<String, String> readWxActionResult(String strXml) {
		Document doc = null;
		logger.info("#######返回结果为XMl字串###############"+strXml+"##############################");
		Map<String, String> returnValueList = new HashMap<String,String>();
		try {
			doc = DocumentHelper.parseText(strXml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("Result"); // 获取根节点下的子节点
			while (iter.hasNext()) {
				 Element recordEle = (Element) iter.next();
				 returnValueList.put("StateCode", recordEle.elementTextTrim("StateCode"));
				 returnValueList.put("ErrorInfo", recordEle.elementTextTrim("ErrorInfo"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnValueList;
	}
}
