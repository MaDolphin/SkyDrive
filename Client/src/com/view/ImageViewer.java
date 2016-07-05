package com.view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class ImageViewer extends JFrame {
    private JLabel label;
    private JFileChooser chooser;
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 700;

    public ImageViewer(String filePath) {
        super();
        setTitle("ͼƬ���");
        //setSize(this.DEFAULT_WIDTH,this.DEFAULT_HEIGHT);

        label = new JLabel();
        add(label);

        this.chooser = new JFileChooser();//java�ṩ���ļ�ѡ����
        chooser.setCurrentDirectory(new File("."));//���õ�ǰ���·��

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("�ļ�");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("��ͼƬ");
        menu.add(openItem);

        if(filePath != null){
            label.setIcon(new ImageIcon(filePath));
        }

        openItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int result = chooser.showOpenDialog(null);//�����ļ�ѡ��Ի���
                if(result==JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getPath();
                    label.setIcon(new ImageIcon(name));

                }

            }

        });

        JMenuItem exitItem = new JMenuItem("�˳�");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                //System.exit(0);
            }

        });
    }

}