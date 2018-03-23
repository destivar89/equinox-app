package com.telefonica.bluetoothwifigiver.wifi;

public interface WifiManager {
    void connectToAccesPoint(Callback callback);

    interface Callback {
        void onConnectionSuccess();

        void onWifiNotConfigured();

        void onWifiNotConnected();

        void onWifiDisabled();
    }
}
