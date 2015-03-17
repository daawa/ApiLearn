package apiDemo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import play.apilearn.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import custom.view.ViewPagerWithBackground;

public class ViewpagerActivity extends Activity implements OnPageChangeListener {

	ViewPagerWithBackground vp;
	GuideViewPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_page);

		vp = (ViewPagerWithBackground) findViewById(R.id.viewpager);


		ArrayList<Bitmap> aa = new ArrayList<Bitmap>();
		ArrayList<Bitmap> bb = new ArrayList<Bitmap>();
		
		Bitmap bm;
		Matrix mt = new Matrix();
		
		InputStream is = getResources().openRawResource(R.drawable.launch_background);
		bm = BitmapFactory.decodeStream(is);
		aa.add(bm);

		mt.reset();
		mt.postRotate(90.0f, 0.5f, 0.5f);
		bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mt, true);
		bb.add(bm);


		is = getResources().openRawResource(R.drawable.laucn_01);
		bm = BitmapFactory.decodeStream(is);
		aa.add(bm);

		mt.reset();
		mt.postRotate(90.0f, 0.5f, 0.5f);
		bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mt, true);
		bb.add(bm);

		is = getResources().openRawResource(R.drawable.laucn_02);
		bm = BitmapFactory.decodeStream(is);
		aa.add(bm);

		mt.reset();
		mt.postRotate(90.0f, 0.5f, 0.5f);
		bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mt, true);
		bb.add(bm);

		is = getResources().openRawResource(R.drawable.laucn_03);
		bm = BitmapFactory.decodeStream(is);
		aa.add(bm);

		mt.reset();
		mt.postRotate(90.0f, 0.5f, 0.5f);
		bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mt, true);
		bb.add(bm);

		is = getResources().openRawResource(R.drawable.laucn_04);
		bm = BitmapFactory.decodeStream(is);
		aa.add(bm);

		mt.reset();
		mt.postRotate(90.0f, 0.5f, 0.5f);
		bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mt, true);
		bb.add(bm);

		

		//la.setOnFinishListener(listener);
		vp.setImages(aa, bb);

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == adapter.getCount() - 1) {
			View v = adapter.mViews.get(arg0);
			v.setOnTouchListener(new View.OnTouchListener() {

				private VelocityTracker mVelocityTracker = null;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int index = event.getActionIndex();
					int action = event.getActionMasked();
					int pointerId = event.getPointerId(index);

					switch (action) {
					case MotionEvent.ACTION_DOWN:
						if (mVelocityTracker == null) {
							// Retrieve a new VelocityTracker object to watch
							// the velocity of a motion.
							mVelocityTracker = VelocityTracker.obtain();
						} else {
							// Reset the velocity tracker back to its initial
							// state.
							mVelocityTracker.clear();
						}
						// Add a user's movement to the tracker.
						mVelocityTracker.addMovement(event);
						break;
					case MotionEvent.ACTION_MOVE:
						mVelocityTracker.addMovement(event);
						// When you want to determine the velocity, call
						// computeCurrentVelocity(). Then call getXVelocity()
						// and getYVelocity() to retrieve the velocity for each
						// pointer ID.
						mVelocityTracker.computeCurrentVelocity(1000);
						// Log velocity of pixels per second
						// Best practice to use VelocityTrackerCompat where
						// possible.
						Log.d("",
								"X velocity: "
										+ VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId));
						// mVelocityTracker.getXVelocity(pointerId);

						Log.d("",
								"Y velocity: "
										+ VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId));
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL:
						// Return a VelocityTracker object back to be re-used by
						// others. You must not touch the object after you call
						// this function
						// mVelocityTracker.recycle();
						break;
					}
					return true;
				}

			});
		}

		// TODO
		// if (arg0 == views.size() - 1) {
		// View v = views.get(arg0);
		//
		// if (arg0 == 2) {
		// v.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		//
		// return false;
		// }
		//
		// });
		// }
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

class GuideViewPagerAdapter extends PagerAdapter {
	private static final String TAG = "GuideViewPagerAdapter";

	public List<View> mViews;

	public GuideViewPagerAdapter(List<View> views) {
		this.mViews = views;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(mViews.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {

	}

	@Override
	public int getCount() {
		if (mViews != null) {
			return mViews.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		Log.w(TAG, "isViewFromObject:" + (arg0 == arg1));
		return (arg0 == arg1);
	}

	@Override
	public Object instantiateItem(View arg0, final int arg1) {
		Log.w(TAG, "instantiateItem :" + arg1);
		((ViewPager) arg0).addView(mViews.get(arg1), 0);
		return mViews.get(arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

	// @Override
	// public int getItemPosition(Object object) {
	//
	// return PagerAdapter.POSITION_NONE;
	// }

}
