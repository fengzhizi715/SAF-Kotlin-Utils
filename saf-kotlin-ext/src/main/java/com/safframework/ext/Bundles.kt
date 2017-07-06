package com.safframework.ext

import android.os.Bundle
import android.os.Parcelable

/**
 * Created by Tony Shen on 2017/6/30.
 */

fun Bundle.put(params: Array<out Pair<String, Any>>): Bundle {

    params.forEach {
        val key = it.first
        val value = it.second
        when (value) {
            is Int -> putInt(key, value)
            is IntArray -> putIntArray(key, value)
            is Long -> putLong(key, value)
            is LongArray -> putLongArray(key, value)
            is CharSequence -> putCharSequence(key, value)
            is String -> putString(key, value)
            is Float -> putFloat(key, value)
            is FloatArray -> putFloatArray(key, value)
            is Double -> putDouble(key, value)
            is DoubleArray -> putDoubleArray(key, value)
            is Char -> putChar(key, value)
            is CharArray -> putCharArray(key, value)
            is Short -> putShort(key, value)
            is ShortArray -> putShortArray(key, value)
            is Boolean -> putBoolean(key, value)
            is BooleanArray -> putBooleanArray(key, value)

            is Parcelable -> putParcelable(key, value)
            is Bundle -> putAll(value)
            is Array<*> -> when {
                value.isArrayOf<Parcelable>() -> putParcelableArray(key, value as Array<out Parcelable>?)
            }

        }
    }
    return this
}