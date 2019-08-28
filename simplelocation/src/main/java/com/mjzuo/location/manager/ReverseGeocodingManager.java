package com.mjzuo.location.manager;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mjzuo.location.bean.Latlng;
import com.mjzuo.location.helper.ConverHelper;

import java.io.IOException;
import java.util.List;

/**
 *  特定经纬度的反向地理编码
 * @author mingjiezuo
 * @since 19/08/28
 */
public class ReverseGeocodingManager implements IReGeManager {

    private static final String LOG_TAG = "tag_sl";
    private static int MAX_RESULTS = 1;

    private Criteria mCriteria;
    /** 编码监听*/
    private IReGeListener mListener;

    private List<Address> mAddresses;

    private Context mContext;

    public ReverseGeocodingManager(Context context) {
        mContext = context;
    }

    @Override
    public void start() {
        if(mCriteria == null){
            mCriteria = new Criteria();
            mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
            mCriteria.setAltitudeRequired(false);
            mCriteria.setBearingRequired(false);
            mCriteria.setCostAllowed(true);
            mCriteria.setPowerRequirement(Criteria.POWER_LOW);
        }
    }

    @Override
    public void reGeToAddress(@Nullable Latlng latlng) {
        Geocoder geocoder = new Geocoder(mContext.getApplicationContext());
        try {
            mAddresses = geocoder.getFromLocation(latlng.getLatitude(), latlng.getLongitude(), MAX_RESULTS);
        }catch (IOException e){
            if(mListener == null)
                mListener.onFail("geocoder get from location error:"+e.getMessage());
            else
                Log.e(LOG_TAG,"geocoder get from location error:"+e.getMessage());
        }
        if(mAddresses != null && mAddresses.size() != 0){
            latlng = ConverHelper.asConverToLatlng(latlng, mAddresses.get(0));
            if(mListener == null)
                mListener.onFail("geocoder listener null");
            else
                mListener.onSuccess(latlng);
        }else{
            Log.e(LOG_TAG,"geocoder get from location address size null or 0");
        }
    }

    @Override
    public void stop() {
        mCriteria = null;
        mAddresses = null;
    }

    @Override
    public void addReGeListener(IReGeListener listener) {
        this.mListener = listener;
    }
}
