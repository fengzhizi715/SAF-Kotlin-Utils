package com.safframework.ext

/**
 *
 * @FileName:
 *          com.safframework.ext.Booleans.java
 * @author: Tony Shen
 * @date: 2018-04-19 00:35
 * @version V1.0 <描述当前版本功能>
 */
infix fun <T> Boolean.then(value: T?) = if (this) value else null