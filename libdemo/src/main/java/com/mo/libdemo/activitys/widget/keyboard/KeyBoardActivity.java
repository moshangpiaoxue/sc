package com.mo.libdemo.activitys.widget.keyboard;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.view.key_board_view.CarNumKeyboardView;
import com.mo.sc.libdemo.R;

/**
 * @ author：mo
 * @ data：2020/11/17:15:09
 * @ 功能：
 */
public class KeyBoardActivity extends BaseActivity {
    private EditText et_keyboard;
    private CarNumKeyboardView cnkbv_keyboard;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_keyboard;
    }

    @Override
    protected void initView() {
        et_keyboard = findViewById(R.id.et_keyboard);
        cnkbv_keyboard = findViewById(R.id.cnkbv_keyboard);
        cnkbv_keyboard.setEditText(getActivity(), et_keyboard);
//        ((ViewGroup) (cnkbv_keyboard.getParent())).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cnkbv_keyboard.setKeyboardShow(false);
//            }
//        });
        findViewById(R.id.tv_keyvoardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                et_keyboard.setFocusable();
                //                et_keyboard.setFocusableInTouchMode(false);
//                view.requestFocus();
                showToast("ssssssss");
                //
            }
        });
    }

    @Override
    protected void initData() {

    }
}
