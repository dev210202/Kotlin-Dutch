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