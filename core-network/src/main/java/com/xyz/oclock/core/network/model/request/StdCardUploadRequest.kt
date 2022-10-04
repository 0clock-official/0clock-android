package com.xyz.oclock.core.network.model.request

data class StdCardUploadRequest(
    val email: String,
    val fileName: String,
    val idCard: String
)
