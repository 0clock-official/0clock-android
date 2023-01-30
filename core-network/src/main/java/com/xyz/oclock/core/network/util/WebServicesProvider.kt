package com.xyz.oclock.core.network.util

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.xyz.oclock.core.model.*
import kotlinx.coroutines.channels.Channel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit


class WebServicesProvider {

    private var webSocket: WebSocket? = null

    private val socketOkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(39, TimeUnit.SECONDS)
        .hostnameVerifier { _, _ -> true }
        .build()

    private var webSocketListener: OClockWebSocketListener? = null

    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun startSocket(): Channel<SocketChatResponse> =
        with(OClockWebSocketListener()) {
            startSocket(this)
            this@with.socketEventChannel
        }

    fun startSocket(webSocketListener: OClockWebSocketListener) {
        this.webSocketListener = webSocketListener
        webSocket = socketOkHttpClient.newWebSocket(
            Request.Builder().url("ws://api.0clock.xyz:34216/websocket/chatting").build(),
            webSocketListener
        )
//        webSocket = socketOkHttpClient.newWebSocket(
//            Request.Builder().url("ws://websocket-echo.com").build(),
//            webSocketListener
//        )
        socketOkHttpClient.dispatcher.executorService.shutdown()
    }

    fun sendMessage(token: String, message: String, type: SocketChatType) {
        val chat = SocketChatRequest(
            authorization = token,
            message = message,
            type = type.name
        )
        Log.d("send", moshi.adapter(SocketChatRequest::class.java).toJson(chat))
        webSocket?.send(moshi.adapter(SocketChatRequest::class.java).toJson(chat))

//        val chat = SocketChatResponse(
//            timestamp = "2023-01-23T16:45:36.840977",
//            message = message,
//            type = type.name
//        )
//        Log.d("send", moshi.adapter(SocketChatResponse::class.java).toJson(chat))
//        webSocket?.send(moshi.adapter(SocketChatResponse::class.java).toJson(chat))

    }

    fun stopSocket() {
        try {
            webSocket?.close(NORMAL_CLOSURE_STATUS, null)
            webSocket = null
            webSocketListener?.socketEventChannel?.close()
            webSocketListener = null
        } catch (ex: Exception) {
        }
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }

}