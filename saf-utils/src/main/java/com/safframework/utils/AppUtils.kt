package com.safframework.utils

import java.lang.reflect.InvocationTargetException;
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context.ACTIVITY_SERVICE
import android.app.ActivityManager
import android.os.Build
import android.annotation.TargetApi



/**
 * Created by tony on 2017/2/26.
 */
object AppUtils {

    /**
     * 获取手机系统SDK版本
     *
     * @return
     */
    @JvmStatic fun getSDKVersion(): Int {
        return android.os.Build.VERSION.SDK_INT
    }

    /**
     * 是否Dalvik模式
     *
     * @return 结果
     */
    @JvmStatic fun isDalvik(): Boolean {
        return "Dalvik" == getCurrentRuntimeValue()
    }

    /**
     * 是否ART模式

     * @return 结果
     */
    @JvmStatic fun isART(): Boolean {
        val currentRuntime = getCurrentRuntimeValue()
        return "ART" == currentRuntime || "ART debug build" == currentRuntime
    }

    /**
     * 获取手机当前的Runtime
     *
     * @return 正常情况下可能取值Dalvik, ART, ART debug build;
     */
    fun getCurrentRuntimeValue(): String {
        try {
            val systemProperties = Class.forName("android.os.SystemProperties")
            try {
                val get = systemProperties.getMethod("get",
                        String::class.java, String::class.java) ?: return "WTF?!"
                try {
                    val value = get.invoke(
                            systemProperties, "persist.sys.dalvik.vm.lib",
                            /* Assuming default is */"Dalvik") as String
                    if ("libdvm.so" == value) {
                        return "Dalvik"
                    } else if ("libart.so" == value) {
                        return "ART"
                    } else if ("libartd.so" == value) {
                        return "ART debug build"
                    }

                    return value
                } catch (e: IllegalAccessException) {
                    return "IllegalAccessException"
                } catch (e: IllegalArgumentException) {
                    return "IllegalArgumentException"
                } catch (e: InvocationTargetException) {
                    return "InvocationTargetException"
                }

            } catch (e: NoSuchMethodException) {
                return "SystemProperties.get(String key, String def) method is not found"
            }

        } catch (e: ClassNotFoundException) {
            return "SystemProperties class is not found"
        }

    }
}