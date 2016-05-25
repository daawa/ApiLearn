package com.thestore.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.thestore.main.component.R;

import java.io.InputStream;

/**
 * Created by zhangzhenwei on 16/5/24.
 */

public class GifView extends View {

    static final int center = 0;
    static final int fit_start = 1;
    static final int fit_end = 2;

    private Movie mMovie;
    private long movieStart;

    int src_id;
    int scale_mode;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(11)
    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GifView);
        src_id = ta.getResourceId(R.styleable.GifView_raw_src, 0);
        scale_mode = ta.getInt(R.styleable.GifView_scale_mode, -1);
        setInputSrc(src_id);
    }

    public void setInputSrc(InputStream is) {
        mMovie = Movie.decodeStream(is);
        movieStart = 0;
    }

    public void setInputSrc(int raw_src_id ) {
        if (src_id > 0) {
            try {
                InputStream is = getContext().getResources().openRawResource(src_id);
                setInputSrc(is);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mMovie != null) {
            int width = getResovledDim(widthMeasureSpec, getPaddingLeft() + getPaddingRight() + mMovie.width());
            int height = getResovledDim(heightMeasureSpec, getPaddingBottom() + getPaddingTop() + mMovie.height());
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    private int getResovledDim(int spec, int expected) {
        int specMode = MeasureSpec.getMode(spec);
        int size = MeasureSpec.getSize(spec);
        if (mMovie != null) {
            switch (specMode) {
                case MeasureSpec.UNSPECIFIED:
                    size = expected;
                    break;
                case MeasureSpec.AT_MOST:
                    size = size > expected ? expected : size;
                    break;
//                case MeasureSpec.EXACTLY:
//                default:
//                    break;
            }
        }

        return size;
    }


    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = (int) now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            float x = 0, y = 0;
            switch (scale_mode) {
                case center:
                    x = (getWidth() - mMovie.width()) / 2;
                    y = (getHeight() - mMovie.height()) / 2;
                    break;
                case fit_start:
                    x = 0; y = 0;
                    break;
                case fit_end:
                    x = getWidth() - mMovie.width();
                    y = getHeight() - mMovie.height();
                    break;
            }
            mMovie.draw(canvas, x, y);
            this.invalidate();
        }
    }
}
