package com.zxzq.housekeeper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zxzq.housekeeper.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/14.
 */

public class ClearArcView extends View {
//    画笔
    private Paint paint;
//    圆所在的矩形
    private RectF rectF;
//    圆的背景颜色
    private int arcColor = 0xFFFF8C00;
//    扫描时变化的角度
    private int sweepAngle = 0;
//    最初的角度
    private final int START_Angle = -90;
//    每次递减的角度
    private int[] back = {-6,-4,-2,-8,-5};
//    递减的角标
    private int backIndex;
//    每次递增的角度
    private int[] goon = {3,5,7,8,5};
//    递增的角标
    private int goonIndex;
//    回退 或 前进的标志
    private int state = 0;
// 是否正在清理
    private boolean isRunning;

    public ClearArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
//        初始扫描角度
        setAngle(360);
        arcColor = context.getResources().getColor(R.color.home_clear_arc);
    }
//   设置初始角度
    public void setAngle(int angle){
        sweepAngle = angle;
        postInvalidate();
        isRunning = false;
    }
//  设置扫描动画的角度
    public void setAngleWithAnim(final int angle){
        if (isRunning){
            return;
        }
        isRunning = true;
        state = 0;
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                switch (state){
                    case 0://回退
                        sweepAngle += back[backIndex++];
                        if (backIndex >= back.length){
                            backIndex = back.length - 1;
                        }
                        postInvalidate();
                        if (sweepAngle <= 0){
                            sweepAngle = 0;
                            backIndex = 0;
                            state = 1;
                        }
                        break;
                    case 1://前进
                        sweepAngle += goon[goonIndex++];
                        if (goonIndex >= goon.length){
                            goonIndex = goon.length - 1;
                        }
                        postInvalidate();
                        if (sweepAngle >= angle){
                            sweepAngle = angle;
                            timer.cancel();
                            isRunning = false;
                            goonIndex = 0;
                        }
                }
            }
        };
        //在延时delay毫秒后重复的执行task，周期是period毫秒。
        timer.schedule(timerTask,24,24);
    }

    /**
     * 测量方法
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        rectF = new RectF(0,0,viewWidth,viewWidth);
        setMeasuredDimension(viewWidth,viewHeight);
    }

    /**
     *
     * 真正画图的方法*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(arcColor);
        paint.setAntiAlias(true);
        canvas.drawArc(rectF,START_Angle,sweepAngle,true,paint);
    }
}
