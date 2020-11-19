package com.mo.lib.utils.app_utils.permissions_util;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.mo.lib.k;

import java.util.List;

/**
 * @ author：mo
 * @ data：2020/8/20:16:23
 * @ 功能：
 */
public abstract class KOnPermission implements OnPermission {
    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            ToastUtils.show("被永久拒绝授权，请手动授予存储和拍照权限");
            // 如果是被永久拒绝就跳转到应用权限系统设置页面
            XXPermissions.startPermissionActivity(k.app(), denied);
        } else {
            ToastUtils.show("获取存储和拍照权限失败");
        }
    }
}
