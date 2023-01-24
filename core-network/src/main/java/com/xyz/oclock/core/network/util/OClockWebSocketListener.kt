package com.xyz.oclock.core.network.util

import com.xyz.oclock.core.model.SocketAbortedException
import com.xyz.oclock.core.model.SocketUpdate
import com.xyz.oclock.core.network.util.WebServicesProvider.Companion.NORMAL_CLOSURE_STATUS
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class OClockWebSocketListener : WebSocketListener(){

    val socketEventChannel: Channel<SocketUpdate> = Channel(10)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("Hi")
        webSocket.send("Hi again")
        webSocket.send("Hi again again")
        webSocket.send("Hi again again again")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        GlobalScope.launch {
            socketEventChannel.send(SocketUpdate(text))
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        GlobalScope.launch {
            socketEventChannel.send(SocketUpdate(exception = SocketAbortedException()))
        }
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        socketEventChannel.close()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        GlobalScope.launch {
            socketEventChannel.send(SocketUpdate(exception = t))
        }
    }

}