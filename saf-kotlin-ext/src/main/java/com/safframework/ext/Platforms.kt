package com.safframework.ext

import android.os.Build

/**
 *
 * @FileName:
 *          com.safframework.ext.Platforms.java
 * @author: Tony Shen
 * @date: 2018-04-23 15:03
 * @version V1.0 <描述当前版本功能>
 */

inline fun support(apiVersion:Int, block : () -> Unit) {

    if (Build.VERSION.SDK_INT>apiVersion) {

        block()
    }
}