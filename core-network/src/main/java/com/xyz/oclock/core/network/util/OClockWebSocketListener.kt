package com.xyz.oclock.core.network.util

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.xyz.oclock.core.model.*
import com.xyz.oclock.core.network.util.WebServicesProvider.Companion.NORMAL_CLOSURE_STATUS
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber

class OClockWebSocketListener : WebSocketListener(){
    private val tag: String = this.javaClass.simpleName
    val socketEventChannel: Channel<SocketChatResponse> = Channel(10)

    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(tag, "onOpen")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        GlobalScope.launch {
            moshi.adapter(SocketChatResponse::class.java).fromJson(text)?.let {
                socketEventChannel.send(it)
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        GlobalScope.launch {
            Log.d(tag, "onClosing")
            if (socketEventChannel.isClosedForSend) {
                webSocket.close(NORMAL_CLOSURE_STATUS, null)
                return@launch
            }
            socketEventChannel.send(SocketChatResponse(
                type = SocketChatType.CLOSING.name,
                message = "연결이 끊어졌습니다. 네트워크 상태를 확인해주세요."
            ))
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            socketEventChannel.close()
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        GlobalScope.launch {
            Log.d(tag, "onFailure")
            if (socketEventChannel.isClosedForSend) {
                return@launch
            }
            socketEventChannel.send(SocketChatResponse(type = SocketChatType.EXCEPTION.name))
        }
    }

}