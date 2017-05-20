/*
* Copyright 2015 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : CMOL
*
* @File name : ExcelGenerator.java
*
* @Author : WFL
*
* @Date : 2016-3-23
*
*/
package com.yonyou.dms.framework.service.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据  Repair、parts、Manager 等功能包产生的 Excel数据，生成一个 Excel文件流，供前台页面下载
 * Created by wfl
 */

public interface ExcelGenerator
{
    
    /**
     * 
    * 导出excel 数据，通过ExcelExportColumn 定义导出的样式
    * @author zhangxc
    * @date 2016年9月28日
    * @param excelData
    * @param columnDefine
    * @param fileName
    * @param response
     */
    void generateExcel(@SuppressWarnings("rawtypes") Map<String, List<Map>> excelData, List<ExcelExportColumn> columnDefineList,String fileName,HttpServletRequest request, HttpServletResponse response);
    
    void generateExcelForDms(@SuppressWarnings("rawtypes") Map<String, List<Map>> excelData, List<ExcelExportColumn> columnDefineList,String fileName,HttpServletRequest request, HttpServletResponse response);

	void generateExcel(Map<String, List<Map>> excelData, Map<String, List<ExcelExportColumn>> exportColumnList,
			String fileName, HttpServletRequest request, HttpServletResponse response);
}
