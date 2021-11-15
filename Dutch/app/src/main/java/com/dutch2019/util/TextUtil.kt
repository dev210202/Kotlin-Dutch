package com.dutch2019.util

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
fun getSafetyText(safetyIndex: Int): String {
    var safetyText = ""
    if (safetyIndex >= 0 && safetyIndex <= 50) {
        safetyText = "코로나 안전지수 : " + safetyIndex + " - 양호"
    } else if (safetyIndex >= 51 && safetyIndex <= 70) {
        safetyText = "코로나 안전지수 : " + safetyIndex + " - 보통"
    } else if (safetyIndex >= 71 && safetyIndex <= 90) {
        safetyText = "코로나 안전지수 : " + safetyIndex + " - 주의"
    } else if (safetyIndex >= 91 && safetyIndex <= 100) {
        safetyText = "코로나 안전지수 : " + safetyIndex + " - 경계"
    }
    return safetyText
}