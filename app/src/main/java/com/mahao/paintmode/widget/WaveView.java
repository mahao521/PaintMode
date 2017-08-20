package com.mahao.paintmode.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.DrmInitData;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.mahao.paintmode.R;

/**
 * Created by mahao on 2017/8/12.
 */

public class WaveView extends View {


    private Bitmap bitmapDes;
    private Bitmap bitmapSrc;
    private int width;
    private Paint paint;

    private int dx ;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData(context);
    }


    private void initData(Context context) {

        bitmapDes = BitmapFactory.decodeResource(context.getResources(), R.mipmap.wave_bg);
        bitmapSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.circle_shape);

        width = bitmapDes.getWidth();

        paint = new Paint();

        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmapSrc,0,0,paint);


        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bitmapDes,new Rect(dx ,0,dx + bitmapSrc.getWidth(),bitmapSrc.getHeight())
                ,new Rect(0,0,bitmapSrc.getWidth(),bitmapSrc.getHeight()),paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmapSrc,0,0,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }

    public void startAnim(){


        ValueAnimator animator = ValueAnimator.ofInt(0,width);
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                dx = (int ) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
