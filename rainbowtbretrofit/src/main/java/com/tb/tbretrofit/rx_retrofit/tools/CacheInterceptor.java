package com.tb.tbretrofit.rx_retrofit.tools;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 下午6:08
 * @最后更新时间：17/11/16 下午6:08
 */
public class CacheInterceptor implements Interceptor {


    @Override
    public Response intercept (Chain chain) throws IOException {
        Request request =
                        chain
                        .request()
                        .newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                                .addHeader("AA", "no-chache, max-age=40, max-stale=40")
                                .build();

        return chain.proceed(request);
    }

}
