//package apiDemo;
//
//import play.apilearn.R;
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//public class SlidingActivity extends Activity implements OnClickListener{
//	SlidingMenu mSlidingMenu;
//
//	@Override
//	protected void onCreate(Bundle arg0) {
//		super.onCreate(arg0);
//		setContentView(R.layout.main);
//
//
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
//		mSlidingMenu.setAlignScreenWidth((dm.widthPixels / 5) * 2);
//		
//		View leftView=getLayoutInflater().inflate(R.layout.left_menu, null);
//		View rightView=getLayoutInflater().inflate(R.layout.right_menu, null);
//		View centerView=getLayoutInflater().inflate(R.layout.center, null);
//		
//		mSlidingMenu.setLeftView(leftView);
//		mSlidingMenu.setRightView(rightView);
//		mSlidingMenu.setCenterView(centerView);
//        
//		Button showLeftMenu=(Button)centerView.findViewById(R.id.center_left_btn);
//		showLeftMenu.setOnClickListener(this);
//		Button showRightMenu=(Button)centerView.findViewById(R.id.center_right_btn);
//		showRightMenu.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.center_left_btn:
//			mSlidingMenu.showLeftView();
//			break;
//        case R.id.center_right_btn:
//        	mSlidingMenu.showRightView();
//			break;
//		default:
//			break;
//		}
//	}
//	
//}