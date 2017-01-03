package com.zxzq.housekeeper.entity;

import java.io.File;

/**
 * Created by Administrator on 2016/11/16.
 */

public class FileInfo {
    private boolean isSelect;
    private File file;
    private String iconName;//图标图像名称
    private String fileType;//文件分类

    public FileInfo(File file, String iconName, String fileType) {
        this.file = file;
        this.iconName = iconName;
        this.fileType = fileType;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
