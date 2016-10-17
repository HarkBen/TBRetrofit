package com.zsw.tbretrofit;

import android.app.Application;

import com.tb.tbretrofit.httputils.factory.TBOkHttpClientFactory;

import com.tb.tbretrofit.httputils.factory.TBRetrofitFactory;
import com.tb.tbretrofit.httputils.factory.TBRequestFactory;

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
        TBOkHttpClientFactory.Builder.create()
                .setDebug(true)
                .syncCookie(this)
                .setTimeout_connection(10)
                .setTimeout_read(10)
                .setTimeout_write(10)
                .build();
        TBRequestFactory.build(TBRetrofitFactory.getInstance(API.BASEURL));

    }
}
