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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;

/**
 * 根据  Repair、parts、Manager 等功能包产生的 Excel数据，生成一个 Excel文件流，供前台页面下载
 * Created by wfl
 */

public interface ExcelRead<T extends DataImportDto>
{
    /**
     * 
    * 解析excel 表格
    * @author zhangxc
    * @date 2016年8月10日
    * @param inputStream
    * @return
     * @throws IOException 
     */
    Map<String, ImportResultDto<T>> analyzeExcel(MultipartFile excelFile,List<AbstractExcelReadCallBack<T>> callBackList) throws IOException;
    
    /**
     * 
    * 解析excel 表格
    * @author zhangxc
    * @date 2016年8月10日
    * @param inputStream
    * @return
     * @throws IOException 
     */
    ImportResultDto<T> analyzeExcelFirstSheet(MultipartFile excelFile,AbstractExcelReadCallBack<T> abstractExcelReadCallBack) throws IOException;
}
