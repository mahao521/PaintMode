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
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.mahao.paintmode.R;

/**
 * Created by mahao on 2017/8/12.
 */

public class RondImageView extends View{


    private Bitmap bitmapSrc;
    private Bitmap bitmapDes;
    private Paint paint;

    public RondImageView(Context context) {
        this(context,null);
    }

    public RondImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RondImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {


        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        paint = new Paint();
        bitmapDes = BitmapFactory.decodeResource(getResources(), R.mipmap.daxue);
        bitmapSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.round);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //添加图层
        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(bitmapDes,70,70,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawBitmap(bitmapSrc,0,0,paint);

        paint.setXfermode(null);

        canvas.restoreToCount(layerId);

    }



}













