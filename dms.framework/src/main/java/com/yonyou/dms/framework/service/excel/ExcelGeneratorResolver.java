/*
* Copyright 2015 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : CMOL
*
* @File name : ExcelGeneratorResolver.java
*
* @Author : WFL
*
* @Date : 2016-3-31
*
*/

package com.yonyou.dms.framework.service.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;

@Component("excelGeneratorResolver")
@Scope(value="singleton")
public class ExcelGeneratorResolver
{
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(ExcelGeneratorResolver.class);

    @Autowired
    private ExcelGenerator excelGeneratorDef;


    /**
     * 根据 BeanName 获取一个  ExcelGenerator 实例
     * @param beanName Bean名称
     * @return ExcelGenerator
     */
    public ExcelGenerator getExcelGenerator(String beanName)
    {
        ExcelGenerator excelGenerator=null;
        try
        {
            excelGenerator=(ExcelGenerator)ApplicationContextHelper.getBeanByName(beanName);
        }
        catch(Exception exception)
        {
            logger.error(exception.getMessage(),exception);
            throw new ServiceBizException(exception.getMessage(),exception);
        }
        return excelGenerator;
    }

    /**
     *  根据 BeanName 获取一个  ExcelGenerator 实例，如果获取不到，使用默认的实现类实例
     * @param beanName Bean名称
     * @return ExcelGenerator
     */
    public ExcelGenerator getExcelGeneratorWithDefault(String beanName)
    {
        ExcelGenerator excelGenerator=excelGeneratorDef;
        try
        {
            excelGenerator = getExcelGenerator(beanName);
        }
        catch(Exception ex)
        {
            logger.warn(ex.getMessage(),ex);
        }
        return excelGenerator ;
    }

}
