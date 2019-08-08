package com.safframework.utils

import android.net.Uri
import android.util.Log
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*

/**
 * @Author zhiqiang
 * @Date 2019/3/7
 * @Description
 * 1.scheme、host、path、encodedPath、query、encodedQuery、queryMap默认不为空
 * 2.自动去掉path最后一个"/"
 * 3.URL相关工具类, 如果需要判断URL类型等Api，请使用[android.webkit.URLUtil]
 */
class UriParse @JvmOverloads constructor(url: String?, charset: String? = null) {
    var charset: String? = "utf-8"
        private set

    /**
     * 获取Scheme
     */
    var scheme: String? = null
        private set
        get() {
            return field ?: ""
        }
    /**
     * 获取Host
     */
    var host: String? = null
        private set
        get() {
            return field ?: ""
        }
    /**
     * 获取Port
     */
    var port = -1
        private set
    /**
     * 获取路径
     */
    var path: String? = null
        private set
        get() {
            return field ?: ""
        }
    /**
     * 获取encode路径
     */
    val encodedPath: String
        get() = toUri().encodedPath ?: ""
    /**
     * 获取Query
     */
    val query: String
        get() = toUri().query ?: ""
    /**
     * 获取encoded Query
     */
    val encodedQuery: String
        get() = toUri().encodedQuery ?: ""
    /**
     * 获取Query的LinkedHashMap（一个key对应多个value时，只取第一个）
     */
    val queryMap: LinkedHashMap<String, String>
        get() {
            return queries.toMapWithSingleValue()
        }
    /**
     * 获取Query的LinkedHashEntity（一个key可能对应多个value）
     */
    var queries: LinkedHashEntity = LinkedHashEntity()
        private set

    var userInfo: String? = null
        private set

    init {
        if (charset?.isNotBlank() == true) {
            this.charset = charset
        }

        if (this.charset != null && !Charset.isSupported(this.charset)) {
            throw IllegalArgumentException("charset is not supported:" + this.charset!!)
        }

        if (isUri(url)) {

            //URI比Uri更敏感，但是Uri不支持IPV6.
            val newUri = URI.create(url)
            this.scheme = newUri.scheme
            this.host = newUri.host
            this.port = newUri.port
            this.userInfo = newUri.userInfo
            this.path = dropLastSeparator(newUri.path)
            this.queries = parseQueryString(newUri.query)
        }

    }

    /**
     * 添加路径
     *
     * @param pathAdded 路径
     * @param encode    是否encode
     * @return 返回当前类
     */
    fun addPath(pathAdded: String?, encode: Boolean): UriParse {
        var pathAddedInner = pathAdded
        if (pathAddedInner?.isNotBlank() == true) {
            pathAddedInner = pathAddedInner.trim()
            var pathTemp: String = pathAddedInner
            if (pathAddedInner.startsWith(SEPARATOR)) {
                pathTemp = pathAddedInner.substring(1)
            }

            if (pathAddedInner.endsWith(SEPARATOR)) {
                pathTemp = pathAddedInner.substring(0, pathAddedInner.length - 1)
            }

            //encode路径
            if (encode) {
                pathTemp = encode(pathTemp)
            }

            if (path != null && path!!.endsWith(SEPARATOR)) {
                path = path + pathTemp + SEPARATOR
            } else if (path != null) {
                path = path + SEPARATOR + pathTemp
            } else {
                path = pathTemp
            }
        }

        return this
    }

    /**
     * 添加Query，且清除之前已有相同query
     *
     * @param name   键值key
     * @param value  键值value
     * @param encode 是否encode
     * @return 返回当前类
     */
    fun addQuery(name: String?, value: String?, encode: Boolean): UriParse {
        if (name.isNullOrBlank() || value.isNullOrEmpty()) {
            return this
        }
        if (encode) {
            removeQuery(encode(name))
            queries.putString(encode(name), encode(value))
        } else {
            removeQuery(name)
            queries.putString(name, value)
        }
        return this
    }

    /**
     * 添加多个Query，且清除之前已有相同query
     *
     * @param input  键值对
     * @param encode 是否encode
     * @return 返回当前类
     */
    fun addQueries(input: HashMap<String, String>?, encode: Boolean): UriParse {
        if (input == null || input.size == 0) {
            return this
        }
        if (encode) {
            for (name in input.keys) {
                removeQuery(encode(name))
                val value = encode(input[name])
                queries.putString(encode(name), value)

            }
        } else {
            for (name in input.keys) {
                removeQuery(name)
                val value = input[name]
                queries.putString(name, value)
            }
        }

        return this
    }


    /**
     * 追加Query
     *
     * @param name   键值key
     * @param value  键值value
     * @param encode 是否encode
     * @return 返回当前类
     */
    fun appendQuery(name: String?, value: String?, encode: Boolean): UriParse {
        if (name.isNullOrBlank() || value.isNullOrEmpty()) {
            return this
        }
        if (encode) {
            queries.putString(encode(name), encode(value))
        } else {
            queries.putString(name, value)
        }
        return this
    }

    /**
     * 追加多个Query
     *
     * @param input  键值对
     * @param encode 是否encode
     * @return 返回当前类
     */
    fun appendQueries(input: HashMap<String, String>?, encode: Boolean): UriParse {
        if (input == null || input.size == 0) {
            return this
        }
        if (encode) {
            for (name in input.keys) {
                val value = encode(input[name])
                queries.putString(encode(name), value)

            }
        } else {
            for (name in input.keys) {
                val value = input[name]
                queries.putString(name, value)
            }
        }

        return this
    }


    /**
     * 根据name删除query
     *
     * @param name 删除掉的query名称
     * @return 返回当前类
     */
    fun removeQuery(name: String?): UriParse {
        if (name == null) {
            return this
        }
        if (queries.remove(name) == null) {
            queries.remove(encode(name))
        }
        return this
    }


    /**
     * 获得decode过的最新一个参数
     *
     * @param name 参数名
     * @return 返回decode过的参数value
     */
    fun getQueryParameter(name: String?): String {
        return toUri().getQueryParameter(name) ?: ""
    }


    /**
     * 获得decode过的参数
     *
     * @param name 参数名
     * @return 返回decode过的参数values
     */
    fun getQueryParameters(name: String?): List<String> {
        return toUri().getQueryParameters(name)
    }

    /**
     * 转为String
     *
     * @return 返回当前类的string, 绝不为null
     */
    override fun toString(): String {
        val sb = StringBuilder()
        if (this.scheme.isNullOrBlank().not()) {
            sb.append(this.scheme).append("://")
        }
        if (this.userInfo.isNullOrBlank().not()) {
            sb.append(this.userInfo).append("@")
        }
        if (this.host.isNullOrBlank().not()) {
            sb.append(host)
        }
        if (this.port != -1) {
            sb.append(":").append(this.port)
        }
        if (this.path.isNullOrBlank().not()) {
            sb.append(this.path)
        }
        val query = createQueryString()
        if (query.isNotBlank()) {
            sb.append("?").append(query.trim())
        }

        return sb.toString()
    }

    /**
     * 转为Uri
     *
     * @return Uri 返回当前类的Uri
     */
    fun toUri(): Uri {
        return Uri.parse(toString())
    }

    private fun createQueryString(): String {
        if (this.queries.isEmpty()) {
            return ""
        }
        val sb = StringBuilder()
        for (name in this.queries.keys) {
            val values = this.queries.getValues(name)
            for (item in values) {
                if (sb.isNotBlank()) {
                    sb.append("&")
                }
                sb.append(name).append("=").append(item)
            }

        }
        return sb.toString()
    }

    private fun encode(value: String?): String {
        if (value != null) {
            try {
                return if (charset == null) {
                    value
                } else {
                    URLEncoder.encode(value, charset)
                }
            } catch (e: UnsupportedEncodingException) {
                Log.e("Exception", e.message)
            }

        }
        return ""
    }

    private fun decode(value: String?): String? {
        if (value != null) {
            try {
                return if (charset == null) {
                    value
                } else {
                    URLDecoder.decode(value, charset)
                }
            } catch (e: UnsupportedEncodingException) {
                Log.e("Exception", e.message)
            }

        }
        return null
    }

    private fun parseQueryString(query: String?): LinkedHashEntity {
        val params = LinkedHashEntity()
        if (query.isNullOrBlank()) {
            return params
        }
        val receivedQueryPairs = query.split("&".toRegex()).dropWhile { it.isBlank() }
        for (queryItem in receivedQueryPairs) {
            val separator = queryItem.indexOf('=')
            if (separator != -1) {
                val name = queryItem.substring(0, separator)
                val value = queryItem.substring(separator + 1)
                params.putString(name, value)
            }
        }
        return params
    }

    private fun isUri(str: String?): Boolean {
        return str?.isNotBlank() == true && str.matches("\\w+://.+".toRegex())
    }

    /**
     *  自动去掉path最后一个"/"
     */
    private fun dropLastSeparator(path: String?): String? {
        var pathVar: String? = path ?: return null
        if (pathVar!!.endsWith(SEPARATOR)) {
            pathVar = pathVar.substring(0, pathVar.length - 1)
        }
        return pathVar
    }

    /**
     * 用来存储encode过的参数跟value
     */
    inner class LinkedHashEntity : LinkedHashMap<String, LinkedHashEntity.Entity>() {

        fun putString(key: String, value: String?): Entity? {
            val entity = get(key)
            if (entity != null) {
                entity.add(value)
                return super.put(key, entity)
            }
            return put(key, Entity(value))
        }

        /**
         * 获取所有的values
         * @param name String?
         * @return List<String>
         */
        fun getValues(name: String?): List<String> {
            val entity = get(name)
            return if (name != null && entity != null) {
                entity.getValues()
            } else {
                ArrayList()
            }
        }

        /**
         * LinkedHashMap<String, LinkedHashEntity.Entity> -> LinkedHashMap<String, String>
         * @return LinkedHashMap<String, String>
         */
        fun toMapWithSingleValue(): LinkedHashMap<String, String> {
            val result = linkedMapOf<String, String>()
            forEach { item ->
                result[item.key] = item.value.getValues()[0]
            }
            return result
        }

        inner class Entity internal constructor(value: String?) {
            /**
             * 数据不重复,且不为Null
             */
            private val values: LinkedHashSet<String>?

            init {
                this.values = LinkedHashSet()
                if (value != null) {
                    this.values.add(value)
                }
            }


            internal fun getValues(): List<String> {
                return if (values != null && values.iterator().hasNext()) {
                    ArrayList(values)
                } else {
                    ArrayList()
                }
            }

            internal fun add(value: String?) {
                //过滤掉encode后重复的数据
                if (value != null && !values!!.contains(encode(value)) && !values.contains(decode(value))) {
                    this.values.add(value)
                }
            }
        }

    }

    companion object {

        private val SEPARATOR = "/"

        fun from(url: String?, charset: String? = null): UriParse {
            return UriParse(url, charset)
        }
    }
}
