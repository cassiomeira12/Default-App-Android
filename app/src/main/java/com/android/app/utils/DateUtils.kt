package com.android.app.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val locale = Locale("pt", "BR")
    private val df: DateFormat = SimpleDateFormat("dd/MM/yyyy", locale) as DateFormat
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
        val hours = date.hours
        val minutes = date.minutes

        val hoursString: String
        val minutesString: String

        if (hours < 10) {
            hoursString = "0${hours}"
        } else {
            hoursString = hours.toString()
        }

        if (minutes < 10) {
            minutesString = "0${minutes}"
        } else {
            minutesString = minutes.toString()
        }

        return hoursString.plus(":").plus(minutesString)
    }

    fun getMinutosPassados(start: Date, end: Date): Int {
        if (start.time > end.time) {
            return 0
        }
        return (((end.time - start.time) / 1000) / 60).toInt()
    }

    fun getMinutosPassadosString(start: Date, end: Date): String {
        if (getMinutosPassados(start, end) <= 0) {
            return "0 min"
        }

        val hour = getMinutosPassados(start, end) / 60
        val minutes = getMinutosPassados(start, end) - (hour * 60)

        if (hour == 0) {
            return "$minutes min"
        }

        if (hour == 1 && minutes == 0) {
            return "$hour hora"
        } else if (hour >= 24 && hour < 48) {
            return "ontem"
        } else if (hour > 48) {
            return formatDateExtenso(start)
        } else if (hour > 1 && minutes == 0) {
            return "$hour horas"
        } else if (hour == 1 && minutes > 0) {
            return "$hour hora e $minutes min"
        } else if (hour > 1 && minutes > 0) {
            return "$hour horas e $minutes min"
        } else {
            return "error"
        }
    }

}