package com.util;

import java.io.*;
import java.net.ConnectException;

/**
 * Created by xfcq on 2016/5/12.
 */
public class FileOperateUtil {
    private static final int BUFFER_SIZE = 16*1024*1024;

    public static void copy(File src, File dst,String fileName){
        InputStream in = null;
        OutputStream out = null;
        try{
            in = new BufferedInputStream(new FileInputStream(src),BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0){
                out.write(buffer,0,len);
            }
            in.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getFilePrefix(String fileName){
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
    }

    public static String getFileSufix(String fileName){
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
    }

}
