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

inline fun <T> Boolean.then(function: () -> T, default: () -> T) = if (this) function() else default()

infix inline fun <reified T> Boolean.then(function: () -> T) = if (this) function() else null

infix inline fun <reified T, reified Type> Type?.then(block: (Type) -> T) = if (this != null) block(this) else null