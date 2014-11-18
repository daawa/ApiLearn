package apiDemo;

import play.apilearn.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class TouchEventActivity extends Activity {
	
	public static final String DEBUG_TAG = "TouchEventActivity";
	
	View tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.touchevent_activity);
		
		tv = findViewById(R.id.textview1);
		
		tv.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d("","");
				
				int action = MotionEventCompat.getActionMasked(event);
		        
			    switch(action) {
			        case (MotionEvent.ACTION_DOWN) :
			            Log.d("TextView","Action was DOWN");
			            return true;
			        case (MotionEvent.ACTION_MOVE) :
			            Log.d("TextView","Action was MOVE");
			            return false;
			        case (MotionEvent.ACTION_UP) :
			            Log.d("TextView","Action was UP");
			            return true;
			        case (MotionEvent.ACTION_CANCEL) :
			            Log.d("TextView","Action was CANCEL");
			            return true;
			        case (MotionEvent.ACTION_OUTSIDE) :
			            Log.d("TextView","Movement occurred outside bounds " +
			                    "of current screen element");
			            return true;      
			        default : 
			            return false;
			    }      

				
			}
		});
		
		int dt = ViewConfiguration.getDoubleTapTimeout();
		int lpt = ViewConfiguration.getLongPressTimeout();
		int tt = ViewConfiguration.getTapTimeout();
		
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event){ 
	        
	    int action = MotionEventCompat.getActionMasked(event);
	        
	    switch(action) {
	        case (MotionEvent.ACTION_DOWN) :
	            Log.d(DEBUG_TAG,"Action was DOWN");
	            return true;
	        case (MotionEvent.ACTION_MOVE) :
	            Log.d(DEBUG_TAG,"Action was MOVE");
	            return true;
	        case (MotionEvent.ACTION_UP) :
	            Log.d(DEBUG_TAG,"Action was UP");
	            return true;
	        case (MotionEvent.ACTION_CANCEL) :
	            Log.d(DEBUG_TAG,"Action was CANCEL");
	            return true;
	        case (MotionEvent.ACTION_OUTSIDE) :
	            Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
	                    "of current screen element");
	            return true;      
	        default : 
	            return super.onTouchEvent(event);
	    }      
	}

}
