package com.dutch2019.util

object MarkerId {
    const val MIDDLE = "중간지점"
    const val RATIO = "비율변경지점"
}

object Category {
    const val TRANSPORT = "대중교통"
    const val FOOD = "음식점"
    const val CAFE = "카페"
    const val CULTURE = "문화시설"
}

fun filtNull(value: String): String = value.replace(" null", "")

fun filtZero(value: String): String = value.replace(" 0", "")


fun filtBlank(value: String): String = value.replace(" ", "")

fun filtDoubleBlank(value: String): String = value.replace("  ", " ")


fun getFacilitySearchCategory(input: String): String {

    when (input) {
        Category.TRANSPORT -> {
            return "지하철;버스;버스정류장;"
        }
        Category.CULTURE -> {
            return "주요시설물;문화시설;영화관;놀거리;"
        }
        Category.FOOD -> {
            return "식음료;한식;중식;양식;"
        }
        Category.CAFE -> {
            return "카페"
        }
    }
    return ""
}


fun convertTime(time: String): String {
    var result = ""
    var totalTime = time.toInt()
    val hour: Int
    val minute: Int
    if (totalTime >= 3600) {
        hour = totalTime / 3600
        totalTime %= 3600
        result = hour.toString() + "시간"
    }
    if (totalTime >= 60) {
        minute = totalTime / 60
        totalTime %= 60
        result += minute.toString() + "분"
    }
    if (totalTime > 0) {
        result += totalTime.toString() + "초"
    }
    return result
}
