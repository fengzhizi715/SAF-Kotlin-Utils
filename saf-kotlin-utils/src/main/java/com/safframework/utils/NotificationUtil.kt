package com.safframework.utils


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews


/**
 * @Author zhiqiang
 * @Date 2019-06-17
 * @Description 通知管理类 参考https://github.com/yangchong211/YCNotification
 */
class NotificationUtil(base: Context) : ContextWrapper(base) {
    private var mManager: NotificationManager? = null
    private var flags: IntArray? = null

    /**
     * 获取创建一个NotificationManager的对象
     *
     * @return NotificationManager对象
     */
    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }


    private var ongoing = false
    private var remoteViews: RemoteViews? = null
    private var intent: PendingIntent? = null
    private var ticker: String? = ""
    private var priority = NotificationCompat.PRIORITY_DEFAULT
    private var onlyAlertOnce = false
    private var `when`: Long = 0
    private var sound: Uri? = null
    private var defaults = 0
    private var pattern: LongArray? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        //第一个参数：channel_id
        //第二个参数：channel_name
        //第三个参数：设置通知重要性级别
        //注意：该级别必须要在 NotificationChannel 的构造函数中指定，总共要五个级别；
        //范围是从 NotificationManager.IMPORTANCE_NONE(0) ~ NotificationManager.IMPORTANCE_HIGH(4)
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        //是否绕过请勿打扰模式
        channel.canBypassDnd()
        //闪光灯
        channel.enableLights(true)
        //锁屏显示通知
        channel.lockscreenVisibility = NotificationCompat.VISIBILITY_SECRET
        //闪关灯的灯光颜色
        channel.lightColor = Color.RED
        //桌面launcher的消息角标
        channel.canShowBadge()
        //是否允许震动
        channel.enableVibration(true)
        //设置可绕过 请勿打扰模式
        channel.setBypassDnd(true)
        //设置震动模式
        channel.vibrationPattern = longArrayOf(100, 100, 200)
        //是否会有灯光
        channel.shouldShowLights()
        manager!!.createNotificationChannel(channel)
    }

    /**
     * 清空所有的通知
     */
    fun clearNotification() {
        manager!!.cancelAll()
    }

    /**
     * 获取Notification
     *
     * @param title   title
     * @param content content
     */
    fun getNotification(title: String, content: String, icon: Int): Notification {
        val build: Notification
        val builder = getNotificationBuilder(title, content, icon)
        build = builder.build()
        if (flags != null && flags!!.size > 0) {
            for (a in flags!!.indices) {
                build.flags = build.flags or flags!![a]
            }
        }
        return build
    }

    /**
     * 调用该方法可以发送通知
     *
     * @param notifyId notifyId
     * @param title    title
     * @param content  content
     */
    fun sendNotification(notifyId: Int, title: String, content: String, icon: Int) {
        val build = getNotification(title, content, icon)
        manager!!.notify(notifyId, build)
    }


    /**
     * 获取Notification的建造Builder
     *
     * @param title
     * @param content
     * @param icon
     * @return
     */
    fun getNotificationBuilder(title: String, content: String, icon: Int): NotificationCompat.Builder {

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,
            CHANNEL_ID
        )

        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setSmallIcon(icon)
        builder.priority = priority
        builder.setOnlyAlertOnce(onlyAlertOnce)
        builder.setOngoing(ongoing)
        if (remoteViews != null) {
            builder.setCustomContentView(remoteViews)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setCustomContentView(remoteViews)
            } else {
                builder.setContent(remoteViews)
            }
        }
        if (intent != null) {
            builder.setContentIntent(intent)
        }
        if (ticker != null && ticker!!.isNotEmpty()) {
            builder.setTicker(ticker)
        }
        if (`when` != 0L) {
            builder.setWhen(`when`)
        }
        if (sound != null) {
            builder.setSound(sound)
        }
        if (defaults != 0) {
            builder.setDefaults(defaults)
        }
        if (pattern != null) {
            //自定义震动效果
            builder.setVibrate(pattern)
        }
        //点击自动删除通知
        builder.setAutoCancel(true)
        return builder
    }

    /**
     * 让通知左右滑的时候是否可以取消通知
     *
     * @param ongoing 是否可以取消通知
     * @return
     */
    fun setOngoing(ongoing: Boolean): NotificationUtil {
        this.ongoing = ongoing
        return this
    }

    /**
     * 设置自定义view通知栏布局
     *
     * @param remoteViews view
     * @return
     */
    fun setContent(remoteViews: RemoteViews): NotificationUtil {
        this.remoteViews = remoteViews
        return this
    }

    /**
     * 设置内容点击
     *
     * @param intent intent
     * @return
     */
    fun setContentIntent(intent: PendingIntent): NotificationUtil {
        this.intent = intent
        return this
    }

    /**
     * 设置状态栏的标题
     *
     * @param ticker 状态栏的标题
     * @return
     */
    fun setTicker(ticker: String): NotificationUtil {
        this.ticker = ticker
        return this
    }


    /**
     * 设置优先级
     * 注意：
     * Android 8.0以及上，在 NotificationChannel 的构造函数中指定，总共要五个级别；
     * Android 7.1（API 25）及以下的设备，还得调用NotificationCompat 的 setPriority方法来设置
     *
     * @param priority 优先级，默认是Notification.PRIORITY_DEFAULT
     * @return
     */
    fun setPriority(priority: Int): NotificationUtil {
        this.priority = priority
        return this
    }

    /**
     * 是否提示一次.true - 如果Notification已经存在状态栏即使在调用notify函数也不会更新
     *
     * @param onlyAlertOnce 是否只提示一次，默认是false
     * @return
     */
    fun setOnlyAlertOnce(onlyAlertOnce: Boolean): NotificationUtil {
        this.onlyAlertOnce = onlyAlertOnce
        return this
    }

    /**
     * 设置通知时间，默认为系统发出通知的时间，通常不用设置
     *
     * @param when when
     * @return
     */
    fun setWhen(`when`: Long): NotificationUtil {
        this.`when` = `when`
        return this
    }

    /**
     * 设置sound
     *
     * @param sound sound
     * @return
     */
    fun setSound(sound: Uri): NotificationUtil {
        this.sound = sound
        return this
    }


    /**
     * 设置默认的提示音
     *
     * @param defaults defaults
     * @return
     */
    fun setDefaults(defaults: Int): NotificationUtil {
        this.defaults = defaults
        return this
    }

    /**
     * 自定义震动效果
     *
     * @param pattern pattern
     * @return
     */
    fun setVibrate(pattern: LongArray): NotificationUtil {
        this.pattern = pattern
        return this
    }

    /**
     * 设置flag标签
     *
     * @param flags flags
     * @return
     */
    fun setFlags(vararg flags: Int): NotificationUtil {
        this.flags = flags
        return this
    }

    companion object {


        val CHANNEL_ID = "default"
        private val CHANNEL_NAME = "default_channel"
    }

}