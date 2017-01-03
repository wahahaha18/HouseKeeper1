package com.zxzq.housekeeper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.util.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 饼状图
 * Created by Administrator on 2016/11/3.
 */

public class PiechatView extends View {
    //画笔
    private Paint paint;

    //三种颜色
    private int phoneSpaceColor;
    private int sdSpaceColor;
    private int backgroundColor;
    //圆所在的矩形
    private RectF oval;
    //绘制过程中，手机所占存储空间 在 饼状图 中的角度  计算:360*proportionPhone
    private float phoneSpaceAngle;
    //绘制过程中，sd卡所占存储空间 在 饼状图 中的角度  计算:360*proportionSD
    private float sdCardSpaceAngle;
    //手机所占存储空间 在 饼状图 所占的比例
    private float phoneSpaceProportion;
    //sd卡所占存储空间 在 饼状图 所占的比例
    private float sdCardSpaceProportion;

    //View框架的机制:measure机制-->layout机制--->draw机制
    //测量每个View的大小(宽度和高度)-->将View放在layout处-->绘制View

    public PiechatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        phoneSpaceColor = getResources().getColor(R.color.piechat_phone_space);
        sdSpaceColor = getResources().getColor(R.color.piechart_sdcard_space);
        backgroundColor = getResources().getColor(R.color.piechat_background_color);
//        initColor();
    }

    /*private void initColor(){

        phoneSpaceColor = getResources().getColor(R.color.piechat_phone_space);
        sdSpaceColor = getResources().getColor(R.color.piechart_sdcard_space);
        backgroundColor = getResources().getColor(R.color.piechat_background_color);
    }*/



    //参数:父布局对子控件的限制信息，即父布局的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //MeasureSpec包含2部分，模式和大小
        //父布局通过getSize方法来计算子控件要显示的大小
        //子控件显示大小时，要考虑父布局对自己的影响  ==>在计算子控件的大小时，要考虑父布局对它的限制信息
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //实例化矩形
        oval = new RectF(0,0,width,height);
        //测量之后，一定要调用该方法，保存测量之后的宽度和高度
        setMeasuredDimension(width,height);
    }

    /**
     *
     * @param f1   手机所占存储空间 应在 饼状图中 所占的比例
     * @param f2   sd卡所占存储空间 应在 饼状图中 所占的比例
     */
    public void setPhonePiechatAnim(float f1,float f2){
        phoneSpaceProportion = f1;
        sdCardSpaceProportion = f2;

        //饼状图绘制结束，手机内置空间在饼状图中所占的角度
        final float phoneSpaceAngle1 = 360*phoneSpaceProportion;
        //饼状图绘制结束，sd卡内置空间在饼状图中所占的角度
        final float sdCardSpaceAngle1 = 360*sdCardSpaceProportion;
        //定时器
        final Timer timer = new Timer();
        //有自己独立的线程
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LogUtil.d("PiechatView","phoneSpaceAngle1:"+phoneSpaceAngle1);
                LogUtil.d("PiechatView","sdCardSpaceAngle1:"+sdCardSpaceAngle1);


                //手机和SD卡内置空间 在饼状图中所占的角度    实时的角度
                phoneSpaceAngle +=4;
                sdCardSpaceAngle +=4;
                LogUtil.d("PiechatView","phoneSpaceAngle:"+phoneSpaceAngle);
                LogUtil.d("PiechatView","sdCardSpaceAngle1:"+sdCardSpaceAngle);
                //立即更新界面
                postInvalidate();
                //当手机内置空间 达到 应占角度时， 角度增加停止   即绘制结束
                if (phoneSpaceAngle >=phoneSpaceAngle1){
                    phoneSpaceAngle = phoneSpaceAngle1;
                }
                if (sdCardSpaceAngle >= sdCardSpaceAngle1){
                    sdCardSpaceAngle = sdCardSpaceAngle1;
                }
                //当手机和sd卡内置空间均达到应占值时，定时器取消
                if (phoneSpaceAngle == phoneSpaceAngle1 && sdCardSpaceAngle == sdCardSpaceAngle1){
                    timer.cancel();
                }

            }
        };
        //第一个参数:TimerTask
        //第二个参数:调用schedule方法后，与执行run()方法前，时间差值   (第一次执行run()方法是在调用schedule方法之后的多久时间)
        //第三个参数:从第二次执行run()方法开始，每次间隔多久执行下一次run()方法
        LogUtil.d("TAG","运行了圆饼动画效果");
        timer.schedule(timerTask,0,100);
    }

    /**
     *
     * @param canvas  画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制饼状图
        paint.setColor(backgroundColor);
        canvas.drawArc(oval,-90,360,true,paint);
        //绘制手机内置空间
        paint.setColor(phoneSpaceColor);
        canvas.drawArc(oval,-90,phoneSpaceAngle,true,paint);
        //绘制SD卡内置空间
        paint.setColor(sdSpaceColor);
        canvas.drawArc(oval,-90,sdCardSpaceAngle,true,paint);
    }

}
