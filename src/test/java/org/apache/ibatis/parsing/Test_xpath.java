package org.apache.ibatis.parsing;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

/**
 * https://www.cnblogs.com/zengda/p/4765854.html
 * XPath即为XML路径语言（XML Path Language），它是一种用来确定XML文档中某部分位置的语言。
 * XPath基于XML的树状结构，提供在数据结构树中找寻节点的能力。
 */
public class Test_xpath {

    public static void main(String[] args) throws ParserConfigurationException,
            SAXException, IOException, XPathExpressionException {

        // 解析文件，生成document对象
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document document = builder.parse(Test_xpath.class.getClassLoader().getResourceAsStream("bookstore.xml"));
//        Document document = builder.parse(Resources.getResourceAsStream("bookstore.xml"));

        // 生成XPath对象
        XPath xpath = XPathFactory.newInstance().newXPath();

        // 获取节点值
        String webTitle = (String) xpath.evaluate(
                "/bookstore/book[@category='WEB']/title/text()", document,
                XPathConstants.STRING);
        System.out.println(webTitle);

        System.out.println("===========================================================");

        // 获取节点属性值
        String webTitleLang = (String) xpath.evaluate(
                "/bookstore/book[@category='WEB']/title/@lang", document,
                XPathConstants.STRING);
        System.out.println(webTitleLang);

        System.out.println("===========================================================");

        // 获取节点对象
        Node bookWeb = (Node) xpath.evaluate(
                "/bookstore/book[@category='WEB']", document,
                XPathConstants.NODE);
        System.out.println(bookWeb.getNodeName());

        System.out.println("===========================================================");

        // 获取节点集合
        NodeList books = (NodeList) xpath.evaluate("/bookstore/book", document,
                XPathConstants.NODESET);
        for (int i = 0; i < books.getLength(); i++) {
            Node book = books.item(i);
            System.out.println(xpath.evaluate("@category", book,
                    XPathConstants.STRING));
        }

        System.out.println("===========================================================");
    }

}