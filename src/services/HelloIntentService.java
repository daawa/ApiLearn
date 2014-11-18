package services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class HelloIntentService extends IntentService {

	  /**
	   * A constructor is required, and must call the super IntentService(String)
	   * constructor with a name for the worker thread.
	   */
	  public HelloIntentService() {
	      super("HelloIntentService");
	  }
	  
	  
	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	      Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
	      return super.onStartCommand(intent,flags,startId);
	  }


	  /**
	   * The IntentService calls this method from the default worker thread with
	   * the intent that started the service. When this method returns, IntentService
	   * stops the service, as appropriate.
	   */
	  @Override
	  protected void onHandleIntent(Intent intent) {
	      // Normally we would do some work here, like download a file.
	      // For our sample, we just sleep for 5 seconds.
		  Bundle b = intent.getExtras();
		  String key = b.getString("key");
		  /**
		   * 
		   * IntentServices create a new thread when you call the onHandleIntent method,
		   * and then kills that thread as soon as that onHandleIntent method returns;
		   * when you use Toast... to make hint, it depends on the looper the thread, which may have been dead.
		   */
		  //Toast.makeText(getApplicationContext(), "handle intent start, key:" + key, Toast.LENGTH_SHORT ).show();
	      long endTime = System.currentTimeMillis() + 5*1000;
	      while (System.currentTimeMillis() < endTime) {
	    	  Log.w("service, " ,"test, key:" + key + " currentTimeMillis:" + System.currentTimeMillis());
	          synchronized (this) {
	              try {
	                  Thread.sleep(endTime - System.currentTimeMillis());
	              } catch (Exception e) {
	              }
	          }
	          Log.w("service, " ,"test, key:" + key + " currentTimeMillis:" + System.currentTimeMillis());
	      }
	      
	     // Toast.makeText(getApplicationContext(), "handle intent end, key:" + key, Toast.LENGTH_LONG ).show();
	  }
	}