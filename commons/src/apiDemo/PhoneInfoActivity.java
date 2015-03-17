package apiDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class PhoneInfoActivity extends Activity {

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(android.R.layout.simple_list_item_1);
		TextView text = (TextView) findViewById(android.R.id.text1);
		text.setBackgroundColor(0xffffffff);
		text.setTextColor(0xff000000);
		text.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
					Log.w("TEST EVENT", "action_down ");
					switch(event.getEdgeFlags()){
					case MotionEvent.EDGE_RIGHT:
					case MotionEvent.EDGE_BOTTOM:
					case MotionEvent.EDGE_LEFT:
					case MotionEvent.EDGE_TOP:
						Log.w("TEST EVENT", "edge touched !! ");
						break;
						
					}
					Log.w("TEST TOUCH EVENT", " edgeFlags:" + event.getEdgeFlags());
				}
				int count = event.getPointerCount();
				for (int i = 0; i < count; i++) {
					int id = event.getPointerId(i);
					Log.w("TEST EVENT", " " + i + ": " + id);
				}
				return false;
			}
		});
		
//		ScrollView scroller = new ScrollView(this);
//		TextView text = new TextView(this);
//		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, this.getResources().getDisplayMetrics());
//		text.setPadding(padding, padding, padding, padding);
//		scroller.addView(text);
//		
//		setContentView(scroller);
		
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		StringBuilder sb = new StringBuilder();
		sb.append(" deviceID (imei): ");
		sb.append(tm.getDeviceId());
		sb.append("\n");

		sb.append(" network type: ");
		sb.append(tm.getNetworkType());
		sb.append("\n");

		sb.append(" Line1Number: ");
		sb.append(tm.getLine1Number());
		sb.append("\n");

		sb.append(" SubscriberId (imsi): ");
		sb.append(tm.getSubscriberId());
		sb.append("\n");

		sb.append(" NetworkCountryIso: ");
		sb.append(tm.getNetworkCountryIso());
		sb.append("\n");

		sb.append(" NetworkOperator: ");
		sb.append(tm.getNetworkOperator());
		sb.append("\n");

		String phoneType = getPhoneType(tm);
		sb.append(" phone type:" + phoneType);
		
		sb.append("\n Build Info: \n" + getSystemBuildInfo());
		
		text.setText(sb.toString());

	}

	private String getPhoneType(TelephonyManager tm) {
		switch (tm.getPhoneType()) {
		case TelephonyManager.PHONE_TYPE_CDMA:
			return "PHONE_TYPE_CDMA";
		case TelephonyManager.PHONE_TYPE_GSM:
			return "PHONE_TYPE_GSM";

		case TelephonyManager.PHONE_TYPE_SIP:
			return "PHONE_TYPE_SIP";

		case TelephonyManager.PHONE_TYPE_NONE:
			return "PHONE_TYPE_NONE";
		}
		
		return "unkown";

	}
	
	public String getSystemBuildInfo(){
		String phoneInfo = "\n Product: " + android.os.Build.PRODUCT;
        phoneInfo += ", \n CPU_ABI: " + android.os.Build.CPU_ABI;
        phoneInfo += ", \n TAGS: " + android.os.Build.TAGS;
        phoneInfo += ", \n VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE;
        phoneInfo += ", \n MODEL: " + android.os.Build.MODEL;
        phoneInfo += ", \n SDK: " + android.os.Build.VERSION.SDK;
        phoneInfo += ", \n VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += ", \n DEVICE: " + android.os.Build.DEVICE;
        phoneInfo += ", \n DISPLAY: " + android.os.Build.DISPLAY;
        phoneInfo += ", \n BRAND: " + android.os.Build.BRAND;
        phoneInfo += ", \n BOARD: " + android.os.Build.BOARD;
        phoneInfo += ", \n FINGERPRINT: " + android.os.Build.FINGERPRINT;
        phoneInfo += ", \n ID: " + android.os.Build.ID;
        phoneInfo += ", \n MANUFACTURER: " + android.os.Build.MANUFACTURER;
        phoneInfo += ", \n USER: " + android.os.Build.USER;
        
        return phoneInfo;
	}
}
