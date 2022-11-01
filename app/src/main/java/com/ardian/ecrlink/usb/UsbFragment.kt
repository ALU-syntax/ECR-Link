package com.ardian.ecrlink.usb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ardian.ecrlink.R
import com.facebook.stetho.websocket.SimpleEndpoint
import com.facebook.stetho.websocket.SimpleSession

class UsbFragment : Fragment() {

    private val ADDRESS = "msg-test"
    private lateinit var mMessageEditText : EditText
    private lateinit var mServer: LocalWebSocketServer
    private lateinit var mEndpoint : MessageEndpoint
    private lateinit var mButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mEndpoint = MessageEndpoint()
        startServer()
    }

    private fun startServer() {
        mServer = LocalWebSocketServer.createAndStart(requireActivity(),ADDRESS, mEndpoint)
//        mMessageView.appendSystemMessage(getString(R.string.msg_server_started, ADDRESS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_usb, container, false)
    }

    override fun onDestroy() {
        mServer.stop()
        super.onDestroy()
    }

    inner class MessageEndpoint : SimpleEndpoint {

        private var sessions : ArrayList<SimpleSession> = ArrayList()

        fun broadcast(message : String){
            for (session: SimpleSession in sessions){
                session.sendText(message)
            }
            requireActivity().runOnUiThread {
//                mMessageView.appendServerMessage(message)
            }
        }
        override fun onOpen(session: SimpleSession?) {
            sessions.add(session!!)
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), "Device Connected", Toast.LENGTH_LONG).show()
//                mMessageView.appendSystemMessage(getString(R.string.msg_client_connected))
            }

        }

        override fun onMessage(session: SimpleSession?, message: String?) {
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), "Data Masuk", Toast.LENGTH_LONG).show()
//                mMessageView.appendClientMessage(message)
            }
        }


        override fun onMessage(session: SimpleSession?, message: ByteArray?, messageLen: Int) {
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), "Ignored a binary message from remote.", Toast.LENGTH_LONG).show()
//                mMessageView.appendSystemMessage(getString(R.string.msg_client_ignored))
            }
        }

        override fun onClose(
            session: SimpleSession?,
            closeReasonCode: Int,
            closeReasonPhrase: String?
        ) {
            sessions.remove(session)
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), "Device Disconnected", Toast.LENGTH_LONG).show()
//                mMessageView.appendSystemMessage(getString(R.string.msg_client_disconnected))
            }
        }

        override fun onError(session: SimpleSession?, t: Throwable?) {
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), t!!.message.toString(), Toast.LENGTH_LONG).show()
//                mMessageView.appendSystemMessage(getString(R.string.msg_client_error))
            }
        }
    }
}