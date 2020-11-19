package com.mo.lib.base;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.billy.android.swipe.SmartSwipeBack;
import com.hjq.toast.ToastInterceptor;
import com.hjq.toast.ToastUtils;
import com.mo.lib.action.SwipeAction;
import com.mo.lib.k;

import org.litepal.LitePal;

/**
 * @ author：mo
 * @ data：2020/7/21:13:32
 * @ 功能：
 */
public class BaseApplication extends Application {
    private boolean isDebug = true;
    @Override
    public void onCreate() {
        super.onCreate();

        if(isDebug){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        // 初始化kutils
        k.Ext.init(this);

        //初始化数据库
        LitePal.initialize(this);

        // 吐司工具类
        ToastUtils.init(this);

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Log.e("Toast", "空 Toast");
                } else {
                    Log.i("Toast", text.toString());
                }
                return intercept;
            }
        });

        // Activity 侧滑返回
        SmartSwipeBack.activitySlidingBack(this, activity -> {
            if (activity instanceof SwipeAction) {
                return ((SwipeAction) activity).isSwipeEnable();
            }
            return true;
        });
    }
}
