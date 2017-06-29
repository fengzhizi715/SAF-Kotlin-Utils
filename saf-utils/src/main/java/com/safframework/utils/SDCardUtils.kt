package com.safframework.utils

import android.annotation.TargetApi
import android.os.Build
import android.os.Environment
import android.os.StatFs
import java.io.*

/**
 * Created by tony on 2017/2/26.
 */
object SDCardUtils {

    val KB = 1024

    val MB = 1024 * 1024

    val GB = 1024 * 1024 * 1024

    /**
     * Check the SD card
     *
     * @return 是否存在SDCard
     */
    @JvmStatic fun checkSDCardAvailable(): Boolean = android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED

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
            closeQuietly(fin)
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

    /**
     * 获取SD卡的路径
     *
     * @return SD卡的路径
     */
    @JvmStatic fun getSDCardPath(): String? =
            if (!checkSDCardAvailable()) {
                null
            } else {
                val command = "car /proc/mounts"
                val runtime = Runtime.getRuntime()
                var bufferReader: BufferedReader? = null
                try {
                    val p = runtime.exec(command)
                    bufferReader = BufferedReader(InputStreamReader(p.inputStream))
                    var line = bufferReader.readLine()
                    while (line != null) {
                        if (line.contains("sdcard") && line.contains(".android_secure")) {
                            val strArray = line.split(" ")
                            if (strArray.size >= 5) {
                                strArray[1].replace("/.android_secure", "") + File.separator
                            }
                        }
                        if (p.waitFor() != 0 && p.exitValue() == 1) {
                            null
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    closeQuietly(bufferReader)
                }
                Environment.getExternalStorageDirectory().path
            }


    /**
     * 获取SD卡的剩余空间
     *
     * @return SD卡的剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @JvmStatic fun getSDCardFreeSpace(): String? =
            if (!checkSDCardAvailable()) {
                null
            } else {
                val statFs = StatFs(getSDCardPath())
                byte2FitMemorySize(statFs.availableBlocksLong * statFs.blockSizeLong)
            }

    /**
     * 字节数转合适的内存大小
     *
     * @param byteNum 字节数
     * @return 合适的内存大小
     */
    fun byte2FitMemorySize(byteNum: Long): String
            = when {
        byteNum < 0 -> "shouldn't be less than zero"
        byteNum < SDCardUtils.KB -> String.format("%.3fB", byteNum + 0.0005)
        byteNum < SDCardUtils.MB -> String.format("%.3fKB", byteNum / SDCardUtils.KB)
        byteNum < SDCardUtils.GB -> String.format("%.3fMB", byteNum / SDCardUtils.MB)
        else -> String.format("%.3fGB", byteNum / SDCardUtils.GB)
    }

    /**
     * 获取SD卡信息
     *
     * @return SD卡信息
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @JvmStatic fun getSDCardInfo(): SDCardInfo =
            if (!checkSDCardAvailable()) {
                SDCardInfo()
            } else {
                val statFs = StatFs(getSDCardPath())
                SDCardInfo(true,
                        statFs.blockCountLong,
                        statFs.freeBlocksLong,
                        statFs.availableBlocksLong,
                        statFs.blockSizeLong,
                        statFs.totalBytes,
                        statFs.freeBytes,
                        statFs.availableBytes)
            }

    data class SDCardInfo(var isExist: Boolean = false,
                          var totalBlocks: Long = -1,
                          var freeBlocks: Long = -1,
                          var availableBlocks: Long = -1,
                          var blockByteSize: Long = -1,
                          var totalBytes: Long = -1,
                          var freeBytes: Long = -1,
                          var availableBytes: Long = -1)

}