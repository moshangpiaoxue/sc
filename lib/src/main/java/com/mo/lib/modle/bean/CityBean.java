package com.mo.lib.modle.bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mo.lib.utils.dataUtil.stringUtils.StringUtil;

public class CityBean implements MultiItemEntity {
    private String topc;
    private String name;

    public CityBean(String name) {
        this.name = name;
        if (name.contains("热门")) {
            topc = name;
        } else if (name.contains("定位")) {
            topc = name;
        } else if (name.equals("重庆")) {
            topc = "C";
        } else {
            topc = StringUtil.getFirstPy(name).toUpperCase();
        }
    }

    public String getTopc() {
        return topc;
    }

    public void setTopc(String topc) {
        this.topc = topc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getItemType() {
        if (getName().contains("定位")) {
            return 0;
        } else if (getName().contains("热门")) {
            return 1;
        } else {
            return 2;
        }
    }
}