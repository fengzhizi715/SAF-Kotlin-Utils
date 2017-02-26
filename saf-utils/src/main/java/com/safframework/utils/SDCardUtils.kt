package com.safframework.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


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
    @JvmStatic fun isFileExistsInSDCard(filePath: String, fileName: String): Boolean {
        var flag = false
        if (checkSDCardAvailable()) {
            val file = File(filePath, fileName)
            if (file.exists()) {
                flag = true
            }
        }
        return flag
    }

    /**
     * Write file to SD card

     * @param filePath 文件路径
     * *
     * @param filename 文件名
     * *
     * @param content  内容
     * *
     * @return 是否保存成功
     * *
     * @throws Exception
     */
    @Throws(Exception::class)
    @JvmStatic fun saveFileToSDCard(filePath: String, filename: String,
                         content: String): Boolean {
        var flag = false
        if (checkSDCardAvailable()) {
            val dir = File(filePath)
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(filePath, filename)
            val outStream = FileOutputStream(file)
            outStream.write(content.toByteArray())
            outStream.close()
            flag = true
        }
        return flag
    }

    /**
     * Read file as stream from SD card

     * @param fileName String PATH =
     * *                 Environment.getExternalStorageDirectory().getAbsolutePath() +
     * *                 "/dirName";
     * *
     * @return Byte数组
     */
    fun readFileFromSDCard(filePath: String, fileName: String): ByteArray? {

        var buffer: ByteArray? = null
        var fin: FileInputStream? = null;

        try {
            if (checkSDCardAvailable()) {
                val filePaht = filePath + "/" + fileName
                fin = FileInputStream(filePaht)
                val length = fin.available()
                buffer = ByteArray(length)
                fin.read(buffer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            IOUtils.closeQuietly(fin)
        }

        return buffer
    }

    /**
     * Delete file
     *
     * @param filePath 文件路径
     * *
     * @param fileName filePath =
     * *                 android.os.Environment.getExternalStorageDirectory().getPath()
     * *
     * @return 是否删除成功
     */
    @JvmStatic fun deleteSDFile(filePath: String, fileName: String): Boolean {
        val file = File(filePath + "/" + fileName)
        if (!file.exists() || file.isDirectory)
            return false
        return file.delete()
    }

}