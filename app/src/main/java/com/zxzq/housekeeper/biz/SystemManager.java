package com.zxzq.housekeeper.biz;

import android.os.Build;

/**
 * Created by Administrator on 2016/11/10.
 */

public class SystemManager {
//    获得手机名称
    public static String getPhoneName(){
        return Build.BRAND;
    }
//    获得手机系统版本
    public static String getSystemVersionlName(){
        return Build.VERSION.RELEASE;
    }
//    获得手机型号及系统版本
    public static String getModelVersionName(){
        return Build.MODEL+"Android:"+Build.VERSION.RELEASE;
    }
}
