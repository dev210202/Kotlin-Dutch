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