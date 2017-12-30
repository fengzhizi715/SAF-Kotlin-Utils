package com.safframework.utils

import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.safframework.tony.common.reflect.Reflect
import java.lang.reflect.InvocationTargetException


/**
 * Created by tony on 2017/2/26.
 */

private var mContext: Context? = null

/**
 * 获取全局的context，也就是Application Context
 * @return
 */
fun getApplicationContext(): Context? {

    if (mContext == null) {

        mContext = Reflect.on("android.app.ActivityThread").call("currentApplication").get()
    }

    return mContext
}

/**
 * 获取手机系统SDK版本
 *
 * @return
 */
fun getSDKVersion(): Int = android.os.Build.VERSION.SDK_INT

/**
 * 是否Dalvik模式
 *
 * @return 结果
 */
fun isDalvik(): Boolean {
    return "Dalvik" == getCurrentRuntimeValue()
}

/**
 * 是否ART模式
 *
 * @return 结果
 */
fun isART(): Boolean {
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

/**
 * 获取设备的可用内存大小,单位是M
 *
 * @param context 应用上下文对象context
 * @return 当前内存大小
 */
fun getDeviceUsableMemory(context: Context): Long {

    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val mi = MemoryInfo()
    am.getMemoryInfo(mi)
    // 返回当前系统的可用内存
    return mi.availMem / (1024 * 1024)
}


/**
 * 清理后台进程与服务
 * @param context 应用上下文对象context
 *
 * @return 被清理的数量
 */
fun gc(context: Context): Int {
    val i = getDeviceUsableMemory(context)
    var count = 0 // 清理掉的进程数
    val am = context
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    // 获取正在运行的service列表
    val serviceList = am.getRunningServices(100)
    if (serviceList != null)
        for (service in serviceList) {
            if (service.pid === android.os.Process.myPid())
                continue

            try {
                android.os.Process.killProcess(service.pid)
                count++
            } catch (e: Exception) {
                e.message
            }

        }

    // 获取正在运行的进程列表
    val processList = am.runningAppProcesses
    if (processList != null)
        for (process in processList) {
            // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
            // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
            if (process.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                // pkgList 得到该进程下运行的包名
                val pkgList = process.pkgList
                for (pkgName in pkgList) {

                    Log.d("AppUtils", "======正在杀死包名：" + pkgName)
                    try {
                        am.killBackgroundProcesses(pkgName)
                        count++
                    } catch (e: Exception) { // 防止意外发生
                        e.message
                    }

                }
            }
        }
    Log.d("AppUtils", "清理了" + (getDeviceUsableMemory(context) - i) + "M内存")

    return count
}

/**
 * 检查某个权限是否开启
 *
 * @param permission
 * @return true or false
 */
fun checkPermission(context: Context, permission: String): Boolean {

    if (!isMOrHigher()) {
        val localPackageManager = context.applicationContext.packageManager
        return localPackageManager.checkPermission(permission, context.applicationContext.packageName) == PackageManager.PERMISSION_GRANTED
    } else {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}
