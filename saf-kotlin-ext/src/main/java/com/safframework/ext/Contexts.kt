package com.safframework.ext

import android.content.Context
import android.content.pm.PackageManager
import android.support.annotation.StringRes

/**
 * Created by Tony Shen on 2017/6/30.
 */

fun Context.string(@StringRes id: Int): String = getString(id)

/**
 * 获取当前app的版本号
 */
fun Context.getAppVersion(): String {

    val appContext = applicationContext
    val manager = appContext.getPackageManager()
    try {
        val info = manager.getPackageInfo(appContext.getPackageName(), 0)

        if (info != null)
            return info.versionName

    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return ""
}