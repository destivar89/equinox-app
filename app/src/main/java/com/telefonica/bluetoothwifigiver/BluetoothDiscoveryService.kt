package com.telefonica.bluetoothwifigiver

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.IBinder
import android.support.v7.app.AlertDialog
import android.util.Log


class BluetoothDiscoveryService: Service() {
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("TAG", "Received start")

        checkBluetooth()
        startBluetooth()
        startScanning()
        return Service.START_STICKY
    }

    private fun checkBluetooth() {

        // Phone does not support Bluetooth so let the user know and exit.
        if (bluetoothAdapter == null) {
            AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", { _, _ -> System.exit(0) })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
        }

    }

    private fun startBluetooth() {

        if (!bluetoothAdapter.isEnabled) {
            val enableBT = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            applicationContext.startActivity(enableBT)
        }

    }

    private fun startScanning() {

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(BluetoothBroadcastReceiver(), filter)
        val handler = Handler()
        val delay = 1000L //milliseconds
        handler.postDelayed(object : Runnable {
            override fun run() {
                bluetoothAdapter.startDiscovery()
                handler.postDelayed(this, delay)
            }
        }, delay)

    }

}