package com.safframework.ext

import android.content.Context
import android.support.annotation.StringRes

/**
 * Created by Tony Shen on 2017/6/30.
 */

fun Context.string(@StringRes id: Int): String = getString(id)