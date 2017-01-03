package com.zxzq.housekeeper.entity;

import android.content.pm.PackageInfo;

/**
 * Created by Administrator on 2016/11/4.
 */

public class AppInfo {
//应用程序包的信息
    private PackageInfo packageInfo;
    private boolean isDel;

    public AppInfo(PackageInfo packageInfo) {
        this(packageInfo,false);
    }

    public AppInfo(PackageInfo packageInfo, boolean isDel) {
        this.packageInfo = packageInfo;
        this.isDel = isDel;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
