package com.tb.tbretrofit.rx_retrofit.tools;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/17 下午12:11
 * @最后更新时间：17/11/17 下午12:11
 */
public class CacheConfig {

    /**
     * CacheControl.Builder :
     - noCache();//不使用缓存，用网络请求
     - noStore();//不使用缓存，也不存储缓存
     - onlyIfCached();//只使用缓存
     - noTransform();//禁止转码
     - maxAge(10, TimeUnit.MILLISECONDS);//设置超时时间为10ms。
     - maxStale(10, TimeUnit.SECONDS);//超时之外的超时时间为10s
     - minFresh(10, TimeUnit.SECONDS);//超时时间为当前时间加上10秒钟。
     */


    /**
     * 不考虑缓存，直接走网络，但会存储响应到缓存区
     * @return
     */
    public static String forceNetWork () {

        return CacheControl.FORCE_NETWORK.toString();
    }

    /**
     * 不使用缓存，并且不会存储响应到缓存区
     * @return
     */
    public static String forceNetWorkAndNoStore () {
        return new CacheControl.Builder()
                .maxAge(10, TimeUnit.SECONDS)
                .maxStale(20, TimeUnit.SECONDS)
                .noCache()
                .noStore()
                .build().toString();
    }

    /**
     * 直接读取缓存区
     * 如果已有缓存，则将缓存可用时间设置为 Integer.MAX_VALUE
     * 如果缓存区无数据则返回null  code： 504-unsatisfiable request
     * @return
     */
    public static String forceCache () {

        return CacheControl.FORCE_CACHE.toString();
    }


    /**
     * 完全交给Cache－Control：value
     * 由value来决定 重新请求还是使用缓存
     * 另外：value 区分 request  与 response
     * 并且由request 为准 response为辅
     *
     * @return
     */
    public static String normal () {
        return new CacheControl.Builder()
                .maxAge(10, TimeUnit.SECONDS)//
                .maxStale(20, TimeUnit.SECONDS)
                .build().toString();
    }

}
