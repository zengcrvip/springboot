package com.example.demo.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description: 枚举校验器
 * @Author: changrong.zeng
 * @Date: Created in 下午4:06 2018/11/19 .
 */
public class EnumValidator implements ConstraintValidator<EnumCheck, String> {
    @SuppressWarnings("rawtypes")
    private Class enumType;

    @Override
    public void initialize(EnumCheck constraintAnnotation) {
        enumType = constraintAnnotation.value();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ((value == null || "".equals(value.replaceAll(" ", "")))) {
            return true;
        }
        try {
            Enum.valueOf(enumType, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
