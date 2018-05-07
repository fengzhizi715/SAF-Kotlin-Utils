package com.safframework.ext

/**
 *
 * @FileName:
 *          com.safframework.ext.`Object+Extension`.java
 * @author: Tony Shen
 * @date: 2018-05-08 01:13
 * @version V1.0 <描述当前版本功能>
 */
fun <T : Any> T.TAG() = this::class.simpleName