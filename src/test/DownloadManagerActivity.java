package test;
 

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import play.apilearn.R;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.MimeTypeMap;
  
public class DownloadManagerActivity extends Activity {  
    private DownloadManager downloadManager;  
    private SharedPreferences prefs;  
    private static final String DL_ID = "downloadId";  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.test_download_manager);  
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);  
        prefs = PreferenceManager.getDefaultSharedPreferences(this);   
    }  
    @Override  
    protected void onPause() {   
        super.onPause();  
        unregisterReceiver(receiver);  
    }  
    @Override  
    protected void onResume() {   
        super.onResume();  
        if(!prefs.contains(DL_ID)) {   
            String url = "http://10.0.2.2/android/film/G3.mp4";  
            //开始下载   
            Uri resource = Uri.parse(encodeGB(url));   
            DownloadManager.Request request = new DownloadManager.Request(resource);   
            request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);   
            request.setAllowedOverRoaming(false);   
            //设置文件类型  
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));  
            request.setMimeType(mimeString);  
            //在通知栏中显示   
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE); 
            request.setVisibleInDownloadsUi(true);  
            //sdcard的目录下的download文件夹  
            request.setDestinationInExternalPublicDir("/download/", "G3.mp4");  
            request.setTitle("移动G3广告");   
            long id = downloadManager.enqueue(request);   
            //保存id   
            prefs.edit().putLong(DL_ID, id).commit();   
        } else {   
            //下载已经开始，检查状态  
            queryDownloadStatus();   
        }   
  
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));  
    }  
      
    /** 
     * 如果服务器不支持中文路径的情况下需要转换url的编码。 
     * @param string 
     * @return 
     */  
    public String encodeGB(String string)  
    {  
        //转换中文编码  
        String split[] = string.split("/");  
        for (int i = 1; i < split.length; i++) {  
            try {  
                split[i] = URLEncoder.encode(split[i], "GB2312");  
            } catch (UnsupportedEncodingException e) {  
                e.printStackTrace();  
            }  
            split[0] = split[0]+"/"+split[i];  
        }  
        split[0] = split[0].replaceAll("\\+", "%20");//处理空格  
        return split[0];  
    }  
      
    private BroadcastReceiver receiver = new BroadcastReceiver() {   
        @Override   
        public void onReceive(Context context, Intent intent) {   
            //这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听  
            Log.v("intent", ""+intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));  
            queryDownloadStatus();   
        }   
    };   
      
    private void queryDownloadStatus() {   
        DownloadManager.Query query = new DownloadManager.Query();   
        query.setFilterById(prefs.getLong(DL_ID, 0));   
        Cursor c = downloadManager.query(query);   
        if(c.moveToFirst()) {   
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));   
            switch(status) {   
            case DownloadManager.STATUS_PAUSED:   
                Log.v("down", "STATUS_PAUSED");  
            case DownloadManager.STATUS_PENDING:   
                Log.v("down", "STATUS_PENDING");  
            case DownloadManager.STATUS_RUNNING:   
                //正在下载，不做任何事情  
                Log.v("down", "STATUS_RUNNING");  
                break;   
            case DownloadManager.STATUS_SUCCESSFUL:   
                //完成  
                Log.v("down", "下载完成");  
                break;   
            case DownloadManager.STATUS_FAILED:   
                //清除已下载的内容，重新下载  
                Log.v("down", "STATUS_FAILED");  
                downloadManager.remove(prefs.getLong(DL_ID, 0));   
                prefs.edit().clear().commit();   
                break;   
            }   
        }  
    }  
} 