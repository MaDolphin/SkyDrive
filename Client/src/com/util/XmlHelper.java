package com.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by �� on 2016/7/3.
 */
public class XmlHelper {
    public static List<String> xmlElements(String xmlDoc,String opttype) {
        List<String> filenames=new ArrayList<String>();
        //����һ���µ��ַ���
        StringReader read = new StringReader(xmlDoc);
        //�����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����
        InputSource source = new InputSource(read);
        //����һ���µ�SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //ͨ������Դ����һ��Document
            Document doc = sb.build(source);
            Element root = doc.getRootElement();
            //System.out.println(root.getName());//�����Ԫ�ص����ƣ����ԣ�
            //�õ���Ԫ��������Ԫ�صļ���
            List node = root.getChildren();
            Element et = null;
            for(int i=0;i<node.size();i++) {
                et = (Element) node.get(i);//ѭ�����εõ���Ԫ��
                if (opttype.equals("file")) {
                    String type = et.getAttributeValue("type");
                    if ("file".equals(type))
                        filenames.add(et.getChild("name").getText() +
                                "." + et.getChild("ext").getText());
                }
                if (opttype.equals("dir")) {
                    String type = et.getAttributeValue("type");
                    if ("dir".equals(type))
                        filenames.add(et.getChild("name").getText());
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filenames;
    }
}
