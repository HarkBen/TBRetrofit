package com.tb.tbretrofit.httputils.factory;

import android.util.Log;


import com.tb.tbretrofit.httputils.tools.StringConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Create on 2016/8/19.
 *
 * @author Ben
 *         Description-
 *         <p>
 *         github  https://github.com/HarkBen
 * @Last_update time - 2016年9月19日14:33:21
 */
public  class TBRetrofitFactory {

    private   Retrofit retrofit;

    /**
     * volatile 简单解释
     * ---------
     */
    private volatile static TBRetrofitFactory retorfitManager;

    /**
     * 构造私有掉，保证TBRetrofitFactory唯一，retrofit也就唯一
     * @param baseUrl
     */
    private TBRetrofitFactory(String baseUrl){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(TBOkHttpClientFactory.getOkHttpClient());
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(StringConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        retrofit = builder.build();
        Log.d("TBRetrofitFactory","Have created TBRetrofitFactory");
    }

    /**
     * 如果这里的 TBRetrofitFactory 需要单独使用，
     * 不使用{@link TBRetrofitService}的接口定义 这里的baseUrl 则需要规范传入 http://www.xxx.com/
     * 否则传入一个假的即可 http://xx.x.com/
     * @param baseUrl
     * @return
     */
    public static TBRetrofitFactory getInstance(String baseUrl){
        if(null == retorfitManager){
            synchronized (TBRetrofitFactory.class){
                if(null == retorfitManager){
                    retorfitManager = new TBRetrofitFactory(baseUrl);
                }
            }
        }
        return  retorfitManager;
    }
    public  <T> T createService(Class<T> clz){
        return retrofit.create(clz);
    }

}
