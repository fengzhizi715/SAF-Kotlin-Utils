package com.safframework.ext

import android.content.Context
import android.content.pm.PackageManager
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Tony Shen on 2017/6/30.
 */

fun Context.string(@StringRes id: Int): String = getString(id)

fun Context.color(@ColorRes id: Int): Int = resources.getColor(id)

fun Context.inflateLayout(@LayoutRes layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View
        = LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)

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

fun Context.getAppVersionCode(): Int {

    val appContext = applicationContext
    val manager = appContext.getPackageManager()
    try {
        val info = manager.getPackageInfo(appContext.getPackageName(), 0)

        if (info != null)
            return info.versionCode

    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return 0
}

/**
 * 获取应用的包名
 *
 * @param context context
 * @return package name
 */
fun Context.getPackageName(): String = packageName