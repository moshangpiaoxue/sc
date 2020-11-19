package com.mo.lib.view.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.TextureView;
import android.view.View;

import com.mo.lib.R;
import com.mo.lib.utils.hardware_utils.CameraHelper;
import com.mo.lib.utils.tips_utils.LogUtil;

import java.io.File;

import static org.litepal.Operator.getHandler;

/**
 * @ author：mo
 * @ data：2020/9/23:16:53
 * @ 功能：拍照弹窗
 */
public class CarmeraDialog extends BaseDialog {
    private TextureView mTextureView;
    private CameraHelper mHelper;

    public CarmeraDialog(Activity mActivity, CameraHelper.OnTakePic onTakePic) {
        super(mActivity);
        if (mTextureView != null) {
            mHelper = new CameraHelper(mActivity, mTextureView, new CameraHelper.OnTakePic() {
                @Override
                public void getTakeResult(boolean isSave, File targetFile, String path, Bitmap bitmap) {
                    if (onTakePic != null) {
                        onTakePic.getTakeResult(isSave, targetFile, path, bitmap);
                    }
                    dismiss();
                }
            });
        }
        setDialogScale(1);
        setDismissType(DismissType.BACK);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_camera;
    }

    @Override
    protected void doWhat(Dialog dialog, View view) {
        mTextureView = view.findViewById(R.id.textureView);
        View iv_dialog_camera_back = view.findViewById(R.id.iv_dialog_camera_back);

        iv_dialog_camera_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        View iv_dialog_camera_take = view.findViewById(R.id.iv_dialog_camera_take);

        iv_dialog_camera_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_dialog_camera_back.setVisibility(View.VISIBLE);
                iv_dialog_camera_take.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void takePicture() {
        if (mHelper != null) {
            mHelper.takePic();
        } else {
            LogUtil.i("mHelper未创建！");
        }

    }

}
