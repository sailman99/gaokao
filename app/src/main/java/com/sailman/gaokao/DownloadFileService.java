
package com.sailman.gaokao;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class DownloadFileService extends Service {
    private MyApp myApp;
    private String[] rootPath;
    public DownloadFileService(){
        myApp=new MyApp();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onCreate() {

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 4) {
                List<Gaokao_vedioartitleSendPhone> list=MyTools.getGaokao_vedioartitle(myApp.getVedioartitle_ThreadVar());
                for(Gaokao_vedioartitleSendPhone gaokao_vedioartitleSendPhone:list){
                    String fileName=MyTools.fileIsExists(rootPath,MyTools.getFileName(gaokao_vedioartitleSendPhone.getTitle()));
                    if(fileName!=null){
                        myApp.addList(gaokao_vedioartitleSendPhone);
                    }
                }
                if(myApp.getList().size()>0){
                    Gaokao_vedioartitleSendPhone gaokao_vedioartitleSendPhone=myApp.getList().get(0);
                    myApp.removeList(0);
                    final String url="http://113.107.154.131:9001/download/"+ MyTools.getFileName(gaokao_vedioartitleSendPhone.getTitle())+".mp4";
                    downloadFile(url,MyTools.getFileName(gaokao_vedioartitleSendPhone.getTitle())+".mp4");
                }else{
                    stopSelf();
                }


            }
        }
    };
    public int onStartCommand(Intent intent, int flags, int startId) {

        //rootPath = intent.getStringExtra("rootPath");


        final String url="http://113.107.154.131:9001/gupiao/JsonActiongetGaokao_vedioartitle";
        final String post="jsonString="+" where  typeid='001' ";
        new Thread() {
            public void run() {
                try {
                    myApp.setVedioartitle_ThreadVar(MyTools.readFromDBA(url,post));
                    Message msg = new Message();
                    msg.what = 4; //subjectchapter
                    handler.sendMessage(msg);
                }catch (Exception e){Log.v("TAG",e.getMessage());}
            }}.start();




        return super.onStartCommand(intent, flags, startId);
    }
    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                List<Gaokao_vedioartitleSendPhone> list=myApp.getList();
                if(list.size()>0){
                    Gaokao_vedioartitleSendPhone gaokao_vedioartitleSendPhone=list.get(0);
                    list.remove(0);
                    final String url="http://113.107.154.131:9001/download/"+ MyTools.getFileName(gaokao_vedioartitleSendPhone.getTitle())+".mp4";
                    downloadFile(url,MyTools.getFileName(gaokao_vedioartitleSendPhone.getTitle())+".mp4");
                }else{
                    stopSelf();
                }
            }

        }
    };
    //使用系统下载器下载
    private void downloadFile(String filenameUrl, String downloadfilename) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(filenameUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);//漫游网络是否可以下载

        //设置文件类型，可以在下载结束后自动打开该文件
        // MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        // String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(versionUrl));
        // request.setMimeType(mimeString);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);

        //sdcard的目录下的download文件夹，必须设置
        request.setDestinationInExternalPublicDir("/gaokao/", downloadfilename);
        //request.setDestinationInExternalFilesDir(this,rootPath+"/gaokao/", downloadfilename);
        //request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

        //将下载请求加入下载队列
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
        long mTaskId = downloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }
}