package com.yanqing.kotlindemo.utils

import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun formatTimeToString(time: Long): String {
            val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA)
            return simpleDateFormat.format(Date(time)).toString()
        }
    }
}