package com.mahao.paintmode.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mahao.paintmode.R;

/**
 * Created by mahao on 2017/8/13.
 */

public class ReverseView extends View {

    private Bitmap bitmapDes,bitmapSrc,bitMapRever;
    private Paint paint;


    public ReverseView(Context context) {
        this(context,null);
    }

    public ReverseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ReverseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData(context);
    }

    private void initData(Context context) {


        paint = new Paint();
        bitmapDes = BitmapFactory.decodeResource(getResources(), R.mipmap.rion_bg);
        bitmapSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.rion);

        Matrix matrix = new Matrix();
        matrix.setScale(1,-1);

        bitMapRever = Bitmap.createBitmap(bitmapSrc,0,0,
                bitmapSrc.getWidth(),bitmapSrc.getHeight(),matrix,true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(bitmapSrc,new Rect(0,bitmapSrc.getHeight()/4,bitmapSrc.getWidth(),bitmapSrc.getHeight()/2),new Rect(0,0,bitmapSrc.getWidth(),bitmapSrc.getHeight()/4),paint);


        int id =  canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.translate(0,bitmapSrc.getHeight()/4);
        canvas.drawBitmap(bitMapRever,new Rect(0,bitMapRever.getHeight()/2,bitMapRever.getWidth(),bitMapRever.getHeight()/4*3),new Rect(0,2,bitMapRever.getWidth(),bitMapRever.getHeight()/4),paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        canvas.drawBitmap(bitmapDes,0,0,paint);

        paint.setXfermode(null);
        canvas.restoreToCount(id);

    }
}














