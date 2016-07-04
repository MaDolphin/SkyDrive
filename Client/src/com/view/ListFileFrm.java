package com.view;
import javax.swing.*;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
public class ListFileFrm  extends JFrame{

    private JButton btnOK=new JButton("获取文件");
    private JPanel mainpane=new JPanel();
    public ListFileFrm(){
        this.setTitle("我的云盘");
        JPanel jp=(JPanel)this.getContentPane();
        jp.setLayout(new BorderLayout());
        JPanel toppane=new JPanel();
        toppane.add(btnOK);
        jp.add(toppane,BorderLayout.NORTH);
        jp.add(mainpane);
        btnOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btnOK_Clicked();
            }
        });
    }
    private List<String> xmlElements(String xmlDoc) {
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
            for(int i=0;i<node.size();i++){
                et = (Element) node.get(i);//循环依次得到子元素
                String type=et.getAttributeValue("type");
                if("file".equals(type))
                    filenames.add(et.getChild("name").getText());
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filenames;
    }
    private JPopupMenu initPopMenu(){
        JPopupMenu menu = new JPopupMenu();
        menu.add(new JMenuItem("复制"));
        menu.add(new JMenuItem("粘贴"));
        menu.add(new JMenuItem("删除"));
        menu.add(new JMenuItem("下载"));
        menu.add(new JMenuItem("重命名"));
        return menu;
    }
    private void dispFiles(String rvalue){
        List<String> filenames=xmlElements(rvalue);
        mainpane.removeAll();
        int WIDTH=127;
        int HEIGHT=116;
        for(String fname:filenames){

            //String imgpath="/icons/"+fname.substring(fname.lastIndexOf(".")+1).toUpperCase()+".png";
            String imgpath="/icons/PPT.png";
            final JLabel lbl=new JLabel();
            //lbl.setSize(WIDTH,HEIGHT);
            ImageIcon img=new ImageIcon(JLabel.class.getResource(imgpath));
            img.setImage(img.getImage().getScaledInstance(WIDTH,HEIGHT,Image.SCALE_DEFAULT));
            lbl.setIcon(img);
            lbl.setText(fname);
            lbl.setHorizontalTextPosition(JLabel.CENTER);
            lbl.setVerticalTextPosition(JLabel.BOTTOM);
            final JPopupMenu menu=initPopMenu();

            lbl.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        JLabel src=(JLabel)(e.getSource());
                        menu.show(mainpane,src.getX()+50,src.getY()+40);
                    }
                }
            });
            mainpane.add(lbl);
        }
        mainpane.validate();
    }
    public String listFiles(String opttype,String path) {
        String rvalue="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/file.action");
        // 创建参数队列
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("opttype", opttype));
        formparams.add(new BasicNameValuePair("path", path));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rvalue=EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rvalue;
    }
    private void btnOK_Clicked(){
        String rvalue=listFiles("listfiles","D:\\eclipse");
        rvalue=rvalue.substring(0,rvalue.length()-2);
        dispFiles(rvalue);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        ListFileFrm frm=new ListFileFrm();
        frm.setSize(1200,800);
        frm.setVisible(true);
    }
}
