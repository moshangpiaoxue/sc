package com.mo.lib.base.adapter;

import android.content.Context;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * RecyclerView适配器基类
 * author   ：mo
 * data     ：2016/12/6
 * time     ：18:11
 * function :分隔符悬停效果RecycleView适配器
 */

public abstract class HoverRecycleViewAdapter<T extends MultiItemEntity> extends BaseRvMultiAdapter<T> {
    public HoverRecycleViewAdapter(@Nullable List<T> data) {
        super(data);
    }

    /**
     * 从哪里开始悬停，默认从第0个位置开始，之后的通过抽象方法里相邻的两个实体类里的数据自行判断，true显示，false 不显示
     */
    public boolean isCategoryFistItem(int position) {
        // 第一条数据是该分类下的第一个条目
        if (position == 0) {
            return true;
        }
        return isCategoryFistItem(position, getData().get(position), getData().get(position - 1));
    }

    protected abstract boolean isCategoryFistItem(int position, T noeBean, T beforeBean);

    /**
     * 获取悬停的位置
     */
    public int getPositionForCategory(int category) {
        for (int i = 0; i < getItemCount(); i++) {
            return getPositionForCategory(category,i,getData().get(i));
        }
        return -1;
    }

    protected abstract int getPositionForCategory(int category,int position ,T bean);
}