package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.FilesDao;
import com.util.FileOperateUtil;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import com.entity.Files;

public class UploadServlet extends HttpServlet {

    public UploadServlet() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println("����POST��ʽ�ϴ��ļ�");
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file1 = null;
        String md5 = null;
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        DiskFileUpload diskFileUpload = new DiskFileUpload();
        try {
            List<FileItem> list = diskFileUpload.parseRequest(request);
            for (FileItem fileItem : list) {
                //�鿴�Ƿ�Ϊ���ύ
                if (fileItem.isFormField()) {
                    if("md5".equals(fileItem.getFieldName())){
                        md5 = fileItem.getString("UTF-8");
                        System.out.println("Service MD5:"+md5);
                    }
                } else {
                    if ("file1".equals(fileItem.getFieldName())) {
                        File remoteFile = new File(new String(fileItem.getName().getBytes(), "ISO-8859-1"));
                        //�ļ�����·��
                        String downloadpath = this.getServletContext().getRealPath("upload");
                        //�ļ���ʽ
                        String fileFormat = FileOperateUtil.getFileSufix(remoteFile.getName());
                        //�����ļ�����ʽΪ MD5+��ʽ
                        String filename = md5 + "." + fileFormat;
                        System.out.println("Service filename:"+filename);
                        file1 = new File(downloadpath, filename);
                        file1.getParentFile().mkdirs();
                        file1.createNewFile();
                        InputStream ins = fileItem.getInputStream();
                        OutputStream ous = new FileOutputStream(file1);
                        try {
                            byte[] buffer = new byte[1024*1024];
                            int len = 0;
                            while ((len = ins.read(buffer)) > -1)
                                ous.write(buffer, 0, len);
                            out.println("success");
                        }catch (Exception e){
                            out.println("error");
                        }
                        finally {
                            ous.close();
                            ins.close();
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            out.println("error");
            e.printStackTrace();
        }
        out.flush();
        out.close();
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
