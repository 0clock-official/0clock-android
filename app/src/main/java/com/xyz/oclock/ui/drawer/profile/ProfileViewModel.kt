package com.xyz.oclock.ui.drawer.profile

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.LogoutHelper
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.ChatRepository
import com.xyz.oclock.core.data.repository.CommonRepository
import com.xyz.oclock.core.data.repository.TokenRepository
import com.xyz.oclock.core.data.repository.UserInfoRepository
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.Sex
import com.xyz.oclock.core.model.User
import com.xyz.oclock.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val commonRepository: CommonRepository,
    private val chatRepository: ChatRepository,
    private val tokenRepository: TokenRepository,
    private val logoutHelper: LogoutHelper
): BaseViewModel() {

    @get: Bindable
    var inputNickname: String = ""
        set(value) {
            field = value
            checkNicknameFormat(value)
            notifyPropertyChanged(::nicknameFormatError)
        }

    @get: Bindable
    var nicknameFormatError by bindingProperty("")

    @get: Bindable
    var selectedPartnerSex: Sex? by bindingProperty(null)

    @get: Bindable
    var selectedTime: ChattingTime? by bindingProperty(null)

    @get: Bindable
    var timeArray by bindingProperty(ChattingTime.values().sortedBy{ it.index }.map { it.desc }.toTypedArray())

    @get: Bindable
    var partnerSexArray by bindingProperty(Sex.values().sortedBy{ it.index }.map{ it.desc }.toTypedArray())


    private fun checkNicknameFormat(nickname: String) {
        var nicknamePattern = "^[A-Za-z가-힣]{1,10}\$"
        var pattern = Pattern.compile(nicknamePattern)
        if (pattern.matcher(nickname).matches()) {
            nicknameFormatError = ""
        } else {
            if (nickname.isEmpty() || nickname.length > 10) {
                nicknameFormatError = resourceProvider.getString(R.string.error_nickname_format1)
                return
            }
            nicknamePattern = "([0-9])"
            pattern = Pattern.compile(nicknamePattern)
            if (pattern.matcher(nickname).find()) {
                nicknameFormatError = resourceProvider.getString(R.string.error_nickname_format2)
            } else {
                nicknameFormatError = resourceProvider.getString(R.string.error_nickname_format3)
            }
        }
    }

    fun setPreferredTime(position: Int) {
        selectedTime = ChattingTime.values().filter { it.desc == timeArray[position] }[0]
    }

    fun setPartnerSex(position: Int) {
        selectedPartnerSex = Sex.values().filter { it.desc == partnerSexArray[position] }[0]
    }

    fun editProfile() = viewModelScope.launch {
        if (nicknameFormatError.isNotEmpty()) {
            showToast("잘못된 닉네임 입니다")
            return@launch
        } else if (selectedTime == null) {
            showToast("채팅시간을 선택해주세요")
            return@launch
        } else if (selectedPartnerSex == null) {
            showToast("매칭성별을 선택해주세요")
            return@launch
        } else if (tokenRepository.getAccessToken() == null ) {
            return@launch
        }
        commonRepository.editProfile(
            token = tokenRepository.getAccessToken()!!,
            nickname = inputNickname,
            chattingTime = selectedTime!!,
            matchingSex = selectedPartnerSex!!,
            onError = {
                showToast(it?: resourceProvider.getString(R.string.unknown_error))
            },
            onStart = {

            },
            onComplete = {

            }
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    showToast("정보를 수정했습니다.")
                }
                is CommonResponse.Fail ->  {
                    showToast(resourceProvider.getString(R.string.unknown_error))
                }
            }
        }
    }

}