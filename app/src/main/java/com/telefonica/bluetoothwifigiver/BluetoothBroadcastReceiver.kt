package com.telefonica.bluetoothwifigiver

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.util.Log
import com.telefonica.bluetoothwifigiver.wifi.WifiManagerImpl
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class BluetoothBroadcastReceiver : BroadcastReceiver() {

    val RSSI_THRESHOLD: Int = -75
    val MAC_MH: String = "B8:AE:ED:CD:4D:96"

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.d("DEBUG", "Action received")
        if (BluetoothDevice.ACTION_FOUND == action) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, java.lang.Short.MIN_VALUE)
            val mac = device.address
            Log.d("DEBUG", device.name + "  RSSI: " + rssi + " dBm " + " Address " + mac)
            if ((rssi > RSSI_THRESHOLD) && (mac.equals(MAC_MH))) {
                val wifiInfo = getWifiInfo(device.address)
                connectToWifi(wifiInfo, context)
            }
        }
    }

    private fun getWifiInfo(mac: String): WifiDataModel {
        return NetworkModule.getInstance().getWifiFromApi(mac)
    }

    private fun connectToWifi(wifiDataModel: WifiDataModel, context: Context) {
        Log.d("DEBUG", "Connecting to wifi...")
        var wifiConnection = WifiManagerImpl(context, wifiDataModel)
        wifiConnection.connectToAccesPoint(object : com.telefonica.bluetoothwifigiver.wifi.WifiManager.Callback {
            override fun onConnectionSuccess() {
                Log.d("DEBUG", "Connected!")
            }

            override fun onWifiNotConfigured() {
                Log.d("DEBUG", "onWifiNotConfigured!")
            }

            override fun onWifiNotConnected() {
                Log.d("DEBUG", "onWifiNotConnected!")
            }

            override fun onWifiDisabled() {
                Log.d("DEBUG", "onWifiDisabled!")
            }

        })
    }

    @SuppressLint("WifiManagerLeak")
    private fun shareWifiInfo(context: Context) {

        val wifiManager: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var method: Method? = null

        try {
            method = wifiManager.javaClass.getMethod("getPrivilegedConfiguredNetworks")
        } catch (e: SecurityException) {
        } catch (e: NoSuchMethodException) {
        }

        var configurations: List<WifiConfiguration>? = null

        try {
            configurations = method?.invoke(wifiManager) as List<WifiConfiguration>?
        } catch (e: IllegalArgumentException) {
        } catch (e: IllegalAccessException) {
        } catch (e: InvocationTargetException) {
        }

        Log.d("Yo", "pass: " + configurations?.get(0)?.preSharedKey)
        Log.d("Yo", "ssid: " + configurations?.get(0)?.SSID)

    }

}