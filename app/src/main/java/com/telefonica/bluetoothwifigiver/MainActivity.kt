package com.telefonica.bluetoothwifigiver

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var REQUEST_BLUETOOTH = 1

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var pairedDevices = bluetoothAdapter.bondedDevices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBluetooth()
        startBluetooth()
        startScanning()

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
            startActivityForResult(enableBT, REQUEST_BLUETOOTH)
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
