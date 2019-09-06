package com.silence.movies.data.network;

import android.Manifest;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import javax.inject.Inject;

public class NetworkHandler {

    private ConnectivityManager cm;

    @Inject
    public NetworkHandler(ConnectivityManager connectivityManager) {
        this.cm = connectivityManager;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public boolean hasNetworkConnection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return hasNetworkConnection(nc);
        } else {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return hasNetworkConnection(networkInfo);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean hasNetworkConnection(@Nullable NetworkCapabilities nc) {
        return nc != null && (
                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        );
    }

    private boolean hasNetworkConnection(@Nullable NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isConnected();
    }
}
