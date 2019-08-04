package com.safframework.utils

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * @Author zhiqiang
 * @Date 2019/3/8
 * @Description UriUtils的测试类
 * 自动去掉path最后一个"/"
 * scheme {://} host /path {/?} query
 */
@RunWith(AndroidJUnit4::class)
class UriParseTest {

    private val EXC_NULL: String? = null//非法
    private val EXC_EMPTY = ""//非法
    private val EXC_ONLY_SCHEME = "http://"//非法
    private val EXC_ONLY_HOST = "host"//非法
    private val EXC_SCHEME_LESS_SLASH = "http:/host/path?key1=value1"//非法

    private val EXC_UNNECESSARY_SLASH_BEFORE_HOST = "http:///host/path?key1=value1"//虽然三个杠，但是默认合法
    private val EXC_UNNECESSARY_SLASH_BEFORE_PATH = "http://host//path?key1=value1"
    private val EXC_UNNECESSARY_SLASH_BEFORE_QUERY = "http://host/path/?key1=value1"
    private val EXC_UNNECESSARY_SLASH_ONLY_HOST = "http://host/?key1=value1"


    private val ONLY_HOST = "http://host"
    private val ONLY_PATH = "http://host/path"
    private val WITH_QUERY = "http://host/path?key1=value1"
    private val HTTPS_WITH_QUERY = "https://host/path?key1=value1"
    private val WITH_QUERY2 = "http://host/path?key1=value1&key2=value2"
    private val WITH_QUERY_HAN = "http://host/path?key1=刘"
    private val WITH_QUERY_HAN_KEY = "http://host?key1=刘&姓名=志强"
    private val OTHER_SCHEME = "other://host/path?key1=value1"
    private val WITH_QUERY3 = "http://host/path?key1=value1&key1=value2&key1=大强&key2=value2"

    private val WITH_PORT = "http://host:8080"
    private val IPV6 = "http://[2001:db8::dead:e1f]/"

    @Before
    fun setUp() {

    }

    @Test
    fun getScheme() {
        Assert.assertEquals(UriParse(EXC_NULL).scheme, "")
        Assert.assertEquals(UriParse(EXC_EMPTY).scheme, "")
        Assert.assertEquals(UriParse(EXC_ONLY_SCHEME).scheme, "")
        Assert.assertEquals(UriParse(EXC_ONLY_HOST).scheme, "")
        Assert.assertEquals(UriParse(EXC_SCHEME_LESS_SLASH).scheme, "")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_HOST).scheme, "http")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_PATH).scheme, "http")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_QUERY).scheme, "http")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_ONLY_HOST).scheme, "http")

        Assert.assertEquals(UriParse(ONLY_HOST).scheme, "http")
        Assert.assertEquals(UriParse(ONLY_PATH).scheme, "http")
        Assert.assertEquals(UriParse(WITH_QUERY).scheme, "http")
        Assert.assertEquals(UriParse(HTTPS_WITH_QUERY).scheme, "https")
        Assert.assertEquals(UriParse(WITH_QUERY2).scheme, "http")
        Assert.assertEquals(UriParse(WITH_QUERY_HAN).scheme, "http")
        Assert.assertEquals(UriParse(WITH_QUERY_HAN_KEY).scheme, "http")
        Assert.assertEquals(UriParse(OTHER_SCHEME).scheme, "other")
        Assert.assertEquals(UriParse(WITH_PORT).scheme, "http")
    }

    @Test
    fun getHost() {
        Assert.assertEquals(UriParse(EXC_NULL).host, "")
        Assert.assertEquals(UriParse(EXC_EMPTY).host, "")
        Assert.assertEquals(UriParse(EXC_ONLY_SCHEME).host, "")
        Assert.assertEquals(UriParse(EXC_ONLY_HOST).host, "")
        Assert.assertEquals(UriParse(EXC_SCHEME_LESS_SLASH).host, "")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_HOST).host, "")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_PATH).host, "host")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_QUERY).host, "host")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_ONLY_HOST).host, "host")

        Assert.assertEquals(UriParse(ONLY_HOST).host, "host")
        Assert.assertEquals(UriParse(ONLY_PATH).host, "host")
        Assert.assertEquals(UriParse(WITH_QUERY).host, "host")
        Assert.assertEquals(UriParse(HTTPS_WITH_QUERY).host, "host")
        Assert.assertEquals(UriParse(WITH_QUERY2).host, "host")
        Assert.assertEquals(UriParse(WITH_QUERY_HAN).host, "host")
        Assert.assertEquals(UriParse(WITH_QUERY_HAN_KEY).host, "host")
        Assert.assertEquals(UriParse(OTHER_SCHEME).host, "host")
        Assert.assertEquals(UriParse(WITH_PORT).host, "host")
        Assert.assertEquals(UriParse(IPV6).host, "[2001:db8::dead:e1f]")
    }

    @Test
    fun getPort() {
        Assert.assertEquals(UriParse(OTHER_SCHEME).port, -1)
        Assert.assertEquals(UriParse(WITH_PORT).port, 8080)
    }


    @Test
    fun getPath() {
        Assert.assertEquals(UriParse(EXC_NULL).path, "")
        Assert.assertEquals(UriParse(EXC_EMPTY).path, "")
        Assert.assertEquals(UriParse(EXC_ONLY_SCHEME).path, "")
        Assert.assertEquals(UriParse(EXC_ONLY_HOST).path, "")
        Assert.assertEquals(UriParse(EXC_SCHEME_LESS_SLASH).path, "")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_HOST).path, "/host/path")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_PATH).path, "//path")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_QUERY).path, "/path")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_ONLY_HOST).path, "")

        Assert.assertEquals(UriParse(ONLY_HOST).path, "")
        Assert.assertEquals(UriParse(ONLY_PATH).path, "/path")
        Assert.assertEquals(UriParse(WITH_QUERY).path, "/path")
        Assert.assertEquals(UriParse(HTTPS_WITH_QUERY).path, "/path")
        Assert.assertEquals(UriParse(WITH_QUERY2).path, "/path")
        Assert.assertEquals(UriParse(WITH_QUERY_HAN).path, "/path")
        Assert.assertEquals(UriParse(WITH_QUERY_HAN_KEY).path, "")
        Assert.assertEquals(UriParse(OTHER_SCHEME).path, "/path")
        Assert.assertEquals(UriParse(WITH_PORT).path, "")
    }

    @Test
    fun getUserInfo() {
    }

    @Test
    fun getQuery() {
        Assert.assertEquals(UriParse(EXC_NULL).query, "")
        Assert.assertEquals(UriParse(EXC_EMPTY).query, "")
        Assert.assertEquals(UriParse(EXC_ONLY_SCHEME).query, "")
        Assert.assertEquals(UriParse(EXC_ONLY_HOST).query, "")
        Assert.assertEquals(UriParse(EXC_SCHEME_LESS_SLASH).query, "")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_HOST).query, "key1=value1")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_PATH).query, "key1=value1")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_QUERY).query, "key1=value1")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_ONLY_HOST).query, "key1=value1")

        Assert.assertEquals(UriParse(ONLY_HOST).query, "")
        Assert.assertEquals(UriParse(ONLY_PATH).query, "")
        Assert.assertEquals(UriParse(WITH_QUERY).query, "key1=value1")
        Assert.assertEquals(UriParse(HTTPS_WITH_QUERY).query, "key1=value1")
        Assert.assertEquals(UriParse(WITH_QUERY2).query, "key1=value1&key2=value2")
        Assert.assertEquals(UriParse(WITH_QUERY_HAN).query, "key1=刘")
        Assert.assertEquals(UriParse(WITH_QUERY_HAN_KEY).query, "key1=刘&姓名=志强")
        Assert.assertEquals(UriParse(OTHER_SCHEME).query, "key1=value1")
        Assert.assertEquals(UriParse(WITH_PORT).query, "")
        Assert.assertEquals(UriParse(WITH_QUERY3).query, "key1=value1&key1=value2&key1=大强&key2=value2")

    }

    @Test
    fun getQueryMap() {
        Assert.assertEquals(UriParse(EXC_NULL).queryMap, linkedMapOf<String, String>())
        Assert.assertEquals(UriParse(EXC_EMPTY).queryMap, linkedMapOf<String, String>())
        Assert.assertEquals(UriParse(EXC_ONLY_SCHEME).queryMap, linkedMapOf<String, String>())
        Assert.assertEquals(UriParse(EXC_ONLY_HOST).queryMap, linkedMapOf<String, String>())
        Assert.assertEquals(UriParse(EXC_SCHEME_LESS_SLASH).queryMap, linkedMapOf<String, String>())
        val result = linkedMapOf<String, String>()
        result["key1"] = "value1"
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_HOST).queryMap, result)
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_PATH).queryMap, result)
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_QUERY).queryMap, result)
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_ONLY_HOST).queryMap, result)

        Assert.assertEquals(UriParse(ONLY_HOST).queryMap, linkedMapOf<String, String>())
        Assert.assertEquals(UriParse(ONLY_PATH).queryMap, linkedMapOf<String, String>())
        Assert.assertEquals(UriParse(WITH_QUERY).queryMap, result)
        Assert.assertEquals(UriParse(HTTPS_WITH_QUERY).queryMap, result)
        val result2 = linkedMapOf<String, String>()
        result2["key1"] = "value1"
        result2["key2"] = "value2"
        Assert.assertEquals(UriParse(WITH_QUERY2).queryMap, result2)
        val result3 = linkedMapOf<String, String>()
        result3["key1"] = "刘"
        Assert.assertEquals(UriParse(WITH_QUERY_HAN).queryMap, result3)
        val result4 = linkedMapOf<String, String>()
        result4["key1"] = "刘"
        result4["姓名"] = "志强"
        Assert.assertEquals(UriParse(WITH_QUERY_HAN_KEY).queryMap, result4)
        Assert.assertEquals(UriParse(OTHER_SCHEME).queryMap, result)
        Assert.assertEquals(UriParse(WITH_PORT).queryMap, linkedMapOf<String, String>())
        val result5 = linkedMapOf<String, String>()
        result5["key1"] = "value1"
        result5["key2"] = "value2"
        Assert.assertEquals(UriParse(WITH_QUERY3).queryMap, result5)

    }

    @Test
    fun addPath() {
        val utils = UriParse(ONLY_HOST)
        utils.addPath("test", false)
        Assert.assertEquals(utils.path, "/test")

        val utils1 = UriParse(OTHER_SCHEME)
        utils1.addPath("test", false)
        Assert.assertEquals(utils1.path, "/path/test")

    }

    @Test
    fun addParamAndQueryParameter() {
        val utils = UriParse(ONLY_HOST)
        utils.addQuery("key1", "value1", false)
        Assert.assertEquals(utils.getQueryParameter("key1"), "value1")
        Assert.assertEquals(utils.query, "key1=value1")

        val utils1 = UriParse(OTHER_SCHEME)
        utils1.appendQuery("key1", "大强", false)
        Assert.assertEquals(utils1.getQueryParameter("key1"), "value1")
        Assert.assertEquals(utils1.query, "key1=value1&key1=大强")
        utils1.addQuery("key1", "大强", false)
        Assert.assertEquals(utils1.query, "key1=大强")
        Assert.assertEquals(utils1.getQueryParameter("key1"), "大强")

        val utils2 = UriParse(OTHER_SCHEME)
        utils2.appendQuery("key1", "大强", true)
        Assert.assertEquals(utils2.encodedQuery, "key1=value1&key1=%E5%A4%A7%E5%BC%BA")
        Assert.assertEquals(utils2.getQueryParameter("key1"), "value1")
        Assert.assertEquals(utils2.query, "key1=value1&key1=大强")
        utils2.addQuery("key1", "大强", false)
        Assert.assertEquals(utils2.query, "key1=大强")
        Assert.assertEquals(utils2.getQueryParameter("key1"), "大强")

        val utils3 = UriParse(OTHER_SCHEME)
        utils3.addQuery("刘", "大强", true)
        Assert.assertEquals(utils3.encodedQuery, "key1=value1&%E5%88%98=%E5%A4%A7%E5%BC%BA")
        Assert.assertEquals(utils3.getQueryParameter("刘"), "大强")
        Assert.assertEquals(utils3.query, "key1=value1&刘=大强")

        val utils4 = UriParse.from(WITH_QUERY3)
        utils4.appendQuery("key1", "大强", true)
        val value = ArrayList<String>()
        value.add("value1")
        value.add("value2")
        value.add("大强")
        Assert.assertEquals(utils4.getQueryParameters("key1"), value)
        utils4.addQuery("key1", "大强", true)
        val value1 = ArrayList<String>()
        value1.add("大强")
        Assert.assertEquals(utils4.getQueryParameters("key1"), value1)
    }

    @Test
    fun addParamsAndQueryParameter() {
        val utils = UriParse(ONLY_HOST)
        val hashMap = HashMap<String, String>()
        hashMap["key1"] = "value1"
        hashMap["key2"] = "value2"
        utils.appendQueries(hashMap, false)
        Assert.assertEquals(utils.getQueryParameter("key1"), "value1")
        Assert.assertEquals(utils.query, "key1=value1&key2=value2")

        val utils1 = UriParse(OTHER_SCHEME)
        val hashMap1 = HashMap<String, String>()
        hashMap1["key1"] = "大强"
        hashMap1["key2"] = "占丰"
        utils1.appendQueries(hashMap1, false)
        Assert.assertEquals(utils1.getQueryParameter("key1"), "value1")
        Assert.assertEquals(utils1.query, "key1=value1&key1=大强&key2=占丰")
        utils1.addQueries(hashMap1, false)
        Assert.assertEquals(utils1.getQueryParameter("key1"), "大强")
        Assert.assertEquals(utils1.query, "key1=大强&key2=占丰")

        val utils2 = UriParse(OTHER_SCHEME)
        val hashMap2 = HashMap<String, String>()
        hashMap2["key1"] = "大强"
        hashMap2["key2"] = "占丰"
        utils2.appendQueries(hashMap2, true)
        Assert.assertEquals(utils2.getQueryParameter("key1"), "value1")
        Assert.assertEquals(utils2.getQueryParameter("key2"), "占丰")
        utils2.addQueries(hashMap2, true)
        Assert.assertEquals(utils2.getQueryParameter("key1"), "大强")
        Assert.assertEquals(utils2.getQueryParameter("key2"), "占丰")

        val utils3 = UriParse(OTHER_SCHEME)
        val hashMap3 = HashMap<String, String>()
        hashMap3["刘"] = "大强"
        hashMap3["大"] = "占丰"
        utils3.appendQueries(hashMap3, true)
        Assert.assertEquals(utils3.getQueryParameter("刘"), "大强")
        Assert.assertEquals(utils3.getQueryParameter("大"), "占丰")
        val hashMap4 = HashMap<String, String>()
        hashMap4["刘"] = "刘大强"
        hashMap4["大"] = "大占丰"
        utils3.addQueries(hashMap4, true)
        Assert.assertEquals(utils3.getQueryParameter("刘"), "刘大强")
        Assert.assertEquals(utils3.getQueryParameter("大"), "大占丰")
    }

    @Test
    fun removeParams() {
        val utils3 = UriParse(OTHER_SCHEME)
        utils3.removeQuery("key1")
        Assert.assertEquals(utils3.getQueryParameter("key1"), "")
    }


    @Test
    fun testToString() {
        Assert.assertEquals(UriParse(EXC_NULL).toString(), "")
        Assert.assertEquals(UriParse(EXC_EMPTY).toString(), "")
        Assert.assertEquals(UriParse(EXC_ONLY_SCHEME).toString(), "")
        Assert.assertEquals(UriParse(EXC_ONLY_HOST).toString(), "")
        Assert.assertEquals(UriParse(EXC_SCHEME_LESS_SLASH).toString(), "")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_HOST).toString(), EXC_UNNECESSARY_SLASH_BEFORE_HOST)
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_PATH).toString(), EXC_UNNECESSARY_SLASH_BEFORE_PATH)
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_BEFORE_QUERY).toString(), "http://host/path?key1=value1")
        Assert.assertEquals(UriParse(EXC_UNNECESSARY_SLASH_ONLY_HOST).toString(), "http://host?key1=value1")

        Assert.assertEquals(UriParse(ONLY_HOST).toString(), ONLY_HOST)
        Assert.assertEquals(UriParse(ONLY_PATH).toString(), ONLY_PATH)
        Assert.assertEquals(UriParse(WITH_QUERY).toString(), WITH_QUERY)
        Assert.assertEquals(UriParse(HTTPS_WITH_QUERY).toString(), HTTPS_WITH_QUERY)
        Assert.assertEquals(UriParse(WITH_QUERY2).toString(), WITH_QUERY2)
        Assert.assertEquals(UriParse(WITH_QUERY_HAN).toString(), WITH_QUERY_HAN)
        Assert.assertEquals(UriParse(WITH_QUERY_HAN_KEY).toString(), WITH_QUERY_HAN_KEY)
        Assert.assertEquals(UriParse(OTHER_SCHEME).toString(), OTHER_SCHEME)
        Assert.assertEquals(UriParse(WITH_PORT).toString(), WITH_PORT)
        Assert.assertEquals(
                UriParse(HTTPS_WITH_QUERY).addPath("刘", false).toString(),
                "https://host/path/刘?key1=value1"
        )
        Assert.assertEquals(
                UriParse(HTTPS_WITH_QUERY).addPath("刘", true).toString(),
                "https://host/path/%E5%88%98?key1=value1"
        )
        Assert.assertEquals(
                UriParse(HTTPS_WITH_QUERY).addQuery("key", "values", false).toString(),
                "$HTTPS_WITH_QUERY&key=values"
        )
        Assert.assertEquals(
                UriParse(HTTPS_WITH_QUERY).addQuery("刘", "values", false).toString(),
                "$HTTPS_WITH_QUERY&刘=values"
        )
        Assert.assertEquals(
                UriParse(HTTPS_WITH_QUERY).addQuery("刘", "values", true).toString(),
                "$HTTPS_WITH_QUERY&%E5%88%98=values"
        )
        Assert.assertEquals(
                UriParse(HTTPS_WITH_QUERY).addQuery("刘", "志强", true).toString(),
                "$HTTPS_WITH_QUERY&%E5%88%98=%E5%BF%97%E5%BC%BA"
        )

    }

    @Test
    fun others() {
        val map = hashMapOf<String, String>()
        map.forEach {
            //            LogUtil.logDebug("UriPasheTest", "it$it")
        }
    }
}