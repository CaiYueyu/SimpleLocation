package com.mjzuo.location.helper;

import android.location.LocationManager;

import java.util.List;

public class Helper {

    public static String getProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        // 网络定位
        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER))
            return LocationManager.NETWORK_PROVIDER;
            // gps定位
        else if(prodiverlist.contains(LocationManager.GPS_PROVIDER))
            return LocationManager.GPS_PROVIDER;
        return null;
    }
}
