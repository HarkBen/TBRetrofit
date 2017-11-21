package com.tb.rx_retrofit.tools.fuction;

import com.tb.rx_retrofit.tools.cache.CacheModel;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @描述： -
 * 使用永久缓存时 处理首次无缓存
 *  -
 * @作者：zhusw
 * @创建时间：17/11/16 下午6:08
 * @最后更新时间：17/11/16 下午6:08
 */
public class CacheForeverInterceptor implements Interceptor {

    @Override
    public Response intercept (Chain chain) throws IOException {

        Request originalRequest = chain.request();//原始接口请求
        Response originalResponse = chain.proceed(originalRequest);//原始接口结果
        //读缓存但没有
        if(originalResponse.code() == 504){
            Request newRequest = chain.request()
                    .newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", CacheModel.FOREVER.getValue())
                    .build();
            return chain.proceed(newRequest);
        }

        return originalResponse;
    }

}
