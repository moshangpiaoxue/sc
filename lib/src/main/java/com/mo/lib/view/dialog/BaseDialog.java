package com.mo.lib.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mo.lib.R;
import com.mo.lib.utils.viewUtil.ViewUtil;


/**
 * @ author：mo
 * @ data：2018/9/25：8:58
 * @ 功能：基础dialog
 */
public abstract class BaseDialog {
    protected Activity mActivity;
    protected Dialog dialog;

    /**
     * Dialog消失类型
     */
    public enum DismissType {
        //从不取消
        NEVER,
        //系统返回键取消
        BACK,
        //点返回键、空白处都取消
        OTHER,
    }

    public BaseDialog(Activity mActivity) {
        this.mActivity = mActivity;
        if (mActivity == null) {
            throw new UnsupportedOperationException("参数空");
        }
        // 获取Dialog布局
        View view = ViewUtil.getView(mActivity, getLayoutId());
        // 定义Dialog布局和参数
        dialog = new Dialog(mActivity, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        doWhat(dialog, view);
        setDialogScale(0.8);
        setDimAmount(0.4);
        setDismissType(DismissType.NEVER);
    }

    protected abstract int getLayoutId();

    /**
     * 设置dialog宽与屏幕宽之间的比例  0最小 1最大
     */
    public BaseDialog setDialogScale(double scale) {
        WindowManager.LayoutParams lp = getLayoutParams();
        WindowManager windowManager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        lp.width = (int) (display.getWidth() * scale);
        dialog.getWindow().setAttributes(lp);
        return this;
    }

    /**
     * 设置黑暗度（Dialog自身的黑暗度.也就是透明度）
     */
    public BaseDialog setDimAmount(double dimAmount) {
        WindowManager.LayoutParams lp = getLayoutParams();
        lp.dimAmount = (float) dimAmount;
        dialog.getWindow().setAttributes(lp);
        return this;
    }

    public BaseDialog setDialogListener(DialogCallBack dialogCallBack) {
        if (dialogCallBack != null) {
            //            dialog取消监听
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialogCallBack.onCancel(dialogInterface);
                }
            });
            //            dialog显示监听
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    dialogCallBack.onShow(dialogInterface);
                }
            });
            //            dialog消失监听
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    dialogCallBack.onDismiss(dialogInterface);
                }
            });
            //            监听系统返回键
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    return dialogCallBack.onKey(dialogInterface, i, keyEvent);
                }
            });
        }
        return this;
    }

    protected WindowManager.LayoutParams getLayoutParams() {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        return lp;
    }

    /**
     * 设置消失类型
     */
    public BaseDialog setDismissType(DismissType dismissType) {
        //        false=dialog弹出后会点击屏幕或物理返回键，dialog不消失
        dialog.setCancelable(dismissType == DismissType.OTHER || dismissType == DismissType.BACK);
        //        false=dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
        dialog.setCanceledOnTouchOutside( dismissType == DismissType.OTHER);
        return this;
    }


    protected abstract void doWhat(Dialog dialog, View view);

    /**
     * 开启
     */
    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 关闭
     */
    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }
    }
}
