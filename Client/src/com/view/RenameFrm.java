package com.view;

import com.dao.Dao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by çå on 2016/7/4.
 */
public class RenameFrm extends JFrame{
    private JLabel lbl=new JLabel("Rename");
    private JTextField text=new JTextField();
    private JButton btn=new JButton("OK");
    private Dao dao=new Dao();
    private JPanel jp;
    private int fileno;
    public RenameFrm(int Fileno){
        this.fileno=Fileno;
        jp=(JPanel)this.getContentPane();
        jp.setLayout(null);
        lbl.setBounds(40,40,100,30);
        text.setBounds(120,40,80,30);
        btn.setBounds(80,100,80,30);
        jp.add(lbl);
        jp.add(text);
        jp.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Btn_Ok();
            }
        });
    }

    public void Btn_Ok(){
        String result=dao.Rename("rename",fileno,text.getText());
        if(result.equals("error"))
            JOptionPane.showMessageDialog(this,"ÃüÃûÖØ¸´£¬ÇëÐÞ¸Ä");
        else
            this.setVisible(false);
    }
}
