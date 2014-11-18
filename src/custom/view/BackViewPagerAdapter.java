package custom.view;


import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class BackViewPagerAdapter extends PagerAdapter{
	private static final String TAG = "GuideViewPagerAdapter";

	public List<? extends View> mViews;

	public BackViewPagerAdapter(List<? extends View> views){
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
		if(mViews != null){
			return mViews.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		Log.i(TAG, "isViewFromObject:" + (arg0 == arg1));
		return (arg0 == arg1);
	}
	
    @Override
    public Object instantiateItem(View arg0, final int arg1) {
    	Log.w(TAG, "instantiateItem :" + arg1);
		((ViewPager) arg0).addView(mViews.get(arg1),0);
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

//	@Override
//	public int getItemPosition(Object object) {
//
//		return PagerAdapter.POSITION_NONE;
//	}
    
    

}