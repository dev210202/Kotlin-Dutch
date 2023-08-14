package com.dutch2019.util

enum class MarkerId(val value: String) {
    MIDDLE("중간지점"), RATIO("비율변경지점")
}

enum class Category {
    TRANSPORT, FOOD, CAFE, CULTURE;

    fun getSearchKeyWords(): String = when (this) {
        TRANSPORT -> {
            "지하철;버스;버스정류장;"
        }
        FOOD -> {
            "식음료;한식;중식;양식;"
        }
        CAFE -> {
            "카페"
        }
        CULTURE -> {
            "주요시설물;문화시설;영화관;놀거리;"
        }
    }

}

fun filtNull(value: String): String = value.replace(" null", "")

fun filtZero(value: String): String = value.replace(" 0", "")


fun filtDoubleBlank(value: String): String = value.replace("  ", " ")

fun flitBracket(value: String): String = value.replace("[", " ").replace("]", "")

fun convertTime(time: String): String {
    var result = ""
    var totalTime = time.toInt()
    if (totalTime >= 3600) {
        var hour = totalTime / 3600
        totalTime %= 3600
        result = hour.toString() + "시간"
    }
    if (totalTime >= 60) {
        var minute = totalTime / 60
        totalTime %= 60
        result += minute.toString() + "분"
    }
    if (totalTime > 0) {
        result += totalTime.toString() + "초"
    }
    return result
}