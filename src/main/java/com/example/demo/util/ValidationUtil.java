package com.example.demo.util;

import com.example.demo.exception.ServiceException;
import enums.ResponseCode;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @Description: 内部参数校验工具
 * @Author: changrong.zeng
 * @Date: Created in 下午3:40 2018/11/19 .
 */
public class ValidationUtil {
    /**
     * 一般性校验，利用validator只校验输入参数的非空
     *
     * @param t
     */
    public static <T> void commonValidate(T t) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<T>> set = validator.validate(t);
        if (!CollectionUtils.isEmpty(set) ) {
            throw new ServiceException(ResponseCode.INVALID_PARAMETER_VALUE.getCode(),
                    set.iterator().next().getMessageTemplate());
        }
    }
}
