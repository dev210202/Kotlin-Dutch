package com.dutch2019.util

inline fun <T> Collection<T>?.isNotNull() : Boolean{
    return this != null
}

