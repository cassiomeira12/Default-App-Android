package com.android.app.utils

import android.util.Log
import com.google.firebase.firestore.model.value.IntegerValue
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val locale = Locale("pt", "BR")
    private val df: DateFormat = SimpleDateFormat("dd/MM/yyyy", locale)
    private val timeZone = TimeZone.getTimeZone("GMT-03:00")


    fun formatDate(date: Long): String {
        return formatDate(Date(date))
    }

    fun formatDate(date: Date): String {
        return df.format(date)
    }

    fun formatDateExtenso(date: Date): String {
        var value = df.format(date)
        val mouth = value.substring(3, 5)
        when (mouth) {
            "1" -> { value = df.format(date).substring(0, 2).plus(" de jan") }
            "2" -> { value = df.format(date).substring(0, 2).plus(" de fev") }
            "3" -> { value = df.format(date).substring(0, 2).plus(" de mar") }
            "4" -> { value = df.format(date).substring(0, 2).plus(" de abr") }
            "5" -> { value = df.format(date).substring(0, 2).plus(" de mai") }
            "6" -> { value = df.format(date).substring(0, 2).plus(" de jun") }
            "7" -> { value = df.format(date).substring(0, 2).plus(" de jul") }
            "8" -> { value = df.format(date).substring(0, 2).plus(" de ago") }
            "9" -> { value = df.format(date).substring(0, 2).plus(" de set") }
            "10" -> { value = df.format(date).substring(0, 2).plus(" de out") }
            "11" -> { value = df.format(date).substring(0, 2).plus(" de nov") }
            "12" -> { value = df.format(date).substring(0, 2).plus(" de dez") }
        }
        return value
    }

    fun getHourMinute(date: Date): String {
        return date.hours.toString().plus(":").plus(date.minutes.toString())
    }


}