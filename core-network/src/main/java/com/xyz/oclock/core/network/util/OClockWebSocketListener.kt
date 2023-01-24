package com.xyz.oclock.core.network.util

import android.util.Log
import com.squareup.moshi.JsonAdapter
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

class OClockWebSocketListener : WebSocketListener(){

    val socketEventChannel: Channel<SocketChat> = Channel(10)

    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    override fun onOpen(webSocket: WebSocket, response: Response) {
//        webSocket.send("Hi")
//        webSocket.send("Hi again")
//        webSocket.send("Hi again again")
//        webSocket.send("Hi again again again")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        GlobalScope.launch {
            moshi.adapter(SocketChat::class.java).fromJson(text)?.let {
                socketEventChannel.send(it)
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        GlobalScope.launch {
            socketEventChannel.send(SocketChat(type = SocketChatType.EXEPTION.name))
        }
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        socketEventChannel.close()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        GlobalScope.launch {
            socketEventChannel.send(SocketChat(type = SocketChatType.EXEPTION.name))
        }
    }

}