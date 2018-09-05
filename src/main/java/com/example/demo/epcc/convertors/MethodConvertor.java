package com.example.demo.epcc.convertors;
import com.example.demo.epcc.model.EpccTxn;
import com.example.demo.epcc.protocol.ConvertMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description:方法转换器
 * 不同的报文类型，调用不同的方法转换器，通过xml的方式进行配置
 * @Author: changrong.zeng
 * @Date: Created in 14:16 2018/9/4 .
 */
@Component("methodConvertor")
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "convert")
public class MethodConvertor implements PlaceHolderConvertor{

    @Autowired
    private Map<String, ConvertMethod> convertMethodMap;


    @Override
    public String convert(Object obj, String placeHolder) throws Exception {
        EpccTxn debitTxn = (EpccTxn) obj;
        //获取报文类型
        String msgTp = debitTxn.getMsgTp();
        ConvertMethod convertMethod = null;
        //取到该报文类型的转换器对象
        if(msgTp.equals("epcc.201.001.01")){
             convertMethod = convertMethodMap.get("epccPaymentRequestConvertMethod");
        }

        Class<?> clazz = convertMethod.getClass();
        Method method = clazz.getMethod(placeHolder, EpccTxn.class);
        //调用该转换器的方法取值
        return (String) method.invoke(convertMethod, new Object[] { debitTxn });
    }
}
