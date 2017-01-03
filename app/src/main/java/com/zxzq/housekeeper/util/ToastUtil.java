package com.zxzq.housekeeper.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast  管理工具类, 有且只有一个 Toast
 * Created by Administrator on 2016/10/26.
 */

public class ToastUtil {
    private static Toast toast;
    public static void show(Context context,String text,int duration){
        if (toast == null){
            toast = Toast.makeText(context,text,duration);
        }
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }
}
