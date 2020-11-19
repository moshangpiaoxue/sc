package com.mo.lib.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.hjq.toast.ToastUtils;
import com.mo.lib.R;
import com.mo.lib.utils.hardware_utils.CameraUtil;
import com.mo.lib.utils.systemUtils.storageUtil.SDCardUtil;


/**
 * @ author：mo
 * @ data：2018/11/22
 * @ 功能：拍照或者从相册取图片
 */
public abstract class PhoneTakeDialog extends BaseDialog {
    public PhoneTakeDialog(Activity mActivity) {
        super(mActivity);
        setDismissType(DismissType.OTHER);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_phone_take;
    }

    @Override
    protected void doWhat(final Dialog dialog, View view) {
        //拍照
        view.findViewById(R.id.btn_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CameraUtil.isExistCamera()) {
                    dialog.dismiss();
                    actionPhoneTake();
                } else {
                    ToastUtils.show("没有找到拍照设备！");
                }
            }
        });
        //从相册选
        view.findViewById(R.id.btn_pick_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SDCardUtil.isEnable()) {
                    dialog.dismiss();
                    actionPhoneChoose();
                } else {
                    ToastUtils.show("SD卡不可用");
                }
            }
        });
        //取消
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    protected abstract void actionPhoneChoose();

    protected abstract void actionPhoneTake();

}
