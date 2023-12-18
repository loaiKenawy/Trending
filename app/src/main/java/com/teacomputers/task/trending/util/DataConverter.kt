package com.teacomputers.task.trending.util

import androidx.room.TypeConverter

class DataConverter {
    @TypeConverter
    fun intArrayToString(array: Array<Int>): String {
        var res = ""
        array.forEach {
            res += "$it,"
        }
        return res
    }

    @TypeConverter
    fun stringToIntArray(string: String): Array<Int> {
        return string.map { it.code }.toTypedArray()
    }
}