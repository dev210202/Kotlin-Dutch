package com.dutch2019.model

data class ZipDetailData(
    val coordType: String,
    val addressFlag: String,
    val page: String,
    val count: String,
    val totalCount: String,
    val coordinate: ArrayList<Coordinate>,
)
