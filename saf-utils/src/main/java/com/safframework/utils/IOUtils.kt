package com.safframework.utils

import java.io.Closeable
import java.io.IOException

/**
 * Created by Tony Shen on 2017/1/16.
 */
object IOUtils {

    private val BUFFER_SIZE = 0x400 // 1024

    
    /**
     * 安全关闭io流
     * @param closeable
     */
    fun closeQuietly(vararg closeables: Closeable?) {

        closeables.map {
            if (it != null) {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }
}