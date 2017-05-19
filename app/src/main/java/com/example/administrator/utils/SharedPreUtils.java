package com.example.administrator.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/3/25.
 */

public class SharedPreUtils {

    private final static String config = "news";

    // 设置boolean值
    public static void setBoolean(Context ctx, String key, Boolean value) {
        SharedPreferences ref = ctx.getSharedPreferences(config, Context.MODE_PRIVATE);
        ref.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key, Boolean defValue) {
        SharedPreferences ref = ctx.getSharedPreferences(config, Context.MODE_PRIVATE);
        return ref.getBoolean(key, defValue);
    }


}
