package com.safframework.ext

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * Created by Tony Shen on 2017/6/29.
 */

fun Context.showToast(string: String) = showToast(string,Toast.LENGTH_SHORT)

fun Context.showToast(string: String,duration: Int) = Toast.makeText(this, string, duration).show()

fun Context.showToast(@StringRes id: Int ) = showToast(id,Toast.LENGTH_SHORT)

fun Context.showToast(@StringRes id: Int,duration: Int) = showToast(getString(id),duration)

fun Context.showLongToast(string: String) = Toast.makeText(this, string, Toast.LENGTH_LONG).show()

fun Context.showLongToast(@StringRes id: Int) = Toast.makeText(this, getString(id), Toast.LENGTH_LONG).show()