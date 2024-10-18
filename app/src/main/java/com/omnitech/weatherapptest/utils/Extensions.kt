package com.omnitech.weatherapptest.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


public fun Long.timeDifferenceInMins(to: Long): String {
    val differenceInMillis = to - this
    val differenceInMinutes = differenceInMillis / (1000 * 60) // Convert milliseconds to minutes
    return "$differenceInMinutes mins ago"
}


fun String.convertTo12HourFormat(): String {
    // Define the input format
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    // Parse the input string to a Date object
    val date: Date = inputFormat.parse(this) ?: return "Invalid date"

    // Define the output format for 12-hour clock with AM/PM
    val outputFormat = SimpleDateFormat("h a", Locale.getDefault())

    // Format the Date object to the desired output format
    return outputFormat.format(date)
}