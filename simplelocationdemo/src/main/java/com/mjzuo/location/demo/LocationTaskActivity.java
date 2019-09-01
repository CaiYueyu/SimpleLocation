package com.mjzuo.location.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mjzuo.location.bean.Latlng;
import com.mjzuo.location.constant.Constant;
import com.mjzuo.location.IManager;
import com.mjzuo.location.location.IReGe;
import com.mjzuo.location.ReverseGeocodingManager;
import com.mjzuo.location.SimpleLocationManager;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class LocationTaskActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private static final String LOG_TAG = "tag_sl";

    /** 所要申请的权限*/
    String[] perms = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };

    /** 获取经纬度*/
    SimpleLocationManager siLoManager;
    /** 反地理编码的manager*/
    ReverseGeocodingManager reGeManager;

    TextView tvSimpleLo;
    TextView tvSimpleAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_location_activity);

        tvSimpleLo = findViewById(R.id.tv_simple_location_txt);
        tvSimpleAd = findViewById(R.id.tv_simple_address_txt);

        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this
                    , "必要的权限"
                    , 0
                    , perms);
        }

        ReverseGeocodingManager.ReGeOption reGeOption = new ReverseGeocodingManager.ReGeOption()
                .setReGeType(Constant.BAIDU_API)// 百度api返地理编码
                .setSn(true)// sn 签名校验方式
                .setIslog(true);// 打印log
        reGeManager = new ReverseGeocodingManager(this, reGeOption);
        reGeManager.addReGeListener(new IReGe.IReGeListener() {
            @Override
            public void onSuccess(int state, Latlng latlng) {
                Log.e(LOG_TAG,"reGeManager onSuccess:" + latlng);
                tvSimpleAd.setText("country:"+ latlng.getCountry()
                        + "\n,city:" + latlng.getCity()
                        + "\n,sublocality:" + latlng.getSublocality()
                        + "\n,address:" + latlng.getAddress()
                        + "\n,name:" + latlng.getName());
            }

            @Override
            public void onFail(int errorCode, String error) {
                Log.e(LOG_TAG,"error:" + error);
                tvSimpleAd.setText("errorCode:"+errorCode+", error:" + error);
            }
        });

        SimpleLocationManager.SiLoOption option = new SimpleLocationManager.SiLoOption()
                .setGpsFirst(false);// network优先
        siLoManager = new SimpleLocationManager(this, option);
        siLoManager.start(new IManager.ISiLoResponseListener() {
            @Override
            public void onSuccess(Latlng latlng) {
                Log.e(LOG_TAG,"siLoManager onSuccess:" + latlng);
                tvSimpleLo.setText("latlng:" + latlng.getLatitude()
                        + "\n,long:" + latlng.getLongitude()
                        + "\n,provider:" + latlng.getProvider());
                reGeManager.reGeToAddress(latlng);
            }

            @Override
            public void onFail(String msg) {
                Log.e(LOG_TAG,"error:" + msg);
                tvSimpleAd.setText("error:" + msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(siLoManager != null)
            siLoManager.stop();
        if(reGeManager != null){
            reGeManager.stop();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(requestCode == 0 && siLoManager != null)
            siLoManager.reStart();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
