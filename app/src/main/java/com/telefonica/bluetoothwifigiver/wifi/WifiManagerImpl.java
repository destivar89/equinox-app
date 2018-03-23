package com.telefonica.bluetoothwifigiver.wifi;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.text.TextUtils;
import android.util.Log;


import com.telefonica.bluetoothwifigiver.WifiDataModel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

public class WifiManagerImpl implements WifiManager {

    public final static int TIMEOUT_SECONDS = 60;

    private WifiDataModel wifiConfig;
    private Context context;
    private android.net.wifi.WifiManager wifiManager;
    private ConnectivityManager connectivityManager;
    private WifiManager.Callback callback;
    private ScheduledFuture taskHandler;
    private final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
    private ConnectionReceiver connectionReceiver;

    public WifiManagerImpl(Context context, WifiDataModel config) {
        this.wifiConfig = config;
        this.context = context;
    }

    @Override
    public void connectToAccesPoint(Callback callback) {
        this.callback = callback;
        if (wifiManager == null)
            wifiManager = (android.net.wifi.WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        if (connectivityManager == null)
            connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            connectToSpecificNetwork();
        } else {
            callback.onWifiDisabled();
        }
    }

    private void connectToSpecificNetwork() {
        if (wifiConfig == null || TextUtils.isEmpty(wifiConfig.getSsid()) || TextUtils.isEmpty(wifiConfig.getPassword())) {
            if (callback != null) {
                callback.onWifiNotConfigured();
            }
        } else {
            if (isConnectedToSpecificNetwork(wifiConfig.getSsid())) {
                if (callback != null) {
                    callback.onConnectionSuccess();
                }
            } else {
                taskHandler = worker.schedule(new TimeoutTask(), TIMEOUT_SECONDS, TimeUnit.SECONDS);
                WifiConfiguration wifiConfiguration = addNetwork(wifiConfig.getSsid(), wifiConfig.getPassword());
                if (wifiConfiguration != null && wifiConfiguration.networkId != -1) {
                    connectionReceiver = new ConnectionReceiver();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION);
                    intentFilter.addAction(android.net.wifi.WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
                    intentFilter.addAction(android.net.wifi.WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
                    context.registerReceiver(connectionReceiver, intentFilter);
                    wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                } else {
                    //Wifi not configured and no password provided
                    callback.onWifiNotConfigured();
                    if (taskHandler != null) {
                        taskHandler.cancel(true);
                    }
                }

            }
        }
    }

    private WifiConfiguration addNetwork(String SSID, String password) {
        //Remove network in case it already exist
        removeNetwork(SSID);
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + SSID + "\"";
        conf.preSharedKey = "\"" + password + "\"";
        conf.status = WifiConfiguration.Status.ENABLED;
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        int networkId = wifiManager.addNetwork(conf);
        conf.networkId = networkId;
        return conf;
    }

    private void removeNetwork(String SSID) {
        WifiConfiguration conf = getWifiConfiguration(SSID);
        if (conf != null) {
            boolean removed = wifiManager.removeNetwork(conf.networkId);
            if (removed) {
                wifiManager.saveConfiguration();
            }
        }
    }

    private WifiConfiguration getWifiConfiguration(String SSID) {
        for (WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
            if (wifiConfiguration.SSID.equalsIgnoreCase("\"" + SSID + "\"")) {
                return wifiConfiguration;
            }
        }
        return null;
    }


    private boolean isConnectedToSpecificNetwork(String ssid) {
        if (TextUtils.isEmpty(ssid)) {
            return false;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && !TextUtils.isEmpty(wifiInfo.getSSID())) {
            String wifiSSIDConnected = wifiInfo.getSSID().replace("\"", "");
            if (wifiSSIDConnected.equals(ssid) && wifiInfo.getSupplicantState().equals(SupplicantState.COMPLETED)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Broadcast receiver for connection related events
     */
    private class ConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("DEBUG", "On receive connected to " + wifiConfig.getSsid() + " " + isConnectedToSpecificNetwork(wifiConfig.getSsid()));
            if (isConnectedToSpecificNetwork(wifiConfig.getSsid())) {
                context.unregisterReceiver(this);
                if (taskHandler != null) {
                    taskHandler.cancel(true);
                }
                if (callback!=null) {
                    callback.onConnectionSuccess();
                }
            }
        }
    }

    /**
     * Timeout task. Called when timeout is reached
     */
    private class TimeoutTask implements Runnable {
        @Override
        public void run() {
            try {
                context.unregisterReceiver(connectionReceiver);
            } catch (Exception ex) {
            }
            if (isConnectedToSpecificNetwork(wifiConfig.getSsid())) {
                callback.onConnectionSuccess();
            } else {
                removeNetwork(wifiConfig.getSsid());
                callback.onWifiNotConnected();
            }
        }
    }

}
