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

/**
 * Created by 珏 on 2016/7/5.
 */
public class ShareServlet extends HttpServlet {
    FilesDao filesDao = new FilesDao();

    public ShareServlet() {
        super();
    }

    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int fileno = Integer.valueOf(request.getParameter("FileNo"));

        //System.out.println(fileno);
        String path = this.getServletContext().getRealPath("upload");
        path = path.substring(0, path.lastIndexOf("\\"));
        //System.out.print(path);
        Files files = filesDao.findFile(fileno);
        if (files.getShareStatus() == 0||files.getFileType()==0) {
            response.getWriter().println("<script>alert('File is not shared！')</script>");
        } else {
            File f = new File(path + files.getDownloadPath());
            if (f.exists()) {
                FileInputStream fis = new FileInputStream(f);
                String filename = URLEncoder.encode(files.getFileName() + "." + files.getFileFormat(), "utf-8"); //解决中文文件名下载后乱码的问题
                byte[] b = new byte[fis.available()];
                fis.read(b);
                response.setHeader("Content-Disposition", "attachment; filename=" + filename + "");
                //获取响应报文输出流对象
                ServletOutputStream out = response.getOutputStream();
                //输出
                out.write(b);
                out.flush();
                out.close();
            }
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


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



