package com.safframework.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import java.io.File

/**
 * Created by Tony Shen on 2017/1/17.
 */
object SAFUtils {

    /**
     * 调用该方法需要申请权限
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    @JvmStatic
    fun isWiFiActive(context: Context): Boolean {
        var wm: WifiManager? = null
        try {
            wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return wm!=null && wm.isWifiEnabled
    }

    /**
     * 安装apk
     * @param fileName apk文件的绝对路径
     *
     * @param context
     */
    @JvmStatic
    fun installAPK(fileName: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.fromFile(File(fileName)), "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    /**
     * 检测网络状态
     * @param context
     *
     * @return
     */
    @JvmStatic
    fun checkNetworkStatus(context: Context): Boolean {
        var resp = false
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connMgr.activeNetworkInfo
        if (activeNetInfo != null && activeNetInfo.isAvailable) {
            resp = true
        }
        return resp
    }

    /**
     * 检测gps状态
     * @param context
     *
     * @return
     */
    @JvmStatic
    fun checkGPSStatus(context: Context): Boolean {
        var resp = false
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            resp = true
        }
        return resp
    }

    /**
     * 生成app日志tag
     * 可以这样使用: SAFUtils.makeLogTag(this.getClass());
     * @param cls
     *
     * @return
     */
    @JvmStatic
    fun makeLogTag(cls: Class<*>): String {
        return cls.simpleName
    }

    /**
     * 获取AndroidManifest.xml中<meta-data>元素的值
     * @param context
     *
     * @param name
     *
     * @return
     */
    @JvmStatic
    fun <T> getMetaData(context: Context, name: String): T? {
        try {
            val ai = context.packageManager.getApplicationInfo(context.packageName,
                    PackageManager.GET_META_DATA)

            return ai.metaData?.get(name) as T
        } catch (e: Exception) {
            print("Couldn't find meta-data: " + name)
        }

        return null
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context
     *
     * @param dpValue
     *
     * @return
     */
    @JvmStatic
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从  px(像素) 转成为dp
     * @param context
     *
     * @param pxValue
     *
     * @return
     */
    @JvmStatic
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (0.5f + pxValue / scale).toInt()
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     * @param context
     *
     * @param spValue
     *
     * @return
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (spValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从  px(像素) 转成为sp
     * @param context
     *
     * @param pxValue
     *
     * @return
     */
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}