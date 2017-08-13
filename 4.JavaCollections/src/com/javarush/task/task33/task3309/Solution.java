package com.javarush.task.task33.task3309;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) {

        String xmlStr = "";

        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal(obj, sw);

            xmlStr = sw.toString();
/*
            StringBuffer sb = new StringBuffer(xmlStr);

            int pos = 0;
            int len = sb.length();
            String fixedTagName = "<" + tagName + ">";
            String fixedCloseTagName = "</" + tagName + ">";
            String fixedComment = "\n" + "<!--" + comment + "-->" + "\n";
            int fixedCommentLen = fixedComment.length();
            int fixedCloseTagNameLen = fixedCloseTagName.length();
            String notBeforeTag = "CDATA";
            int pos2 = 0, pos3 = 0;

            while ((pos = sb.lastIndexOf(fixedTagName, len-pos)) > 0) {
                String subStr = sb.substring(pos);
                if (subStr.contains(fixedCloseTagName))
                {
                    pos2 = subStr.indexOf(fixedCloseTagName);
                    pos3 = subStr.lastIndexOf(notBeforeTag, pos2);
                    if (pos3 == -1)
                    {
                        sb.insert(pos - 1, fixedComment);
                    }
                }
                //sb.insert(pos - 1, fixedComment);
            }

            xmlStr = sb.toString();
*/
            Document doc = convertXMLStringToDocument(xmlStr);

            Element element = doc.getDocumentElement();
            NodeList nodeList = doc.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeName().equals(tagName)) {
                    Comment commentElement = doc.createComment(comment);
                    element.insertBefore(commentElement, node);
                }
                replaceTextWithCDATA(node, doc);
            }

            xmlStr = convertDocumentToXMLString(doc);


        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return xmlStr;
    }

    private static String convertDocumentToXMLString(Document doc) {
        try {
            doc.setXmlStandalone(false);
            StringWriter writer = new StringWriter();
            TransformerFactory
                    .newInstance()
                    .newTransformer()
                    .transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (TransformerException ignore) {
        }
        return null;
    }

    private static Document convertXMLStringToDocument(String xmlStr) {
        try {
            return DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xmlStr)));
        } catch (Exception ignore) {
        }
        return null;
    }

    private static void replaceTextWithCDATA(Node node, Document doc) {
        if ((node.getNodeType() == 3) && (Pattern.compile("[<>&'\"]").matcher(node.getTextContent()).find())) {
            Node cnode = doc.createCDATASection(node.getNodeValue());
            node.getParentNode().replaceChild(cnode, node);
        }

        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            replaceTextWithCDATA(nodeList.item(i), doc);
        }
    }

    public static void main(String[] args) {

        //Integer intObj = new Integer(1);
        Test test = new Test();

        String str = toXmlWithComment(test, "id", "my additional comment");
        System.out.println(str);
    }

    @XmlRootElement
    public static class Test
    {
        public int id = 5;
    }
}
