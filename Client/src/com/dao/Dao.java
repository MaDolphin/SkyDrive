package com.dao;

import com.util.Md5CaculateUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by �� on 2016/7/3.
 */
public class Dao {

    private String GetEntity( HttpPost httppost,List<BasicNameValuePair> formparams ){
        String rvalue="";
        // ����Ĭ�ϵ�httpClientʵ��.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rvalue= EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // �ر�����,�ͷ���Դ
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rvalue;
    }

    public String CheckUser(String uid,String upwd){
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/user.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("uid", uid));
        formparams.add(new BasicNameValuePair("upwd", upwd));

        return this.GetEntity(httppost,formparams);
    }


    //��ѯ�ļ����
    public String getFileNo(String opttype,int userNo,String filename,int supFolder){
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/file.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("UserNo",String.valueOf(userNo)));
        formparams.add(new BasicNameValuePair("SupFolder", String.valueOf(supFolder)));
        formparams.add(new BasicNameValuePair("opttype", opttype));
        formparams.add(new BasicNameValuePair("Filename",filename));
        String rvalue=this.GetEntity(httppost,formparams);
        return rvalue.substring(0, rvalue.length() - 2);
    }

    //��ѯ�ļ�
    public String ListFiles(String opttype,int userNo,int supFolder) {
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/file.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("UserNo",String.valueOf(userNo)));
        formparams.add(new BasicNameValuePair("SupFolder", String.valueOf(supFolder)));
        formparams.add(new BasicNameValuePair("opttype", opttype));
        String t=this.GetEntity(httppost,formparams);
        //System.out.println(t);
        return t;
    }


    //����ճ��
    public String Paste(String opttype,int fileno,int supFolder){
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/file.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("FileNo",String.valueOf(fileno)));
        formparams.add(new BasicNameValuePair("SupFolder", String.valueOf(supFolder)));
        formparams.add(new BasicNameValuePair("opttype", opttype));
        return this.GetEntity(httppost,formparams);
    }


    //ɾ���ļ�
    public String Delete(String opttype,int fileno) {
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/file.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("FileNo", String.valueOf(fileno)));
        formparams.add(new BasicNameValuePair("opttype", opttype));
        return this.GetEntity(httppost, formparams);
    }

    //�����ļ�
    public String Download(String opttype, int fileno,String fileName,String savePath) throws IOException {
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/servlet/DownloadServlet");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("fileNo", String.valueOf(fileno)));
        formparams.add(new BasicNameValuePair("opttype", opttype));
        String downLoadPath =  this.GetEntity(httppost, formparams);
        downLoadPath = downLoadPath.substring(0, downLoadPath.length() - 2);
        System.out.println("DownLoadPath:"+downLoadPath);
        if(this.DownMethod(downLoadPath,savePath,fileName) == true){
            return "success";
        }else {
            return "error";
        }
    }

    public Boolean DownMethod(String downpath,String savepath,String fileName) throws IOException{
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(downpath);
            HttpResponse response = httpclient.execute(post);
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    File storeFile = new File(savepath,fileName);
                    FileOutputStream output = new FileOutputStream(storeFile);
                    InputStream input = entity.getContent();
                    byte b[] = new byte[1024];
                    int j = 0;
                    while( (j = input.read(b))!=-1){
                        output.write(b,0,j);
                    }
                    output.flush();
                    output.close();
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //�½��ļ���
    public String NewFolder(String opttype,int userno,int supfolder){
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/file.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("UserNo", String.valueOf(userno)));
        formparams.add(new BasicNameValuePair("SupFolder", String.valueOf(supfolder)));
        formparams.add(new BasicNameValuePair("opttype", opttype));
        return this.GetEntity(httppost, formparams);
    }


    //������
    public void Rename(String opttype,int fileno, String text) {
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/file.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("FileNo", String.valueOf(fileno)));
        formparams.add(new BasicNameValuePair("NewName", String.valueOf(text)));
        formparams.add(new BasicNameValuePair("opttype", opttype));
    }


    public Boolean CkeckMd5(String opttype,String hash) {
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/file.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("Md5",hash));
        formparams.add(new BasicNameValuePair("opttype", opttype));
        String rvalue = this.GetEntity(httppost,formparams);
        rvalue = rvalue.substring(0, rvalue.length() - 2);
        if("true".equals(rvalue)){
            return true;
        }else {
            return false;
        }
    }

    public String AddFile(String opttype,String fileRealName,String filePath,int userNo,int supfloder,String path,String hash) {
        // ����httppost
        HttpPost httppost = new HttpPost("http://localhost:8080/SkyDrive/file.action");
        // ������������
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("Md5",hash));
        formparams.add(new BasicNameValuePair("FileRealName",fileRealName));
        formparams.add(new BasicNameValuePair("UserNo",String.valueOf(userNo)));
        formparams.add(new BasicNameValuePair("SupFloder",String.valueOf(supfloder)));
        formparams.add(new BasicNameValuePair("FilePath",path));
        formparams.add(new BasicNameValuePair("opttype", "addfile"));
        String rvalue = this.GetEntity(httppost,formparams);
        rvalue = rvalue.substring(0, rvalue.length() - 2);
        return rvalue;
    }

    public String FileUpload(String hash,String filePath)throws IOException, NoSuchAlgorithmException {
        String rvalue="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // ��һ����ͨ�������ļ��ϴ������������ַ ��һ��servlet
            HttpPost httpPost = new HttpPost("http://localhost:8080/SkyDrive/servlet/UploadServlet");
            // ���ļ�ת����������FileBody
            System.out.println("Client filePath:"+filePath);
            System.out.println("Client hash:"+hash);
            File file = new File(filePath);
            FileBody bin = new FileBody(file);
            StringBody md5 = new StringBody(hash, ContentType.create("text/plain", Consts.UTF_8));
            //�����������ģʽ���У���ֹ�ļ������롣
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .setCharset(Charset.forName(HTTP.UTF_8))
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart("md5", md5)
                    .addPart("file1", bin).build();
            httpPost.setEntity(reqEntity);

//            System.out.println("���������ҳ���ַ " + httpPost.getRequestLine());
            // �������� �������������Ӧ
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
//                System.out.println("----------------------------------------");
//                // ��ӡ��Ӧ״̬
//                System.out.println(response.getStatusLine());
//                // ��ȡ��Ӧ����
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    rvalue= EntityUtils.toString(resEntity, "UTF-8");
                }
                // ����
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return rvalue;
    }

    public String Upload(String fileRealName,String filePath,int userNo,int supfloder,String path) throws IOException, NoSuchAlgorithmException {
        String hash = Md5CaculateUtil.getHash(filePath,"MD5");
        if(this.CkeckMd5("checkmd5",hash)){
            System.out.println("Exist");
            return this.AddFile("addfile",fileRealName,filePath,userNo,supfloder,path,hash);
        }else{
            System.out.println("NotExist");
            String rvalue = this.FileUpload(hash,filePath);
            rvalue = rvalue.substring(0, rvalue.length() - 2);
            if("success".equals(rvalue)){
                System.out.println("CopyFileSuccess");
                return this.AddFile("addfile",fileRealName,filePath,userNo,supfloder,path,hash);
            }
            return "error";
        }
    }


}
