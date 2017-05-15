package com.infoeai.eai.action.ctcai;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.infoeai.eai.wsClient.ctcai.CbmServiceImplService;
import com.infoeai.eai.wsClient.ctcai.CbmServiceImplServiceLocator;
import com.infoeai.eai.wsClient.ctcai.CbmServiceImplServiceSoapBindingStub;

/**
 * WebService DCS->CTCAI ZDRQ-订单取消同步接口（预撤单）
 * 
 * @author luoyang
 *
 */
@Service
public class SI25Impl extends BaseService implements SI25 {

	private static final Logger logger = LoggerFactory.getLogger(SI25Impl.class);

	@Autowired
	SICommonDao dao;

	@Override
	public String doCtcaiMethod(String vin) {
		logger.info("====SI25 is begin====");
		String returnResult = null;
		try {
			// dbService.beginTxn();
			/* 初始化对方方法 */
			CbmServiceImplServiceSoapBindingStub stub = new CbmServiceImplServiceSoapBindingStub();
			CbmServiceImplService cbmService = new CbmServiceImplServiceLocator();
			stub = (CbmServiceImplServiceSoapBindingStub) cbmService.getCbmServiceImplPort();
			stub.setTimeout(1000 * 60 * 10);
			// 根据传递的vehicleId封装传入中进的xmlString
			String xmlString = getXmlString(vin);
			logger.info("---撤单---往中进传送数据格式：" + xmlString);
			// 调用对方方法接收反馈的参数
			returnResult = stub.cgcslCancelOrder(xmlString);
			// returnResult = "1";
			// 解析对方放回的结果信息
			// returnResult = si25.readXml(returnString);
			// dbService.endTxn(true);
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// dbService.endTxn(false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			// Base.detach();
			// dbService.clean();
		}
		logger.info("====SI25 is finish====");
		return returnResult.trim();
	}

	/***
	 * 功能说明：根据传递的车辆ID查找对应的VIN和中进DealerCode
	 * 
	 * @param vehicleId
	 *            create by ZRM create date 2013-10-18
	 */
	public String getXmlString(String vin) {
		StringBuffer xmlParam = new StringBuffer("");
		List<Map> tempVehicleList = dao.getVinAndDealerCode(vin);
		xmlParam.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		xmlParam.append("<Root>\n");
		xmlParam.append("<Content>\n");
		for (int i = 0; i < tempVehicleList.size(); i++) {
			Map tempVehicle = tempVehicleList.get(i);
			xmlParam.append("<Car>\n");
			xmlParam.append("<DealerCode>" + tempVehicle.get("CTCAI_CODE") + "</DealerCode >\n");
			xmlParam.append("<Vin>" + tempVehicle.get("VIN") + "</Vin >\n");
			xmlParam.append("</Car>\n");
		}
		xmlParam.append("</Content>\n");
		xmlParam.append("</Root>\n");

		return xmlParam.toString();
	}

	/**
	 * 输入：XML的字符串 解析CTCAI反馈结果 输出：
	 */
	public List<Map<String, String>> readXml(String strXml) {
		Document doc = null;
		List<Map<String, String>> returnValueList = new ArrayList<Map<String, String>>();
		try {
			doc = DocumentHelper.parseText(strXml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("Result"); // 获取根节点下的子节点
			while (iter.hasNext()) {
				Map<String, String> returnValue = new HashMap<String, String>();
				Element recordEle = (Element) iter.next();
				returnValue.put("Vin", recordEle.elementTextTrim("Vin"));
				returnValue.put("StateCode", recordEle.elementTextTrim("StateCode"));
				returnValue.put("ErrorInfo", recordEle.elementTextTrim("ErrorInfo"));
				returnValueList.add(returnValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValueList;
	}

}
