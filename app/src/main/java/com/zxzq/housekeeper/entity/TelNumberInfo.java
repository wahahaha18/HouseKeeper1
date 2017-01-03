package com.zxzq.housekeeper.entity;

/**
 * Created by Administrator on 2016/11/22.
 */

public class TelNumberInfo {

    private String name;
    private String number;

    public TelNumberInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
