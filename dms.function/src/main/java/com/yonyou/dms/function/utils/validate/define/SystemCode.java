/**
 * 
 */
package com.yonyou.dms.function.utils.validate.define;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.yonyou.dms.function.utils.validate.define.impl.SystemCodeValidator;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SystemCodeValidator.class)
@Documented
public @interface SystemCode {
	String message() default "{systemCodeValidate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
