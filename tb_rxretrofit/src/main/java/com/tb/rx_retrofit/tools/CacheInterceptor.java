package com.tb.rx_retrofit.tools;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @描述： -不推荐直接使用缓存拦截器来指定缓存需要
 * 不适用于单独接口单独设置缓存机制
 *  -
 * @作者：zhusw
 * @创建时间：17/11/16 下午6:08
 * @最后更新时间：17/11/16 下午6:08
 */
@Deprecated
public class CacheInterceptor implements Interceptor {


    @Override
    public Response intercept (Chain chain) throws IOException {
        Response originRes = chain.proceed(chain.request());
        //修改原请求体再返回
        Response newRes =   originRes.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "max-age=30")
                .build();
        return newRes;
    }

}
