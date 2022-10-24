package com.ducdiep.bookmarket.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.clearTime(): Calendar {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

fun Calendar.parseToString(format: String): String {
    val sdf = SimpleDateFormat(format)
    return sdf.format(this.time)
}