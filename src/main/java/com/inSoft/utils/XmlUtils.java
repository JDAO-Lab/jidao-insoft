package com.inSoft.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class XmlUtils {

    /**
     * 将 XML 数据转换为 Map 数据
     *
     * @param xmlData XML 数据字符串
     * @return 转换后的 Map 数据
     * @throws Exception 如果解析过程中发生错误
     */
    public static Map<String, Object> xmlToMap(String xmlData) throws Exception {
        Map<String, Object> map = new HashMap<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
        Document doc = db.parse(new InputSource(is));
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                map.put(element.getNodeName(), parseNode(element));
            }
        }
        return map;
    }

    /**
     * 递归解析 XML 节点
     *
     * @param node 当前节点
     * @return 解析后的值
     */
    private static Object parseNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            NodeList childNodes = element.getChildNodes();
            if (childNodes.getLength() == 1 && childNodes.item(0).getNodeType() == Node.TEXT_NODE) {
                return childNodes.item(0).getTextContent();
            } else if (childNodes.getLength() == 1 && childNodes.item(0).getNodeType() == Node.CDATA_SECTION_NODE) {
                return childNodes.item(0).getTextContent();
            } else {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        String key = childNode.getNodeName();
                        Object value = parseNode(childNode);
                        if (map.containsKey(key)) {
                            if (map.get(key) instanceof List) {
                                ((List<Object>) map.get(key)).add(value);
                            } else {
                                List<Object> list = new ArrayList<>();
                                list.add(map.get(key));
                                list.add(value);
                                map.put(key, list);
                            }
                        } else {
                            map.put(key, value);
                        }
                    }
                }
                return map.isEmpty() ? null : map;
            }
        }
        return null;
    }
}
