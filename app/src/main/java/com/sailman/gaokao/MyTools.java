package com.sailman.gaokao;

import android.app.Activity;
import android.os.Environment;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */

public final  class MyTools {
    public static String readFromDBA(String path, String post) throws Exception {

        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒


            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write(post);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getFileName(String title){//去除了标题后的括号及时间
        String[] st=title.split("\\(");
        String str="";
        if(st.length>2){
            for(int i=0;i<st.length-1;i++){
                if(i==0){
                    str=str+st[i];
                }else{
                    str=str+"("+st[i];
                }
            }
        }else{
            if(st.length>1)
                str=st[st.length-2];
            else{
                str=title;
            }
        }
        return str;
    }
    public static String getRootPath(Activity activity){
        StorageList storageList=new StorageList(activity);
        String[] path=storageList.getVolumePaths();
        return path[path.length-1];
    }
    public static  boolean fileIsExists(String rootPath,String strFile)
    {
        try
        {
            File f=new File(rootPath+"/gaokao/"+strFile+".mp4");
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }
    public static List<Gaokao_vedioartitleSendPhone> getGaokao_vedioartitle(String vedioartitle){
        List<Gaokao_vedioartitleSendPhone> list= new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(vedioartitle);
            for (int i = 0; i < jsonArray.length(); i++) {
                Gaokao_vedioartitleSendPhone gaokao_vedioartitleSendPhone = new Gaokao_vedioartitleSendPhone();
                gaokao_vedioartitleSendPhone.setVedioartitleid(jsonArray.getJSONObject(i).getInt("vedioartitleid"));
                gaokao_vedioartitleSendPhone.setTitle(jsonArray.getJSONObject(i).getString("title"));
                gaokao_vedioartitleSendPhone.setLabelclassificationname(jsonArray.getJSONObject(i).getString("labelclassificationname"));
                gaokao_vedioartitleSendPhone.setSubjectchaptername(jsonArray.getJSONObject(i).getString("subjectchaptername"));
                gaokao_vedioartitleSendPhone.setPublication(jsonArray.getJSONObject(i).getString("publication"));
                gaokao_vedioartitleSendPhone.setKeyword(jsonArray.getJSONObject(i).getString("keyword"));
                gaokao_vedioartitleSendPhone.setUrl(jsonArray.getJSONObject(i).getString("url"));
                gaokao_vedioartitleSendPhone.setSubjectid(jsonArray.getJSONObject(i).getString("subjectid"));
                gaokao_vedioartitleSendPhone.setTypeid(jsonArray.getJSONObject(i).getString("typeid"));
                gaokao_vedioartitleSendPhone.setImageurl(jsonArray.getJSONObject(i).getString("imageurl"));
                gaokao_vedioartitleSendPhone.setComments(jsonArray.getJSONObject(i).getString("comments"));
                gaokao_vedioartitleSendPhone.setLabelclassificationid(jsonArray.getJSONObject(i).getInt("labelclassificationid"));
                gaokao_vedioartitleSendPhone.setSubjectchapterid(jsonArray.getJSONObject(i).getInt("subjectchapterid"));
                gaokao_vedioartitleSendPhone.setVedio(jsonArray.getJSONObject(i).getString("vedio"));
                gaokao_vedioartitleSendPhone.setContent(jsonArray.getJSONObject(i).getString("content"));
                gaokao_vedioartitleSendPhone.setLearn(jsonArray.getJSONObject(i).getString("learn"));
                gaokao_vedioartitleSendPhone.setStay(jsonArray.getJSONObject(i).getString("stay"));
                gaokao_vedioartitleSendPhone.setReadcount(jsonArray.getJSONObject(i).getInt("readcount"));
                gaokao_vedioartitleSendPhone.setMaxlookdate(jsonArray.getJSONObject(i).getString("maxlookdate"));
                list.add(gaokao_vedioartitleSendPhone );
            }
        }catch (Exception e){}



        return list;
    }
}

