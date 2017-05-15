
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : MaxPrecisionValidator.java
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

import java.text.DecimalFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yonyou.dms.function.utils.validate.define.MaxPrecision;

/**
* 最大小数位数校验
* @author zhangxc
* @date 2016年9月27日
*/

public class MaxPrecisionValidator implements ConstraintValidator<MaxPrecision, Number>{
    int maxPrecison;
    
    @Override
    public void initialize(MaxPrecision constraintAnnotation) {
        this.maxPrecison = constraintAnnotation.value();
    }

    /**
     * 执行小数位数校验
    * @author zhangxc
    * @date 2016年9月27日
    * @param value
    * @param context
    * @return
    * (non-Javadoc)
    * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(Number number, ConstraintValidatorContext context) {
        //如果为空，则返回true
        if (number==null) {
            return true;
        }
        if(this.maxPrecison ==-1 ){
            return true;
        }
        DecimalFormat df = new DecimalFormat("0.########");
        String formatValue = df.format(number);
        String[] digitArray = formatValue.split("\\.");
        if(digitArray.length<2){
            return true;
        }
        int precisonLength = digitArray[1].length();
        //如果小数位数超过最大小数
        if(precisonLength>this.maxPrecison){
            return false;
        }
        return true;
    }

}
