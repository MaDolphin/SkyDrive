package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.*;
import com.service.FilesDao;
import com.util.FileOperateUtil;

public class FilesServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String opttype = request.getParameter("opttype");
        //��ѯ�ļ����������ļ�
        if ("listfiles".equals(opttype)) {
            FilesDao filesDao = new FilesDao();
            response.setContentType("application/xml;charset=utf-8");
            int user = Integer.valueOf(request.getParameter("UserNo"));
            int path = Integer.valueOf(request.getParameter("SupFolder"));
            List<Files> files = filesDao.queryAllFileInPath(user, path);
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<files>");
            for (Files file : files) {
                if (file.getFileType() == 1) {
                    out.println(" <file type=\"file\">");
                    out.println("   <path>" + file.getFilePath() + "</path>");
                    out.println("   <no>" + file.getFileNo() + "</no>");
                    out.println("   <name>" + file.getFileName() + "</name>");
                    out.println("   <ext>" + file.getFileFormat() + "</ext>");
                    out.println(" </file>");
                } else {
                    out.println(" <file type=\"dir\">");
                    out.println("   <path>" + file.getFilePath() + "</path>");
                    out.println("   <no>" + file.getFileNo() + "</no>");
                    out.println("   <name>" + file.getFileName() + "</name>");
                    out.println(" </file>");
                }
            }
            out.println("</files>");
        }
        if ("checkmd5".equals(opttype)) {
            FilesDao filesDao = new FilesDao();
            String hash = request.getParameter("Md5");
            if (filesDao.CheckMd5(hash)) {
                out.println("true");
            } else {
                out.println("false");
            }
        }
        if ("addfile".equals(opttype)) {
            try {
                FilesDao filesDao = new FilesDao();
                String md5 = request.getParameter("Md5");
                String fileRealName = request.getParameter("FileRealName");
                int user = Integer.valueOf(request.getParameter("UserNo"));
                int supfloder = Integer.valueOf(request.getParameter("SupFloder"));
                String path = request.getParameter("FilePath");
                String fileFormat = FileOperateUtil.getFileSufix(fileRealName);
                String fileName = FileOperateUtil.getFilePrefix(fileRealName);
                if (filesDao.ExistFileByUser(user, md5, supfloder, fileName, fileFormat) == true) {
                    Files initFiles = filesDao.CheckFileByUser(user, md5, supfloder, 0, fileName, fileFormat);
                    if (initFiles != null) {
                        initFiles.setFileStatus(1);
                        filesDao.addFile(initFiles);
                        System.out.println("change FileStatus Success");
                        out.println("suceess");
                    } else {
                        System.out.println("exist");
                        out.println("exist");
                    }
                } else {
                    Files file = new Files();
                    file.setFileName(FileOperateUtil.getFilePrefix(fileRealName));
                    file.setUserNo(user);
                    file.setFileFormat(fileFormat);
                    file.setFileType(1);
                    file.setFileMd5(md5);
                    file.setSupFolder(supfloder);
                    file.setFilePath(path);
                    file.setDownloadPath("/upload/" + md5 + "." + fileFormat);
                    file.setFileStatus(1);
                    file.setShareStatus(0);
                    filesDao.addFile(file);
                    System.out.println("Add fileInfo to DB Success");
                    out.println("success");
                }
            } catch (Exception e) {
                out.println("error");
            }
        }
        //��ѯ�ļ����
        if ("fileNo".equals(opttype)) {
            int user = Integer.valueOf(request.getParameter("UserNo"));
            int path = Integer.valueOf(request.getParameter("SupFolder"));
            String filename = request.getParameter("Filename");
            FilesDao filesDao = new FilesDao();
            List<Files> files = filesDao.queryFileNoInPathByFileName(user, path, filename);
            if (files.size() != 0) {
                out.println(files.get(0).getFileNo());
            } else {
                out.println("false");
            }
        }
        //�����ļ�
        if ("paste".equals(opttype)) {
            int fileno = Integer.valueOf(request.getParameter("FileNo"));
            int path = Integer.valueOf(request.getParameter("SupFolder"));
            FilesDao filesDao = new FilesDao();
            Files files = filesDao.findFile(fileno);
            List<Files> filesList = filesDao.queryFileNoInPathByFileName(files.getUserNo(), path, files.getFileName());

            Files paste = new Files();
            paste.setFileType(files.getFileType());
            paste.setDownloadPath(files.getDownloadPath());
            paste.setFileFormat(files.getFileFormat());
            paste.setFileMd5(files.getFileMd5());
            paste.setUserNo(files.getUserNo());
            paste.setFileStatus(1);
            paste.setShareStatus(0);
            paste.setFileName(files.getFileName());

            if (path != 0) {
                Files dir = filesDao.findFile(path);
                paste.setFilePath(dir.getFilePath() + "/" + dir.getFileName());
                paste.setSupFolder(dir.getFileNo());
            } else {
                paste.setFilePath("../" + files.getUserNo());
                paste.setUserNo(files.getUserNo());
            }
            if (filesList.size() != 0)
                out.print("error");
            else
                filesDao.addFile(paste);

        }

        //ɾ���ļ�
        if ("delete".equals(opttype)) {
            int fileno = Integer.valueOf(request.getParameter("FileNo"));
            FilesDao filesDao = new FilesDao();
            if (!filesDao.disableFile(fileno))
                out.print("error");
        }

		//�½��ļ���
        if("newfolder".equals(opttype)){
			int user=Integer.valueOf(request.getParameter("UserNo"));
			int path=Integer.valueOf(request.getParameter("SupFolder"));
			FilesDao filesDao=new FilesDao();

			Files files=new Files();
			files.setUserNo(user);
			files.setFileType(0);
			files.setFileName("999");
			files.setSupFolder(path);
			files.setFileStatus(1);
            if(path==0)
                files.setFilePath("../"+user);
            else{
                Files supfolder=filesDao.findFile(path);
                files.setFilePath(supfolder.getFilePath()+"/"+path);
            }

			filesDao.addFile(files);
		}

        //������
        if ("rename".equals(opttype)) {
            int fileno = Integer.valueOf(request.getParameter("FileNo"));
            String name=request.getParameter("NewName");
            FilesDao filesDao = new FilesDao();
            Files files= filesDao.findFile(fileno);
            files.setFileName(name);
            filesDao.editFile(files);
        }
        out.close();
    }

}