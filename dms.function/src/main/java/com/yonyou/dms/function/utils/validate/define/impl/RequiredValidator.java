
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : RequiredValidator.java
*
* @Author : zhangxc
*
* @Date : 2016年10月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月18日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.function.utils.validate.define.impl;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 对必填字段进行校验
 * 
 * @author zhangxc
 * @date 2016年10月18日
 */

public class RequiredValidator implements ConstraintValidator<Required, Object> {

    @Override
    public void initialize(Required constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        // 判断是字符串的空格的长度
        if (value instanceof String) {
            return ((String) value).trim().length() > 0;
        }
        // 如果是集合类型
        if (value instanceof Collection) {
            return !((Collection) value).isEmpty();
        }
        // 如果是数组
        if (value.getClass().isArray()) {
            return ((Object[]) value).length > 0;
        }
        return true;

    }
}
