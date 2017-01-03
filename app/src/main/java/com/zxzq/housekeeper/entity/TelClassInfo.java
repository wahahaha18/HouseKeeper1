package com.zxzq.housekeeper.entity;

/**
 * Created by Administrator on 2016/11/22.
 */

public class TelClassInfo {

    private String name;
    private int idx;

    public TelClassInfo(String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
