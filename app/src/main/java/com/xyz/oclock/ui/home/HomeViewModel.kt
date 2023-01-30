package com.xyz.oclock.ui.home

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.asBindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.asChat
import com.xyz.oclock.common.utils.LogoutHelper
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.*
import com.xyz.oclock.core.model.*
import com.xyz.oclock.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val tokenRepository: TokenRepository,
    private val chatRepository: ChatRepository,
    private val commonRepository: CommonRepository,
    private val logoutHelper: LogoutHelper,
    private val resourceProvider: ResourceProvider,
    private val deviceStateRepository: DeviceStateRepository
): BaseViewModel() {

    private var calendar = Calendar.getInstance()

    val chatFlow = MutableStateFlow<List<Chat>>(arrayListOf())

    @get:Bindable
    val chatList: List<Chat> by chatFlow.asBindingProperty()


    fun isChattingTime(): Boolean {
        return true
    }

    fun getNewToken() = viewModelScope.launch {
        val token = tokenRepository.getRefreshToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        commonRepository.getNewToken(
            refreshToken = token,
            onError = {}
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val pair = it.data as Pair<*, *>
                    val accessToken = pair.first as String
                    val refreshToken = pair.second as String
                    tokenRepository.setAccessToken(accessToken)
                    tokenRepository.setRefreshToken(refreshToken)
                }
                is CommonResponse.Fail ->  {
                    showToast(it.message)
                }
            }
        }
    }

    fun startMatching(
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
        onAlreadyMatched: () -> Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        chatRepository.startMatching(
            token = token,
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = {
                onFail(it?: resourceProvider.getString(R.string.unknown_error))
            },
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val roomId = it.data as Int
                    onSuccess()
                }
                is CommonResponse.Fail ->  {
                    when (it.code) {
                        401 -> {
                            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
                        }
                        404 -> {
                            onFail(it.message)
                        }
                        409 -> {
                            onAlreadyMatched()
                        }
                        else -> {
                            onFail(it.message)
                        }
                    }
                }
            }
        }
    }

    fun getMatchingUserInfo(
        onSuccess: (MatchingUser) -> Unit,
        onFail: () -> Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        chatRepository.getMatchingUserInfo(
            token = token,
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = {
                showToast(it?: resourceProvider.getString(R.string.unknown_error))
            },
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val matchingUser = it.data as MatchingUser
                    onSuccess(matchingUser)
                }
                is CommonResponse.Fail ->  {
                    when (it.code) {
                        401 -> {
                            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
                        }
                        404 -> {
                            showToast(it.message)
                        }
                        409 -> {
                            showToast(it.message)
                        }
                        else -> {
                            showToast(it.message)
                        }
                    }
                }
            }
        }
    }

    fun getServerTime() = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        chatRepository.getServerTime(
            token = token
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val c = it.data as Calendar
                    calendar = c
                }
                else -> {}
            }
        }
    }

    fun getMyInfo(
        onSuccess: (User) -> Unit,
        onGetDate: (Calendar) -> Unit,
        onFail: () -> Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        chatRepository.getMyInfo(
            token = token,
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = {
                showToast(it?: resourceProvider.getString(R.string.unknown_error))
            },
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    if (it.data is User) {
                        val user = it.data as User
                        onSuccess(user)
                    } else if (it.data is Calendar) {
                        val c = it.data as Calendar
                        calendar = c
                        onGetDate(c)
                    } else if (it.data == null) {

                    }
                }
                is CommonResponse.Fail ->  {
                    when (it.code) {
                        401 -> {
                            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
                        }
                        404 -> {
                            showToast(it.message)
                            onFail()
                        }
                        409 -> {
                            showToast(it.message)
                            onFail()
                        }
                        else -> {
                            showToast(it.message)
                            onFail()
                        }
                    }
                }
            }
        }
    }

    fun subscribeToSocketEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                chatRepository.openSocket().consumeEach {
                    if (it.type != SocketChatType.EXCEPTION.name) {
                        addNewChat(it.asChat())
                    } else {
                        onSocketError(it)
                    }
                }
            } catch (ex: java.lang.Exception) {
                println("Error occurred : ${ex}")
            }
        }
    }

    private fun onSocketError(msg: SocketChatResponse) {
        println("Error occurred : ${msg}")
    }

    fun sendMessage(msg: String) = viewModelScope.launch(Dispatchers.IO) {
        val token = tokenRepository.getSocketAccessToken()
        token?.let {
            chatRepository.sendMessage(token, msg, SocketChatType.MESSAGE)
        }
    }

    fun addNewChat(chat: Chat) = viewModelScope.launch {
        chatFlow.emit(ArrayList(chatFlow.value).plus(chat))
    }

    override fun onCleared() {
        chatRepository.closeSocket()
        super.onCleared()
    }
}