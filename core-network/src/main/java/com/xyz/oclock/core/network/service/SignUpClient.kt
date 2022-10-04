package com.xyz.oclock.core.network.service

import android.graphics.Bitmap
import android.util.Base64
import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.request.EmailRequest
import com.xyz.oclock.core.network.model.request.EmailVerificationRequest
import com.xyz.oclock.core.network.model.request.NicknameRequest
import com.xyz.oclock.core.network.model.request.StdCardUploadRequest
import com.xyz.oclock.core.network.model.response.EmailVerificationResponse
import com.xyz.oclock.core.network.model.response.OClockResponse
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

class SignUpClient @Inject constructor(
    private val signUpService: SignUpService
) {

    suspend fun checkVerifyCode(email: String, code: String): ApiResponse<EmailVerificationResponse> {
        return signUpService.checkVerifyCode(EmailVerificationRequest(email, code))
    }

    suspend fun sendVerifyCodeToEmail(email: String): ApiResponse<OClockResponse> {
        return signUpService.sendVerifyCodeToEmail(EmailRequest(email))
    }

    suspend fun checkNicknameDuplication(nickname: String): ApiResponse<OClockResponse> {
        return signUpService.checkNicknameDuplication(NicknameRequest(nickname))
    }

    suspend fun uploadStdCard(email: String, stdCard: Bitmap): ApiResponse<OClockResponse> {
        val fileName = Calendar.getInstance().timeInMillis.toString()
        val byteArrayOutputStream = ByteArrayOutputStream()
        stdCard.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
        return signUpService.uploadStdCard(
            StdCardUploadRequest(
                email = email,
                fileName = fileName,
                idCard = imageString
            )
        )
    }
}