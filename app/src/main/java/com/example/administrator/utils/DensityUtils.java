package com.example.administrator.utils;

import android.content.Context;

/**
 *  用来完成dpi与px的相互转化
 */

public class DensityUtils {

    // dpi转px
    public static int dpi2px(Context ctx,int dpi){
        return (int)(ctx.getResources().getDisplayMetrics().density * dpi);
    }

    public static int px2dpi(Context ctx,int px){
        return (int)(px / ctx.getResources().getDisplayMetrics().density);
    }
}
