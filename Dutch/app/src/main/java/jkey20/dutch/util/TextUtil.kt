package jkey20.dutch.util

fun filtNull(value: String): String {
    return value.replace(" null", "")
}
