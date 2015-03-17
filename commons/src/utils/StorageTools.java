package utils;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.util.Log;

public class StorageTools {
	
	private final String TAG = "StorageTools";
	
	BroadcastReceiver mExternalStorageReceiver;
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;

	void updateExternalStorageState(ExternalStorageStateListener listener) {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        mExternalStorageAvailable = mExternalStorageWriteable = true;
	    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        mExternalStorageAvailable = true;
	        mExternalStorageWriteable = false;
	    } else {
	        mExternalStorageAvailable = mExternalStorageWriteable = false;
	    }
	    
	    if(listener != null)
	    	listener.onExternalStorageStateChanged(mExternalStorageAvailable, mExternalStorageWriteable);
	}

	public void startWatchingExternalStorage(Context ctx, final ExternalStorageStateListener listener) {
	    mExternalStorageReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            Log.i("test", "Storage: " + intent.getData());
	            updateExternalStorageState(listener);
	        }
	    };
	    IntentFilter filter = new IntentFilter();
	    filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
	    filter.addAction(Intent.ACTION_MEDIA_REMOVED);
	    ctx.registerReceiver(mExternalStorageReceiver, filter);
	    updateExternalStorageState(listener);
	}

	void stopWatchingExternalStorage(Context ctx) {
	    ctx.unregisterReceiver(mExternalStorageReceiver);
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 *  Checks if external storage is available to at least read 
	 */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	public File getAlbumStorageDir(String albumName) {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),albumName);
		if (!file.isDirectory() || !file.mkdirs()) {
			Log.e(TAG, "Directory not created");
			
			return null;
		}
		return file;
	}
	
	
	public static void checkPathStrings(Context context){
		
		String cache = 
		context.getExternalCacheDir().toString() + " ==\n ";
		//context.getExternalCacheDirs();
		cache += context.getExternalFilesDir("textFilesDir").toString() + " ==\n ";
		cache += context.getDatabasePath("testDatabasePath").toString() + " ==\n ";
		
		cache += context.getDir("testDir", Context.MODE_PRIVATE).toString() + " ==\n ";
		cache += context.getDir("testDir", Context.MODE_WORLD_READABLE).toString() + " ==\n ";		
		cache += context.getDir("testDir", Context.MODE_WORLD_WRITEABLE).toString() + " ==\n ";
		
		cache += context.getCacheDir().toString() + " ==\n ";
		cache += context.getFilesDir().toString() + " ==\n ";
		cache += context.getPackageCodePath().toString() + " ==\n ";
		cache += context.getPackageResourcePath().toString() + " ==\n ";
		cache += context.getObbDir().toString() + " ==\n ";
		
		Log.d("checkPathsStrings", "string:" + cache);
		
	}
	
	
	
	public static interface ExternalStorageStateListener{
		
		/**
		 * handle
		 * @param mExternalStorageAvailable mounted ,readable
		 * @param mExternalStorageWriteable mounted ,readable, writable
		 */
		public void onExternalStorageStateChanged(boolean mExternalStorageAvailable, boolean mExternalStorageWriteable);
		
	}
}
