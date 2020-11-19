package com.mo.lib.base.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @ author：mo
 * @ data：2020/11/12:14:50
 * @ 功能：多布局rv适配器
 * 注意：1、item 实体类必须实现MultiItemEntity接口 定义itemtype
 * 2、在实现addItemTypes抽象方法里 addItemType（itemType，layoutId）
 */
public abstract class BaseRvMultiAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {
    public BaseRvMultiAdapter(@Nullable List<T> data) {
        super(data);
        addItemTypes();
    }

    public abstract void addItemTypes();

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, T t) {
        doWhat(baseViewHolder,t,baseViewHolder.getItemViewType());
    }

    protected abstract void doWhat(BaseViewHolder holder, T bean, int itemViewType);


    /**
     * 次方法是为了在new出后可以直接添加点击监听，不必对适配器变量设置
     */
    public   BaseRvMultiAdapter<T> setOnItemClickListener2(OnItemClickListener listener2) {
        this.setOnItemClickListener(listener2);
        return this;
    }
}
