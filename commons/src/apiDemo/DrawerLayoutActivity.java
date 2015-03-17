package apiDemo;

import play.apilearn.NoteActivity;
import play.apilearn.R;
import test.EditTextWithImageActivity;
import utils.StorageTools;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import camera.CameraActivity;
import camera.MoreFeaturedCameraActivity;
import camera.PickPhotoActivity;
import fragment.test.TestFragmentFullscreenActivity;

/**
 * test touch event; drawer layout
 * 
 * @author may
 * 
 */

public class DrawerLayoutActivity extends Activity {
	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	//View trackView ;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override 
	public void onResume( ){
		super.onResume();
		
		
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer_layout);
		
		//ViewGroup vp = (ViewGroup)this.findViewById(R.id.content_frame);
		

		// mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mPlanetTitles = new String[] {
				"0 AAAAAAA",
				"1 Note",
				"2 PickPhoto",
				"3 Camera",
				"4 MoreFeaturedCameraActivity",
				"5 EditTextWithImage",
				"6 Start Service",
				"7 获取手机信息",
				"8 TouchEvent",
				"9 ViewPager",
				"10 TestFragmentActivity"};

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_comment_gd_nor, R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mPlanetTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		StorageTools.checkPathStrings(this);
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		Intent it;
		switch (position) {
		case 0:
			// Create a new fragment and specify the planet to show based on
			// position
			Fragment fragment = new PlanetFragment();
			Bundle args = new Bundle();
			args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
			fragment.setArguments(args);

			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment)
					.commit();

			break;

		case 1:
			it = new Intent(this.getApplicationContext(), NoteActivity.class);
			startActivity(it);
			break;

		case 2:
			it = new Intent(this.getApplicationContext(), PickPhotoActivity.class);
			startActivity(it);
			break;
		case 3:
			it = new Intent(this.getApplicationContext(), CameraActivity.class);
			startActivity(it);
			break;
		case 4:
			it = new Intent(this.getApplicationContext(), MoreFeaturedCameraActivity.class);
			startActivity(it);
			break;

		case 5:
			it = new Intent(this.getApplicationContext(), EditTextWithImageActivity.class);
			startActivity(it);
			break;
		case 6:
			Intent intent = new Intent(this, services.HelloIntentService.class);
			intent.putExtra("key", "test string");
			startService(intent);
			break;
			
		case 7:
			it = new Intent(this.getApplicationContext(), PhoneInfoActivity.class);
			startActivity(it);
			break;
		case 8:
			it = new Intent(this.getApplicationContext(), TouchEventActivity.class);
			startActivity(it);
			break;
		case 9:
			it = new Intent(this.getApplicationContext(), ViewpagerActivity.class);
			startActivity(it);
			break;

		case 10:
			it = new Intent(this.getApplicationContext(), TestFragmentFullscreenActivity.class);
			startActivity(it);
			break;



		}

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}
}

class PlanetFragment extends Fragment {
	public static final String ARG_PLANET_NUMBER = "planet_num";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		ScrollView scroller = new ScrollView(getActivity());
		scroller.setBackgroundColor(0x11ffffff);
		scroller.setOnTouchListener(new View.OnTouchListener() {

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

		TextView text = new TextView(getActivity());
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity()
				.getResources()
				.getDisplayMetrics());
		text.setPadding(padding, padding, padding, padding);
		text.setText("TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST");
		scroller.addView(text);
		return scroller;
	}

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}
	
	
}