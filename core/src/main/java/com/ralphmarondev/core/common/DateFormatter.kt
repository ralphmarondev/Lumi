package com.ralphmarondev.core.common

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {

    @SuppressLint("ConstantLocale")
    private val displayFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault())

    fun format(isoDate: String?): String {
        if (isoDate.isNullOrBlank()) {
            return ""
        }
        return try {
            val date = LocalDate.parse(isoDate)
            date.format(displayFormatter)
        } catch (e: Exception) {
            isoDate
        }
    }
}