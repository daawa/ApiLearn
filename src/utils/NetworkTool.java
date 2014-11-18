package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class NetworkTool {
	
	/** 
	 * 没有网络 
	 */  
	public static final int NETWORKTYPE_INVALID = 0;  
	
	/** 
	 * WAP 网络 
	 */  
	public static final int NETWORKTYPE_WAP = 1; 
	
	/** 
	 * 2G网络 
	 */  
	public static final int NETWORKTYPE_2G = 2;  
	
	/** 
	 * 3G和3G以上网络，或统称为快速网络 
	 */  
	public static final int NETWORKTYPE_3G = 3;  
	
	/**
	 *  WIFI 网络
	 */  
	public static final int NETWORKTYPE_WIFI = 4; 
	
	
	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("error", e.toString());
		}
		return false;
	}
	
	/**
	 * 判断是否是FastMobileNetWork，将3G或者3G以上的网络称为快速网络
	 * @param context
	 * @return
	 */
	public static boolean isFastMobileNetwork(Context context) {  
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);  
		switch (telephonyManager.getNetworkType()) {  
		       case TelephonyManager.NETWORK_TYPE_1xRTT:  
		           return false; // ~ 50-100 kbps  
		       case TelephonyManager.NETWORK_TYPE_CDMA:  
		           return false; // ~ 14-64 kbps  
		       case TelephonyManager.NETWORK_TYPE_EDGE:  
		           return false; // ~ 50-100 kbps  
		       case TelephonyManager.NETWORK_TYPE_GPRS:  
		           return false; // ~ 100 kbps 
		       case TelephonyManager.NETWORK_TYPE_IDEN:  
		           return false; // ~25 kbps 
		       case TelephonyManager.NETWORK_TYPE_UNKNOWN:  
		           return false;  
		           
		           
		       case TelephonyManager.NETWORK_TYPE_EVDO_0:  
		           return true; // ~ 400-1000 kbps  
		       case TelephonyManager.NETWORK_TYPE_EVDO_A:  
		           return true; // ~ 600-1400 kbps  		       
		       case TelephonyManager.NETWORK_TYPE_HSDPA:  
		           return true; // ~ 2-14 Mbps  
		       case TelephonyManager.NETWORK_TYPE_HSPA:  
		           return true; // ~ 700-1700 kbps  
		       case TelephonyManager.NETWORK_TYPE_HSUPA:  
		           return true; // ~ 1-23 Mbps  
		       case TelephonyManager.NETWORK_TYPE_UMTS:  
		           return true; // ~ 400-7000 kbps  
		       case TelephonyManager.NETWORK_TYPE_EHRPD:  
		           return true; // ~ 1-2 Mbps  
		       case TelephonyManager.NETWORK_TYPE_EVDO_B:  
		           return true; // ~ 5 Mbps  
		       case TelephonyManager.NETWORK_TYPE_HSPAP:  
		           return true; // ~ 10-20 Mbps  
		       
		       case TelephonyManager.NETWORK_TYPE_LTE:  
		           return true; // ~ 10+ Mbps  
		       
		       default:  
		           return false;  
		    }  
		} 
	
	/** 
     * 获取网络状态，wifi,wap,2g,3g. 
     * 
     * @param context 上下文 
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G},          *{@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}* <p>{@link #NETWORKTYPE_WIFI} 
     */  
  
    public static int getNetWorkType(Context context) {  
  
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo networkInfo = manager.getActiveNetworkInfo(); 
        
        int mNetWorkType = NETWORKTYPE_INVALID;
  
        if (networkInfo != null && networkInfo.isConnected()) {  
            String type = networkInfo.getTypeName();  
  
            if (type.equalsIgnoreCase("WIFI")) {  
                mNetWorkType = NETWORKTYPE_WIFI;  
            } else if (type.equalsIgnoreCase("MOBILE")) {  
                String proxyHost = android.net.Proxy.getDefaultHost();  
  
                mNetWorkType = TextUtils.isEmpty(proxyHost)  
                        ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G)  
                        : NETWORKTYPE_WAP;  
            }  
        }
  
        return mNetWorkType;  
    }   


}
