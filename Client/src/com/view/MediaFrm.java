package com.view;

import com.dao.Dao;
import com.util.MyPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by xfcq on 2016/7/5.
 */
public class MediaFrm extends JFrame {
    private JLabel lbl=new JLabel("File Name:");
    private JTextField text=new JTextField();
    private JButton btn=new JButton("Stop");
    private JPanel jp;
    private String filePath;
    private MyPlayer myPlayer = new MyPlayer();
    public MediaFrm(String filePath,String filename) throws Exception {
        this.filePath=filePath;
        jp=(JPanel)this.getContentPane();
        jp.setLayout(null);
        lbl.setBounds(40,40,100,30);
        text.setBounds(120,40,80,30);
        btn.setBounds(80,100,80,30);
        text.setText(filename);
        jp.add(lbl);
        jp.add(text);
        jp.add(btn);
//        this.setAlwaysOnTop(true);
//        this.setSize(300, 200);
//        this.setVisible(true);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myPlayer.StartPlayer(filePath);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showDialog(new JLabel(), "选择上传文件");
        File file = jfc.getSelectedFile();
        File soundFile = new File(file.getAbsolutePath());
        MediaFrm mediaFrm = new MediaFrm(file.getAbsolutePath(),"111.mp3");
    }
}
