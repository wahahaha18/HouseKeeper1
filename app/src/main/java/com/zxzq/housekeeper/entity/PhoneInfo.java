package com.zxzq.housekeeper.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/11/15.
 */

public class PhoneInfo {
    private Drawable icon;
    private String title;
    private String text;

    public PhoneInfo(Drawable icon, String title, String text) {
        this.icon = icon;
        this.title = title;
        this.text = text;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
