package com.zxzq.housekeeper.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/10/24.
 * 日志管理工具类（耦合度高，有改进空间）
 */

public class LogUtil {
    public static boolean isOpenDeBug = true;
    public static boolean isOpenDeWarn = true;
    public static void d(String tag,String msg){
        if (isOpenDeBug){
            Log.d(tag,msg);
        }
    }
    public static void e(String tag,String msg){
        if (isOpenDeWarn){
            Log.e(tag,msg);
        }
    }
}
