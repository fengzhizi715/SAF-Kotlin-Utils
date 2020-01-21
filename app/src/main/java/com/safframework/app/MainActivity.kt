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
    }
}
