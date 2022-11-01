package com.ardian.ecrlink.wifi

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ardian.ecrlink.R
import kotlinx.android.synthetic.main.fragment_wifi.*
import java.io.IOException

class WifiFragment : Fragment() {

    private lateinit var server : MyHttpd
    private var port = 8013

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var intent = requireActivity().intent
        var result : String = intent.getStringExtra("message").toString()


        try {
            server = MyHttpd(ipAddress(), port, this.requireContext())
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun initIPAddress() {
        var wm: WifiManager = requireActivity().applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        var ip: String = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        txt_ip.text = "server running at: $ip:$port"
        Log.i("TAG", "onCreate: $ip")
    }


    fun ipAddress(): String {
        var wm: WifiManager = requireActivity().applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        var ip: String = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)

        return ip
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_wifi.text = ""

        btn_start.setOnClickListener{
            try {
                server.start()
                initIPAddress()
                Toast.makeText(requireActivity(), "Server Start", Toast.LENGTH_LONG).show()
            }catch (e : IOException){
                e.printStackTrace()
            }
        }

        btn_stop.setOnClickListener{
            server.stop()
            txt_ip.text = ""
            Toast.makeText(requireActivity(), "Server Stop", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wifi, container, false)
    }
}