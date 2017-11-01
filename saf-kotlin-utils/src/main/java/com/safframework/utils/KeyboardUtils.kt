package com.safframework.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Created by tony on 2017/11/1.
 */

fun closeKeyboard(activity: Activity) {
    val view = activity.window.peekDecorView()
    if (view != null) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}