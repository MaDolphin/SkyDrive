package com.view;

import com.dao.Dao;
import com.util.Md5CaculateUtil;
import com.util.XmlHelper;
import org.apache.http.client.methods.HttpPost;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;


public class MainFrm extends JFrame implements ActionListener {
    private JMenu m1 = new JMenu("ϵͳ����");
    private JToolBar toolBar = new JToolBar();
    private JTree tree = null;
    private JPanel rightpane = new JPanel();
    private JPanel mainpane = new JPanel();
    private JTextField searchText = new JTextField("",20);
    private Dao dao = new Dao();
    private int userNo, fileno, copyno, nowfolder;
    private int supfolder = 0;
    private String path, filename;

    public MainFrm(String rvalue, final int userNo, int nowfolder) {
        this.userNo = userNo;
        this.nowfolder = nowfolder;
        this.path = "../" + userNo;
        this.setTitle("�ҵ�����ϵͳ");
        //initMenu();
        rightpane.setLayout(new BorderLayout());
        rightpane.add(mainpane, BorderLayout.CENTER);
        mainpane.setLayout(new FlowLayout(FlowLayout.LEFT));
        final JPopupMenu menu = mainpaneMenu();

        mainpane.addMouseListener(new java.awt.event.MouseAdapter() {
            //�Ҽ��ļ������Ҽ��˵�
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu.show(mainpane, e.getX(), e.getY());
                }
            }
        });

        initTree();
        initToolBar();
        JSplitPane jsplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), rightpane);
        jsplit.setDividerLocation(300);
        jsplit.setDividerSize(2);
        this.getContentPane().add(jsplit);
        dispFiles(rvalue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMenu() {
        m1.add(new JMenuItem("�޸�����"));
        m1.add(new JMenuItem("�˳�ϵͳ"));
        rightpane.setLayout(new BorderLayout());
        rightpane.add(mainpane, BorderLayout.CENTER);
        mainpane.setLayout(new FlowLayout(FlowLayout.LEFT));
        JMenuBar bar = new JMenuBar();
        bar.add(m1);
        this.setJMenuBar(bar);

    }

    private void initToolBar() {
        JPanel tool = new JPanel();
        JPanel search = new JPanel();
        tool.setLayout(new FlowLayout(FlowLayout.CENTER));
        JToolBar toolbar = new JToolBar();// ����������
        JButton addFolderButton = new JButton("�½��ļ���", getButtonIcon("/icons/cloud (5).png"));
        JButton addFileButton = new JButton("�ϴ��ļ�", getButtonIcon("/icons/cloud (18).png"));
        JButton refreshButton = new JButton("ˢ��", getButtonIcon("/icons/cloud (2).png"));
        JButton searchButton = new JButton("����",getButtonIcon("/icons/cloud (3).png"));
        toolbar.add(addFolderButton);
        toolbar.add(addFileButton);
        toolbar.add(refreshButton);
        toolbar.add(searchText);
        toolbar.add(searchButton);
        toolbar.setFloatable(false);
        tool.add(toolbar);
        rightpane.add(tool, BorderLayout.NORTH);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh_clicked(e);
            }
        });
        addFolderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dao.NewFolder("newfolder", userNo, nowfolder);
                dispFiles(dao.ListFiles("listfiles", userNo, nowfolder));
                mainpane.repaint();
            }
        });
        addFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new JLabel(), "ѡ���ϴ��ļ�");
                File file = jfc.getSelectedFile();
                if (file.isFile()) {
                    try {
                        String result = dao.Upload(file.getName(), file.getAbsolutePath(), userNo, nowfolder, path);
                        if (result.equals("error")) {
                            JOptionPane.showMessageDialog(null, "�ϴ��ļ�ʧ��!");
                        }
                        if (result.equals("success")) {
                            JOptionPane.showMessageDialog(null, "�ϴ��ļ��ɹ�!");
                        }
                        if (result.equals("exist")) {
                            JOptionPane.showMessageDialog(null, "���ļ��Ѵ��ڣ�");
                        }
                        dispFiles(dao.ListFiles("listfiles", userNo, nowfolder));
                        mainpane.repaint();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchAlgorithmException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!searchText.getText().equals("")) {
                    String result = dao.Search("search", userNo, searchText.getText());
                    dispFiles(result);
                    mainpane.repaint();
                }
            }
        });

    }

    private void refresh_clicked(ActionEvent e) {
        this.dispFiles(dao.ListFiles("listfiles", this.userNo, nowfolder));
        mainpane.repaint();
    }

    private void initTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("�ҵ�����");
        DefaultMutableTreeNode m1 = new DefaultMutableTreeNode("�ҵ���Դ");
        DefaultMutableTreeNode m2 = new DefaultMutableTreeNode("�ҵķ���");
        DefaultMutableTreeNode m3 = new DefaultMutableTreeNode("����վ");
        DefaultMutableTreeNode n0 = new DefaultMutableTreeNode("��ҳ");
        DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("ȫ���ļ�");
        DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("ͼƬ");
        DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("�ĵ�");
        DefaultMutableTreeNode n4 = new DefaultMutableTreeNode("��Ƶ");
        DefaultMutableTreeNode n5 = new DefaultMutableTreeNode("����");
        m1.add(n0);
        m1.add(n1);
        m1.add(n2);
        m1.add(n3);
        m1.add(n4);
        m1.add(n5);
        m2.add(new DefaultMutableTreeNode("�鿴�����ļ�"));
        m3.add(new DefaultMutableTreeNode("�鿴����վ"));
        root.add(m1);
        root.add(m2);
        root.add(m3);
        tree = new JTree(root);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectionNode.isLeaf()) { //Ҷ�ӽڵ�ļ���
                    tree_clicked(selectionNode.toString());
                }
                if (selectionNode.isRoot()) { //���ڵ�ļ���
                    tree_clicked(selectionNode.toString());
                }
            }
        });
    }


    //�ļ�����Ҽ��˵�
    private JPopupMenu mainpaneMenu() {
        JPopupMenu menu = new JPopupMenu();
        //menu.add

        menu.add(new JMenuItem("ճ��")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getSource());
                if (copyno == 0) {
                    JOptionPane.showMessageDialog(mainpane, "��ѡ�����ļ�");
                } else {
                    String result = dao.Paste("paste", copyno, nowfolder);
                    if (result.equals("error")) {
                        JOptionPane.showMessageDialog(mainpane, "��ͬ���ļ��ڱ�Ŀ¼�£����ȸ����ļ���");
                    } else {
                        copyno = 0;
                        dispFiles(dao.ListFiles("listfiles", userNo, nowfolder));
                        mainpane.repaint();
                    }
                }
            }
        });
        menu.add(new JMenuItem("������һ��")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nowfolder != 0) {
                    if (supfolder == 0) {
                        dispFiles(dao.ListFiles("listfiles", userNo, 0));
                        mainpane.repaint();
                        supfolder = 0;
                        nowfolder = 0;
                        path = "../" + userNo;
                        //System.out.println("folder:"+nowfolder+" sup:"+supfolder+" path:"+path);
                    } else {
                        dispFiles(dao.ListFiles("listfiles", userNo, supfolder));
                        mainpane.repaint();
                        nowfolder = supfolder;
                        String t = path.substring(0, path.lastIndexOf("/"));
                        System.out.println(t);
                        if (!t.equals("../" + userNo + "/" + nowfolder)) {
                            path = path.substring(0, path.lastIndexOf("/"));
                            supfolder = Integer.valueOf(path.substring(path.lastIndexOf("/") + 1));


                        } else {
                            supfolder = 0;
                            path = path.substring(0, path.lastIndexOf("/"));
                        }
                        /*System.out.println("�˳�");
                        System.out.println("folder:"+nowfolder+" sup:"+supfolder+" path:"+path);
                        System.out.println();*/
                    }
                }
            }
        });
        return menu;
    }


    //�ļ��Ҽ��˵�
    private JPopupMenu initPopMenu() {
        JPopupMenu menu = new JPopupMenu();
        //menu.add

        menu.add(new JMenuItem("����")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e.getSource());
                copyno = fileno;
                String result=dao.CheckType("type",fileno);
                if(result.equals("folder"))
                    copyno=0;
            }
        });
        /*menu.add(new JMenuItem("ճ��")).addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getSource());
				if(fileno==0){
					JOptionPane.showMessageDialog(mainpane,"��ѡ�����ļ�");
				}else{
					String result=dao.Paste("paste",copyno,nowfolder);
					if(result.equals("error")){
						JOptionPane.showMessageDialog(mainpane,"��ͬ���ļ��ڱ�Ŀ¼�£����ȸ����ļ���");
					}
					else{
						fileno=0;
						dispFiles(dao.ListFiles("listfiles",userNo , nowfolder));
						mainpane.repaint();
					}
				}
			}
		});*/
        menu.add(new JMenuItem("ɾ��")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e.getSource());
                String result = dao.Change("delete", fileno);
                if (result.equals("error")) {
                    JOptionPane.showMessageDialog(mainpane, "ɾ������");
                } else {
                    dispFiles(dao.ListFiles("listfiles", userNo, nowfolder));
                    mainpane.repaint();
                }
            }
        });
        menu.add(new JMenuItem("����")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new JLabel(), "ѡ���ļ���");
                File file = jfc.getSelectedFile();
                try {
                    if (dao.Download("download", fileno, filename, file.getAbsolutePath()).equals("success")) {
                        JOptionPane.showMessageDialog(mainpane, "���سɹ�");
                    } else {
                        JOptionPane.showMessageDialog(mainpane, "����ʧ��");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        menu.add(new JMenuItem("����")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e.getSource());
                String result = dao.Change("share", fileno);
                if (result.equals("disShare"))
                    JOptionPane.showMessageDialog(mainpane, "ȡ������");
                else
                    JOptionPane.showInputDialog(
                            this, "http://localhost:8080/SkyDrive/servlet/ShareServlet?FileNo=" + fileno);

            }
        });
        menu.add(new JMenuItem("������")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame.setDefaultLookAndFeelDecorated(true);
                RenameFrm frm = new RenameFrm(fileno);
                frm.setSize(280, 180);
                frm.setVisible(true);

            }
        });
        menu.add(new JMenuItem("�������")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int splitIndex = filename.lastIndexOf(".");
                String fileformat = filename.substring(splitIndex + 1);
                if(fileformat.equals("mp3")){
                    try {
                        String filePath = dao.MediaDownload("download", fileno, filename, null,1);
                        if(filePath != null){
                            JFrame.setDefaultLookAndFeelDecorated(true);
                            MediaFrm mediaFrm = new MediaFrm(filePath,filename);
                            mediaFrm.setSize(280, 180);
                            mediaFrm.setVisible(true);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if(fileformat.equals("jpg") || fileformat.equals("png")){
                    try {
                        String filePath = dao.MediaDownload("download", fileno, filename, null,1);
                        if(filePath != null){
                            JFrame.setDefaultLookAndFeelDecorated(true);
                            ImageViewer imageViewer = new ImageViewer(filePath);
                            imageViewer.setSize(1024, 768);
                            imageViewer.setVisible(true);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        return menu;
    }

    private void tree_clicked(String event) {
        if ("�ҵ�����".equals(event)) {
            this.dispFiles(dao.ListFiles("listfiles", this.userNo, 0));
        }
        if ("�鿴�����ļ�".equals(event)) {
            this.dispFiles(dao.ListFiles("listSharefiles", this.userNo, 0));
        }
        if ("�鿴����վ".equals(event)) {
            this.dispFiles(dao.ListFiles("listDelfiles", this.userNo, 0));
        }
        if ("��ҳ".equals(event)) {
            this.dispFiles(dao.ListFiles("listfiles", this.userNo, 0));
        }
        if ("ȫ���ļ�".equals(event)) {
            this.dispFiles(dao.ListFiles("listAllfiles", this.userNo, 0));
        }
        if ("ͼƬ".equals(event)) {
            this.dispFiles(dao.ListFiles("listImgfiles", this.userNo, 0));
        }
        if ("�ĵ�".equals(event)) {
            this.dispFiles(dao.ListFiles("listDocfiles", this.userNo, 0));
        }
        if ("��Ƶ".equals(event)) {
            this.dispFiles(dao.ListFiles("listFilmfiles", this.userNo, 0));
        }
        if ("����".equals(event)) {
            this.dispFiles(dao.ListFiles("listMusicfiles", this.userNo, 0));
        }
        mainpane.repaint();
        supfolder = 0;
        nowfolder = 0;
        path = "../" + userNo;
    }

    private void dispFiles(String rvalue) {
        final java.util.List<String> filenames = XmlHelper.xmlElements(rvalue, "file");
        java.util.List<String> dirnames = XmlHelper.xmlElements(rvalue, "dir");
        mainpane.removeAll();
        int WIDTH = 127;
        int HEIGHT = 116;
        //�����ļ��е�lbl
        for (String fname : dirnames) {
            String imgpath = "/icons/ACE.png";
            final JLabel lbl = new JLabel();
            //lbl.setSize(WIDTH,HEIGHT);
            ImageIcon img = new ImageIcon(JLabel.class.getResource(imgpath));
            img.setImage(img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
            lbl.setIcon(img);
            if (fname.length() > 20) {
                lbl.setText(fname.substring(0, 20));
            } else {
                lbl.setText(fname);
            }
            lbl.setHorizontalTextPosition(JLabel.CENTER);
            lbl.setVerticalTextPosition(JLabel.BOTTOM);
            final JPopupMenu menu = initPopMenu();

            lbl.addMouseListener(new java.awt.event.MouseAdapter() {
                //�Ҽ��ļ������Ҽ��˵�
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        JLabel src = (JLabel) (e.getSource());
                        //�Ҽ��ļ�ʱ��ͨ���ļ������û���ź������ļ�������ѯ�ļ����
                        fileno = Integer.valueOf(dao.getFileNo("fileNo", userNo,
                                src.getText(), nowfolder));
                        //System.out.println(fileno);
                        menu.show(mainpane, src.getX() + 50, src.getY() + 40);
                    }
                }

                //˫���ļ��У�����ҳ�棬�����ļ���������
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        JLabel src = (JLabel) (e.getSource());

                        fileno = Integer.valueOf(dao.getFileNo("fileNo", userNo,
                                src.getText(), nowfolder));
                        System.out.println(fileno);
                        supfolder = nowfolder;
                        nowfolder = fileno;
                        path += "/" + fileno;

                        /*System.out.println("����");
                        System.out.println("folder:"+nowfolder+" sup:"+supfolder+" path:"+path);
                        System.out.println();*/
                        dispFiles(dao.ListFiles("listfiles", userNo, fileno));
                        mainpane.repaint();
                    }

                }
            });
            mainpane.add(lbl);
        }
        //�����ļ���lbl
        for (String fname : filenames) {
            String imgpath = "/icons/" + fname.substring(fname.lastIndexOf(".") + 1).toUpperCase() + ".png";

            if(JLabel.class.getResource(imgpath) == null){
                imgpath = "/icons/Default.png";
            }
            //String imgpath="/icons/PPT.png";
            final JLabel lbl = new JLabel();
            //lbl.setSize(WIDTH,HEIGHT);
            ImageIcon img = new ImageIcon(JLabel.class.getResource(imgpath));
            img.setImage(img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
            lbl.setIcon(img);
            if (fname.length() > 20) {
                lbl.setText(fname.substring(0, 20));
            } else {
                lbl.setText(fname);
            }
            lbl.setHorizontalTextPosition(JLabel.CENTER);
            lbl.setVerticalTextPosition(JLabel.BOTTOM);
            final JPopupMenu menu = initPopMenu();

            lbl.addMouseListener(new java.awt.event.MouseAdapter() {
                //�Ҽ��ļ������Ҽ��˵�
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        JLabel src = (JLabel) (e.getSource());
                        //�Ҽ��ļ�ʱ��ͨ���ļ������û���ź������ļ�������ѯ�ļ����
                        System.out.println(src.getText());
                        fileno = Integer.valueOf(dao.getFileNo("fileNo", userNo,
                                src.getText().substring(0, src.getText().lastIndexOf(".")), nowfolder));
                        filename = src.getText();
                        //System.out.println(fileno);
                        menu.show(mainpane, src.getX() + 50, src.getY() + 40);
                    }
                }
            });
            mainpane.add(lbl);
        }
        mainpane.validate();
    }

    private ImageIcon getButtonIcon(String filename) {// ������ť
        ImageIcon imageicon = null;
        if (filename != null) {
            imageicon = getImageIcon(filename);
            Image image = imageicon.getImage();
            image = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);// ����ͼƬ�����Ű汾
            imageicon.setImage(image);
        }
        return imageicon;
    }

    /**
     * ��ȡͼƬͼ��
     */
    public ImageIcon getImageIcon(String url) {
        return new ImageIcon(getURL(url));
    }

    /**
     * ����ļ��ľ��Ե�ַ
     */
    public URL getURL(String path) {
        return "".getClass().getResource(path);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        MainFrm frm = new MainFrm("123", 1, 0);
        frm.setSize(1200, 600);
        frm.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
