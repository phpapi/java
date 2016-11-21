package com.caozj.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.caozj.framework.util.common.XmlUtil;

public class TextRootXML {

  public static void main(String[] args) throws ParserConfigurationException, SAXException,
      IOException, XPathExpressionException, TransformerException {
    String file =
        "D:\\local\\develop2.0\\SmartAs\\target\\depoly\\xxx-web\\src\\main\\resources\\config\\root-context.xml";
    Document doc = XmlUtil.parseXML(file);
    Element root = doc.getDocumentElement();
//    Element c =  (Element)XmlUtil.selectSingleNode("//jee:jndi-lookup[@id='dataSource']", root);
//    if(c==null){
//      System.out.println("********no node*********");
//    }else{
//      System.out.println( c.getNodeName());
//    }
    
    // System.out.println(XmlUtil.toXml(root));
//    NodeList nodeList = XmlUtil.selectNodes("/beans/beans", root);
//    for (int i = 0; i < nodeList.getLength(); i++) {
//      System.out.println("************" + i + "***********beans***");
//      Node node = nodeList.item(i);
//      System.out.println(node.getChildNodes().getLength());
//      for (int j = 0; j < node.getChildNodes().getLength(); j++) {
//        Node c = node.getChildNodes().item(j);
//        System.out.println(c.getNodeName() + "," + c.getNodeValue() + "," + c.getNodeType());
//      }
//    }

    // for(int i = 0;i<nodeList.getLength();i++){
    // Node node = nodeList.item(i);
    // System.out.println( node.getFirstChild().getNodeName());
    // Element context= (Element) XmlUtil.selectSingleNode("/bean", node);
    // if(context!=null){
    // System.out.println(context.getAttribute("class"));
    // }
    // }
    // Element a =(Element) XmlUtil.selectSingleNode("/beans/*/bean", root);
    // if(a!=null){
    // System.out.println(a.getAttribute("id"));
    // }else{
    // System.out.println("1");
    // }
    // Element jndi =
    // (Element) XmlUtil.selectSingleNode("/beans/beans/jee:jndi-lookup[id='dataSource']", root);
    // if (jndi == null) {
    // jndi = doc.createElement("jee:jndi-lookup");
    // jndi.setAttribute("id", "dataSource");
    // jndi.setAttribute("jndi-name", "jndi-name");
    // Element context =
    // (Element) XmlUtil.selectSingleNode("/beans/beans/context:property-placeholder", root);
    // NodeList nodeList = root.getChildNodes();
    // for(int i =0;i<nodeList.getLength();i++){
    // Element e = (Element)nodeList.item(i);
    // }
    // root.appendChild(jndi);
    // } else {
    // jndi.setAttribute("jndi-name", "jndi-name" + System.currentTimeMillis());
    // }
    // XmlUtil.saveXML(file, doc);
  }
}
