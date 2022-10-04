package com.xyz.oclock.core.model

sealed class CommonResponse {
    class Success(val message: String? = null) : CommonResponse()
    class Fail(val message: String) : CommonResponse()
}