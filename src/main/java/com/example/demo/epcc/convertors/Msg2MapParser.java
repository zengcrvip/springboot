package com.example.demo.epcc.convertors;

import com.example.demo.epcc.model.AppBizException;
import com.example.demo.epcc.model.EpccTxn;

import java.util.Properties;
import java.util.Map;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 15:24 2018/9/4 .
 */
public abstract class Msg2MapParser {
    protected static final String PARSER_TYPE = "parserType";

    protected static final String DATA_SOURCE = "dataSource";

    /**
     * 针对银行正常和非正常的报文格式不一样时，需要做此配置
     */
    public static final String MSG_STATS = "msgStats";

    public abstract void parse(EpccTxn epccTxn, Properties props, String message, Map<String, String> entityMap,
                               Map<String, PlaceHolderConvertor> placeHolderConvertors) throws Exception;

    /**
     * 提供银行返回信息的校验功能
     *
     * @param
     * @param proKey
     * @param propValue
     * @param value
     * @param placeHolderConvertors
     * @return
     * @throws Exception
     */
    protected boolean checkFieldInfo(EpccTxn epccTxn, String proKey, String propValue, String value,
                                     Map<String, PlaceHolderConvertor> placeHolderConvertors) throws Exception {
        if(proKey.startsWith("@")) {
            String directive = proKey.substring(0, 2);
            PlaceHolderConvertor convertor = placeHolderConvertors.get(directive);
            String convertedContent = convertor.convert(epccTxn, proKey.substring(2));
            if(!value.equals(convertedContent)) {
                throw new AppBizException("R.XML.0002", "field=" + propValue + " ,sendValue="
                        + convertedContent + ",receiveValue=" + value);
            }
            return true;
        }
        return false;
    }
}
