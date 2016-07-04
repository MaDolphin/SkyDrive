package com.service;
import java.io.File;
import org.hibernate.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.entity.*;
import com.util.HibernateSessionFactory;

import javax.servlet.FilterConfig;

public class FilesDao {

    public Boolean CheckMd5(String MD5){
        boolean flag=false;
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from Files a where a.fileMd5=?");
        query.setParameter(0,MD5);
        List<Files> list = query.list();
        if(list.size() != 0)
            flag=true;
        return flag;
    }

    public Files CheckFileByUser(int userNo,String md5,int supFolder,int status,String fileName,String fileFormat){
        boolean flag=false;
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from Files a where a.userNo=? and a.fileMd5=? and a.supFolder=? and a.fileStatus=? and a.fileName=? and a.fileFormat=?");
        query.setParameter(0,userNo);
        query.setParameter(1,md5);
        query.setParameter(2,supFolder);
        query.setParameter(3,status);
        query.setParameter(4,fileName);
        query.setParameter(5,fileFormat);
        List<Files> list = query.list();
        if(list.size() != 0){
            return list.get(0);
        }else {
            return null;
        }
    }

    public Boolean ExistFileByUser(int userNo,String md5,int supFolder,String fileName,String fileFormat){
        boolean flag=false;
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from Files a where a.userNo=? and a.fileMd5=? and a.supFolder=? and a.fileName=? and a.fileFormat=?");
        query.setParameter(0,userNo);
        query.setParameter(1,md5);
        query.setParameter(2,supFolder);
        query.setParameter(3,fileName);
        query.setParameter(4,fileFormat);
        List<Files> list = query.list();
        if(list.size() != 0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean addFile(Files file){
        Session session = HibernateSessionFactory.getSession();
        Transaction tran=session.beginTransaction();
        file.setCreatTime(new Date(System.currentTimeMillis()));
        session.saveOrUpdate(file);
        tran.commit();
        return true;
    }

    public Boolean editFile(Files file){
        Session session = HibernateSessionFactory.getSession();
        Transaction tran=session.beginTransaction();
        session.saveOrUpdate(file);
        tran.commit();
        return true;
    }

    public Boolean disableFile(int fileNo){
        Files files = this.findFile(fileNo);
        files.setFileStatus(0);
        this.editFile(files);
        return true;
    }

    public Boolean enableFile(int fileNo){
        Files files = this.findFile(fileNo);
        files.setFileStatus(1);
        this.editFile(files);
        return true;
    }

    public Boolean enableShareFile(int fileNo){
        Files files = this.findFile(fileNo);
        files.setShareStatus(1);
        this.editFile(files);
        return true;
    }

    public Boolean disableShareFile(int fileNo){
        Files files = this.findFile(fileNo);
        files.setShareStatus(0);
        this.editFile(files);
        return true;
    }

    public Files findFile(int fileNo){
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from Files where fileNo=:fileNo");
        query.setParameter("fileNo",fileNo);
        List<Files> list = query.list();
        if(list.size()!=0)
            return (Files) list.get(0);
        else return null;
    }

    public List<Files> queryAllFileInPath(int userNo,int folder){
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from Files f where f.userNo=:userNo and f.supFolder=:folder and f.fileStatus=:filestatus order by f.fileFormat,f.fileNo");
        query.setParameter("userNo",userNo);
        query.setParameter("folder",folder);
        query.setParameter("filestatus",1);
        List<Files> list = query.list();
        return list;
    }


    public List<Files> queryFileNoInPathByFileName(int userNo,int folder,String fileName){
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from Files f where f.userNo=:userNo and f.supFolder=:folder and f.fileName=:filename");
        query.setParameter("userNo",userNo);
        query.setParameter("folder",folder);
        query.setParameter("filename",fileName);
        List<Files> list = query.list();
        return list;
    }

    public List<Files> queryLikeFileInPath(int userNo,String filePath,String fileName){
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from Files f where f.userNo=:userNo and f.fileName like %:fileName% and f.filePath=:filePath");
        query.setParameter("userNo",userNo);
        query.setParameter("fileName",fileName);
        query.setParameter("filePath",filePath);
        List<Files> list = query.list();
        return list;
    }

    public List<Files> queryLikeFileInAllPath(int userNo, String fileName){
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from Files f where f.userNo=:userNo and f.fileName like %:fileName%");
        query.setParameter("userNo",userNo);
        query.setParameter("fileName",fileName);
        List<Files> list = query.list();
        return list;
    }

//	public static List<File> getFileList(String dir) {
//		List<File> listFiles = new ArrayList<File>();
//		File dirFile = new File(dir);
//		// 如果不是目录文件，则直接返回
//		if (dirFile.isDirectory()) {
//			// 获得文件夹下的文件列表，然后根据文件类型分别处理
//			File[] files = dirFile.listFiles();
//			if (null != files && files.length > 0) {
//				// 根据时间排序
//				Arrays.sort(files, new Comparator<File>() {
//					public int compare(File f1, File f2) {
//						return (int) (f1.lastModified() - f2.lastModified());
//					}
//					public boolean equals(Object obj) {
//						return true;
//					}
//				});
//				for (File file : files) {
//					listFiles.add(file);
//				}
//			}
//		}
//		return listFiles;
//	}
}
