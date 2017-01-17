package com.safframework.utils

import android.annotation.TargetApi
import android.os.Build

/**
 * Created by Tony Shen on 2017/1/17.
 */
object SAFUtils {

    fun isFroyoOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO
    }

    fun isGingerbreadOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
    }

    fun isHoneycombOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
    }

    fun isICSOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
    }

    fun isJellyBeanOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    @TargetApi(17)
    fun isJellyBeanMR1OrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    @TargetApi(18)
    fun isJellyBeanMR2OrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
    }

    @TargetApi(19)
    fun isKitkatOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    @TargetApi(20)
    fun isKitkatWatchOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH
    }

    @TargetApi(21)
    fun isLOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * api level 22是android 5.1
     * @return
     */
    @TargetApi(22)
    fun isLMR1OrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
    }

    @TargetApi(23)
    fun isMOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
}