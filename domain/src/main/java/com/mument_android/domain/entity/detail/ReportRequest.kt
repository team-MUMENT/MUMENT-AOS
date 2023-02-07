package com.mument_android.domain.entity.detail

data class ReportRequest(
    val etcContent: String,
    val reportCategory: List<Int>
)