
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : DbFunctionConvertUtil.java
*
* @Author : zhangxc
*
* @Date : 2016年12月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月17日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.util.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.dms.framework.domain.FrameworkParamBean;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ApplicationException;

/**
* 数据库函数转换函数，用于兼容oracle、Mysql 数据库
* @author zhangxc
* @date 2016年12月17日
*/

public class DbFunctionConvertUtil {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(DbFunctionConvertUtil.class);
    private static final String dbType;
    
    /**
     * 初始化dbType
     */
    static{
        FrameworkParamBean  frameworkParam = ApplicationContextHelper.getBeanByType(FrameworkParamBean.class);;
        dbType = frameworkParam.getDbType();
        if(dbType==null){
            throw new ApplicationException("获取数据库失败");
        }
    }
    @Autowired
    FrameworkParamBean          frameworkParam;
    /**
     * 获得转换化的日期函数
     */
    public static String convertDateFormat(String colunName,String format){
        if("mysql".equals(dbType)){
            if(CommonConstants.FULL_DATE_TIME_FORMAT.equals(format)){
                return "date_format("+colunName+",'%Y-%m-%d %H:%i:%s')";
            }
            if(CommonConstants.SIMPLE_DATE_TIME_FORMAT.equals(format)){
                return "date_format("+colunName+",'%Y-%m-%d %H:%i')";
            }
            if(CommonConstants.SIMPLE_DATE_FORMAT.equals(format)){
                return "date_format("+colunName+",'%Y-%m-%d')";
            }
            if(CommonConstants.SIMPLE_DATE_MONTH_FORMAT.equals(format)){
                return "date_format("+colunName+",'%Y-%m')";
            }
        }
        return null;
    }
    
    /**
     * 获得转换化的日期函数
     */
    public static String convertIFNull(String colunName,String... NullShow){
        if("mysql".equals(dbType)){
        	return "IFNULL("+colunName+","+(NullShow==null?"0":NullShow[0])+")";
        }
        return null;
    }
    
    
    /**
     * 数据库关键字处理
     */
    	
    public static String reservedWordDeal(String sql,Object ...objects ){
        if("mysql".equals(dbType)){
            String[] randStr = { "ADD", "ANALYZE","ASC","BETWEEN","BLOB","CALL","CHANGE","CHECK"};
            for (int i = 0; i < randStr.length; i++) {
                sql=sql.replaceAll (randStr[i], ("`"+randStr[i]+"`")); 
            }  
        }
      
        return sql;
  }
}
