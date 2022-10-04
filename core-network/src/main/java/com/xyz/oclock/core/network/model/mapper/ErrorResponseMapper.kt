package com.xyz.oclock.core.network.model.mapper

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.squareup.moshi.Moshi
import com.xyz.oclock.core.network.model.response.OClockErrorResponse

object ErrorResponseMapper : ApiErrorModelMapper<OClockErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): OClockErrorResponse {
        val moshi = Moshi.Builder()
            .build()
            .adapter(OClockErrorResponse::class.java)
            .fromJson(apiErrorResponse.message())

        return moshi?: OClockErrorResponse("ErrorResponseMapper return null", "ErrorResponseMapper return null")
    }
}
