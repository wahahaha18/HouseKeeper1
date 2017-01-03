package com.zxzq.housekeeper.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/11/21.
 */

public class ClearInfo {
    private int _id;
    private String softChinesename;
    private String softEnglishname;
    private String apkname;
    private String filepath;
    private Drawable apkIcon;
    private boolean isClear;
    private long size;

    public ClearInfo(int _id, String softChinesename, String softEnglishname, String apkname, String filepath) {
        this._id = _id;
        this.softChinesename = softChinesename;
        this.softEnglishname = softEnglishname;
        this.apkname = apkname;
        this.filepath = filepath;
    }
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSoftChinesename() {
        return softChinesename;
    }

    public void setSoftChinesename(String softChinesename) {
        this.softChinesename = softChinesename;
    }

    public String getSoftEnglishname() {
        return softEnglishname;
    }

    public void setSoftEnglishname(String softEnglishname) {
        this.softEnglishname = softEnglishname;
    }

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Drawable getApkIcon() {
        return apkIcon;
    }

    public void setApkIcon(Drawable apkIcon) {
        this.apkIcon = apkIcon;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }




}
