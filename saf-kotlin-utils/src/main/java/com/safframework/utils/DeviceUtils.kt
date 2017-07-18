package com.safframework.utils

import android.content.Context
import android.content.pm.PackageManager

/**
 * Created by Tony Shen on 2017/7/18.
 */

/**
 * 获取当前app的版本号
 */
fun getAppVersion(context:Context): String {

    val appContext = context.applicationContext
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