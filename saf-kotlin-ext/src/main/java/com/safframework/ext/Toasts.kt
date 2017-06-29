package com.safframework.ext

import android.content.Context
import android.widget.Toast

/**
 * Created by Tony Shen on 2017/6/29.
 */

fun Context.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}