package com.example.demo.util;

/**
 * @author: zegncr
 * @description:
 * @date: 2018-12-13 17:48
 */

import com.example.demo.exception.ServiceException;
import enums.ErrorEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.Collection;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Assert {

    public static void isTrue(boolean expression, ErrorEnum errorEnum) {
        isTrue(expression, errorEnum, null);
    }

    public static void isTrue(boolean expression, ErrorEnum errorEnum, String extendMsg) {
        if (!expression) {
            throw new ServiceException(errorEnum.getCode(), errorEnum.getMsg() + (null == extendMsg ? "" : ":" + extendMsg));
        }
    }

    public static void isFalse(boolean expression, ErrorEnum errorEnum) {
        isFalse(expression, errorEnum, null);
    }

    public static void isFalse(boolean expression, ErrorEnum errorEnum, String extendMsg) {
        if (expression) {
            throw new ServiceException(errorEnum.getCode(), errorEnum.getMsg() + (null == extendMsg ? "" : ":" + extendMsg));
        }
    }

    public static void isNull(@Nullable Object object, ErrorEnum errorEnum) {
        isNull(object, errorEnum, null);
    }

    public static void isNull(@Nullable Object object, ErrorEnum errorEnum, String extendMsg) {
        if (object != null) {
            throw new ServiceException(errorEnum.getCode(), errorEnum.getMsg() + (null == extendMsg ? "" : ":" + extendMsg));
        }
    }

    public static void notNull(@Nullable Object object, ErrorEnum errorEnum) {
        notNull(object, errorEnum, null);
    }

    public static void notNull(@Nullable Object object, ErrorEnum errorEnum, String extendMsg) {
        if (object == null) {
            throw new ServiceException(errorEnum.getCode(), errorEnum.getMsg() + (null == extendMsg ? "" : ":" + extendMsg));
        }
    }

    public static void isEmpty(Collection collection, ErrorEnum errorEnum) {
        isEmpty(collection, errorEnum, null);
    }

    public static void isEmpty(Collection collection, ErrorEnum errorEnum, String extendMsg) {
        if (collection != null && !collection.isEmpty()) {
            throw new ServiceException(errorEnum.getCode(), errorEnum.getMsg() + (null == extendMsg ? "" : ":" + extendMsg));
        }
    }

    public static void notEmpty(Collection collection, ErrorEnum errorEnum) {
        notEmpty(collection, errorEnum, null);
    }

    public static void notEmpty(Collection collection, ErrorEnum errorEnum, String extendMsg) {
        if (collection == null || collection.isEmpty()) {
            throw new ServiceException(errorEnum.getCode(), errorEnum.getMsg() + (null == extendMsg ? "" : ":" + extendMsg));
        }
    }

    public static void isBlank(String str, ErrorEnum errorEnum) {
        isBlank(str, errorEnum, null);
    }

    public static void isBlank(String str, ErrorEnum errorEnum, String extendMsg) {
        if (str != null && !str.isEmpty()) {
            throw new ServiceException(errorEnum.getCode(), errorEnum.getMsg() + (null == extendMsg ? "" : ":" + extendMsg));
        }
    }

    public static void notBlank(String str, ErrorEnum errorEnum) {
        notBlank(str, errorEnum, null);
    }

    public static void notBlank(String str, ErrorEnum errorEnum, String extendMsg) {
        if (str == null || str.isEmpty()) {
            throw new ServiceException(errorEnum.getCode(), errorEnum.getMsg() + (null == extendMsg ? "" : ":" + extendMsg));
        }
    }
}
