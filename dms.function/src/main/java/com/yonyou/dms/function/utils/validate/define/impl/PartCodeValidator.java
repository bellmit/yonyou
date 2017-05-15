
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : PartCodeValidator.java
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
import com.yonyou.dms.function.utils.validate.define.PartCode;

/**
* 配件号校验
* @author zhangxc
* @date 2016年9月26日
*/

public class PartCodeValidator implements ConstraintValidator<PartCode, String>{

    @Override
    public void initialize(PartCode constraintAnnotation) {
    }

    /**
     * 执行配件号校验
    * @author zhangxc
    * @date 2016年9月26日
    * @param value
    * @param context
    * @return
    * (non-Javadoc)
    * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String partCode, ConstraintValidatorContext context) {
        //如果为空，则返回true
        if (StringUtils.isBlank(partCode)) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[^\u4E00-\u9FA5]{1,20}$");
        Matcher matcher = pattern.matcher(partCode);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }

}
