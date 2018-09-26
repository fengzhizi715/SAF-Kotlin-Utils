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

fun <T> Boolean.then(value: T?, default: T) = if (this) value else default

inline fun <T> Boolean.then(function: () -> T, default: T) = if (this) function() else default

inline fun <T> Boolean.then(function: () -> T, default: () -> T) = if (this) function() else default()

infix inline fun <reified T> Boolean.then(function: () -> T) = if (this) function() else null