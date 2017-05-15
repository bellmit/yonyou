package com.infoeai.eai.action.ctcai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.ctcai.OrderRepealqueryDao;
import com.yonyou.dms.common.domains.PO.basedata.TmpCancelOrderDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * WebService
 * CTCAI->DCS
 * 中进撤单结果处理
 * @author luoyang
 *
 */
@Service
public class SI37Impl extends BaseService implements SI37 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI37Impl.class);
	
	@Autowired
	OrderRepealqueryDao dao;

	@Override
	public void execute(String in) throws Exception {
		logger.info("====SI37 is begin====");
		List<Map<String,String>> list  = new ArrayList<Map<String,String>>();
		/******************************开启事物*********************/
		//事务开启
		beginDbService();
		try {
			list = readXml(in);
			dao.undoOrder(list);
			dbService.endTxn(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
		} finally {
			logger.info("====SI37 is finish====");
			Base.detach();
			dbService.clean();
		}		
		/******************************结束事物*********************/
		
	}

	@Override
	public List<Map<String, String>> readXml(String strXml) {
		Document doc = null;
		List<Map<String, String>> returnValueList = new ArrayList<Map<String,String>>();
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
			logger.info("撤单strXml:"+strXml);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnValueList;
	}

	@Override
	public boolean insertTmpCancelOrderDcsPO(List<TmpCancelOrderDcsPO> listCancelOrder) {
		beginDbService();
		boolean flag = false;
		try {
			if (null != listCancelOrder && listCancelOrder.size() > 0) {
		    	for(int i = 0; i < listCancelOrder.size(); i++){
		    		flag = listCancelOrder.get(i).insert();
		    		if(!flag){
		    			throw new ServiceBizException("插入临时表失败");
		    		}
		    	}
			}
			dbService.endTxn(true);
		} catch (Exception e) {
			try {
				dbService.endTxn(false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("------中进返回的结果写入临时表TMP_CANCEL_ORDER异常------"+e);	
		} finally {
			Base.detach();
			dbService.clean();
		}
		return true;
	}

}
