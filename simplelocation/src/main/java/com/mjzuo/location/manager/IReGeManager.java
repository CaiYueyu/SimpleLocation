package com.mjzuo.location.manager;

import com.mjzuo.location.bean.Latlng;

public interface IReGeManager {

    /**
     * 开始反编码
     */
    void start();

    /**
     * 反向编码
     */
    void reGeToAddress(Latlng latlng);

    /**
     * 结束
     */
    void stop();

    /**
     * 注册监听
     */
    void addReGeListener(IReGeListener listener);

    /**
     * 反编码的回调
     */
    interface IReGeListener {
        void onSuccess(Latlng latlng);
        void onFail(String error);
    }
}
