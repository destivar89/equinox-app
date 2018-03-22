package com.telefonica.bluetoothwifigiver

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.util.Log
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class BluetoothBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (BluetoothDevice.ACTION_FOUND == action) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, java.lang.Short.MIN_VALUE)

            Log.d("David", device.name +"  RSSI: " + rssi + "dBm")

            shareWifiInfo(context)

        }
    }

    @SuppressLint("WifiManagerLeak")
    private fun shareWifiInfo(context: Context){

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