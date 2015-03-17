package custom.view;

/**
 * Project: Taobao aPad
 * Create Date: 2014-5-30
 * Version: 1.0.0
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import play.apilearn.R;

public class BannerIndicator extends View {
    /* Fields */
    private boolean itemIsHollow = false;
    private int itemCount = 1;
    private int itemSize = 10;
    private int itemMargin = 10;
    private int itemColor = 0x000000;
    private int itemBgColor = 0xFFFFFF;

    private int startX = 0;
    private int startY = 0;
    private int currentPosition = 0;
    private float currentOffset = 0;

    private Paint bgPaint = null;
    private Paint paint = null;

    /* View */

    public BannerIndicator(Context context) {
        super(context);

        this.init(context, null);
    }

    public BannerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.init(context, attrs);
    }

    public BannerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BannerIndicator);
            this.itemIsHollow = attributes.getBoolean(R.styleable.BannerIndicator_itemIsHollow, false);
            this.itemCount = attributes.getInt(R.styleable.BannerIndicator_itemCount, 1);
            this.itemColor = attributes.getColor(R.styleable.BannerIndicator_itemColor, 0x000000);
            this.itemBgColor = attributes.getColor(R.styleable.BannerIndicator_itemBgColor, 0xFFFFFF);
            this.itemSize = (int) attributes.getDimension(R.styleable.BannerIndicator_itemSize, 10);
            this.itemMargin = (int) attributes.getDimension(R.styleable.BannerIndicator_itemMargin, this.itemSize);

            attributes.recycle();
        }

        this.bgPaint = new Paint();
        this.bgPaint.setColor(this.itemBgColor);
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setStyle(this.itemIsHollow ? Paint.Style.STROKE : Paint.Style.FILL);
        this.bgPaint.setStrokeWidth(2);

        this.paint = new Paint();
        this.paint.setColor(this.itemColor);
        this.bgPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int xUnit = this.itemSize + this.itemMargin;
        for (int i = 0; i < this.itemCount; ++i) {
            canvas.drawCircle(this.startX + i * xUnit, this.startY, this.itemSize / 2, this.bgPaint);
        }

        float currentX = this.startX + (this.currentPosition + this.currentOffset) * xUnit;

        float scrollWith = this.itemCount * xUnit;
        if (currentX > this.startX - xUnit / 2 + scrollWith) {
            currentX -= scrollWith;
        }
        canvas.drawCircle(currentX, startY, this.itemSize / 2.0f, this.paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.startX = (w - this.itemCount * (this.itemSize + this.itemMargin) + this.itemMargin) / 2;
        this.startY = h / 2;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    /* I/F */

    public void setItemIsHollow(boolean isHollow) {
        this.itemIsHollow = isHollow;
        this.bgPaint.setStyle(this.itemIsHollow ? Paint.Style.STROKE : Paint.Style.FILL);
    }

    public void setItemCount(int count) {
        this.itemCount = count;
    }

    public void onPageScrolled(int position, float offset) {
        this.currentPosition = position;
        this.currentOffset = offset;

        this.invalidate();
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public void setItemMargin(int itemMargin) {
        this.itemMargin = itemMargin;
    }

    public void setItemColor(int color) {
        this.itemColor = color;
    }

    public void setItemBgColor(int color) {
        this.itemBgColor = color;
    }
}
