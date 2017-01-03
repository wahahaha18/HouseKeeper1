package com.zxzq.housekeeper.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/11/10.
 */

public class RunningAppInfo {
    private String packageName;
    private Drawable icon;
    private long size;
    private String lableName;
    private boolean isClear;
    private boolean isSystem;

    public RunningAppInfo(String packageName, Drawable icon, long size, String lableName) {
        this.packageName = packageName;
        this.icon = icon;
        this.size = size;
        this.lableName = lableName;
        isClear = false;
        isSystem = false;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }
}
