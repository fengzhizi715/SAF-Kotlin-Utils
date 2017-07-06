package com.safframework.ext

import android.app.Activity
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

fun Activity.showToast(string:String) {

    val context = this.application
    this.runOnUiThread { showToast(string) }
}

fun Activity.showToast(string:String,duration: Int) {

    val context = this.application
    this.runOnUiThread { showToast(string,duration) }
}

fun Activity.showToast(@StringRes id: Int ) {

    val context = this.application
    this.runOnUiThread { showToast(id) }
}

fun Activity.showToast(@StringRes id: Int,duration: Int ) {

    val context = this.application
    this.runOnUiThread { showToast(id,duration) }
}

fun Activity.showLongToast(string: String ) {

    val context = this.application
    this.runOnUiThread { showLongToast(string) }
}

fun Activity.showLongToast(@StringRes id: Int ) {

    val context = this.application
    this.runOnUiThread { showLongToast(id) }
}