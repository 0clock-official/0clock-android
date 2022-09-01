package com.xyz.oclock.core.network.model

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message

object ErrorResponseMapper : ApiErrorModelMapper<OClockErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): OClockErrorResponse {
        return OClockErrorResponse(apiErrorResponse.statusCode.code, apiErrorResponse.message())
    }
}
