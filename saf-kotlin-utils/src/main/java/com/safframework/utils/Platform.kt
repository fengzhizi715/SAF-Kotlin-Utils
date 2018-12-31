package com.safframework.utils

import android.os.Build

/**
 * Created by Tony Shen on 2017/6/28.
 */

fun isFroyoOrHigher(): Boolean = versionOrHigher(8)

fun isGingerbreadOrHigher(): Boolean = versionOrHigher(9)

fun isHoneycombOrHigher(): Boolean = versionOrHigher(11)

fun isICSOrHigher(): Boolean = versionOrHigher(14)

fun isJellyBeanOrHigher(): Boolean = versionOrHigher(16)

fun isJellyBeanMR1OrHigher(): Boolean = versionOrHigher(17)

fun isJellyBeanMR2OrHigher(): Boolean = versionOrHigher(18)

fun isKitkatOrHigher(): Boolean = versionOrHigher(19)

fun isKitkatWatchOrHigher(): Boolean = versionOrHigher(20)

fun isLOrHigher(): Boolean = versionOrHigher(21)

fun isLMR1OrHigher(): Boolean = versionOrHigher(22)

fun isMOrHigher(): Boolean = versionOrHigher(23)

fun isNOrHigher(): Boolean = versionOrHigher(24)

fun isNMR1OrHigher(): Boolean = versionOrHigher(25)

fun isOOrHigher(): Boolean = versionOrHigher(26)

fun isOMR1OrHigher(): Boolean = versionOrHigher(27)

fun isPOrHigher(): Boolean = versionOrHigher(28)

fun support(apiVersion:Int, block : () -> Unit) {

    if (versionOrHigher(apiVersion)) {

        block()
    }
}

fun <T> support(apiVersion: Int, function: () -> T, default: () -> T): T = if (versionOrHigher(apiVersion)) function() else default()

private fun versionOrHigher(version: Int) = Build.VERSION.SDK_INT >= version

