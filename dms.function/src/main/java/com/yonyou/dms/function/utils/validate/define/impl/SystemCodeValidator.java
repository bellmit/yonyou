/**
 * 
 */
package com.yonyou.dms.function.utils.validate.define.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.validate.define.SystemCode;

/**
 * 代码验证
 * @author ZhengHe
 * @date 2017年1月19日
 */
public class SystemCodeValidator  implements ConstraintValidator<SystemCode, String>{

	@Override
	public void initialize(SystemCode constraintAnnotation) {
	}

	/**
     * 执行配件号校验
    * @author ZhengHe
    * @date 2017年1月19日
    * @param value
    * @param context
    * @return
    * (non-Javadoc)
    * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
	@Override
	public boolean isValid(String SystemCode, ConstraintValidatorContext context) {
		 //如果为空，则返回true
        if (StringUtils.isBlank(SystemCode)) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[\\d|a-zA-Z|-]{1,}$");
        Matcher matcher = pattern.matcher(SystemCode);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
	}

}
