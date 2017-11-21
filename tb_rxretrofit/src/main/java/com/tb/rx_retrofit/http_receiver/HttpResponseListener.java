package com.tb.rx_retrofit.http_receiver;

import android.content.Context;

import com.tb.rx_retrofit.tools.cache.CacheModel;

import retrofit2.Response;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 上午11:13
 * @最后更新时间：17/11/16 上午11:13
 */
public interface HttpResponseListener {

    void onStart();

    void onResponse (Response<String> response);

    void onFailure (int errorCode, String message);

    void onFinish ();

    Context getContext ();

    CacheModel cacheModel ();

}
