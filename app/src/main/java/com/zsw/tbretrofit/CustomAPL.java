package com.zsw.tbretrofit;

import android.app.Application;

import com.tb.tbretrofit.httputils.excute.TBRequestExecute;
import com.tb.tbretrofit.httputils.factory.TBOkHttpClientFactory;

import com.tb.tbretrofit.httputils.factory.TBRetrofitFactory;
import com.tb.tbretrofit.httputils.factory.TBTranslateFactory;
import com.tb.tbretrofit.rx_retrofit.http_excuter.HttpClientFactory;
import com.tb.tbretrofit.rx_retrofit.http_excuter.RetrofitFactory;

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

        initHttpSystem();


    }
    private void initHttpSystem(){
        OkHttpClient client = new HttpClientFactory.Builder()
                .setDebug(true)
                .autoCache(this)
                .build();
        RetrofitFactory.Builder.create()
                .setBaseUrl("http://www.aa.com")
                .setOkHttpClient(client)
                .init();
    }
}
