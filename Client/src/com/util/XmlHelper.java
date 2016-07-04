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
 * Created by 珏 on 2016/7/3.
 */
public class XmlHelper {
    public static List<String> xmlElements(String xmlDoc,String opttype) {
        List<String> filenames=new ArrayList<String>();
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            Element root = doc.getRootElement();
            //System.out.println(root.getName());//输出根元素的名称（测试）
            //得到根元素所有子元素的集合
            List node = root.getChildren();
            Element et = null;
            for(int i=0;i<node.size();i++) {
                et = (Element) node.get(i);//循环依次得到子元素
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
