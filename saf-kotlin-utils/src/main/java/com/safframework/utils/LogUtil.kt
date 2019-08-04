package com.safframework.utils

import android.text.TextUtils
import android.util.Log
import com.safframework.saf_utils.BuildConfig


/**
 * @author zhiqiang
 * @date 2019-06-04
 * @desc Log打印工具类
 */
object LogUtil {

    private val isDebug = BuildConfig.DEBUG

    private const val MIN_STACK_OFFSET = 2
    private const val DEFAULT_TAG = "LogUtil"
    @JvmStatic
    fun e(message: String?) {
        if (isDebug) {
            Log.e(DEFAULT_TAG, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun w(message: String?) {
        if (isDebug) {
            Log.w(DEFAULT_TAG, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun d(message: String?) {
        if (isDebug) {
            Log.d(DEFAULT_TAG, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun i(message: String?) {
        if (isDebug) {
            Log.i(DEFAULT_TAG, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun v(message: String?) {
        if (isDebug) {
            Log.v(DEFAULT_TAG, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun e(tag: String, message: String?) {
        if (isDebug && checkParams(tag, message)) {
            Log.e(tag, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun w(tag: String, message: String?) {
        if (isDebug && checkParams(tag, message)) {
            Log.w(tag, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun d(tag: String, message: String?) {
        if (isDebug && checkParams(tag, message)) {
            Log.d(tag, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun i(tag: String, message: String?) {
        if (isDebug && checkParams(tag, message)) {
            Log.i(tag, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    fun v(tag: String, message: String?) {
        if (isDebug && checkParams(tag, message)) {
            Log.v(tag, getMsgWithStackTrace(message))
        }
    }

    @JvmStatic
    private fun checkParams(tag: String, message: String?): Boolean {
        return !TextUtils.isEmpty(tag)
    }

    /**
     * 返回带一层StackTrace的message
     *
     * @return msg
     */
    private fun getMsgWithStackTrace(msg: String?): String {

        val builder = StringBuilder().append(msg).append("|")

        val ste = Thread.currentThread().stackTrace
        val stackOffset = getStackOffset(ste)
        val traceElement = ste[stackOffset]
        if (traceElement != null) {
            builder.append("|")
                .append(traceElement.className)
                .append(".")
                .append(traceElement.methodName)
                .append("(")
                .append(traceElement.fileName)
                .append(":")
                .append(traceElement.lineNumber)
                .append(")")
        }
        return builder.toString()
    }

    /**
     * 获取调用MTLog.e(log)的类的层级
     *
     * @param trace trace
     * @return 层级
     */
    private fun getStackOffset(trace: Array<StackTraceElement>): Int {
        var i = MIN_STACK_OFFSET
        while (i < trace.size) {
            val e = trace[i]
            val name = e.className
            if (name == LogUtil::class.java.name) {
                //LogUtil内有两次调用 LogUtil.e()和getMsgWithStackTrace()
                return i + 2
            }
            i++
        }
        return 0
    }
}