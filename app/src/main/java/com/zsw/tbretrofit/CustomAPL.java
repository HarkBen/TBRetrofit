package com.zsw.tbretrofit;

import android.app.Application;

import com.tb.tbretrofit.httputils.factory.TBOkHttpClientFactory;

import com.tb.tbretrofit.httputils.factory.TBRetrofitFactory;
import com.tb.tbretrofit.httputils.factory.TBTranslateFactory;

import okhttp3.OkHttpClient;

/**
 * Create on 2016/10/11.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/10/11
 */
public class CustomAPL extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
      OkHttpClient okHttpClient =  TBOkHttpClientFactory.Builder.create()
                .setDebug(true)
                .syncCookie(this)
                .setTimeout_connection(10)
                .setTimeout_read(10)
                .setTimeout_write(10)
                .build();
        TBRetrofitFactory retrofitFactory =  TBRetrofitFactory.Builder.create()
                .setBaseUrl(API.BASEURL)
                .builder(okHttpClient);
        TBTranslateFactory.build(retrofitFactory);

        //初始化需要使用三个工厂来构建，这样的好处是解除隐藏关联
        //坏处是，构建的代码多一点，但是自己写个工具类对初始化构建进行封装就好。
        //对于转译工厂TBTranslateFactory，对TBRetrofitFactory是强制依赖的。
        //关于拓展，需要更换CallBack返回，或者增加delte put insert接口时，只需要写自己的
        //Service 并继承 TBRetrofitService ,按需拓展TBTranslateFactory和TBTranslateService即可

    }
}
