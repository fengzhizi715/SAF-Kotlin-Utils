package com.safframework.utils

import java.io.Closeable
import java.io.IOException

/**
 * Created by Tony Shen on 2017/1/16.
 */
object IOUtils {

    /**
     * 安全关闭io流
     * @param closeable
     */
    fun closeQuietly(closeable: Closeable?) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}