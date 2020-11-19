package com.mo.libdemo.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @ author：mo
 * @ data：2020/11/12:14:40
 * @ 功能：
 */
public class ItemBean implements MultiItemEntity {
    public static final int COLOR = 1; //颜色说明
    public static final int PINK_PIGGY = 2; //粉色佩奇
    public static final int BLUE_PIGGY = 3; //蓝色佩奇

    private String string;
    private int possion;

    public ItemBean(String string, int possion) {
        this.string = string;
        this.possion = possion;
    }

    public int getPossion() {
        return possion;
    }

    public void setPossion(int possion) {
        this.possion = possion;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public int getItemType() {
        return (getPossion() & 1) == 1 ? 1 : 0;
//        return COLOR;
    }
}
