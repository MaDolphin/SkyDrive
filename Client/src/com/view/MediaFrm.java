package com.view;

import com.dao.Dao;
import com.util.MyPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xfcq on 2016/7/5.
 */
public class MediaFrm extends JFrame {
    private JLabel lbl=new JLabel("File Name:");
    private JTextField text=new JTextField();
    private JButton btn=new JButton("Stop");
    private MyPlayer myPlayer = new MyPlayer();
    private JPanel jp;
    private String filePath;
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
        myPlayer.StartPlayer(filePath);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myPlayer = null;
                jp.setVisible(false);
            }
        });
    }
}
