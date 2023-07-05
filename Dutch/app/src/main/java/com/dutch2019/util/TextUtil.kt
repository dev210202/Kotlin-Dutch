package com.dutch2019.util

object markerIdValue {
    const val MIDDLE = "중간지점"
    const val RATIO = "비율변경지점"
}

object category {
    const val TRANSPORT = "대중교통"
    const val FOOD = "음식점"
    const val CAFE = "카페"
    const val CULTURE = "문화시설"
}
fun filtNull(value: String): String {
    return value.replace(" null", "")
}
fun filtZero(value : String):String{
    return value.replace(" 0", "")
}

fun filtBlank(value : String):String{
    return value.replace(" ", "")
}
fun filtDoubleBlank(value : String):String{
    return value.replace("  ", " ")
}

fun getFacilitySearchCategory(input: String): String {

    when (input) {
        category.TRANSPORT -> {
            return "지하철;버스;버스정류장;"
        }
        category.CULTURE -> {
            return "주요시설물;문화시설;영화관;놀거리;"
        }
        category.FOOD -> {
            return "식음료;한식;중식;양식;"
        }
        category.CAFE -> {
            return "카페"
        }
    }
    return ""
}