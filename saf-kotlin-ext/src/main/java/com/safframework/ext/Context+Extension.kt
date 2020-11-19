package com.safframework.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import java.io.File

/**
 * Created by Tony Shen on 2017/6/30.
 */

/**
 * screen width in pixels
 */
inline val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * screen height in pixels
 */
inline val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

inline val Context.isNetworkAvailable: Boolean
    @SuppressLint("MissingPermission")
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

/**
 * returns dip(dp) dimension value in pixels
 * @param value dp
 */
fun Context.dp2px(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dp2px(value: Float): Int = (value * resources.displayMetrics.density).toInt()

/**
 * return sp dimension value in pixels
 * @param value sp
 */
fun Context.sp2px(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

fun Context.sp2px(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()

/**
 * converts [px] value into dip or sp
 * @param px
 */
fun Context.px2dp(px: Int): Float = px.toFloat() / resources.displayMetrics.density

fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

/**
 * return dimen resource value in pixels
 * @param resource dimen resource
 */
fun Context.dimen2px(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)


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

data class AppInfo(
        val apkPath: String,
        val packageName: String,
        val versionName: String,
        val versionCode: Long,
        val appName: String,
        val icon: Drawable
)

fun Context.getAppInfo(apkPath: String): AppInfo {
    val packageInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA) as PackageInfo
    packageInfo.applicationInfo.sourceDir = apkPath
    packageInfo.applicationInfo.publicSourceDir = apkPath

    val packageName = packageInfo.packageName
    val appName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString()
    val versionName = packageInfo.versionName
    val versionCode = packageInfo.versionCode
    val icon = packageManager.getApplicationIcon(packageInfo.applicationInfo)
    return AppInfo(apkPath, packageName, versionName, versionCode.toLong(), appName, icon)
}

fun Context.getAppInfos(apkFolderPath: String): List<AppInfo> {
    val appInfoList = ArrayList<AppInfo>()
    for (file in File(apkFolderPath).listFiles())
        appInfoList.add(getAppInfo(file.path))
    return appInfoList
}