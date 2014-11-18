package custom.view;

import java.util.ArrayList;

import play.apilearn.R;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class ViewPagerWithBackground extends FrameLayout {

	public static final String TAG = "LaunchAct";
	
	DisplayMetrics mDm ;

	boolean mFinished = false;

	@InjectView(R.id.back_img)
	ImageView mBackImg;

	@InjectView(R.id.launch_view_pager)
	ViewPager mViewPager;

	@InjectView(R.id.indicator)
	BannerIndicator indicator;

	@InjectView(R.id.skip_button)
	ImageView skip;

	private int mBackMoveStep = 0;
	private int mCurPage = 0;

	private ArrayList<Bitmap> mHBitmaps;

	private ArrayList<Bitmap> mVBitmaps;

	private BackViewPagerAdapter adapter;

	public ViewPagerWithBackground(Context c) {
		super(c);
		init();
	}

	public ViewPagerWithBackground(Context c, AttributeSet as) {
		super(c, as);
		init();
	}

	public ViewPagerWithBackground(Context c, AttributeSet as, int style) {
		super(c, as, style);
		init();
	}

	private void init() {
		final LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
		
		mDm = getContext().getResources().getDisplayMetrics();
		
		View v = mLayoutInflater.inflate(R.layout.view_pager_with_background, null, false);
		addView(v);
		SimpleInjector.injectViewMembers(this, v);

	}

	public void setImages(ArrayList<Bitmap> hbms, ArrayList<Bitmap> vbms) {
		mHBitmaps = hbms;
		mVBitmaps = vbms;

		if (hbms.size() > 2) {
			indicator.setItemCount(hbms.size() - 1);
//			skip.setImageResource(R.drawable.start_page_skip1);
//			skip.setOnClickListener(clickListener);
		} else
			indicator.setVisibility(View.GONE);

		Configuration config = getContext().getResources().getConfiguration();
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			initBackImg(mHBitmaps);
			initViewPager(mHBitmaps, mDm.widthPixels);
		} else {
			initBackImg(mVBitmaps);
			initViewPager(mVBitmaps, mDm.widthPixels);
		}
	}

	public void onConfigChanged(Configuration config) {
		if (this.getVisibility() != View.VISIBLE)
			return;

		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			initBackImg(mHBitmaps);
			initViewPager(mHBitmaps, mDm.widthPixels);
		} else {
			initBackImg(mVBitmaps);
			initViewPager(mVBitmaps, mDm.widthPixels);
		}
	}

	private void adjustBackImgScroll() {
		int scroll = mCurPage * mBackMoveStep;
		mBackImg.scrollTo(scroll, 0);
	}

	private void initBackImg(ArrayList<Bitmap> bms) {

		final Bitmap bm = bms.get(0);

		/**
		 * bm can be null, indicating no background exists
		 */
		if (bm == null) {
			return;
		}
		

		final int cw = mDm.widthPixels;
		final int ch = mDm.heightPixels;

		int w = bm.getWidth();
		int h = bm.getHeight();
		float wr = ((float) cw) / w;
		float hr = ((float) ch) / h;

		/**
		 * after resized, the width of the new bit map would be less than the
		 * width of the container
		 */
		if (hr < wr) {
			// mBackImg.setScaleType(ScaleType.CENTER_CROP);
			// mBackImg.setImageBitmap(bm);
			// mBackMoveStep = 0;
			// adjustBackImgScroll();
			// return;
			hr = wr;
		}

		Matrix mt = new Matrix();
		mt.postScale(hr, hr);

		Bitmap nbm = Bitmap.createBitmap(bm, 0, 0, w, h, mt, true);

		Log.d(TAG, "scale  hr :" + hr);

		int size = nbm.getByteCount();

		Log.d(
				TAG,
				" \n scale  size bytes:" + size + " \nwidth:" + nbm.getWidth() + "\nheight:"
						+ nbm.getHeight());

		mBackMoveStep = 0;
		int count = bms.size();
		if (count > 2) {
			int s = (nbm.getWidth() - cw) / (count - 2);
			if (s > 0) {
				mBackMoveStep = s;
			}
		}
		mBackImg.setScaleType(ScaleType.MATRIX);
		mBackImg.setImageBitmap(nbm);
		adjustBackImgScroll();
	}

	private void initViewPager(ArrayList<Bitmap> bms, int width) {

		if (adapter == null || adapter.mViews == null) {
			LayoutInflater inf = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			ArrayList<View> views = new ArrayList<View>(bms.size() - 1);

			for (int i = 1; i < bms.size(); i++) {
				ImageView im = (ImageView) inf.inflate(R.layout.image_view, mViewPager, false);
				// im.setImageResource(R.drawable.banner_default);
				// im.setImageBitmap(bms.get(i));
				views.add(im);
				if (i == bms.size() - 1) {
					im.setOnClickListener(clickListener);
				}
			}

			adapter = new BackViewPagerAdapter(views);
			mViewPager.setAdapter(adapter);
			mViewPager.setOnPageChangeListener(viewpagerListener);
		}

		if (adapter.mViews.size() != bms.size() - 1) {
			Log.e(TAG, "adapter.mViews.size() != bms.size() -1");
		} else {
			for (int i = 1; i < bms.size(); i++) {
				Matrix mt = new Matrix();
				ImageView im = (ImageView) adapter.mViews.get(i - 1);
				Bitmap b = bms.get(i);
				if (b != null) {
					int w = b.getWidth();
					float r = ((float) width) / w;
					Log.d(TAG, "width ratio: " + r);
					mt.reset();
					mt.postScale(r, r);

					b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), mt, true);
				}
				im.setImageBitmap(b);
			}
			return;
		}
	}
	
	private ViewPager.OnPageChangeListener viewpagerListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			Log.e(TAG, "onPageSelected:" + arg0);
			if (arg0 < adapter.getCount()) {
				// int pre = mCurPage;
				// mCurPage = arg0;
				// mBackImg.scrollTo(mCurPage * mBackMoveStep, 0);
			}

		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			indicator.onPageScrolled(position, positionOffset);

			int delta = -1;
			if (positionOffset > 0.0f) {

				delta = (int) (mBackMoveStep * positionOffset) + position * mBackMoveStep;
				mBackImg.scrollTo(delta, 0);
			}

			Log.d(TAG, "onPageScrolled \n pos:" + position + " \npositionOffset:" + positionOffset
					+ "\n delta:" + delta);

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};



	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Log.d(TAG, "the last page " + " clicked");

			mFinished = true;
//			Launcher.displayLaunchView(LaunchAct.this, false);

//			if (mFinishListener != null)
//				mFinishListener.onFinish();

		}
	};

//	private FinishListener mFinishListener;

//	public void setOnFinishListener(FinishListener listener) {
//		mFinishListener = listener;
//	}

}
