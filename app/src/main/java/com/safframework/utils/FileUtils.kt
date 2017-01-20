package com.safframework.utils

import java.io.File

/**
 * Created by Tony Shen on 2017/1/20.
 */
object FileUtils {

    fun exists(file: File?): Boolean {
        return file != null && file.exists()
    }

    /**
     * 判断是否文件
     * @param file
     * *
     * @return
     */
    fun isFile(file: File) : Boolean = exists(file) && file.isFile

    /**
     * 判断是否目录
     * @param file
     * *
     * @return
     */
    fun isDirectory(file: File) : Boolean = exists(file) && file.isDirectory
}