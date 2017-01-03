package com.zxzq.housekeeper.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * 设备工具类
 * Created by Administrator on 2016/11/7.
 */

public class DeviceUtill {
//    dp转px
    public  static int dp2px(Context context,int dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(scale*dp+0.5f);
    }

//    px 转 dp
    public static int px2dp(Context context,int px){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(px/scale+0.5f);
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidthPX(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeightPX(Context context){
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }
}
