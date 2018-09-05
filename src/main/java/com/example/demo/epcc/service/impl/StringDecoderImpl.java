package com.example.demo.epcc.service.impl;

import com.example.demo.epcc.convertors.Msg2MapParser;
import com.example.demo.epcc.convertors.PlaceHolderConvertor;
import com.example.demo.epcc.model.EpccTxn;
import com.example.demo.epcc.service.StringDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 15:20 2018/9/4 .
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "decoder")
public class StringDecoderImpl implements StringDecoder {
    private Map<String, String> propTemplates;
    public void setPropTemplates(Map<String, String> propTemplates) {
        this.propTemplates = propTemplates;
    }


    @Autowired
    @Qualifier("xmlMsg2MapParser4Nucc")
    private Msg2MapParser msg2MapParser;

    private Map<String, PlaceHolderConvertor> placeHolderConvertors;

    private Map<String, Properties> propsCache = new HashMap<String, Properties>();



    @Override
    public Map decode(String rspMsg, EpccTxn epccTxn) throws Exception {
        String propNames = propTemplates.get(epccTxn.getRspMsgTp());
        String[] propName = propNames.split(",");
        Map<String, String> entityMap = new HashMap<String, String>();
        for (String name : propName) {
            Properties prop = getProperties(name);
            String parserType = prop.getProperty("parserType");
//            Msg2MapParser parser = msg2MapPasers.get(parserType);
            msg2MapParser.parse(epccTxn, prop, rspMsg, entityMap, placeHolderConvertors);
        }
        return entityMap;
    }

    private Properties getProperties(String propName) throws IOException {
        Properties properties = propsCache.get(propName);
        if(properties == null) {
            properties = loadProperty(propName);
            propsCache.put(propName, properties);
        }
        return properties;
    }

    private Properties loadProperty(String propName) throws IOException {
        InputStream ins = null;
        Properties props = new Properties();
        try {
            ins = this.getClass().getResourceAsStream(propName);
            props.load(ins);
        }finally {
            if(ins != null) {
                ins.close();
            }
        }
        return props;
    }
}
