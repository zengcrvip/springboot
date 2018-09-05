package com.example.demo.epcc.convertors;

/**
 * @Description:占位符转换器接口
 * @Author: changrong.zeng
 * @Date: Created in 14:16 2018/9/4 .
 */
public interface PlaceHolderConvertor {
    /**
     * 多数据分割标示
     */
    public static final String MULTI_SPLIT = ",";

    public String convert(Object obj, String placeHolder) throws Exception;
}
