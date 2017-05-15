package com.yonyou.dms.common.Util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
/**
 * <p>Title: InfoFrame2.0.Cc.V01</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: www.infoservice.com.cn</p>
 * <p>Date: 2010-1-29</p>
 *
 * @author andy 
 * @mail   andy.ten@tom.com
 * @version 1.0
 * @remark 
 */

public class DataToXml
{
	
	/**
	 * 不带分页的查询，调用框架的factory.select
	 */
	
	protected static Logger logger = LogManager.getLogger(DataToXml.class);
	public  static Document executeQuery(String sql) throws Exception
	{
	    
	    Document document = DocumentFactory.getInstance().createDocument();	   		
		try 
		{

            logger.info(sql);
            
            List resultList = buildList(OemDAOUtil.findAll(sql.toString(), null));
//            		factory.select(sql,null,new DAOCallback(){
//            	public Document wrapper(ResultSet rs, int idx) {
//            		return DomManager.buildXml(rs);
//            	}
//            });
						
			if(resultList != null && resultList.size() > 0)
				document = (Document)resultList.get(0);
			else 
			{
				Element root = document.addElement("ROOT");
			}

		}catch (Exception e) 
	    {
			logger.error("DBUtil.executeQuery方法："+e.toString());
			throw new Exception(e);
		}finally 
		{
	    	
		}
	    
		return document;
	}
	/**
	 * 
	 * @param findAll
	 * @return
	 */
	private static List<Document> buildList(List<Map> findAll) {
		List<Document> resultList = new ArrayList<>();
		for (Map map : findAll) {
			resultList.add(DomManager.buildXml((ResultSet) map));
		}
		return resultList;
	}
}
	
