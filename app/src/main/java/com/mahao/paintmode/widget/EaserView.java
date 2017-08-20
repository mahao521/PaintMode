package com.mahao.paintmode.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.DrmInitData;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mahao.paintmode.R;

/**
 * Created by mahao on 2017/8/20.
 * 橡皮擦原理： src_out 目标图片为不透明时，原图不显示；
 */

public class EaserView extends View {

    private Bitmap bitmapRes;
    private Bitmap bitmDes;
    private Paint paint;
    private Path path;
    private float mpreX,mpreY;
    private Bitmap bitmapBg;

    public EaserView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EaserView(Context context) {
        this(context,null);
    }

    public EaserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }


    private void initData(Context context) {


        setLayerType(LAYER_TYPE_SOFTWARE,null);
        bitmapRes = BitmapFactory.decodeResource(context.getResources(), R.mipmap.rion,null);
        bitmDes = Bitmap.createBitmap(bitmapRes.getWidth(), bitmapRes.getHeight(), Bitmap.Config.ARGB_8888);
        bitmapBg = BitmapFactory.decodeResource(getResources(), R.mipmap.daxue,null);


        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(40);
        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //默认背景
        canvas.drawBitmap(bitmapBg,(getWidth()-bitmapBg.getWidth())/2,(getHeight()-bitmapBg.getHeight())/2,paint);

        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        //把轨迹绘制到bitmdes上
        Canvas canvas1 = new Canvas(bitmDes);
        canvas1.drawPath(path,paint);

        //然后把目标画像画到画布上
        canvas.drawBitmap(bitmDes,0,0,paint);

        //设置paintMode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        //绘制源图
        canvas.drawBitmap(bitmapRes,0,0,paint);

        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                path.moveTo(event.getX(),event.getY());
                mpreX =  event.getX();
                mpreY =  event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:

                float endX = (mpreX + event.getX())/2;
                float endY = (mpreY+event.getY())/2;
                path.quadTo(mpreX,mpreY,endX,endY);
                mpreX = event.getX();
                mpreY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}











