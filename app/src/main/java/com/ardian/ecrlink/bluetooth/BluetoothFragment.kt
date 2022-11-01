package com.ardian.ecrlink.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ardian.ecrlink.R
import kotlinx.android.synthetic.main.fragment_bluetooth.*
import java.io.IOException
import java.util.*

class BluetoothFragment : Fragment() {

    companion object{
        const val APP_NAME = "ECR Link"
        val MY_UUID : UUID = UUID.fromString("69cf5823-e045-4a1f-bf93-365e4bf9ebef")
        const val ENABLE_BLUETOOTH = 1
        const val REQUEST_ENABLE_DISCOVERY = 2
        const val REQUEST_ACCESS_COARSE_LOCATION = 3

        const val STATE_LISTENING = 1
        const val STATE_CONNECTING = 2
        const val STATE_CONNECTED = 3
        const val STATE_CONNECTION_FAILED = 4
        const val STATE_MESSAGE_RECEIVED = 5


    }

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initServer()
    }

    private fun initServer(){
        try {
            val serverClass = ServerClass()
            serverClass.start()
        } catch (e : IOException){
            e.printStackTrace()
        }
    }

    @SuppressLint("HandlerLeak")
    val handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                STATE_LISTENING -> txt_status.text = "Listening"

                STATE_CONNECTING -> txt_status.text = "Connecting"

                STATE_CONNECTED -> txt_status.text = "Connected"

                STATE_CONNECTION_FAILED -> txt_status.text = "Connection Failed"

                STATE_MESSAGE_RECEIVED ->{
                    val readBuff : ByteArray = msg.obj as ByteArray
                    var tempMsg = String(readBuff, 0, msg.arg1)
//                    txt_msg.text = tempMsg
//                    if (whatSide == SERVER){
//                        mMessageView.appendClientMessage(tempMsg)
//                    }else{
//                        mMessageView.appendServerMessage(tempMsg)
//                    }
                }
            }

        }
    }


    @SuppressLint("MissingPermission")
    private fun initBluetooth() {
        if (bluetoothAdapter.isDiscovering) return

        if (bluetoothAdapter.isEnabled){
            enableDiscovery()
        }else {
            // Bluetooth isn't enabled - prompt user to turn it on
            val intent1 = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent1, ENABLE_BLUETOOTH)
        }

    }

    @SuppressLint("MissingPermission")
    private fun enableDiscovery() {
        val intent2 = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        startActivityForResult(intent2, REQUEST_ENABLE_DISCOVERY)
    }



    @SuppressLint("MissingPermission")
    private inner class ServerClass : Thread(){
        private var serverSocket : BluetoothServerSocket? = null

        init {
            initBluetooth()
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID)
            } catch (e : IOException){
                e.printStackTrace()
            }
        }

        override fun run() {
            var socket : BluetoothSocket? = null

            while(socket == null){
                try {
                    var message = Message.obtain()
                    message.what = STATE_LISTENING
                    handler.sendMessage(message)

                    socket = serverSocket!!.accept()
                } catch (e : IOException){
                    e.printStackTrace()
                    var message = Message.obtain()
                    message.what = STATE_CONNECTION_FAILED
                    handler.sendMessage(message)
                }

                if (socket != null){
                    var message = Message.obtain()
                    message.what = STATE_CONNECTED
                    handler.sendMessage(message)

                }
            }
        }

        override fun destroy() {
            val message = Message.obtain()
            message.what = STATE_LISTENING
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bluetooth, container, false)
    }
}