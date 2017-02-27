package com.safframework.utils

import java.io.File
import java.io.IOException

/**
 * Created by Tony Shen on 2017/1/20.
 */
object FileUtils {

    @JvmStatic fun exists(file: File?): Boolean = file != null && file.exists()

    /**
     * 判断是否文件
     * @param file
     * *
     * @return
     */
    @JvmStatic fun isFile(file: File) : Boolean = exists(file) && file.isFile

    /**
     * 判断是否目录
     * @param file
     * *
     * @return
     */
    @JvmStatic fun isDirectory(file: File) : Boolean = exists(file) && file.isDirectory

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return boolean
     */
    @JvmStatic fun createOrExistsDir(file: File?): Boolean
            = (file != null) && if (file.exists()) file.isDirectory else file.mkdirs()

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return boolean
     */
    @JvmStatic fun createOrExistsFile(file: File?): Boolean {
        if (file == null || !createOrExistsDir(file.parentFile)) {
            return false
        }
        if (file.exists()) {
            return file.isFile
        }
        try {
            return file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }
}