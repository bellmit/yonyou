package com.yonyou.dms.common.Util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.ws.RequestWrapper;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.EOMonth;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.GetVinUtil;

/**
 * <p>Title: InfoFrame3.0.Cc.V01</p>
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

public class DomManager  implements Serializable
{

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) 
//	{
//		//test
//		String inputs= "<ROOT><CONDITION><NAME></NAME></CONDITION></ROOT>";
//		
//		Document document = getDocument(inputs);
//
//		Node node = document.selectSingleNode("//ROOT/CONDITION/NAME/mail");
////		if(node!=null){
////			System.out.println("sdfffff");
////		}else{
////			System.out.println(".......");
////		}
//
//	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7934189702598722480L;
	protected static Logger logger = LogManager.getLogger(DomManager.class);
	
	/**
	 *  added by andy.ten@tom.com
	 *  将结果集转化为dom对象
	 */
    public static Document buildXml(ResultSet rs) 
    {
    	//System.out.println("-->debug1");
    	Document document = DocumentFactory.getInstance().createDocument();
    	Element root = document.addElement("ROOT");
    	try 
    	{
    		ResultSetMetaData rsmd = rs.getMetaData();
    		/**
    		 * added by andy.ten@tom.com
    		 * 由于3代框架中ResultSet rs 默认已经执行过一次rs.next，故采用do..while
    		 */
    		do
			{
    			Element recordItem = root.addElement("ROW");
        		for (int i = 1; i <= rsmd.getColumnCount(); i++)
        		{
        			String colname = rsmd.getColumnName(i).toUpperCase();
        			//int coltype = rsmd.getColumnType(i); 
        			String colvalue = rs.getString(i);
        			if(colvalue == null) colvalue = "";
        			
        			Element fieldNode = recordItem.addElement(colname);
        			fieldNode.addText(colvalue);
        		}
			} while (rs.next());
    	    //System.out.println("result："+document.asXML());
		} catch (Exception e) 
		{
			//TODO: handle exception
			logger.error(e.getMessage());
			System.out.println("buildXml方法："+e.toString());
			
		}
		
		return document;
		
    }
    
    /**
	 *  added by andy.ten@tom.com
	 *  将结果集转化为dom对象
	 */
    public static Document buildXml(ResultSet rs , ResultSetMetaData rsmd )
    {
    	//System.out.println("-->debug1");
    	Document document = DocumentFactory.getInstance().createDocument();
    	Element root = document.addElement("ROOT");
//    	if (page != null)
//        {
//    		Element pageinfo = root.addElement("PAGEINFO");
//    		pageinfo.addAttribute("currentpagenum",
//                                    String.valueOf(page.getCurrentPage()));
//    		pageinfo.addAttribute("recordsperpage",
//                                    String.valueOf(page.getPageRows()));
//    		pageinfo.addAttribute("totalpage",
//                                    String.valueOf(page.getCountPage()));
//    		pageinfo.addAttribute("countrows",
//                                    String.valueOf(page.getCountRows()));
//        }
    	try 
    	{
    		while (rs.next()) 
        	{
        		Element recordItem = root.addElement("ROW");
        		for (int i = 1; i <= rsmd.getColumnCount(); i++)
        		{
        			String colname = rsmd.getColumnName(i).toUpperCase();
        			//int coltype = rsmd.getColumnType(i); 
        			String colvalue = rs.getString(i);
        			if(colvalue == null) colvalue = "";
        			
        			Element fieldNode = recordItem.addElement(colname);
        			fieldNode.addText(colvalue);
        		}
    		}
    	    //System.out.println("result："+document.asXML());
		} catch (Exception e) 
		{
			//TODO: handle exception
			logger.error(e.getMessage());
			System.out.println("buildXml方法："+e.toString());
			
		}
		
		return document;
		
    }
    
    /**
	 *  added by andy.ten@tom.com
	 */
    public static Document AddPageInfo(Document document )
    {
    	//System.out.println("-->debug1");
    	try
		{
    		Element root = document.getRootElement();
    		
//        	if (page != null)
//            {
//        		Element pageinfo = root.addElement("PAGEINFO");
//        		pageinfo.addAttribute("currentpagenum",
//                                        String.valueOf(page.getCurrentPage()));
//        		pageinfo.addAttribute("recordsperpage",
//                                        String.valueOf(page.getPageRows()));
//        		pageinfo.addAttribute("totalpage",
//                                        String.valueOf(page.getCountPage()));
//        		pageinfo.addAttribute("countrows",
//                                        String.valueOf(page.getCountRows()));
//            }
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error(e.getMessage());
			System.out.println("DomManager.AddPageInfo方法："+e.toString());
		}
    	
		return document;
		
    }
    
    /**
     *  added by andy.ten@tom.com
	 *  将结果集转化为dom对象
	 */
    public static Document buildXml(List list , ResultSetMetaData rsmd , Document document)
    {
    	//System.out.println("-->debug1");
    	Element root = document.addElement("ROOT");
    	try 
    	{
    		
    	    //System.out.println("result："+document.asXML());
		} catch (Exception e) 
		{
			//TODO: handle exception
			logger.error(e.getMessage());
			System.out.println("buildXml方法："+e.toString());
			
		}
		
		return document;
		
    }
    
    /**
     * added by andy.ten@tom.com
     * 生成xml文件
     */
    
    private static final void writeXml(Document doc ,String filename ,String filepath) throws Exception
    {
    	FileOutputStream fos = null;
    	if(doc == null) return;
    	HashMap hMap = new HashMap();
    	try
        {
    		File file = new File(filepath);
            file.mkdirs();
            file = null;
            fos = new FileOutputStream(filename);
            OutputStream os = fos;
	    	OutputFormat of = new OutputFormat();
	        of.setEncoding("UTF-8");
	        of.setIndent(true);
	        of.setNewlines(true);
	        XMLWriter writer = new XMLWriter(os, of);
	        writer.write(doc);
        }catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (fos != null)
            {
                fos.close();
            }
        } 
		
	}
    
    /**
     * added by andy.ten@tom.com
     * 将查询条件转换为document对象
     * @param querycond xml格式的字符串
     * @return
     */
    public static Document getDocument(String querycond)
    {
        try
        {
        	String queryCon=CommonUtils.checkNull(querycond);
        	if("".equals(queryCon)){
        		throw new ServiceBizException("网络不佳，系统无法获得前台初始化的查询条件信息！");	
        	}
            SAXReader reader = new SAXReader();
            return reader.read(new ByteArrayInputStream(querycond.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
        	logger.error("页面查询xml字符串出错 " + querycond);
        	logger.error(e.getMessage());
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    /**
     * added by andy.ten@tom.com
     * 将查询条件转换为document对象
     * @param request
     * @return
     */
    public static HashMap<String, QueryConditionBean> getConditions(Document doc) throws Exception
    {
    	    	
        try
        {
        	 HashMap<String, QueryConditionBean> hm = new HashMap<String, QueryConditionBean>();
        	 List list = doc.selectNodes("//ROOT/CONDITIONS/CONDITION");
        	 if(list == null)
        		 return null;
             Iterator iter = list.iterator();
             Element element = null;
             
             while (iter.hasNext())
             {
            	 element = (Element) iter.next();
            	 //String oldFieldName = element.selectSingleNode("DATASOURCE").getText();
                 String fieldName = element.selectSingleNode("DATASOURCE").getText();
                 
                 Node ndoperation = element.selectSingleNode("OPERATION");
                 String operation = ndoperation == null ? "=" : ndoperation.getText();
                 
                 String fieldValue = element.selectSingleNode("VALUE").getText();

                 //是否替换
                 String isReplace = element.selectSingleNode("ISREPLACE").getText();
                 if("true".equals(isReplace)){
                	//匹配part_code 与 part_show_code
                	 fieldValue = fieldValue.replace(OemDictCodeConstants.replaceChar, "");
                	 //fieldName = "REPLACE("+fieldName+",'"+Constant.replaceChar+"','')";
                	 //logger.info("-------------------------------------------替换:"+fieldName);
                 }
                 
                 Node ndformat = element.selectSingleNode("DATEFORMAT");
                 String fieldFormat = ndformat == null ? "YYYY-MM-DD" :ndformat.getText();
                 if(fieldFormat == null) fieldFormat = "";
                 
                 Node ndkind = element.selectSingleNode("KIND");
                 String fieldKind = ndkind == null ? "text" : ndkind.getText();
                 
                 hm.put(fieldName, new QueryConditionBean(fieldName ,operation ,fieldValue ,fieldKind ,fieldFormat));
             }
                   
             //排序字段
             Node ndorder= doc.selectSingleNode("//ROOT/ORDER");
             String order = ndorder == null ? "" : ndorder.getText();
             if(order == null) order = "";
             //hm.put("$ORDER$", order);
             
             return hm;
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage());
            e.printStackTrace(System.out);
            throw new Exception(e);
            
        }
        
    }
    
    /**
     * added by andy.ten@tom.com
     * 将查询条件转换为where---
     * @param request
     * @return
     */
    public static String getConditionsWhere(Document doc) throws Exception
    {
    	String sWhere = "";
        try
        {
        	 List list = doc.selectNodes("//ROOT/CONDITIONS/CONDITION");
        	 if(list == null)
        		 return null;
             Iterator iter = list.iterator();
             Element element = null;
             
             while (iter.hasNext())
             {
            	 
            	 String condition = "";
                 element = (Element) iter.next();
                 
                 Node ndAction = element.selectSingleNode("ACTION");
                 String sAction = ndAction == null ? "=" : ndAction.getText();
                 if(sAction == null || "".equals(sAction)) sAction = "";
                 if(sAction.equals("show"))
                	 continue;
                 
                 String fieldName = element.selectSingleNode("DATASOURCE").getText();
                 
                 Node ndoperation = element.selectSingleNode("OPERATION");
                 String operation = ndoperation == null ? "=" : ndoperation.getText();
                 if(operation == null || "".equals(operation)) operation = "=";
                 
                 String fieldValue = element.selectSingleNode("VALUE").getText();
                 
                 //是否替换
                 String isReplace = element.selectSingleNode("ISREPLACE").getText();
                 if("true".equals(isReplace)){
                	//匹配part_code 与 part_show_code
                	 fieldValue = fieldValue.replace(OemDictCodeConstants.replaceChar, "");
                	 fieldName = "REPLACE("+fieldName+",'"+OemDictCodeConstants.replaceChar+"','')";
                	 //logger.info("-------------------------------------------替换:"+fieldName);
                 }

                 Node ndformat = element.selectSingleNode("DATEFORMAT");
                 String fieldFormat = ndformat == null ? "YYYY-MM-DD" :ndformat.getText();
                 if(fieldFormat == null || "".equals(fieldFormat)) fieldFormat = "YYYY-MM-DD";
                 
                 Node ndkind = element.selectSingleNode("KIND");
                 String fieldKind = ndkind == null ? "text" : ndkind.getText();
                 if(fieldKind == null || "".equals(fieldKind)) fieldKind = "text";
                 
                 if(operation.equalsIgnoreCase("isnull"))
                 {
            		 condition = fieldName + " is null ";
                 } 
                 if(operation.equals("=") || operation.equals("!="))
             	    condition = " " + fieldName + " " + operation +"'" + fieldValue +"'";
             	 
             	 if(operation.equalsIgnoreCase("in") || operation.equalsIgnoreCase("not in"))
             	 {
             		String s = fieldValue.replaceAll(",", "', '");
              	    condition = fieldName + " " + operation +"('" + s +"')";
             	 }   
             	 
             	 if(operation.equalsIgnoreCase("like"))
             		 condition = fieldName + " " + operation +"'%" + fieldValue +"%'";
             	 
             	 if(operation.equalsIgnoreCase("left_like"))
             		 condition = fieldName + " " + "like" +"'%" + fieldValue +"'";
             	 
             	 if(operation.equalsIgnoreCase("right_like"))
             		 condition = fieldName + " " + "like" +"'" + fieldValue +"%'";
             	 
             	if(operation.equalsIgnoreCase("&lt;") || operation.equalsIgnoreCase("<"))
             	{
            		 operation = "<";
                 	 condition = fieldName + " " + operation +"'" + fieldValue +"'";
             	}	 
             	if(operation.equalsIgnoreCase("&gt;") || operation.equalsIgnoreCase(">"))
             	{
             		operation = ">";
             		condition = fieldName + " " + operation +"'" + fieldValue +"'";
             	}	
             	if(operation.equalsIgnoreCase("&lt;=") || operation.equalsIgnoreCase("<="))
             	{	
             		operation = "<=";
             		condition = fieldName + " " + operation +"'" + fieldValue +"'";
             	}	
            	if(operation.equalsIgnoreCase("&gt;=") || operation.equalsIgnoreCase(">="))
            	{	
            		operation = ">=";
            		condition = fieldName + " " + operation +"'" + fieldValue +"'";
            	}	
            	
             	
                 if (fieldKind.equalsIgnoreCase("int") || fieldKind.equalsIgnoreCase("float") || fieldKind.equalsIgnoreCase("money")) 
                 {
                	 if(operation.equals("="))
                 	    condition = fieldName + " " + operation + fieldValue;
                 	 
                 	 
				 }else if (fieldKind.equalsIgnoreCase("date"))
				 {
					 if(operation.equals("<="))
						 condition = fieldName + " " + operation +" to_date('" + fieldValue + " 23:59:59','" + "YYYY-MM-DD HH24:MI:SS') ";
					 else 
						 condition = fieldName + " " + operation +" to_date('" + fieldValue + "','" + fieldFormat + "') ";
				 }else if(fieldKind.equals("vin"))
				 {
					 if(fieldName.indexOf(".")>0)
					 {
						 String alias = fieldName.split("\\.")[0];
                         String s = fieldValue.replaceAll("\\^\\^", "\n");
						 condition = GetVinUtil.getVins(s, alias, "1");
					 }	 
				 }else if(fieldKind.equals("dea") || fieldKind.equals("org") || fieldKind.equals("mat"))
				 {
					 if(fieldValue.indexOf(",")>0)
					 {
						 operation = "in";
						 String[] fields = fieldValue.split(",");
						 String temp = "";
						 for(int i=0;i<fields.length;i++)
						 {
							 if(temp.length() > 0)
							 {
								 temp += ",'" + fields[i] + "'";
							 }	 
							 else
							 {
								 temp += "'" + fields[i] + "'";
							 }	 
						 }
						 condition = fieldName + " " + operation + " (" + temp +") ";
					 }else 
					 {
						 String temp = "'" + fieldValue + "'";
						 operation = "=";
						 condition = fieldName + " " + operation + " " + temp;
					 }
				 }
                 
                 if(sWhere.length() > 0)
                 {
                	 sWhere += " AND " + condition; 
                 }else 
                 {
					 sWhere += condition;
				 }
             }
             
             //排序
             Node ndorder= doc.selectSingleNode("//ROOT/ORDER");
             String order = ndorder == null ? "" : ndorder.getText();
             if(order == null) order = "";
             if(!"".equals(order))
                sWhere += " ORDER BY " + order ;
             
//             if(sWhere.length() > 0)
//            	sWhere = " WHERE " + sWhere;
             return sWhere;
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage());
            e.printStackTrace(System.out);
            throw new Exception(e);
        }

    }

    
    /**
	 * 将主数据生成xml方法
	 */
	public static synchronized int generateAllXml(String filepath) throws Exception
	{
		String filename = filepath + "\\dataxml\\";
		filepath = filepath + "\\dataxml\\";
	    Method method = null;
	    Field field  = null;
	    Field[] fields = null;
		String methodName = "";
		String execSql = "";
		try 
		{
			String classpath = "com.infodms.dms.common.dataForXml.DataCollection";
			Class methodClass = Class.forName(classpath);
			fields = methodClass.getFields();
			for(int i=0; i<fields.length; i++)
			{
				Document document = DocumentFactory.getInstance().createDocument();
				field = fields[i];
				String fieldName = field.getName();
				method = methodClass.getMethod(field.get(fieldName).toString());
				if (method != null) 
				{
					methodName = method.getName();
					execSql = (String)method.invoke(null);
					if("".equals(execSql))
						throw new Exception("生成xml方法执行语句为空！");
					//执行生成Document方法
					document = DataToXml.executeQuery(execSql);
                    //生成到指定位置xml
				    writeXml(document ,filename+field.getName()+".xml" ,filepath);
				}
				document = null;
			}
			
		} catch (NoSuchMethodException e) 
		{
			method = null;
			throw new Exception("DomManager.generateXml方法抛出异常:NoSuchMethodException:"+methodName);
		} catch (Exception e) 
		{
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 将指定主数据生成xml方法
	 */
	public static synchronized int generateXmlByName(String filepath ,String dataName) throws Exception 
	{
		
		Document document = DocumentFactory.getInstance().createDocument();
		String filename = filepath + "\\dataxml\\";
		filepath = filepath + "\\dataxml\\";
	    Method method = null;
	    Field field  = null;
	    Field[] fields = null;
		String methodName = "";
		String execSql = "";
		try 
		{
			
			String classpath = "com.infodms.dms.common.dataForXml.DataCollection";
			Class methodClass = Class.forName(classpath);
			
			field = methodClass.getField(dataName);
			String fieldName = field.getName();
			method = methodClass.getMethod(field.get(fieldName).toString());
			if (method != null) 
			{
				methodName = method.getName();
				execSql = (String)method.invoke(null);
				if("".equals(execSql))
					throw new Exception("保存失败，请与系统管理员联系！");
				//执行生成Document方法		
				document = DataToXml.executeQuery(execSql);
                //生成到指定位置xml
			    writeXml(document ,filename+field.getName()+".xml" ,filepath);
			}
			
		}catch (NoSuchFieldException e) 
		{
			System.out.println("DomManager.generateXml方法抛出异常:NoSuchFieldException:"+dataName);
			field = null;
		}  catch (NoSuchMethodException e) 
		{
			System.out.println("DomManager.generateXml方法抛出异常:NoSuchMethodException:"+methodName);
			field = null;
			method = null;
			return 0;    
		} catch (Exception e) 
		{
			e.printStackTrace();
			return 0;
		}
		
		return 1;

	}
	
    /**
	 *  将结果集转化为dom对象
	 */
    public static Document buildXml(ResultSet rs , ResultSetMetaData rsmd , Document document)
    {
    	//System.out.println("-->debug1");
    	Element root = document.addElement("ROOT");
    	try 
    	{
    		while (rs.next()) 
        	{
        		Element recordItem = root.addElement("ROW");
        		for (int i = 1; i <= rsmd.getColumnCount(); i++)
        		{
        			String colname = rsmd.getColumnName(i).toUpperCase();
        			//int coltype = rsmd.getColumnType(i); 
        			String colvalue = rs.getString(i);
        			if(colvalue == null) colvalue = "";
        			
        			Element fieldNode = recordItem.addElement(colname);
        			fieldNode.addText(colvalue);
        		}
    		}
    	    //System.out.println("result："+document.asXML());
		} catch (Exception e) 
		{
			//TODO: handle exception
			System.out.println("buildXml方法："+e.toString());	
		}
    
    	return document;
    }
    
    /**
	 * 通用执行代码
	 */
	private static Document commonExec(Connection conn ,Document document ,String sql)
	{
	    Statement stmt = null;
	    ResultSet rs = null;
	    ResultSetMetaData rsmd = null;
	    try 
	    {
	    	stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			DomManager.buildXml(rs , rsmd , document);

	    }catch (Exception e) 
	    {
			// TODO: handle exception
	    	e.printStackTrace();
		}finally
		{
			stmt = null;
			rs = null;
		}
	    
		return document;
	}
	 public static String getConditionsWhere(List<QueryConditionBean> list) throws Exception{
		 StringBuffer conditionWhere = new StringBuffer();
		 for(QueryConditionBean qcb : list){
			 if(!qcb.getValue().equals("")){
				 if(qcb.getOperation().equals("=")){
					 conditionWhere.append(" and "+qcb.getField()+qcb.getOperation()+"'"+qcb.getValue()+"' ");
				 }else if(qcb.getOperation().equals("between")){
					 conditionWhere.append(" and to_char("+qcb.getField()+",'yyyy-MM-dd')='"+qcb.getValue()+"' ");
				 }else if(qcb.getOperation().equals("<=")){
					 conditionWhere.append(" and "+qcb.getField()+">=to_char("+qcb.getField()+",'yyyy-MM-dd') ");
				 }else if(qcb.getOperation().equals(">=")){
					 conditionWhere.append(" and "+qcb.getField()+"<=to_char("+qcb.getField()+",'yyyy-MM-dd') ");
				 }else if(qcb.getOperation().equals("like")){
					 conditionWhere.append(" and "+qcb.getField()+" like '%"+qcb.getValue()+"%' ");
				 }else if(qcb.getOperation().equals("in")){
					 conditionWhere.append(" and "+qcb.getField()+" in ('"+qcb.getValue().replace(",", "','")+"') ");
				 }				 
			 }
		 }
		 return conditionWhere.toString();
	 }
	/**
	 * 
	* @Title: getConditionWhere 
	* @Description: TODO(取where条件) 
	* @param @param request
	* @param @return   where条件字符串
	* @return String    返回类型 
	 * @throws Exception 
	 */
//	public static String getConditionWhere(RequestWrapper request) throws Exception {
//		String sParams = request.getParamValue("params"); // 
//		//输入流解析xml格式条件
//		Document doc = DomManager.getDocument(sParams);
//		String conditionWhere = DomManager.getConditionsWhere(doc);
//		return conditionWhere;
//	}
//	
	
}