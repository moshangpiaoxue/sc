package com.mo.lib.utils.dataUtil.CacheUtils;

import android.content.SharedPreferences;
import android.os.Parcelable;

import com.mo.lib.k;
import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.Set;

/**
 * @ author：mo
 * @ data：2020/10/27:18:06
 * @ 功能：腾讯开源框架，用来替换原生spf
 * @ 注意：1、集成、并且在触发此类之后，当有白屏发生，log日志没有显示报错信息，注意检查调用方法返回值是否为空。
 * @ 注意：2、集成后报错：More than one file was found with OS independent path 'META-INF/proguard/androidx-annotations.pro'
 * 在app目录下的gradle里的Android节点里添加
 * packagingOptions {
 * pickFirst 'META-INF/proguard/androidx-annotations.pro'
 * }
 */
public class MmkvUtils {
    /**
     * 初始化 在 k.Ext.init(this);的时候默认调用了
     */
    public static void initMmkv() {
        MMKV.initialize(k.app());
    }

    public static MMKV getDef() {
        return MMKV.defaultMMKV();
    }

    /**
     * 导入spf文件 默认不删除原spf文件，根据需要自行删除
     */
    public static void importSpf(SharedPreferences old_man) {
        getDef().importFromSharedPreferences(old_man);
        //    old_man.edit().clear().commit();
    }

    /**
     * 移除某个key对
     */
    public static void removeKey(String key) {
        getDef().removeValueForKey(key);
    }

    /**
     * 清除所有key
     */
    public static void clearAll() {
        getDef().clearAll();
    }

    /**
     * 保存数据,根据默认值的类型调用不同的保存方法
     */
    public static void encode(String key, Object object) {
        if (object instanceof String) {
            getDef().encode(key, (String) object);
        } else if (object instanceof Integer) {
            getDef().encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            getDef().encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            getDef().encode(key, (Float) object);
        } else if (object instanceof Long) {
            getDef().encode(key, (Long) object);
        } else if (object instanceof Double) {
            getDef().encode(key, (Double) object);
        } else if (object instanceof byte[]) {
            getDef().encode(key, (byte[]) object);
        } else {
            getDef().encode(key, object.toString());
        }
    }

    /**
     * 获取数据，根据默认值的类型调用不同的取值方法
     */
    public static Object decode(String key, Object defObj) {
        if (defObj instanceof String) {
            return getDef().decodeString(key, (String) defObj);
        } else if (defObj instanceof Integer) {
            return getDef().decodeInt(key, (Integer) defObj);
        } else if (defObj instanceof Boolean) {
            return getDef().decodeBool(key, (Boolean) defObj);
        } else if (defObj instanceof Float) {
            return getDef().decodeFloat(key, (Float) defObj);
        } else if (defObj instanceof Long) {
            return getDef().decodeLong(key, (Long) defObj);
        } else if (defObj instanceof Double) {
            return getDef().decodeDouble(key, (Double) defObj);
        } else if (defObj instanceof byte[]) {
            return getDef().decodeBytes(key, (byte[]) defObj);
        } else {
            return getDef().decodeString(key, defObj.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Integer decodeInt(String key) {
        return getDef().decodeInt(key, 0);
    }

    public static Double decodeDouble(String key) {
        return getDef().decodeDouble(key, 0.00);
    }

    public static Long decodeLong(String key) {
        return getDef().decodeLong(key, 0L);
    }

    public static Boolean decodeBoolean(String key) {
        return getDef().decodeBool(key, false);
    }

    public static Float decodeFloat(String key) {
        return getDef().decodeFloat(key, 0F);
    }

    public static byte[] decodeBytes(String key) {
        return getDef().decodeBytes(key);
    }

    public static String decodeString(String key) {
        return getDef().decodeString(key, "");
    }

    public static Set<String> decodeStringSet(String key) {
        return getDef().decodeStringSet(key, Collections.<String>emptySet());
    }

    public static Parcelable decodeParcelable(String key) {
        return getDef().decodeParcelable(key, null);
    }

    public static void encodeSet(String key, Set<String> sets) {
        getDef().encode(key, sets);
    }

    public static void encodeParcelable(String key, Parcelable obj) {
        getDef().encode(key, obj);
    }
}
