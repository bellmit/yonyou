
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : ExcelExportColumn.java
*
* @Author : zhangxc
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service.excel;

/**
* 导出的excel 列定义
* @author zhangxc
* @date 2016年9月28日
*/
public class ExcelExportColumn {
    
    private String fieldName;
    private String title;
    private String format;
    private ExcelDataType dataType;

    
   /**
    * 
   * 默认构造函数
   * @author zhangxc
   * @date 2016年9月28日
   * @param fieldName
   * @param title
    */
    public ExcelExportColumn(String fieldName,String title){
        this.fieldName = fieldName;
        this.title = title;
    }
    
    /**
     * 
    * 指定格式化样式
    * @author zhangxc
    * @date 2016年9月28日
    * @param fieldName
    * @param title
    * @param format
     */
    public ExcelExportColumn(String fieldName,String title,String format){
        this.fieldName = fieldName;
        this.title = title;
        this.format = format;
    }
    
    /**
     * 
    * 指定字段类型
    * @author zhangxc
    * @date 2016年9月28日
    * @param fieldName
    * @param title
    * @param dataType
     */
    public ExcelExportColumn(String fieldName,String title,ExcelDataType dataType){
        this.fieldName = fieldName;
        this.title = title;
        this.dataType = dataType;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getFormat() {
        return format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    public ExcelDataType getDataType() {
        return dataType;
    }
    
    public void setDataType(ExcelDataType dataType) {
        this.dataType = dataType;
    }
    
}
