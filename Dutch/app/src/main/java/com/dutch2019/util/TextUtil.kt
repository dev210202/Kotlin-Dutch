package com.dutch2019.util

object markerIdValue {
    const val MIDDLE = "중간지점"
    const val RATIO = "비율변경지점"
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