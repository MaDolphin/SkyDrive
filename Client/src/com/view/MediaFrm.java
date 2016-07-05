package com.view;

import com.dao.Dao;
import com.util.MyPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created by xfcq on 2016/7/5.
 */
public class MediaFrm extends JFrame {
    private JLabel lbl=new JLabel("File Name:");
    private JTextField text=new JTextField("",20);
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
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myPlayer.StopThread();

            }
        });
        myPlayer.StartPlayer(filePath);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myPlayer.StopThread();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showDialog(new JLabel(), "Ñ¡ÔñÎÄ¼þ");
        File file = jfc.getSelectedFile();
        File soundFile = new File(file.getAbsolutePath());
        MediaFrm mediaFrm = new MediaFrm(file.getAbsolutePath(),"111.mp3");
    }
}
