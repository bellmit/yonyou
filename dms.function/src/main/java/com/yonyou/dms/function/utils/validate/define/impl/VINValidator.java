
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : VINValidator.java
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

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.validate.define.VIN;

/**
* 执行VIN 码校验
* @author zhangxc
* @date 2016年9月26日
*/

public class VINValidator implements ConstraintValidator<VIN, String>{
    
    private static final Map<Character, Integer> kv = new HashMap<>();
    private static final Map<Integer, Integer> wv = new HashMap<>();
    
    /**
     * 执行初始化
     */
    static {
        for (int i = 0; i < 10; i++) {
            kv.put(String.valueOf(i).charAt(0), i);
        }
 
        kv.put('a', 1);
        kv.put('b', 2);
        kv.put('c', 3);
        kv.put('d', 4);
        kv.put('e', 5);
        kv.put('f', 6);
        kv.put('g', 7);
        kv.put('h', 8);
        kv.put('j', 1);
        kv.put('k', 2);
        kv.put('l', 3);
        kv.put('m', 4);
        kv.put('n', 5);
        kv.put('p', 7);
        kv.put('q', 8);
        kv.put('r', 9);
        kv.put('s', 2);
        kv.put('t', 3);
        kv.put('u', 4);
        kv.put('v', 5);
        kv.put('w', 6);
        kv.put('x', 7);
        kv.put('y', 8);
        kv.put('z', 9);
 
        wv.put(1, 8);
        wv.put(2, 7);
        wv.put(3, 6);
        wv.put(4, 5);
        wv.put(5, 4);
        wv.put(6, 3);
        wv.put(7, 2);
        wv.put(8, 10);
        wv.put(10, 9);
        wv.put(11, 8);
        wv.put(12, 7);
        wv.put(13, 6);
        wv.put(14, 5);
        wv.put(15, 4);
        wv.put(16, 3);
        wv.put(17, 2);
    }
    
    /**
     * VIN 码校验初始化
    * @author zhangxc
    * @date 2016年9月26日
    * @param constraintAnnotation
    * (non-Javadoc)
    * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(VIN constraintAnnotation) {
    }

    /**
     * 校验是否有效
    * @author zhangxc
    * @date 2016年9月26日
    * @param value
    * @param context
    * @return
    * (non-Javadoc)
    * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String vin, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(vin)) {
            return true;
        } else if (vin.trim().length() == 17) {
            vin = vin.trim().toLowerCase();
            char[] codes = vin.toCharArray();
 
            int resultInCode = 0;
            if ("0123456789".contains(vin.subSequence(8, 9))) {
                resultInCode = Integer
                        .valueOf(vin.subSequence(8, 9).toString());
            } else {
                if ("x".equals(vin.subSequence(8, 9))) {
                    resultInCode = 10;
                }else{
                    return false;
                }
            }
            int total = 0;
            for (int i = 1; i < codes.length + 1; i++) {
                char code = codes[i - 1];
 
                if (kv.containsKey(code)) {
                    if (9 == i) {
                        continue;
                    } else {
                        total += kv.get(code) * wv.get(i);
                    }
                } else {
                    return false;
                }
            }
            return resultInCode == total % 11;
        } else {
            return false;
        }
    }

}
