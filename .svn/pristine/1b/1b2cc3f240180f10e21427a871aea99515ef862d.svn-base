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
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

import lbx.xloadlib.R;
import lbx.xloadlib.state.State;
import lbx.xloadlib.thread.IconThread;
import lbx.xloadlib.utils.UIUtils;

public class DefaultDownloadSurfaceView extends SurfaceView implements Callback {
    private int mHeight;
    private int mWidth;
    private Bitmap mRunningImg;
    private Bitmap mSuccessImg;
    private Bitmap mNormalImg;
    private Bitmap mErrImg;
    private Bitmap mWaitImg;
    private int mCenterImgSize;
    private int mBackgroundColor;
    private static final int REFRESH_TIME = 20;
    private int mStartColor;
    private int mErrColor;
    private int mWaitColor;
    private int mSucColor;
    private int mNormalColor;
    private float mProgress;
    private int mCircleWidth;
    private State mState;
    private Context mContext;
    private boolean isRunning;
    private ThreadPoolExecutor mThread;
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Paint mRunningPaint;
    private Paint mSuccessPaint;
    private Paint mErrorPaint;
    private Paint mNormalPaint;
    private Paint mWaitPaint;

    public DefaultDownloadSurfaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public DefaultDownloadSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultDownloadSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mState = State.NORMAL;
        this.mContext = context;
        this.init(attrs);
        this.mThread = new ThreadPoolExecutor(1, 1, 30L, TimeUnit.SECONDS, new SynchronousQueue(), new IconThread(), new DiscardPolicy());
        this.mHolder = this.getHolder();
        this.mHolder.addCallback(this);
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
        this.mErrImg = this.scaleImg(BitmapFactory.decodeResource(this.mContext.getResources(), array.getResourceId(R.styleable.dlView_dl_errImg, R.drawable.err)));
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

    @NonNull
    private Matrix getMatrix(Bitmap bitmap) {
        float scaleWidth = (float) this.mCenterImgSize / (float) bitmap.getWidth();
        float scaleHeight = (float) this.mCenterImgSize / (float) bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return matrix;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.isRunning = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        this.mThread.execute(new DefaultDownloadSurfaceView.DrawRunnable());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.isRunning = false;
    }

    private void draw() {
        try {
            this.mCanvas = this.mHolder.lockCanvas();
            if (this.mCanvas != null) {
                this.drawBg(this.mCanvas);
                this.drawView(this.mCanvas);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        } finally {
            if (this.mCanvas != null) {
                this.mHolder.unlockCanvasAndPost(this.mCanvas);
            }

        }

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

    }

    public void setProgressState(State state) {
        this.mState = state;
        if (this.mState == State.SUCCESS) {
            this.mProgress = 360.0F;
        }

    }

    public State getProgressState() {
        return this.mState;
    }

    public void setSuccessImg(Bitmap mSuccessImg) {
        this.mSuccessImg = mSuccessImg;
    }

    public void setNormalImg(Bitmap mNormalImg) {
        this.mNormalImg = mNormalImg;
    }

    public void setErrImg(Bitmap mErrImg) {
        this.mErrImg = mErrImg;
    }

    public void setWaitImg(Bitmap mWaitImg) {
        this.mWaitImg = mWaitImg;
    }

    public void setCenterImgSize(int mCenterImgSize) {
        this.mCenterImgSize = mCenterImgSize;
    }

    public void setBackground(@ColorInt int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
    }

    public void setStartColor(@ColorInt int mStartColor) {
        this.mStartColor = mStartColor;
        this.mRunningPaint.setColor(mStartColor);
    }

    public void setErrColor(@ColorInt int mErrColor) {
        this.mErrColor = mErrColor;
        this.mErrorPaint.setColor(mErrColor);
    }

    public void setWaitColor(@ColorInt int mWaitColor) {
        this.mWaitColor = mWaitColor;
        this.mWaitPaint.setColor(mWaitColor);
    }

    public void setSucColor(@ColorInt int mSucColor) {
        this.mSucColor = mSucColor;
        this.mSuccessPaint.setColor(mSucColor);
    }

    public void setNormalColor(@ColorInt int mNormalColor) {
        this.mNormalColor = mNormalColor;
        this.mNormalPaint.setColor(mNormalColor);
    }

    public void setmCircleWidth(int mCircleWidth) {
        this.mCircleWidth = mCircleWidth;
    }

    private class DrawRunnable implements Runnable {
        private DrawRunnable() {
        }

        public void run() {
            while (DefaultDownloadSurfaceView.this.isRunning) {
                long start = System.currentTimeMillis();
                DefaultDownloadSurfaceView.this.draw();
                long end = System.currentTimeMillis();
                if (end - start < 20L) {
                    SystemClock.sleep(20L - (end - start));
                }
            }

        }
    }
}
