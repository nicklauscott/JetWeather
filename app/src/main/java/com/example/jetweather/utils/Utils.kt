package com.example.jetweather.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}

fun formatToDayOfWeek(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE", Locale.getDefault()) // Use "EEE" to format to the day of the week
    val date = Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatToHour(timestamp: Int): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault()) // Use "HH:mm" to format to the hour and minute
    val date = Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}