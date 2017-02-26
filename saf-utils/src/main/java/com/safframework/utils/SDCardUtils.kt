package com.safframework.utils

import java.io.File



/**
 * Created by tony on 2017/2/26.
 */
object SDCardUtils {

    /**
     * Check the SD card
     *
     * @return 是否存在SDCard
     */
    @JvmStatic fun checkSDCardAvailable(): Boolean {
        return android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
    }


    /**
     * Check if the file is exists
     *
     * @param filePath 文件路径
     * *
     * @param fileName 文件名
     * *
     * @return 是否存在文件
     */
    fun isFileExistsInSDCard(filePath: String, fileName: String): Boolean {
        var flag = false
        if (checkSDCardAvailable()) {
            val file = File(filePath, fileName)
            if (file.exists()) {
                flag = true
            }
        }
        return flag
    }

}