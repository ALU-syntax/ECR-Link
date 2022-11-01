package com.ardian.ecrlink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ardian.ecrlink.bluetooth.BluetoothFragment
import com.ardian.ecrlink.usb.UsbFragment
import com.ardian.ecrlink.wifi.WifiFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var textView: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var wifiFragment = WifiFragment()
        var bluetoothFragment = BluetoothFragment()
        var usbFragment = UsbFragment()

        btn_radio_wifi.setOnClickListener{
            var selectedId = radio_group.checkedRadioButtonId
            var radioButton = findViewById<RadioButton>(selectedId)
            loadFragment(wifiFragment)
            Toast.makeText(this, "Wifi Services Selected", Toast.LENGTH_LONG).show()
        }

        btn_radio_usb.setOnClickListener{
            var selectedId = radio_group.checkedRadioButtonId
            var radioButton = findViewById<RadioButton>(selectedId)
            loadFragment(usbFragment)
            Toast.makeText(this, "Usb Services Selected", Toast.LENGTH_LONG).show()
        }

        btn_radio_bluetooth.setOnClickListener{
            var selectedId = radio_group.checkedRadioButtonId
            var radioButton = findViewById<RadioButton>(selectedId)
            loadFragment(bluetoothFragment)
            Toast.makeText(this, "Bluetooth Services Selected", Toast.LENGTH_LONG).show()
        }
    }

    fun loadFragment(fragment : Fragment){
        // create a FragmentManager
        var fm : FragmentManager = supportFragmentManager

        // create a FragmentTransaction to begin the transaction and replace the Fragment
        var fragmentTransaction : FragmentTransaction = fm.beginTransaction()

        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}