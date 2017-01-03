package com.zxzq.housekeeper.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.zxzq.housekeeper.R;
import com.zxzq.housekeeper.activity.HomeActivity;

/**
 * 通知栏的工具类
 * Created by Administrator on 2016/11/2.
 */

public class NotificationUtil {
    //发送通知时，用来标识是本应用程序发送的通知，id值可以自定义
    private static final int NOTIFY_ID = 333;
    //通知栏的管理类，可以通过此对象发送、取消通知
    private static NotificationManager notificationManager;
    //设置通知的详细信息，获取一个通知
    private static Notification.Builder builder;

    /**
     * 获取此 应用程序 对是否允许发送通知的配置
     * @param context
     * @return 若应用程序发送通知，则返回true;若不发送通知，则返回false ; 若无相关配置，默认发送通知
     */

    public static boolean isOpenNotification(Context context){
        SharedPreferences spf = context.getSharedPreferences("notify_config", Context.MODE_PRIVATE);
        boolean isOpenNotification = spf.getBoolean("isOpenNotification", true);
        return isOpenNotification;
    }

    /**
     * 修改 应用程序 发送通知的状态
     * @param context
     * @param isOpen
     */
    public static void setOpenNotification(Context context,boolean isOpen){
        SharedPreferences spf = context.getSharedPreferences("notify_config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
//        随着用户的自助选择开启关闭，会不断地对isOpenNotification的值进行覆盖，
        editor.putBoolean("isOpenNotification",isOpen);
        editor.commit();
    }

    /**
     * 发送通知
     * @param context
     */
    public static void showAppIconNotification(Context context){

        if (notificationManager == null){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (builder == null){
            builder = new Notification.Builder(context);
        }
        Intent intent = new Intent(context, HomeActivity.class);
        builder.setContentTitle("title")
                .setContentText("text")
                .setTicker("ticker")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT));
//        ?????
        Notification notification = builder.getNotification();
        notificationManager.notify(NOTIFY_ID,notification);

    }

    /**
     * 取消通知
     * @param context
     */
    public static void cancelAppIconNotifiction(Context context){
        if (notificationManager == null){
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(NOTIFY_ID);
        }

    }

}
