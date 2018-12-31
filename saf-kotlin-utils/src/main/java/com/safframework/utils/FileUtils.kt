package com.safframework.utils

import java.io.File
import java.io.IOException

/**
 * Created by Tony Shen on 2017/1/20.
 */

fun exists(file: File?) = file != null && file.exists()

/**
 * 判断是否文件
 * @param file
 *
 * @return
 */
fun isFile(file: File) = exists(file) && file.isFile

/**
 * 判断是否目录
 * @param file
 *
 * @return
 */
fun isDirectory(file: File) = exists(file) && file.isDirectory

/**
 * 判断目录是否存在，不存在则判断是否创建成功
 *
 * @param file 文件
 * @return boolean
 */
fun createOrExistsDir(file: File?) = (file != null) && if (file.exists()) file.isDirectory else file.mkdirs()

/**
 * 判断文件是否存在，不存在则判断是否创建成功
 *
 * @param file 文件
 * @return boolean
 */
fun createOrExistsFile(file: File?): Boolean {
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
