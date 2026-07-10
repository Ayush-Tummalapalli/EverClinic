
/**
 * This file contains utility functions for working with dates.
 */
package com.example.everclinic.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val DISPLAY_FORMAT = "dd MMM yyyy"

    fun toIsoString(date: Date): String {
        return SimpleDateFormat(ISO_FORMAT, Locale.getDefault()).format(date)
    }

    fun fromIsoString(isoString: String): Date? {
        return try {
            SimpleDateFormat(ISO_FORMAT, Locale.getDefault()).parse(isoString)
        } catch (e: Exception) {
            null
        }
    }

    fun toDisplayString(date: Date): String {
        return SimpleDateFormat(DISPLAY_FORMAT, Locale.getDefault()).format(date)
    }
}
