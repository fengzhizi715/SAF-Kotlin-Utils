package com.safframework.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.log.L
import com.safframework.utils.getDeviceUsableMemory
import com.safframework.utils.getSDKVersion

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        L.i("可用内存：" + getDeviceUsableMemory(this))
        L.i("sdk版本：" + getSDKVersion())

//        var json = JSONObject()
//        json.put("ak","XEJ7F76J61LHEWRI3Q9A6UN9BM4CRT3X")
//        json.put("av","3.9.161114")
//        json.put("sv","3.9.161116")
//        json.put("fp","1070021141")
//        json.put("d","1acbd2c3e4cf6389")
//        json.put("os","0")
//        json.put("sr","1440x2560")
//        json.put("re","")
//
//        val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString())
//
//        http {
//
//            url = "https://stats.mlinks.cc/marketing/v2"
//
//            method = "post"
//
//            body = postBody
//
//            onSuccess {
//                string -> L.json(string)
//            }
//
//            onFail {
//                e -> L.i(e.message)
//            }
//        }

    }
}
