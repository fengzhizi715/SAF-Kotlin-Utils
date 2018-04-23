package com.safframework.ext

import java.io.Closeable

/**
 *
 * @FileName:
 *          com.safframework.ext.IOs.java
 * @author: Tony Shen
 * @date: 2018-04-23 15:01
 * @version V1.0 <描述当前版本功能>
 */

fun Closeable?.closeQuietly() {
    try {
        this?.close()
    } catch (e: Throwable) {
        // ignore
    }
}