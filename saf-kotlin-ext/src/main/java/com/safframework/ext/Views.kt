package com.safframework.ext

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Tony Shen on 2017/6/30.
 */

val View.isVisible: Boolean
    get() = visibility == View.VISIBLE
val View.isInvisible: Boolean
    get() = visibility == View.INVISIBLE
val View.isGone: Boolean
    get() = visibility == View.GONE

fun View.hideKeyboard() {
    clearFocus()
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    requestFocus()
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

fun <T : View> T.longClick(block: (T) -> Boolean) = setOnLongClickListener { block(it as T) }

fun View.toBitmap(): Bitmap?{
    clearFocus()
    isPressed = false
    val willNotCache = willNotCacheDrawing()
    setWillNotCacheDrawing(false)
    // Reset the drawing cache background color to fully transparent
    // for the duration of this operation
    val color = drawingCacheBackgroundColor
    drawingCacheBackgroundColor = 0
    if (color != 0) destroyDrawingCache()
    buildDrawingCache()
    val cacheBitmap = drawingCache
    if (cacheBitmap == null) {
        Log.e("Views", "failed to get bitmap from $this", RuntimeException())
        return null
    }
    val bitmap = Bitmap.createBitmap(cacheBitmap)
    // Restore the view
    destroyDrawingCache()
    setWillNotCacheDrawing(willNotCache)
    drawingCacheBackgroundColor = color
    return bitmap
}