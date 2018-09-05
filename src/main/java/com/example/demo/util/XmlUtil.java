package com.example.demo.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 17:57 2018/9/5 .
 */
public final class XmlUtil {

    private Document rootNode;

    public XmlUtil(String xmlStr) {
        try {
            StringReader sr = new StringReader(xmlStr);
            InputSource is = new InputSource(sr);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            rootNode = builder.parse(is);
        }catch (Exception e) {
            throw new IllegalArgumentException("xml init failed:\n" + xmlStr);
        }
    }

    public void resetRootNode(Document rootNode) {
        if(rootNode == null) {
            throw new IllegalArgumentException("root node cannot be null");
        }
        this.rootNode = rootNode;
    }

    public String getValue(String key) {
        Node node = getNode(key);
        if(node == null) {
            return "";
        }
        Node temp = node.getFirstChild();
        if(null == temp) {
            return "";
        }else {
            return temp.getNodeValue();
        }
    }

    public void setValue(String key, String value) {
        Node node = getNode(key);
        if(node == null) {
            return;
        }
        Node temp = node.getFirstChild();
        temp.setNodeValue(value);
    }

    public String getProperty(String key) {
        if(key == null) {
            return "";
        }
        int index = key.lastIndexOf('.');
        String nodeName = key.substring(0, index);
        Node node = getNode(nodeName);
        if(node == null) {
            return "";
        }
        String propertyName = key.substring(index + 1);
        NamedNodeMap attrs = node.getAttributes();
        Node attr = attrs.getNamedItem(propertyName);
        if(attr == null) {
            return "";
        }
        return attr.getNodeValue();
    }

    public boolean hasNode(String key) {
        Node node = getNode(key);
        if(node == null) {
            return false;
        }
        return true;
    }

    public Node getNode(String key) {
        if(key == null) {
            return null;
        }
        Node currentNode = rootNode;
        Node preNode = null;
        String[] nodeNameArr = key.split("\\.");
        for (String nodeName : nodeNameArr) {
            preNode = currentNode;
            NodeList nodes = currentNode.getChildNodes();
            int len = nodes.getLength();
            for (int j = 0; j < len; j++) {
                Node node = nodes.item(j);
                if(nodeName.equalsIgnoreCase(node.getNodeName())) {
                    currentNode = node;
                    break;
                }
            }
            if(currentNode == preNode) {
                return null;
            }
        }
        return currentNode;
    }

    public List<Node> getNodeList(String key) {
        List<Node> list = new ArrayList<Node>();
        if(key == null || key.trim().length() == 0) {
            return list;
        }
        Node currentNode = rootNode;
        Node preNode = null;
        String[] nodeNameArr = key.split("\\.");
        int count = nodeNameArr.length;
        for (int i = 0; i < count - 1; i++) {
            preNode = currentNode;
            NodeList nodes = currentNode.getChildNodes();
            int len = nodes.getLength();
            for (int j = 0; j < len; j++) {
                Node node = nodes.item(j);
                if(nodeNameArr[i].equalsIgnoreCase(node.getNodeName())) {
                    currentNode = node;
                    break;
                }
            }
            if(currentNode == preNode) {
                return list;
            }
        }
        NodeList nodes = currentNode.getChildNodes();
        int len = nodes.getLength();
        for (int j = 0; j < len; j++) {
            Node node = nodes.item(j);
            if(nodeNameArr[count - 1].equalsIgnoreCase(node.getNodeName())) {
                list.add(node);
            }
        }
        return list;
    }

    public Document getRootNode() {
        return rootNode;
    }

    public void setRootNode(Document rootNode) {
        this.rootNode = rootNode;
    }
}
