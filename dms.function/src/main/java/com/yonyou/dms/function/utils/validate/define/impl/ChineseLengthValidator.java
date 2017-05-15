
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : ChineseLengthValidator.java
*
* @Author : zhangxc
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.utils.validate.define.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.validate.define.ChineseLength;

/**
* 检查中文长度
* @author zhangxc
* @date 2016年9月27日
*/

public class ChineseLengthValidator implements ConstraintValidator<ChineseLength, String>{

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(ChineseLengthValidator.class);
    private int max;
    private int min;
    
    @Override
    public void initialize(ChineseLength constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
        logger.debug("length:"+constraintAnnotation.max()+";min"+constraintAnnotation.min());
    }

    /**
     * 执行校验
    * @author zhangxc
    * @date 2016年9月27日
    * @param value
    * @param context
    * @return
    * (non-Javadoc)
    * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //如果为空，则返回true
        if (StringUtils.isBlank(value)) {
            return true;
        }
        int byteLength = value.getBytes().length;
        if(max!=-1){
            if(byteLength>max){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("{lengthMaxValidate}").addConstraintViolation();
                return false;
            }
        }
        if(min !=-1){
            if(byteLength<min){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("{lengthMinValidate}").addConstraintViolation();
                return false;
            }
        }
        return true;
    }

}
