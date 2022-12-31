package com.xyz.oclock.core.network.service

import android.graphics.Bitmap
import android.util.Base64
import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.model.SignUpForm
import com.xyz.oclock.core.network.model.request.*
import com.xyz.oclock.core.network.model.response.OClockResponse
import com.xyz.oclock.core.network.model.response.SignUpResponse
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

class SignUpClient @Inject constructor(
    private val signUpService: SignUpService
) {

    suspend fun checkVerifyCode(email: String, code: String): ApiResponse<OClockResponse<Any>> {
        return signUpService.checkVerifyCode(EmailVerificationRequest(email, code))
    }

    suspend fun sendVerifyCodeToEmail(email: String): ApiResponse<OClockResponse<Any>> {
        return signUpService.sendVerifyCodeToEmail(EmailRequest(email))
    }

    suspend fun checkNicknameDuplication(nickname: String): ApiResponse<OClockResponse<Any>> {
        return signUpService.checkNicknameDuplication(NicknameRequest(nickname))
    }

    suspend fun signUp(signUpForm: SignUpForm): ApiResponse<OClockResponse<SignUpResponse>> {
        val signUpRequest = SignUpRequest(
            email = signUpForm.email,
            password = signUpForm.password,
            nickname = signUpForm.nickname,
            major = signUpForm.major,
            chattingTime = signUpForm.chattingTime,
            matchingSex = signUpForm.matchingSex,
            memberSex = signUpForm.memberSex,
            fcmToken = signUpForm.fcmToken
        )
        return signUpService.signUp(signUpRequest)
    }

    suspend fun uploadStdCard(email: String, stdCard: Bitmap, accessToken: String): ApiResponse<OClockResponse<Any>> {
        val fileName = Calendar.getInstance().timeInMillis.toString()
        val byteArrayOutputStream = ByteArrayOutputStream()
        stdCard.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val image = byteArrayOutputStream.toByteArray()
        val idCard = "data:image/jpeg;base64,"+Base64.encodeToString(image, 0)
        return signUpService.uploadStdCard(
            token = accessToken,
            StdCardUploadRequest(
                email = email,
                fileName = fileName,
                idCard = idCard.replace("\n", "")
            )
        )
    }

    suspend fun checkStudentCardVerified(accessToken: String): ApiResponse<OClockResponse<Boolean>> {
        return signUpService.checkStudentCardVerified(accessToken)
    }
}