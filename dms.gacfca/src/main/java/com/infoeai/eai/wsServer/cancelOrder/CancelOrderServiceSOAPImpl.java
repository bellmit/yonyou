/**
 * CancelOrderServiceSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.cancelOrder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.ctcai.SI37;
import com.yonyou.dms.common.domains.PO.basedata.TmpCancelOrderDcsPO;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.f4.common.database.DBService;

public class CancelOrderServiceSOAPImpl implements CancelOrderService_PortType{
	public static Logger logger = Logger.getLogger(CancelOrderServiceSOAPImpl.class);
	
	@Autowired
	DBService dbService;
		
    public java.lang.String orderCancelBack(java.lang.String in) throws java.rmi.RemoteException {
    	//start
    	SI37 si37 = (SI37)ApplicationContextHelper.getBeanByType(SI37.class);
    	try {
    		//1  把中进返回的结果加入临时表
    		logger.info("------解析中进的返回结果开始------");	
    		List<TmpCancelOrderDcsPO> listCancelOrder = new ArrayList<TmpCancelOrderDcsPO>();
    		Timestamp nousedate = new Timestamp((new Date()).getTime());
    		Document doc = DocumentHelper.parseText(in);
    		Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("Result"); // 获取根节点下的子节点
			logger.info("------中进返回的结果写入临时表TMP_CANCEL_ORDER开始------");	
			while (iter.hasNext()) {
				TmpCancelOrderDcsPO po = new TmpCancelOrderDcsPO();
				Element recordEle = (Element) iter.next();
				po.setString("VIN",recordEle.elementTextTrim("Vin"));//vin
				if (null!=recordEle.elementTextTrim("StateCode")
						&& recordEle.elementTextTrim("StateCode").length() != 0) {
					po.setInteger("STATE_CODE",Integer.parseInt(recordEle.elementTextTrim("StateCode")));//StateCode
				}
				po.setString("ERROR_INFO",recordEle.elementTextTrim("ErrorInfo"));//ErrorInfo
				po.setInteger("STATUS",0);//写入临时表默然是0  未处理过
				po.setTimestamp("CREATE_DATE",nousedate);//创建时间
				po.setLong("CREATE_BY",11111111);//创建人
				listCancelOrder.add(po);
			}
			si37.insertTmpCancelOrderDcsPO(listCancelOrder);
			
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.info("------中进返回的结果写入临时表TMP_CANCEL_ORDER异常------"+e);	
    	}
    	logger.info("------中进返回的结果写入临时表TMP_CANCEL_ORDER结束------");
    	// end
    	
    	//操作业务表    	
		try {
			si37.execute(in);
			logger.info("------撤单接收数据------>"+in);	
		} catch (Exception e) {				
			//将失败信息存入表中,定时器5分钟处理一次
			logger.info("====撤单失败信息写入表成功===="+e);	
			e.printStackTrace();
		} finally{
			logger.info("=======撤单接口回调完成=======");
		}
		return "1";
		/******************************结束事物*********************/
    }
}
