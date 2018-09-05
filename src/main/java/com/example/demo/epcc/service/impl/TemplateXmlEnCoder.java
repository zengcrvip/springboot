package com.example.demo.epcc.service.impl;


import com.example.demo.epcc.convertors.PlaceHolderConvertor;
import com.example.demo.epcc.model.EpccTxn;
import com.example.demo.epcc.model.XmlTemplateConstants;
import com.example.demo.epcc.service.XmlEncoder;
import com.example.demo.util.FileUtil;
import com.example.demo.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 14:05 2018/9/4 .
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "template")
public class TemplateXmlEnCoder implements XmlEncoder {
    private static Logger logger = LogManager.getLogger(TemplateXmlEnCoder.class);

    private Map<String, String> xmlTemplates;
    public void setXmlTemplates(Map<String, String> xmlTemplates) {
        this.xmlTemplates = xmlTemplates;
    }

    private Map<String, String> templatesCache = new HashMap<String, String>();

    @Autowired
    private PlaceHolderConvertor placeHolderConvertor;

    private String commTemplate;

    private String charSet;

    @Value("${template.directiveLen}")
    private int directiveLen;

    @Value("${template.endFlagLen}")
    private int endFlagLen;

    @Value("${template.parseRegex}")
    private String parseRegex;

    @PostConstruct
    public void init() {
        for (Map.Entry<String, String> template : xmlTemplates.entrySet()) {
            InputStream input = null;
            try {
                input = getClass().getResourceAsStream(template.getValue());

                String templateContent = FileUtil.readContent(input, getCharSet());

                if (!StringUtils.isEmpty(commTemplate)) {
                    templateContent = commTemplate.replace(XmlTemplateConstants.OPEN_REPLACE_CHILD, templateContent);
                }

                templatesCache.put(template.getKey(), templateContent);

                logger.info("TxnType:[" + template.getKey() + "] template [" + template.getValue() + "] is loaded.");
            } catch (Exception e) {
                logger.error("TxnType:[" + template.getKey() + "] template [" + template.getValue() + "] can't load!",
                        e);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    @Override
    public String encode(EpccTxn epccTxn) throws Exception {
        String msgTp = epccTxn.getMsgTp();
        logger.info("encode msgTp=" + msgTp);
        String template = this.templatesCache.get(msgTp);
        String dataGram = null;

        if (StringUtils.isNotEmpty(template)) {
            dataGram = fillXmlPlaceHolder(epccTxn, template);
        }

        return dataGram;
    }

    private String fillXmlPlaceHolder(EpccTxn epccTxn, String template) throws Exception {
        logger.info("begin to fillXmlPlaceHolder...");
        StringBuilder dataGram = new StringBuilder(template);

        if (StringUtil.isEmpty(this.parseRegex)) {
            this.parseRegex = XmlTemplateConstants.PARSE_REGEX;
        }
        Pattern pattern = Pattern.compile(this.parseRegex);
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String placeHolder = matcher.group();
            String directive = placeHolder.substring(0, directiveLen);

//            PlaceHolderConvertor convertor = placeHolderConvertors.get(directive);

            String methodName = placeHolder.substring(this.directiveLen, placeHolder.length() - this.endFlagLen);
            String convertedContent = placeHolderConvertor.convert(epccTxn, methodName);
            logger.info("encode method=" + methodName + " value=" + convertedContent);
            if (StringUtils.isEmpty(convertedContent)) {
                convertedContent = "";
            }
            dataGram.replace(dataGram.indexOf(placeHolder), dataGram.indexOf(placeHolder) + placeHolder.length(),
                    convertedContent);
        }
        return dataGram.toString();
    }



    public String getCharSet() {
        if (StringUtil.isEmpty(this.charSet)) {

            this.charSet = "GBK";
        }

        return this.charSet;
    }

    public static void main(String[] args) {
        StringBuilder s = new StringBuilder("abcdefghijk");
        System.out.println(s.replace(s.indexOf("de"), s.indexOf("de") + 2, "000"));
        System.out.println(s);
    }
}
