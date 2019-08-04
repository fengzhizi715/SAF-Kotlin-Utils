package com.safframework.utils

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @Author zhiqiang
 * @Date 2019-06-19
 * @Description 参考：http://blankj.com
 */
object MD5Util {
    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
    /**
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @return the hex string of MD5 encryption
     */
    fun string2MD5(data: String?): String {
        return if (data == null || data.length == 0) {
            ""
        } else data2MD5(data.toByteArray())
    }

    /**
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @param salt The salt.
     * @return the hex string of MD5 encryption
     */
    fun encryptMD5ToString(data: String?, salt: String?): String {
        if (data == null && salt == null) {
            return ""
        }
        if (salt == null) {
            return bytes2HexString(
                encryptMD5(
                    data!!.toByteArray()
                )
            )
        }
        return if (data == null) {
            bytes2HexString(
                encryptMD5(
                    salt.toByteArray()
                )
            )
        } else bytes2HexString(
            encryptMD5(
                (data + salt).toByteArray()
            )
        )
    }

    /**
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @return the hex string of MD5 encryption
     */
    fun data2MD5(data: ByteArray): String {
        return bytes2HexString(
            encryptMD5(
                data
            )
        )
    }

    /**
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @param salt The salt.
     * @return the hex string of MD5 encryption
     */
    fun encryptMD5ToString(data: ByteArray?, salt: ByteArray?): String {
        if (data == null && salt == null) {
            return ""
        }
        if (salt == null) {
            return bytes2HexString(
                encryptMD5(
                    data!!
                )
            )
        }
        if (data == null) {
            return bytes2HexString(
                encryptMD5(
                    salt
                )
            )
        }
        val dataSalt = ByteArray(data.size + salt.size)
        System.arraycopy(data, 0, dataSalt, 0, data.size)
        System.arraycopy(salt, 0, dataSalt, data.size, salt.size)
        return bytes2HexString(
            encryptMD5(
                dataSalt
            )
        )
    }

    /**
     * Return the bytes of MD5 encryption.
     *
     * @param data The data.
     * @return the bytes of MD5 encryption
     */
    fun encryptMD5(data: ByteArray): ByteArray? {
        return hashTemplate(data, "MD5")
    }

    /**
     * Return the hex string of file's MD5 encryption.
     *
     * @param filePath The path of file.
     * @return the hex string of file's MD5 encryption
     */
    fun encryptMD5File2String(filePath: String): String {
        val file = if (isSpace(filePath)) null else File(filePath)
        return encryptMD5File2String(file)
    }

    /**
     * Return the bytes of file's MD5 encryption.
     *
     * @param filePath The path of file.
     * @return the bytes of file's MD5 encryption
     */
    fun encryptMD5File(filePath: String): ByteArray? {
        val file = if (isSpace(filePath)) null else File(filePath)
        return encryptMD5File(file)
    }

    /**
     * Return the hex string of file's MD5 encryption.
     *
     * @param file The file.
     * @return the hex string of file's MD5 encryption
     */
    fun encryptMD5File2String(file: File?): String {
        return bytes2HexString(
            encryptMD5File(
                file
            )
        )
    }

    /**
     * Return the bytes of file's MD5 encryption.
     *
     * @param file The file.
     * @return the bytes of file's MD5 encryption
     */
    fun encryptMD5File(file: File?): ByteArray? {
        if (file == null) {
            return null
        }
        var fis: FileInputStream? = null
        val digestInputStream: DigestInputStream
        try {
            fis = FileInputStream(file)
            var md = MessageDigest.getInstance("MD5")
            digestInputStream = DigestInputStream(fis, md)
            val buffer = ByteArray(256 * 1024)
            while (true) {
                if (digestInputStream.read(buffer) <= 0) {
                    break
                }
            }
            md = digestInputStream.messageDigest
            return md.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * Return the bytes of hash encryption.
     *
     * @param data      The data.
     * @param algorithm The name of hash encryption.
     * @return the bytes of hash encryption
     */
    private fun hashTemplate(data: ByteArray?, algorithm: String): ByteArray? {
        if (data == null || data.size <= 0) {
            return null
        }
        try {
            val md = MessageDigest.getInstance(algorithm)
            md.update(data)
            return md.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }

    }

    private fun bytes2HexString(bytes: ByteArray?): String {
        if (bytes == null) {
            return ""
        }
        val len = bytes.size
        if (len <= 0) {
            return ""
        }
        val ret = CharArray(len shl 1)
        var i = 0
        var j = 0
        while (i < len) {
            ret[j++] = HEX_DIGITS[bytes[i].toInt() shr 4 and 0x0f]
            ret[j++] = HEX_DIGITS[bytes[i].toInt() and 0x0f]
            i++
        }
        return String(ret)
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) {
            return true
        }
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}