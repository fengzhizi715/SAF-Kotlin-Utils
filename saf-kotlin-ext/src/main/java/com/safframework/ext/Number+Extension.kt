package com.safframework.ext

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @Author zhiqiang
 * @Date 2019-06-19
 * @Description
 */


/**
 * 默认保留小数点后10位
 */
const val DEFAULT_DECIMAL_NUMBER = 2
/**
 * 默认分隔符为千分位
 */
const val DEFAULT_SEPARATE_NUMBER = 3


/**
 * @param addComma 是否需要添加逗号，默认不加
 * @param modeFloor 是否使用去尾法，默认true 1.5->1   2.8->2
 * @param decimalNum 小数点后位数
 */
fun Number.formatNumber(
    addComma: Boolean = false,
    modeFloor: Boolean = true,
    decimalNum: Int? = DEFAULT_DECIMAL_NUMBER
): String {
    var decimal = decimalNum
    if (decimal == null) {
        decimal = DEFAULT_DECIMAL_NUMBER
    }
    val decimalFormat = DecimalFormat()
    decimalFormat.maximumFractionDigits = decimal
    decimalFormat.groupingSize = if (addComma) DEFAULT_SEPARATE_NUMBER else 0
    if (modeFloor) decimalFormat.roundingMode = RoundingMode.FLOOR
    return decimalFormat.format(this)
}

/**
 * @param addComma 是否需要添加逗号，默认不加
 * @param modeFloor 是否使用去尾法，默认true 1.5->1   2.8->2
 * @param decimalNum 小数点后位数
 */
fun String.formatNumber(
    addComma: Boolean = false, modeFloor: Boolean = true,
    decimalNum: Int? = DEFAULT_DECIMAL_NUMBER
): String =
    this.toBigDecimalWithNull().formatNumber(addComma, modeFloor, decimalNum)

fun String?.toBigDecimalWithNull(default: BigDecimal = BigDecimal.ZERO) = this.isNullOrBlank().not().then({
    try {
        this!!.toBigDecimal()
    } catch (e: NumberFormatException) {
        default
    }
}, default)

fun String?.toIntWithNull(default: Int = 0) = this.isNullOrBlank().not().then({
    try {
        this!!.toInt()
    } catch (e: NumberFormatException) {
        default
    }
}, default)

fun String?.toFloatWithNull(default: Float = 0F) = this.isNullOrBlank().not().then({
    try {
        this!!.toFloat()
    } catch (e: NumberFormatException) {
        default
    }
}, default)

fun String?.toDoubleWithNull(default: Double = 0.toDouble()) = this.isNullOrBlank().not().then({
    try {
        this!!.toDouble()
    } catch (e: NumberFormatException) {
        default
    }
}, default)