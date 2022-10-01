package com.xyz.oclock.core.model

sealed class CommonResponse {
    object Success : CommonResponse()
    class Fail(val message: String) : CommonResponse()
}