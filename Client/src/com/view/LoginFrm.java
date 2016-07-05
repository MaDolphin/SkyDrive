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
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dao.*;
public class LoginFrm  extends JFrame{
    private JLabel lblID=new JLabel("用户名");
    private JLabel lblPwd=new JLabel("密码");
    private JTextField txtID=new JTextField();
    private JPasswordField txtPwd=new JPasswordField();
    private JButton btnOK=new JButton("登录");
    private JButton btnCancel=new JButton("取消");
    private int userNo;
    private Dao dao = new Dao();
    public LoginFrm(){
        JPanel jp=(JPanel)this.getContentPane();
        jp.setLayout(new GridLayout(3,2));
        jp.add(lblID);jp.add(txtID);
        jp.add(lblPwd);jp.add(txtPwd);
        jp.add(btnOK);jp.add(btnCancel);

        Properties prop = System.getProperties();
        String path = "C:\\Users\\"+prop.getProperty("user.name")+"\\Downloads"+"\\temp";
        File file =new File(path);
        //如果文件夹不存在则创建
        if (!file.exists()  && !file.isDirectory())
        {
            file.mkdir();
        }

        //btnOK.setIcon(new ImageIcon(JButton.class
        //    .getResource("/images/2-1209221U445-50.png")));// 为按钮增加图标
        btnOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btnOK_Clicked();
            }
        });
    }

    //验证登陆
    public void Login(String uid,String upwd) {
        String rvalue = dao.CheckUser(uid,upwd);
        rvalue = rvalue.substring(0, rvalue.length() - 2);
        if(!rvalue.equals("error")) {
            System.out.println(rvalue);
            userNo = Integer.valueOf(rvalue.toString());
        }
    }


    private void btnOK_Clicked(){
        String uid=txtID.getText().trim();
        String upwd=txtPwd.getText().trim();
        System.out.println(uid+" "+upwd);
        this.Login(uid,upwd);
        if(userNo==0){
            JOptionPane.showMessageDialog(this,"登陆失败");
        }
        else {
            //登录成功 查找文件，并创建主界面
            Dao dao=new Dao();
            String rvalue = dao.ListFiles("listfiles",userNo , 0);
            rvalue = rvalue.substring(0, rvalue.length() - 2);
            this.setVisible(false);
            JFrame.setDefaultLookAndFeelDecorated(true);
            MainFrm frm=new MainFrm(rvalue,userNo,0,dao);
            frm.setSize(1200,600);
            frm.setVisible(true);
        }

    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        LoginFrm frm=new LoginFrm();
        frm.setSize(600,200);
        frm.setVisible(true);
    }
}
