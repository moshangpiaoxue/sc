package com.mo.lib.view.key_board_view;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mo.lib.R;
import com.mo.lib.k;
import com.mo.lib.utils.systemUtils.KeyBordUtil;
import com.mo.lib.utils.tips_utils.LogUtil;


/**
 * @ author：mo
 * @ data：2020/9/7:15:11
 * @ 功能：自定义车牌输入键盘
 */
public class CarNumKeyboardView extends RelativeLayout {
    /** 整体view 包括title条 */
    private LinearLayout kkeyboard_;
    /** 键盘view */
    private KeyboardView keyboardView;
    /** 字母键盘 */
    private Keyboard province_keyboard;
    /** 数字键盘 */
    private Keyboard number_keyboar;
    /** 输入控件 */
    private EditText editText;


    public CarNumKeyboardView(Context context) {
        super(context, null);
    }


    public CarNumKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.car_num_keyboard_view, this, true);
        province_keyboard = new Keyboard(k.app(), R.xml.province_abbreviation);
        number_keyboar = new Keyboard(k.app(), R.xml.number_or_letters);
        kkeyboard_ = findViewById(R.id.kkeyboard_);
        keyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        changeKeyboard(true);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);

    }

    public void setEditText(Activity activity, EditText editText) {
        setEditText(activity, editText, false);
    }

    public void setEditText(Activity activity, final EditText editText, final boolean isShow) {
        this.editText = editText;
        keyboardView.setOnKeyboardActionListener(listener);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        //点击弹出
        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.requestFocus();
                editText.setFocusable(true);
                changeKeyboard(!(editText.getText().length() > 0));
                setKeyboardShow(true);
            }
        });
        // 焦点监听
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //关掉系统输入法
                    KeyBordUtil.isShowKeybord(activity, editText, false);
                    // 这里是关键代码了
                    editText.setTextIsSelectable(true);
                    setKeyboardShow(true);
                } else {
                    setKeyboardShow(false);
                }
            }
        });

        ((ViewGroup) (this.getParent())).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setKeyboardShow(false);
            }
        });
    }

    /**
     * 设置键盘是否显示
     * 当显示并且输入框没有数据的时候默认显示省份键盘
     *
     * @param isShow true==显示  false==隐藏
     */
    public void setKeyboardShow(boolean isShow) {
        kkeyboard_.setVisibility(isShow ? VISIBLE : View.GONE);
        if (isShow && editText != null && editText.getText().length() == 0) {
            changeKeyboard(true);
        }
    }

    /**
     * 指定切换软键盘
     *
     * @param isWord true==切到省份键盘  false==切到数字键盘
     */
    public void changeKeyboard(boolean isWord) {
        keyboardView.setKeyboard(isWord ? province_keyboard : number_keyboar);
    }

    /**
     * 监听
     */
    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();

            int start = editable.length();
            //            int start = editText.getSelectionEnd();
            LogUtil.i(editable.toString() + "=" + start);
            // 完成
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {
                setKeyboardShow(false);
                // 回退
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                        if (start == 1) {
                            changeKeyboard(true);
                        }
                    }
                    //                    }else {
                    //                        String temp=editable.toString();
                    //                        LogUtil.i(temp);
                    //                        if (!StringUtil.isEmpty(temp)){
                    //                            editable.delete(temp.length() - 1, temp.length());
                    //                        }
                    //                    }
                }
                // 省份键盘切换到数字键盘
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
                changeKeyboard(true);
                // 数字键盘切换到省份键盘
            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
                changeKeyboard(false);
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
                //                 判断第一个字符是否是中文,是，则自动切换到数字软键盘
                //                if (editText.getText().toString().matches(reg)) {
                //从第二个字符开始默认切换到数字键盘
                if (editText.getText().toString().length() > 0) {
                    changeKeyboard(false);
                }
            }
        }

        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }
    };

    public EditText getEditText() {
        return editText;
    }
}