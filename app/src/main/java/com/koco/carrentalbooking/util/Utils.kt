package com.koco.carrentalbooking.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

object Utils {
    fun formatDateToHumanReadableForm(dateInMillis: Long): LocalDate {
        return Instant.ofEpochMilli(dateInMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}