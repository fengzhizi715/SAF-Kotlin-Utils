package com.safframework.ext

import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by Tony Shen on 2017/6/30.
 */

fun Throwable.getStackTraceText(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    printStackTrace(pw)
    return sw.toString()
}