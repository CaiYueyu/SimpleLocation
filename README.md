### SimpleLocation

本篇主要介绍谷歌自带的LocationManager 获取手机定位的方法，以及通过谷歌服务Geocoder 来进行反地理编码。接口api都比较简单，细节可以查看代码。

由于国内很多机型都删掉了谷歌服务，所以我在demo里除了谷歌服务之外，又额外添加了高德反地理编码api，腾讯反地理编码api，百度反地理编码api，白名单方式和sn签名校验方式都有。大家也可以将demo的依赖直接打包成jar包使用。

这里贴下使用获取经纬度方法的代码片段：

```java
        SimpleLocationManager.SiLoOption option = new SimpleLocationManager.SiLoOption()
                .setGpsFirst(false);// network优先
        siLoManager = new SimpleLocationManager(this, option);
        siLoManager.start(new IManager.ISiLoResponseListener() {
            @Override
            public void onSuccess(Latlng latlng) {
                Log.e(LOG_TAG,"siLoManager onSuccess:" + latlng);
            }

            @Override
            public void onFail(String msg) {
                Log.e(LOG_TAG,"error:" + msg);
            }
        });
```

贴一下使用反地理编码的代码片段：

```java
        ReverseGeocodingManager.ReGeOption reGeOption = new ReverseGeocodingManager.ReGeOption()
                .setReGeType(Constant.BAIDU_API)// 百度api返地理编码
                .setSn(true)// sn 签名校验方式
                .setIslog(true);// 打印log
        reGeManager = new ReverseGeocodingManager(this, reGeOption);
        reGeManager.addReGeListener(new IReGe.IReGeListener() {
            @Override
            public void onSuccess(int state, Latlng latlng) {
                Log.e(LOG_TAG,"reGeManager onSuccess:" + latlng);
            }

            @Override
            public void onFail(int errorCode, String error) {
                Log.e(LOG_TAG,"error:" + error);
            }
        });
```

因为LocationManager 使用起来确实不怎么稳定，我测试了手头的三星S8 android9，华为 JSN-AL00a android9，小米M8 android9，魅族M6 Note andoird7.1.2，都是没有问题的。如果大家手机遇到了问题，可以将手机型号、系统、报错信息提出来，大家一起试着解决。
