package lbx.xloadlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import lbx.xloadlib.R;
import lbx.xloadlib.state.State;
import lbx.xloadlib.utils.UIUtils;

public class DefaultDownloadView extends View {
    private int mHeight;
    private int mWidth;
    private Bitmap mRunningImg;
    private Bitmap mSuccessImg;
    private Bitmap mNormalImg;
    private Bitmap mErrImg;
    private Bitmap mWaitImg;
    private int mCenterImgSize;
    private int mBackgroundColor;
    private int mStartColor;
    private int mErrColor;
    private int mWaitColor;
    private int mSucColor;
    private int mNormalColor;
    private float mProgress;
    private int mCircleWidth;
    private State mState;
    private Context mContext;
    private Paint mRunningPaint;
    private Paint mSuccessPaint;
    private Paint mErrorPaint;
    private Paint mNormalPaint;
    private Paint mWaitPaint;

    public DefaultDownloadView(Context context) {
        this(context, null);
    }

    public DefaultDownloadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultDownloadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mState = State.NORMAL;
        this.mContext = context;
        this.init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.initAttrs(attrs);
        this.initPaints();
    }

    private void initPaints() {
        Paint paint = this.getDefaultPaint();
        paint.setColor(this.mNormalColor);
        paint.setStrokeWidth((float) this.mCircleWidth);
        paint.setStyle(Style.STROKE);
        this.mRunningPaint = paint;
        paint = this.getDefaultPaint();
        paint.setColor(this.mSucColor);
        paint.setStrokeWidth((float) this.mCircleWidth);
        paint.setStyle(Style.STROKE);
        this.mSuccessPaint = paint;
        paint = this.getDefaultPaint();
        paint.setColor(this.mNormalColor);
        paint.setStrokeWidth((float) this.mCircleWidth);
        paint.setStyle(Style.STROKE);
        this.mNormalPaint = paint;
        paint = this.getDefaultPaint();
        paint.setColor(this.mErrColor);
        paint.setStrokeWidth((float) this.mCircleWidth);
        paint.setStyle(Style.STROKE);
        this.mErrorPaint = paint;
        paint = this.getDefaultPaint();
        paint.setColor(this.mWaitColor);
        paint.setStrokeWidth((float) this.mCircleWidth);
        paint.setStyle(Style.STROKE);
        this.mWaitPaint = paint;
    }

    private Paint getDefaultPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        return paint;
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = this.mContext.obtainStyledAttributes(attrs, R.styleable.dlView);
        this.mCenterImgSize = UIUtils.dip2px(this.mContext, (float) ((int) array.getDimension(R.styleable.dlView_dl_centerImgSize, 20.0F)));
        this.mRunningImg = this.scaleImg(BitmapFactory.decodeResource(this.mContext.getResources(), array.getResourceId(R.styleable.dlView_dl_runningImg, R.drawable.running)));
        this.mSuccessImg = this.scaleImg(BitmapFactory.decodeResource(this.mContext.getResources(), array.getResourceId(R.styleable.dlView_dl_successImg, R.drawable.success)));
        this.mNormalImg = this.scaleImg(BitmapFactory.decodeResource(this.mContext.getResources(), array.getResourceId(R.styleable.dlView_dl_normalImg, R.drawable.normal)));
        this.mErrImg = this.scaleImg(BitmapFactory.decodeResource(this.mContext.getResources(), array.getResourceId(R.styleable.dlView_dl_errImg,R. drawable.err)));
        this.mWaitImg = this.scaleImg(BitmapFactory.decodeResource(this.mContext.getResources(), array.getResourceId(R.styleable.dlView_dl_waitImg, R.drawable.normal)));
        this.mBackgroundColor = array.getColor(R.styleable.dlView_dl_bg, UIUtils.getColor(this.mContext, R.color.white));
        this.mStartColor = array.getColor(R.styleable.dlView_dl_startColor, UIUtils.getColor(this.mContext, R.color.blue_light));
        this.mErrColor = array.getColor(R.styleable.dlView_dl_errColor, UIUtils.getColor(this.mContext, R.color.red));
        this.mWaitColor = array.getColor(R.styleable.dlView_dl_waitColor, UIUtils.getColor(this.mContext, R.color.gray));
        this.mSucColor = array.getColor(R.styleable.dlView_dl_successColor, UIUtils.getColor(this.mContext, R.color.green_light));
        this.mNormalColor = array.getColor(R.styleable.dlView_dl_normalColor, UIUtils.getColor(this.mContext, R.color.blue_light));
        this.mCircleWidth = UIUtils.dip2px(this.mContext, (float) ((int) array.getDimension(R.styleable.dlView_dl_circleWidth, 2.0F)));
        array.recycle();
    }

    private Bitmap scaleImg(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), this.getMatrix(bitmap), true);
    }

    private Matrix getMatrix(Bitmap bitmap) {
        float scaleWidth = (float) this.mCenterImgSize / (float) bitmap.getWidth();
        float scaleHeight = (float) this.mCenterImgSize / (float) bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return matrix;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == 1073741824) {
            this.mWidth = width;
        } else {
            this.mWidth = UIUtils.dip2px(this.mContext, 50.0F);
            if (widthMode == -2147483648) {
                this.mWidth = Math.min(width, this.mWidth);
            }
        }

        if (heightMode == 1073741824) {
            this.mHeight = height;
        } else {
            this.mHeight = UIUtils.dip2px(this.mContext, (float) height);
            if (heightMode == -2147483648) {
                this.mHeight = Math.min(height, this.mHeight);
            }
        }

        this.setMeasuredDimension(this.mWidth, this.mHeight);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    protected void onDraw(Canvas canvas) {
        this.drawBg(canvas);
        this.drawView(canvas);
    }

    private void drawBg(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(this.mBackgroundColor);
        }

    }

    private Rect[] getCenterRect(int width, int height) {
        Rect[] rects = new Rect[2];
        Rect src = new Rect(0, 0, width, height);
        int left = this.mWidth / 2 - width / 2;
        int top = this.mHeight / 2 - height / 2;
        Rect dst = new Rect(left, top, left + width, top + height);
        rects[0] = src;
        rects[1] = dst;
        return rects;
    }

    private void drawView(Canvas canvas) {
        if (this.mState == State.NORMAL) {
            this.drawImg(canvas, this.mNormalImg);
        } else if (this.mState == State.RUNNING) {
            this.drawImg(canvas, this.mRunningImg);
            this.drawCircle(canvas, this.mRunningPaint);
        } else if (this.mState == State.SUCCESS) {
            this.drawImg(canvas, this.mSuccessImg);
            this.drawCircle(canvas, this.mSuccessPaint);
        } else if (this.mState == State.ERR) {
            this.drawImg(canvas, this.mErrImg);
            this.drawCircle(canvas, this.mErrorPaint);
        } else if (this.mState == State.WAIT) {
            this.drawImg(canvas, this.mWaitImg);
            this.drawCircle(canvas, this.mWaitPaint);
        }

    }

    private void drawImg(Canvas canvas, Bitmap bitmap) {
        if (canvas != null) {
            Rect[] rects = this.getCenterRect(bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, rects[0], rects[1], (Paint) null);
        }

    }

    private void drawCircle(Canvas canvas, Paint paint) {
        int w = this.mCircleWidth;
        if (canvas != null) {
            canvas.drawArc(new RectF((float) w, (float) w, (float) (this.mWidth - this.mCircleWidth), (float) (this.mHeight - this.mCircleWidth)), -90.0F, this.mProgress, false, paint);
        }

    }

    public void setProgress(float progress) {
        this.mState = State.RUNNING;
        this.mProgress = progress * 3.6F;
        if (this.mProgress >= 360.0F) {
            this.mProgress = 360.0F;
            this.mState = State.SUCCESS;
        }

        this.invalidate();
    }

    public void setProgressState(State state) {
        this.mState = state;
        if (this.mState == State.SUCCESS) {
            this.mProgress = 360.0F;
        }

        this.invalidate();
    }

    public State getProgressState() {
        return this.mState;
    }

    public void setSuccessImg(Bitmap mSuccessImg) {
        this.mSuccessImg = mSuccessImg;
        this.invalidate();
    }

    public void setNormalImg(Bitmap mNormalImg) {
        this.mNormalImg = mNormalImg;
        this.invalidate();
    }

    public void setErrImg(Bitmap mErrImg) {
        this.mErrImg = mErrImg;
        this.invalidate();
    }

    public void setWaitImg(Bitmap mWaitImg) {
        this.mWaitImg = mWaitImg;
        this.invalidate();
    }

    public void setCenterImgSize(int mCenterImgSize) {
        this.mCenterImgSize = mCenterImgSize;
        this.invalidate();
    }

    public void setBackground(@ColorInt int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
        this.invalidate();
    }

    public void setStartColor(@ColorInt int mStartColor) {
        this.mStartColor = mStartColor;
        this.mRunningPaint.setColor(mStartColor);
        this.invalidate();
    }

    public void setErrColor(@ColorInt int mErrColor) {
        this.mErrColor = mErrColor;
        this.mErrorPaint.setColor(mErrColor);
        this.invalidate();
    }

    public void setWaitColor(@ColorInt int mWaitColor) {
        this.mWaitColor = mWaitColor;
        this.mWaitPaint.setColor(mWaitColor);
        this.invalidate();
    }

    public void setSucColor(@ColorInt int mSucColor) {
        this.mSucColor = mSucColor;
        this.mSuccessPaint.setColor(mSucColor);
        this.invalidate();
    }

    public void setNormalColor(@ColorInt int mNormalColor) {
        this.mNormalColor = mNormalColor;
        this.mNormalPaint.setColor(mNormalColor);
        this.invalidate();
    }

    public void setmCircleWidth(int mCircleWidth) {
        this.mCircleWidth = mCircleWidth;
        this.invalidate();
    }
}
