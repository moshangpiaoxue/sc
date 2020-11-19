package com.mo.lib.utils.dataUtil.dealUtil;//package mo.klib.utils.dataUtil.dealUtil;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ author：mo
 * @ data：2018/3/28 0028
 * @ 功能：
 */
public enum GsonUtil {

    /**
     * 枚举单例
     */
    INSTANCE;
    private Gson mGson;

    GsonUtil() {
        mGson = new Gson();
    }

    public Gson getGson() {
        return mGson;
    }

    /**
     * 转成json串
     */
    public String getString(Object object) {
        String gsonString = null;
        if (mGson != null) {
            gsonString = mGson.toJson(object);
        }
        return gsonString;
    }


    /**
     * 转成泛型bean
     */
    public <T> T getBean(String gsonString, Class<T> cls) {
        T TT = null;
        Type localType = new TypeToken<T>() {
        }.getType();
        if (mGson != null) {
            TT = mGson.fromJson(gsonString, localType);
        }
        return TT;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     */
    public <T> List<T> getList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (mGson != null) {
            list = mGson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list
     * 解决泛型问题
     */
    public static <T> List<T> getList2(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成list中有map的
     */
    public <T> List<Map<String, T>> getMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (mGson != null) {
            list = mGson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     */
    public <T> Map<String, T> getMap(String gsonString) {
        Map<String, T> map = null;
        if (mGson != null) {
            map = mGson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}