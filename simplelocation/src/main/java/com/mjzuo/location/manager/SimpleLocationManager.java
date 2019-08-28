package com.mjzuo.location.manager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.mjzuo.location.bean.Latlng;
import com.mjzuo.location.helper.ConverHelper;
import com.mjzuo.location.helper.Helper;

/**
 *  获取当前定位的经纬度。
 *
 * @author mingjiezuo
 * @since 19/08/28
 */
public class SimpleLocationManager implements IManager{

    /** 当前系统定位manager*/
    private LocationManager lm;
    /** 位置提供器*/
    private String mProvider;

    /** 响应用户的回调*/
    private ISiLoResponseListener listener;

    private Context mContext;

    public SimpleLocationManager(Context context) {
        this.mContext = context;
    }

    @Override
    public void start(@Nullable ISiLoResponseListener listener) {
        this.listener = listener;
        lm = (LocationManager) mContext.getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        mProvider = Helper.getProvider(lm);
        if(mProvider != null){
            if(checkPermission()){
                Latlng latlng = ConverHelper.loConverToLatlng(lm.getLastKnownLocation(mProvider));
                if(latlng != null)
                    listener.onSuccess(latlng);
                else
                    listener.onFail("location latlng null");
            }else{
                listener.onFail("location no permission");
            }
        }else{
            listener.onFail("location provider no exist");
        }
    }

    @Override
    public void stop() {
        mProvider = null;
        lm = null;
        listener = null;
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }
}
