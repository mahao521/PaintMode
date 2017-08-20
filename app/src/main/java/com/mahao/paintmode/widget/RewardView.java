package com.mahao.paintmode.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.icu.util.Measure;
import android.os.Message;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import com.mahao.paintmode.R;

/**
 * Created by mahao on 2017/8/12.
 */

public class RewardView extends View {


    private Paint paintLine;
    private Paint paintBg;

    private int cirWidth;
    private int cirHeight;
    private int cirCount;

    private int radius,radSpace;
    private int cirStartColor;
    private int endColro;
    private SweepGradient sweepGradient;
    private Matrix matrix;


    private static final int MSG_START = 1;

    private static final int MSG_TIME = 10;

    private int degree = 0;


    //点击的中心点
    private int centerX,centerY;

    private ObjectAnimator animation;

    private int currentRadius = 0;





    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            switch (msg.what){

                case MSG_START :

                    degree+= 1;
                    matrix.reset();
                    matrix.postRotate(degree,cirHeight/2,cirHeight/2);
                    invalidate();
                    handler.sendEmptyMessageDelayed(msg.what,MSG_TIME);
                    break;
            }

        }
    };
    private Paint paintDown;


    public RewardView(Context context) {
        this(context,null);
    }

    public RewardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RewardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData(context,attrs);

        Log.i("mahao","1");
    }


    private void initData(Context context,AttributeSet attrs) {


        //禁用软件加速
        setLayerType(LAYER_TYPE_SOFTWARE,null);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RewardView);
        cirCount = typedArray.getInteger(R.styleable.RewardView_cirCount,3);
        int colorLine = typedArray.getInt(R.styleable.RewardView_cirLine,Color.WHITE);
        int colorBg =  typedArray.getInt(R.styleable.RewardView_cirBg, Color.BLUE);
        cirStartColor = typedArray.getInt(R.styleable.RewardView_cirStartColor, Color.RED);
        endColro = typedArray.getInt(R.styleable.RewardView_cirEndCorlor, Color.GREEN);


        //画圆背景
        paintBg = new Paint();
        paintBg.setStyle(Paint.Style.FILL);
        paintBg.setAntiAlias(true); //设置抗锯齿
        paintBg.setDither(true);//设置防抖动，颜色之间区分不在明显
        paintBg.setColor(colorBg);


        //画圆环画笔
        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(3);
        paintLine.setDither(true);
        paintLine.setAntiAlias(true);
        paintLine.setColor(colorLine);


        //点击的画笔
        paintDown = new Paint();
        paintDown.setColor(endColro);
        paintDown.setAntiAlias(true);
        paintDown.setDither(true);

        matrix = new Matrix();

        typedArray.recycle();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

       int widSize =  MeasureSpec.getSize(widthMeasureSpec);
       int widMode = MeasureSpec.getMode(widthMeasureSpec);

       int heiSize = MeasureSpec.getSize(heightMeasureSpec);
       int heiMode = MeasureSpec.getMode(heightMeasureSpec);

       if(widMode == MeasureSpec.EXACTLY &&  heiMode == MeasureSpec.EXACTLY ){ //如果宽度为精确值，设置宽高为其中的最小值

           int min = Math.min(widSize,heiSize);
           cirWidth = cirHeight = min;

       }else{  //设置父窗体的大小

       View view = (View) getParent();
       int width = view.getWidth();
       int height = view.getHeight();
       Log.i("mahao","parent......" + width +"....." + height);

       cirWidth = cirHeight = Math.min(cirWidth,cirHeight);
       }
       setMeasuredDimension(cirWidth,cirHeight);

        Log.i("mahao","2");
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.i("mahao","onfinish");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.i("mahao","onsizeChange");

        radius = cirWidth /2;
        radSpace = radius/cirCount;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        paintBg.setShader(null);
        canvas.translate(0,0);
        Log.i("mahao","ondraw......");

        //绘制背景
        canvas.drawCircle(cirWidth/2,cirHeight/2,radius,paintBg);

       // canvas.setMatrix();

       // sweepGradient.setLocalMatrix(matrix);
        //画渐变从透明到不透明
        sweepGradient = new SweepGradient(cirWidth/2,cirHeight/2,cirStartColor,endColro);
        RadialGradient radialGradient = new RadialGradient(cirWidth/2,cirWidth/2,cirWidth,
        new int[]{cirStartColor,Color.BLUE,endColro},new float[]{0.1f,0.3f,0.6f}, Shader.TileMode.CLAMP);
        ComposeShader shader = new ComposeShader(sweepGradient,radialGradient, PorterDuff.Mode.MULTIPLY);

        if(radSpace*(cirCount-1) - degree > 0){

            paintBg.setShader(shader);
        }else{
            paintBg.setShader(sweepGradient);
        }

        //通过matrix旋转画布
        canvas.concat(matrix);
        Log.i("mahao",cirWidth +"..." + cirHeight);
        canvas.drawCircle(cirWidth/2,cirHeight/2,radius,paintBg);


        canvas.restore();
        //绘制坐标轴
        canvas.drawLine(0,cirHeight/2,cirWidth,cirHeight/2,paintLine);
        canvas.drawLine(cirWidth/2,0,cirWidth/2,cirHeight,paintLine);

        //画圆
        for(int i = 0; i < cirCount ; i++){

            canvas.drawOval(new RectF(0+radSpace*i,0+radSpace*i,cirWidth-radSpace*i,cirWidth-radSpace*i),paintLine);

        }

        if(radSpace*(cirCount-1) - degree > 0 ) {

            canvas.drawOval(new RectF(0+radSpace*(cirCount-1) - degree,0+radSpace*(cirCount-1) -degree,
                    cirWidth-radSpace*(cirCount-1) + degree,cirWidth-radSpace*(cirCount-1) + degree)
                    ,paintLine);

        }

        canvas.drawCircle(centerX,centerY,currentRadius,paintDown);

    }


    /**
     *   开始扫描
     */
    public void startScan(){


        handler.removeMessages(MSG_START);
        handler.sendEmptyMessage(MSG_START);

    }

    /**
     *   停止扫描
     */
    public void stopScan(){

        handler.removeMessages(MSG_START);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(centerX != event.getY() || centerY != event.getY()){


            centerX = (int) event.getX();
            centerY = (int) event.getY();

            setMahao(20);

        }

        switch (event.getAction()){


            case MotionEvent.ACTION_DOWN:

                 return true;

            case MotionEvent.ACTION_UP:

                if(animation != null && animation.isRunning()){

                    animation.cancel();
                }

                if(animation == null){

                    animation = ObjectAnimator.ofInt(this,"mahao",10,maxLength(centerX,centerY)/4);
                }
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.setDuration(1500);
                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        setMahao(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                if(isCircle(centerX,centerY)){

                    animation.start();
                }else {

                    setMahao(0);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     *   设置开始的半径和结束的半径
     * @param radius
     */
    public void setMahao(int radius){

        currentRadius = radius;
        if(radius > 0){

            RadialGradient radialGradient = new RadialGradient(centerX,centerY,currentRadius,0x00FFFF,0xFF58FAAC,Shader.TileMode.CLAMP);
            paintDown.setShader(radialGradient);
        }

        postInvalidate();

    }


    /**
     *    计算该点到圆心的距离
     * @param x
     * @param y
     * @return
     */
    public boolean isCircle(int x,int y){

        int xxyy = (x-radius)*(x-radius) + (y-radius)*(y-radius);
        int  subRa = (int) Math.sqrt(xxyy);

        return subRa > radius ? false : true;

    }


    /**
     *    计算最大圆弧半径
     * @param x
     * @param y
     * @return
     */
    public int maxLength(int x,int y){

        int xxyy = (x-radius)*(x-radius) + (y-radius)*(y-radius);
        int  subRa = (int) Math.sqrt(xxyy);
        Log.i("mahao1234","subRa" + subRa);
        int maxLength = subRa + radius;

        return maxLength;
    }


}











