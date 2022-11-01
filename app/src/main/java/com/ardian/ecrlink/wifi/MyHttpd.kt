package com.ardian.ecrlink.wifi

import android.content.Context


/**
 * Created by Ardian Iqbal Yusmartito on 27/10/22
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
class MyHttpd(host : String, port: Int, val context : Context) : NanoWebSocketServer(host, port) {
    private var debug = true

    override fun serve(session: IHTTPSession?): Response {
        return super.serve(session)
    }

    override fun openWebSocket(handshake: IHTTPSession?): WebSocket {
        return DebugWebSocket(handshake, debug, context)
    }

    override fun isWebsocketRequested(session: IHTTPSession?): Boolean {
        return super.isWebsocketRequested(session)
    }


    //    fun DebugWebSocketServer(port: Int, debug: Boolean) {
//        super(port)
//        this.debug = debug
//    }
//
//    override fun serve(session: IHTTPSession?): Response {
//        return super.serve(session)
//    }
//
//    override fun openWebSocket(handshake: IHTTPSession?): WebSocket {
//        return DebugWebSocketServer(handshak) as WebSocket
//    }
//
//    override fun isWebsocketRequested(session: IHTTPSession?): Boolean {
//        return super.isWebsocketRequested(session)
//    }
    //    private val webSocketFactory: WebSocketFactory? = null
//
//    constructor() : this(port = 8317)
//
//    companion object{
//        public val PORT : Int = 8317
//        public lateinit var responsePost : String
//
//        val HEADER_UPGRADE = "upgrade"
//        val HEADER_UPGRADE_VALUE = "websocket"
//        val HEADER_CONNECTION = "connection"
//        val HEADER_CONNECTION_VALUE = "Upgrade"
//        val HEADER_WEBSOCKET_VERSION = "sec-websocket-version"
//        val HEADER_WEBSOCKET_VERSION_VALUE = "13"
//        val HEADER_WEBSOCKET_KEY = "sec-websocket-key"
//        val HEADER_WEBSOCKET_ACCEPT = "sec-websocket-accept"
//        val HEADER_WEBSOCKET_PROTOCOL = "sec-websocket-protocol"
//
//
//    }
//
//    override fun serve(session: IHTTPSession?): Response {
//        val headers = session!!.headers
//        return if (isWebsocketRequested(session)) {
//            if (!HEADER_UPGRADE_VALUE.equals(
//                    headers[HEADER_UPGRADE],
//                    ignoreCase = true
//                )
//                || !isWebSocketConnectionHeader(session.headers)
//            ) {
//                return Response(
//                    Response.Status.BAD_REQUEST,
//                    MIME_PLAINTEXT,
//                    "Invalid Websocket handshake"
//                )
//            }
//            if (!HEADER_WEBSOCKET_VERSION_VALUE.equals(
//                    headers[HEADER_WEBSOCKET_VERSION],
//                    ignoreCase = true
//                )
//            ) {
//                return Response(
//                    Response.Status.BAD_REQUEST,
//                    MIME_PLAINTEXT,
//                    "Invalid Websocket-Version " + headers[HEADER_WEBSOCKET_VERSION]
//                )
//            }
//            if (!headers.containsKey(HEADER_WEBSOCKET_KEY)) {
//                return Response(
//                    Response.Status.BAD_REQUEST,
//                    MIME_PLAINTEXT,
//                    "Missing Websocket-Key"
//                )
//            }
//            val webSocket: WebSocket = openWebSocket(session)
//            try {
//                webSocket.getHandshakeResponse().addHeader(
//                    HEADER_WEBSOCKET_ACCEPT,
//                    makeAcceptKey(
//                        headers[HEADER_WEBSOCKET_KEY]
//                    )
//                )
//            } catch (e: NoSuchAlgorithmException) {
//                return Response(
//                    Response.Status.INTERNAL_ERROR,
//                    MIME_PLAINTEXT,
//                    "The SHA-1 Algorithm required for websockets is not available on the server."
//                )
//            }
//            if (headers.containsKey(HEADER_WEBSOCKET_PROTOCOL)) {
//                webSocket.getHandshakeResponse().addHeader(
//                    HEADER_WEBSOCKET_PROTOCOL,
//                    headers[HEADER_WEBSOCKET_PROTOCOL]!!
//                        .split(",".toRegex()).toTypedArray()[0]
//                )
//            }
//            webSocket.getHandshakeResponse()
//        } else {
//            super.serve(session)
//        }
//    }
//
//    fun openWebSocket(handshake: IHTTPSession?): WebSocket {
//        if (webSocketFactory == null) {
//            throw Error("You must either override this method or supply a WebSocketFactory in the cosntructor")
//        }
//        return webSocketFactory.openWebSocket(handshake)
//    }
//
//    protected fun isWebsocketRequested(session: IHTTPSession): Boolean {
//        val headers = session.headers
//        val upgrade = headers[HEADER_UPGRADE]
//        val isCorrectConnection: Boolean = isWebSocketConnectionHeader(headers)
//        val isUpgrade: Boolean = HEADER_UPGRADE_VALUE.equals(
//            upgrade,
//            ignoreCase = true
//        )
//        return isUpgrade && isCorrectConnection
//    }
//
//    private fun isWebSocketConnectionHeader(headers: Map<String, String>): Boolean {
//        val connection = headers[HEADER_CONNECTION]
//        return connection != null && connection.lowercase(Locale.getDefault()).contains(
//            HEADER_CONNECTION_VALUE.lowercase(
//                Locale.getDefault()
//            )
//        )
//    }


}