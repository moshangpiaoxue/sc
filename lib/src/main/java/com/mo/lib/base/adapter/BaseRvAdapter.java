package com.mo.lib.base.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @ author：mo
 * @ data：2020/11/11:11:03
 * @ 功能：单布局rv适配器
 */
public abstract class BaseRvAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public BaseRvAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, T t) {
        doWhat(baseViewHolder, t);
    }

    protected abstract void doWhat(BaseViewHolder holder, T bean);

    /**
     * 次方法是为了在new出后可以直接添加点击监听，不必对适配器变量设置
     */
    public BaseRvAdapter<T> setOnItemClickListener2(OnItemClickListener listener2) {
        this.setOnItemClickListener(listener2);
        return this;
    }
}
