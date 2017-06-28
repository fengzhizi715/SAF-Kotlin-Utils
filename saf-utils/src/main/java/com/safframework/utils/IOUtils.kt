package com.safframework.utils

import java.io.*

/**
 * Created by Tony Shen on 2017/1/16.
 */
object IOUtils {

    private val BUFFER_SIZE = 0x400 // 1024


    /**
     * 文件拷贝
     * @param src source [File]
     *
     * @param dst destination [File]
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun copyFile(src: File, dst: File) {
        val `in` = FileInputStream(src)
        val out = FileOutputStream(dst)
        val inChannel = `in`.channel
        val outChannel = out.channel

        try {
            inChannel?.transferTo(0, inChannel.size(), outChannel)
        } finally {
            inChannel?.close()
            outChannel?.close()
        }

        closeQuietly(`in`,out)
    }

    /**
     * 安全关闭io流
     * @param closeable
     */
    @JvmStatic fun closeQuietly(vararg closeables: Closeable?) {

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