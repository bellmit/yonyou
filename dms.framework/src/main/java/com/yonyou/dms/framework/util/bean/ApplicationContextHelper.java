
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : ApplicationContextHelper.java
*
* @Author : zhangxc
*
* @Date : 2016年6月30日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年6月30日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.util.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
* 定义获取spring 对象的方法
* @author zhangxc
* @date 2016年6月30日
*/
@Component
public class ApplicationContextHelper implements ApplicationContextAware{
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextHelper.class);
    
    //Spring应用上下文环境  
    private static ApplicationContext applicationContext = null;     
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("执行了Bean 的初始化");
        ApplicationContextHelper.applicationContext = applicationContext;
    }
    
    /** 
     * @return ApplicationContext 
     */  
     public static ApplicationContext getApplicationContext() {  
       return applicationContext;  
     }  
      
     /** 
     * 获取对象   
     * @param name 
     * @return Object 一个以所给名字注册的bean的实例 
     * @throws BeansException 
     */  
     public static Object getBeanByName(String name) {  
       return applicationContext.getBean(name);  
     } 
     
     /** 
      * 获取对象   
      * @param name 
      * @return Object 一个以所给名字注册的bean的实例 
      * @throws BeansException 
      */  
      public static <T> T getBeanByType(Class<T> beanType) {  
        return applicationContext.getBean(beanType);
      }  
      
     /** 
     * 获取类型为requiredType的对象 
     * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException） 
     * @param name       bean注册名 
     * @param requiredType 返回对象类型 
     * @return Object 返回requiredType类型对象 
     * @throws BeansException 
     */  
     public static <T> Object getBeanByName(String name, Class<T> beanType) {
       return applicationContext.getBean(name, beanType);  
     }  
      
      
     /** 
     * @param name 
     * @return Class 注册对象的类型 
     * @throws NoSuchBeanDefinitionException 
     */  
     public static Class getType(String name){ 
       return applicationContext.getType(name);  
     }  
      
     /** 
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名   
     * @param name 
     * @return 
     * @throws NoSuchBeanDefinitionException 
     */  
     public static String[] getAliases(String name){  
       return applicationContext.getAliases(name);  
     }  
}
