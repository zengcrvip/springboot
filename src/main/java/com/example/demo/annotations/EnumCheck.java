package com.example.demo.annotations;

/**
 * @Description:枚举值校验注解，添加在参数属性上，校验参数值是否在枚举范围内
 * @Author: changrong.zeng
 * @Date: Created in 下午4:03 2018/11/19 .
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumCheck {

    String message() default "枚举值不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> value();
}
