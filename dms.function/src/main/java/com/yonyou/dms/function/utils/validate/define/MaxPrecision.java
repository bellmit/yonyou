
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : MaxPrecision.java
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
	
package com.yonyou.dms.function.utils.validate.define;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.yonyou.dms.function.utils.validate.define.impl.MaxPrecisionValidator;

/**
* 最大小数位数
* @author zhangxc
* @date 2016年9月27日
*/
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxPrecisionValidator.class)
@Documented
public @interface MaxPrecision {
    int value() default -1;
    
    String message() default "{maxPrecisionValidate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
