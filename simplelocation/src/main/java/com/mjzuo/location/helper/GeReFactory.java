package com.mjzuo.location.helper;

import com.mjzuo.location.constant.Constant;
import com.mjzuo.location.location.BaiduGeRe;
import com.mjzuo.location.location.GaodeGeRe;
import com.mjzuo.location.location.GoogleRege;
import com.mjzuo.location.location.IReGe;
import com.mjzuo.location.location.TencentGeRe;

public class GeReFactory {

    public static IReGe getReGeByType(int reGeType){
        IReGe iReGe;
        switch (reGeType) {
            case Constant.GOOGLE_API:
                iReGe = new GoogleRege();
                break;
            case Constant.BAIDU_API:
                iReGe = new BaiduGeRe();
                break;
            case Constant.TENCENT_API:
                iReGe = new TencentGeRe();
                break;
            case Constant.GAODE_API:
                iReGe = new GaodeGeRe();
                break;
            default:
                iReGe = new GoogleRege();
        }
        return iReGe;
    }
}
