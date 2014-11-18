package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class AppPrivateFile {
	
	private static final String LOG_TAG = "LaunchCache";
	private Context mContext;
	private static AppPrivateFile mInstance;

	private AppPrivateFile(Context ctx) {
		mContext = ctx;
	}

	
	public static AppPrivateFile getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new AppPrivateFile(ctx);
		}

		return mInstance;

	}

	
	/**
	 * remove the illegal characters in the url, like white space, slash , backslash ..
	 * @param url an arbitrary string 
	 * @return a filtered string
	 */
	public String filterPath(String url) {

		if(url== null)
			return null;
		url = url.replaceAll("\\s", "_");
		return url.replaceAll(":|\\\\|/|\\?\\<\\>", "-");

	}
	
	public boolean isFileExists(String url){
		
		if(url == null){
			url = "";
		}		
		
//		String ap = mContext.getFilesDir().getAbsolutePath() + File.separator + filterPath(url);
//		File f = new File(ap);
		
		File f = mContext.getFileStreamPath(filterPath(url));	
			
		if(f.exists()){
			return true;
		}
			
		return false;
	}

	public boolean deleteCacheFile(String url) {
		String key = filterPath(url);
		return mContext.deleteFile(key);
	}

	public boolean writeCache(String url, byte[] data) {
		String key = filterPath(url);
		FileOutputStream file = null;
		try {
			file = mContext.openFileOutput(key, Context.MODE_PRIVATE);
			try {
				file.write(data);
				file.close();

				return true;
			} catch (IOException e) {
				Log.e(LOG_TAG, "write " + key + " error:" + e.getMessage());
			}
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "open file failed:" + key + " error:" + e.getMessage());
		} finally {

			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	public byte[] getInAppInputFile(String url) {
		String key = filterPath(url);
		FileInputStream file = null;
		FileChannel channel = null;
		ByteBuffer bb = null;
		try {
			file = mContext.openFileInput(key);
			channel = file.getChannel();
			try {
				bb = ByteBuffer.allocate((int) channel.size());
				channel.read(bb);
				channel.close();
				return bb.array();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "open file failed:" + key + " error:" + e.getMessage());
		} finally {

			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
