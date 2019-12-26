package com.demo.xml;

import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class XmlUtil {

    public void parseXml(){
        String fileName = "D:\\data\\javaTest\\github\\java-code-snippet\\code-snippet\\pom.xml";
        SAXBuilder saxBuilder = new SAXBuilder();
        File file = new File(fileName);
        try {
            Document document = saxBuilder.build(file);
            Element rootNode = document.getRootElement();
            Namespace namespace = rootNode.getNamespace();
            List<Element> children = rootNode.getChildren();
            for (Element child : children) {
                log.info(child.getName());
            }
            Element node = rootNode.getChild("dependencies",namespace);
            List<Element> dependencyList = node.getChildren("dependency",namespace);
            for (int i1 = 0; i1 < dependencyList.size(); i1++) {
                Element element = dependencyList.get(i1);
                String artifactId = element.getChildText("artifactId",namespace);
                log.info(artifactId);
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseXml2(){
        String fileName = "D:\\data\\javaTest\\github\\java-code-snippet\\code-snippet\\file\\xml\\test.xml";
        SAXBuilder saxBuilder = new SAXBuilder();
        File file = new File(fileName);
        try {
            Document document = saxBuilder.build(file);
            Element rootElement = document.getRootElement();
            List<Element> list = rootElement.getChildren("author");
            for (Element element : list) {
                log.info(element.getChildText("firstname"));
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new XmlUtil().parseXml2();
    }
}
