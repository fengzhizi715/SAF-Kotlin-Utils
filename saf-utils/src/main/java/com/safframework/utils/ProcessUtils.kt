package com.safframework.utils

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import com.safframwork.tony.common.utils.Preconditions
import java.util.*

/**
 * Created by tony on 2017/2/26.
 */
object ProcessUtils {

    /**
     * 获取前台线程包名
     * 当不是查看当前App，且SDK大于21时，
     * 需添加权限 <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>
     *
     * @param context 上下文
     * @return 前台应哟过包名
     */
    @JvmStatic fun getForegroundProcessName(context: Context): String? {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.runningAppProcesses.map {
            if (it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return it.processName
            }
        }
        if (Build.VERSION.SDK_INT > 21) {
            val pm = context.packageManager
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            val list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            if (list.size > 0) {
                try {
                    val ai = pm.getApplicationInfo(context.packageName, 0)
                    val aom = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, ai.uid, ai.packageName) != AppOpsManager.MODE_ALLOWED) {
                        context.startActivity(intent)
                        return null
                    }
                    val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                    val endTime = System.currentTimeMillis()
                    val beginTime = endTime - 86400000 * 7
                    val usageStats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime)
                    if (usageStats == null || usageStats.isEmpty()) {
                        return null
                    }
                    var recentStats : UsageStats? = null
                    usageStats.map {
                        if (recentStats == null || it.lastTimeUsed > (recentStats as UsageStats).lastTimeUsed) {
                            recentStats = it
                        }
                    }
                    return recentStats?.packageName
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    /**
     * 获取所有的后台进程
     * 需添加权限 <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
     *
     * @param context 上下文
     * @return 所有的后台进程的set
     */
    @JvmStatic fun getAllBackgroundProcesses(context: Context): HashSet<String> {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val set = HashSet<String>()
        am.runningAppProcesses.map {
            set.addAll(it.pkgList)
        }
        return set
    }

    /**
     * 杀死所有的后台进程
     * 需添加权限 <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
     *
     * @param context 上下文
     * @return 被杀死的后台进程的set
     */
    @JvmStatic fun killAllBackgroundProcesses(context: Context): HashSet<String> {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val set = HashSet<String>()
        am.runningAppProcesses.map {
            it.pkgList.map {
                am.killBackgroundProcesses(it)
                set.add(it)
            }
        }
        am.runningAppProcesses.map {
            it.pkgList.map {
                set.remove(it)
            }
        }
        return set
    }

    /**
     * 杀死后台进程服务
     * 需添加权限 <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
     *
     * @param context 上下文
     * @param packageName 包名
     * @return boolean
     */
    @JvmStatic fun killBackgroundProcesses(context: Context, packageName: String?): Boolean {
        if (Preconditions.isBlank(packageName)) {
            return false
        }

        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.runningAppProcesses.map {
            if (it.pkgList.asList().contains(packageName)) {
                am.killBackgroundProcesses(packageName)
            }
        }
        am.runningAppProcesses.map {
            if (it.pkgList.toList().contains(packageName)) {
                return false
            }
        }
        return true
    }
}