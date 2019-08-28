package com.mjzuo.location.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class Helper {

    public static String getGPSProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        // gps定位，建议优先，因为精度较高
        if(prodiverlist.contains(LocationManager.GPS_PROVIDER))
            return LocationManager.GPS_PROVIDER;
        return null;
    }

    public static String getNetWorkProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        // 网络定位
        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER))
            return LocationManager.NETWORK_PROVIDER;
        return null;
    }

    public static boolean checkPermission(Context mContext) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }
}
