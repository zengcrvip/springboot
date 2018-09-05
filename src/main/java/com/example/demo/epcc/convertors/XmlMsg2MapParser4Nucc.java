package com.example.demo.epcc.convertors;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;


import com.example.demo.epcc.model.EpccTxn;
import com.example.demo.util.XmlUtil;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @Description:标准xml格式报文解析工具类
 * @Author: changrong.zeng
 * @Date: Created in 17:55 2018/9/5 .
 */
@Component("xmlMsg2MapParser4Nucc")
public class XmlMsg2MapParser4Nucc extends Msg2MapParser  {
    @Override
    public void parse(EpccTxn epccTxn, Properties props, String message, Map<String, String> entityMap,
                      Map<String, PlaceHolderConvertor> placeHolderConvertors) throws Exception {
        XmlUtil xmlUtil = new XmlUtil(message);
        Iterator<Entry<Object, Object>> propsIt = props.entrySet().iterator();
        while (propsIt.hasNext()) {
            Entry<Object, Object> entry = propsIt.next();
            String proKey = (String) entry.getKey();
            if("parserType".equals(proKey)) {
                continue;
            }
            String propValue = (String) entry.getValue();
            if(propValue.contains("#")) {
                String childNode = propValue.substring(propValue.lastIndexOf("#") + 1, propValue.length());
                List<Node> aList = xmlUtil.getNodeList(propValue.substring(0, propValue.indexOf('#')));
                StringBuilder info = new StringBuilder();
                for (int i = 0; i < aList.size(); i++) {
                    NodeList nodeList = aList.get(i).getChildNodes();
                    for (int index = 0; index < nodeList.getLength(); index++) {
                        Node child = nodeList.item(index);
                        if(child.getNodeName().equals(childNode)) {
                            info.append(child.getFirstChild().getNodeValue());
                            info.append("&");
                        }
                    }
                }
                entityMap.put(proKey, info.toString());
            }else {
                String value = xmlUtil.getValue(propValue);
                if(checkFieldInfo(epccTxn, proKey, propValue, value, placeHolderConvertors)) {
                    continue;
                }
                entityMap.put(proKey, value);
            }
        }
    }
}
