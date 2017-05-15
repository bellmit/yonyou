
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : MaxDigitValidator.java
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

import com.yonyou.dms.function.utils.validate.define.MaxDigit;

/**
* 最大整数位校验
* @author zhangxc
* @date 2016年9月27日
*/

public class MaxDigitValidator implements ConstraintValidator<MaxDigit, Number>{
    //最大整数位数
    int maxDight;
    
    @Override
    public void initialize(MaxDigit constraintAnnotation) {
        this.maxDight = constraintAnnotation.value();
    }

    /**
     * 
    * @author zhangxc
    * @date 2016年9月27日
    * @param number
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
        if(this.maxDight ==-1 ){
            return true;
        }
        DecimalFormat df = new DecimalFormat("0.########");
        String formatValue = df.format(number);
        String[] digitArray = formatValue.split("\\.");
        int digitLength = digitArray[0].length();
        if(digitLength>this.maxDight){
            return false;
        }
        return true;
    }

}
