package com.mo.libdemo.activitys.function;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.utils.hardware_utils.ScreenUtil;
import com.mo.lib.utils.image.DrawableUtil;
import com.mo.lib.view.signature_view.SignatureView;
import com.mo.sc.libdemo.R;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：签字
 */
public class SignatureViewActivity extends BaseActivity implements TitleBarAction {
    private SignatureView sv_signature;
    private TextView tv_signature_cun;
    private TextView tv_signature_chu;


    @Override
    protected int getLayoutId() {
        return R.layout.act_ani_effect_signature;
    }


    @Override
    protected void initView() {
        setTitle("签字");
        ScreenUtil.setScreenOrientationLandscape(getActivity());
        sv_signature = findViewById(R.id.sv_signature);
        tv_signature_cun = findViewById(R.id.tv_signature_cun);
        tv_signature_chu = findViewById(R.id.tv_signature_chu);
        XXPermissions.with((Activity) getContext()).permission(Permission.CAMERA);
        sv_signature.setOnSignedListener(new SignatureView.OnSignedListener() {
            @Override
            public void onSigned() {
                tv_signature_cun.setEnabled(true);
                tv_signature_chu.setEnabled(true);
            }

            @Override
            public void onClear() {
                tv_signature_cun.setEnabled(false);
                tv_signature_chu.setEnabled(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_signature_cun) {
            showToast("保存");
            tv_signature_cun.setBackground(DrawableUtil.getDrawable(sv_signature.getSignatureBitmap()));
        } else if (i == R.id.tv_signature_chu) {
            showToast("清除");
            sv_signature.clear();
        }
    }

    @Override
    protected void initData() {

    }

}
