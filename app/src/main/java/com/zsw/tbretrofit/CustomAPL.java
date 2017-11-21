package com.zsw.tbretrofit;

import android.app.Application;

import com.google.gson.Gson;
import com.tb.rx_retrofit.http_excuter.HttpClientFactory;
import com.tb.rx_retrofit.http_excuter.RetrofitFactory;
import com.zsw.tbretrofit.databox.PostDataUtils;
import com.zsw.tbretrofit.http.API;
import com.zsw.tbretrofit.http.SignInvalidInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .syncCookie(this)
                .addInterceptor(new SignInvalidInterceptor(this,API.loginUrl,PostDataUtils.getSiginParameter()))
                .build();
        RetrofitFactory.Builder.create()
                .setBaseUrl("http://www.aa.com")
                .setOkHttpClient(client)
                .init();
    }
}
