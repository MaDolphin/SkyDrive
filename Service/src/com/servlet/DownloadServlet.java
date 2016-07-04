package com.servlet;

import com.entity.Files;
import com.service.FilesDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

    FilesDao filesDao = new FilesDao();

    public DownloadServlet() {
        super();
    }

    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int fileNo = Integer.valueOf(request.getParameter("fileNo"));
        Files files = filesDao.findFile(fileNo);
        String filePath = "http://localhost:8080/SkyDrive"+files.getDownloadPath();
        //String filePath = this.getServletContext().getRealPath(files.getDownloadPath());
        System.out.println("DownLoadPath:"+filePath);
        out.println(filePath);
//        File f = new File(filePath);
//        if(f.exists()){
//            FileInputStream  fis = new FileInputStream(f);
//            String filename=URLEncoder.encode(files.getFileName()+"."+files.getFileFormat(),"utf-8"); //解决中文文件名下载后乱码的问题
//            byte[] b = new byte[fis.available()];
//            fis.read(b);
//            response.setCharacterEncoding("utf-8");
//            response.setHeader("Content-Disposition","attachment; filename="+filename+"");
//            //获取响应报文输出流对象
//            ServletOutputStream  out =response.getOutputStream();
//            //输出
//            out.write(b);
//            out.flush();
//            out.close();
//        }

    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }

}