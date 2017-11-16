package com.tb.tbretrofit.rx_retrofit.http_reception;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.WeakHashMap;

import retrofit2.Response;

/**
 * @描述： -网络请求面向接口的监听
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:18
 * @最后更新时间：17/11/15 下午3:18
 */
public interface  HttpResponseListener {

    void onStart();
    void onResponse(Response<String> response);
    void onFailure(int errorCode,String message);
    void onFinish();
    Context getContext();

}
