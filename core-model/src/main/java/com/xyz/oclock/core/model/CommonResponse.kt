package com.xyz.oclock.core.model

sealed class CommonResponse {
    class Success<T>(val message: String? = null, val data: T? = null) : CommonResponse()
    class Fail(val message: String, val code: Int) : CommonResponse()
}