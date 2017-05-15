package com.yonyou.dms.common.Util;

/**
 * @Title: empptyProject
 *
 * @Description:
 *
 * @Copyright: Copyright (c) 2010
 *
 * @Company: www.infoservice.com.cn
 * @Date: 2010-2-25
 *
 * @author andy 
 * @mail   andy.ten@tom.com
 * @version 1.0
 * @remark 
 */
public class QueryConditionBean
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static final String KIND_TEXT = "text";
    public static final String KIND_DATE = "date";
    public static final String KIND_DATETIME = "datetime";

    private String field;
    private String operation;
    private String value;
    private String kind;
    private String fieldFormat;
	  
	public QueryConditionBean(String field, String operation,String value,String kind,String format)
	{
	    this.field = field;
	    this.operation = operation;
	    this.value = value;
	    this.kind = kind;
	    this.fieldFormat = format;
	}
	public QueryConditionBean(String field, String operation,String value)
	{
	    this.field = field;
	    this.operation = operation;
	    this.value = value;	   
	}

	/**
	 * @return the field
	 */
	public String getField()
	{
		return field;
	}

	/**
	 * @return the operation
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @return the kind
	 */
	public String getKind()
	{
		return kind;
	}

	/**
	 * @return the fieldFormat
	 */
	public String getFieldFormat()
	{
		return fieldFormat;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field)
	{
		this.field = field;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind)
	{
		this.kind = kind;
	}

	/**
	 * @param fieldFormat the fieldFormat to set
	 */
	public void setFieldFormat(String fieldFormat)
	{
		this.fieldFormat = fieldFormat;
	}

}
