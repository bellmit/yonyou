
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : ZipCodeValidator.java
*
* @Author : zhangxc
*
* @Date : 2016年9月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月26日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.utils.validate.define.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.validate.define.ZipCode;

/**
* 执行邮编校验
* @author zhangxc
* @date 2016年9月26日
*/

public class ZipCodeValidator implements ConstraintValidator<ZipCode, String>{

    @Override
    public void initialize(ZipCode constraintAnnotation) {
    }

    
    @Override
    public boolean isValid(String zipCode, ConstraintValidatorContext context) {
        //如果为空，则返回true
        if (StringUtils.isBlank(zipCode)) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[1-9]\\d{5}(?!\\d)$");
        Matcher matcher = pattern.matcher(zipCode);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }

}
