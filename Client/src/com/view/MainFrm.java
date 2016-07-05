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
    private JMenu m1 = new JMenu("系统管理");
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
        this.setTitle("我的云盘系统");
        //initMenu();
        rightpane.setLayout(new BorderLayout());
        rightpane.add(mainpane, BorderLayout.CENTER);
        mainpane.setLayout(new FlowLayout(FlowLayout.LEFT));
        final JPopupMenu menu = mainpaneMenu();

        mainpane.addMouseListener(new java.awt.event.MouseAdapter() {
            //右键文件创建右键菜单
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
        m1.add(new JMenuItem("修改密码"));
        m1.add(new JMenuItem("退出系统"));
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
        JToolBar toolbar = new JToolBar();// 建立工具栏
        JButton addFolderButton = new JButton("新建文件夹", getButtonIcon("/icons/cloud (5).png"));
        JButton addFileButton = new JButton("上传文件", getButtonIcon("/icons/cloud (18).png"));
        JButton refreshButton = new JButton("刷新", getButtonIcon("/icons/cloud (2).png"));
        JButton searchButton = new JButton("搜索",getButtonIcon("/icons/cloud (3).png"));
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
                jfc.showDialog(new JLabel(), "选择上传文件");
                File file = jfc.getSelectedFile();
                if (file.isFile()) {
                    try {
                        String result = dao.Upload(file.getName(), file.getAbsolutePath(), userNo, nowfolder, path);
                        if (result.equals("error")) {
                            JOptionPane.showMessageDialog(null, "上传文件失败!");
                        }
                        if (result.equals("success")) {
                            JOptionPane.showMessageDialog(null, "上传文件成功!");
                        }
                        if (result.equals("exist")) {
                            JOptionPane.showMessageDialog(null, "该文件已存在！");
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
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("我的云盘");
        DefaultMutableTreeNode m1 = new DefaultMutableTreeNode("我的资源");
        DefaultMutableTreeNode m2 = new DefaultMutableTreeNode("我的分享");
        DefaultMutableTreeNode m3 = new DefaultMutableTreeNode("回收站");
        DefaultMutableTreeNode n0 = new DefaultMutableTreeNode("主页");
        DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("全部文件");
        DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("图片");
        DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("文档");
        DefaultMutableTreeNode n4 = new DefaultMutableTreeNode("视频");
        DefaultMutableTreeNode n5 = new DefaultMutableTreeNode("音乐");
        m1.add(n0);
        m1.add(n1);
        m1.add(n2);
        m1.add(n3);
        m1.add(n4);
        m1.add(n5);
        m2.add(new DefaultMutableTreeNode("查看分享文件"));
        m3.add(new DefaultMutableTreeNode("查看回收站"));
        root.add(m1);
        root.add(m2);
        root.add(m3);
        tree = new JTree(root);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectionNode.isLeaf()) { //叶子节点的监听
                    tree_clicked(selectionNode.toString());
                }
                if (selectionNode.isRoot()) { //根节点的监听
                    tree_clicked(selectionNode.toString());
                }
            }
        });
    }


    //文件面板右键菜单
    private JPopupMenu mainpaneMenu() {
        JPopupMenu menu = new JPopupMenu();
        //menu.add

        menu.add(new JMenuItem("粘贴")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getSource());
                if (copyno == 0) {
                    JOptionPane.showMessageDialog(mainpane, "请选择复制文件");
                } else {
                    String result = dao.Paste("paste", copyno, nowfolder);
                    if (result.equals("error")) {
                        JOptionPane.showMessageDialog(mainpane, "有同名文件在本目录下，请先更改文件名");
                    } else {
                        copyno = 0;
                        dispFiles(dao.ListFiles("listfiles", userNo, nowfolder));
                        mainpane.repaint();
                    }
                }
            }
        });
        menu.add(new JMenuItem("返回上一层")).addActionListener(new ActionListener() {
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
                        /*System.out.println("退出");
                        System.out.println("folder:"+nowfolder+" sup:"+supfolder+" path:"+path);
                        System.out.println();*/
                    }
                }
            }
        });
        return menu;
    }


    //文件右键菜单
    private JPopupMenu initPopMenu() {
        JPopupMenu menu = new JPopupMenu();
        //menu.add

        menu.add(new JMenuItem("复制")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e.getSource());
                copyno = fileno;
                String result=dao.CheckType("type",fileno);
                if(result.equals("folder"))
                    copyno=0;
            }
        });
        /*menu.add(new JMenuItem("粘贴")).addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getSource());
				if(fileno==0){
					JOptionPane.showMessageDialog(mainpane,"请选择复制文件");
				}else{
					String result=dao.Paste("paste",copyno,nowfolder);
					if(result.equals("error")){
						JOptionPane.showMessageDialog(mainpane,"有同名文件在本目录下，请先更改文件名");
					}
					else{
						fileno=0;
						dispFiles(dao.ListFiles("listfiles",userNo , nowfolder));
						mainpane.repaint();
					}
				}
			}
		});*/
        menu.add(new JMenuItem("删除")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e.getSource());
                String result = dao.Change("delete", fileno);
                if (result.equals("error")) {
                    JOptionPane.showMessageDialog(mainpane, "删除出错");
                } else {
                    dispFiles(dao.ListFiles("listfiles", userNo, nowfolder));
                    mainpane.repaint();
                }
            }
        });
        menu.add(new JMenuItem("下载")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new JLabel(), "选择文件夹");
                File file = jfc.getSelectedFile();
                try {
                    if (dao.Download("download", fileno, filename, file.getAbsolutePath()).equals("success")) {
                        JOptionPane.showMessageDialog(mainpane, "下载成功");
                    } else {
                        JOptionPane.showMessageDialog(mainpane, "下载失败");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        menu.add(new JMenuItem("共享")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e.getSource());
                String result = dao.Change("share", fileno);
                if (result.equals("disShare"))
                    JOptionPane.showMessageDialog(mainpane, "取消分享");
                else
                    JOptionPane.showInputDialog(
                            this, "http://localhost:8080/SkyDrive/servlet/ShareServlet?FileNo=" + fileno);

            }
        });
        menu.add(new JMenuItem("重命名")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame.setDefaultLookAndFeelDecorated(true);
                RenameFrm frm = new RenameFrm(fileno);
                frm.setSize(280, 180);
                frm.setVisible(true);

            }
        });
        menu.add(new JMenuItem("浏览播放")).addActionListener(new ActionListener() {
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
        if ("我的云盘".equals(event)) {
            this.dispFiles(dao.ListFiles("listfiles", this.userNo, 0));
        }
        if ("查看分享文件".equals(event)) {
            this.dispFiles(dao.ListFiles("listSharefiles", this.userNo, 0));
        }
        if ("查看回收站".equals(event)) {
            this.dispFiles(dao.ListFiles("listDelfiles", this.userNo, 0));
        }
        if ("主页".equals(event)) {
            this.dispFiles(dao.ListFiles("listfiles", this.userNo, 0));
        }
        if ("全部文件".equals(event)) {
            this.dispFiles(dao.ListFiles("listAllfiles", this.userNo, 0));
        }
        if ("图片".equals(event)) {
            this.dispFiles(dao.ListFiles("listImgfiles", this.userNo, 0));
        }
        if ("文档".equals(event)) {
            this.dispFiles(dao.ListFiles("listDocfiles", this.userNo, 0));
        }
        if ("视频".equals(event)) {
            this.dispFiles(dao.ListFiles("listFilmfiles", this.userNo, 0));
        }
        if ("音乐".equals(event)) {
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
        //创建文件夹的lbl
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
                //右键文件创建右键菜单
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        JLabel src = (JLabel) (e.getSource());
                        //右键文件时，通过文件名、用户编号和所处文件夹来查询文件编号
                        fileno = Integer.valueOf(dao.getFileNo("fileNo", userNo,
                                src.getText(), nowfolder));
                        //System.out.println(fileno);
                        menu.show(mainpane, src.getX() + 50, src.getY() + 40);
                    }
                }

                //双击文件夹，重置页面，查找文件夹中内容
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        JLabel src = (JLabel) (e.getSource());

                        fileno = Integer.valueOf(dao.getFileNo("fileNo", userNo,
                                src.getText(), nowfolder));
                        System.out.println(fileno);
                        supfolder = nowfolder;
                        nowfolder = fileno;
                        path += "/" + fileno;

                        /*System.out.println("进入");
                        System.out.println("folder:"+nowfolder+" sup:"+supfolder+" path:"+path);
                        System.out.println();*/
                        dispFiles(dao.ListFiles("listfiles", userNo, fileno));
                        mainpane.repaint();
                    }

                }
            });
            mainpane.add(lbl);
        }
        //创建文件的lbl
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
                //右键文件创建右键菜单
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        JLabel src = (JLabel) (e.getSource());
                        //右键文件时，通过文件名、用户编号和所处文件夹来查询文件编号
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

    private ImageIcon getButtonIcon(String filename) {// 创建按钮
        ImageIcon imageicon = null;
        if (filename != null) {
            imageicon = getImageIcon(filename);
            Image image = imageicon.getImage();
            image = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);// 创建图片的缩放版本
            imageicon.setImage(image);
        }
        return imageicon;
    }

    /**
     * 获取图片图标
     */
    public ImageIcon getImageIcon(String url) {
        return new ImageIcon(getURL(url));
    }

    /**
     * 获得文件的绝对地址
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
