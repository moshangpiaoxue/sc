package com.mo.lib.base.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hjq.toast.ToastUtils;
import com.mo.lib.action.HandlerAction;
import com.mo.lib.action.ToastAction;
import com.mo.lib.modle.view_holder.ViewHolder;

/**
 * @ author：mo
 * @ data：2020/8/11:17:25
 * @ 功能：
 */
public abstract class BaseFragment extends Fragment implements ToastAction, HandlerAction {
    protected ViewHolder mViewHolder;
    protected Activity mActivity;
    private boolean mIsLoadedData = false;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewHolder = new ViewHolder(inflater, container, getLayoutId());
        return mViewHolder.getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(mViewHolder, savedInstanceState, getArguments());
    }

    /**
     * 初始化 View 控件
     */
    protected abstract void initView(ViewHolder mViewHolder, Bundle savedInstanceState, Bundle arguments);

    /**
     * 获取根布局
     */
    protected abstract int getLayoutId();

    /**
     * 可见状态发生变化
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            if (isVisibleToUser) {
                // 对用户可见
                if (!mIsLoadedData) {
                    mIsLoadedData = true;
                    onLazyLoad();
                }
                onVisible();
            } else {
                // 对用户不可见
                onInvisible();
            }
            isVisibles(isVisibleToUser, mIsLoadedData);
        }
    }

    /**
     * fragment是否被隐藏
     * 当使用show/hide方法时，会触发此回调
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisibles(!hidden, mIsLoadedData);
    }

    /**
     * 显示状态
     *
     * @ param isVisibleToUser 是否可见
     * @ param isLazy 是否懒加载
     */
    protected abstract void isVisibles(boolean isVisibleToUser, boolean isLazy);

    /**
     * 对用户不可见
     */
    protected void onInvisible() {
    }

    /**
     * 对用户可见
     */
    protected void onVisible() {
    }

    /**
     * 懒加载（在第一次可见的时候加载一次数据）
     */
    protected void onLazyLoad() {
    }
    protected void showToast(String toast) {
        ToastUtils.show(toast);
    }

}
