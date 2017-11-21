package com.tb.rx_retrofit.tools.cache;

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
     - maxAge(10, TimeUnit.MILLISECONDS);//超过 maxAge 将走网络。
     - maxStale(10, TimeUnit.SECONDS);//超过 maxStale 缓存将不可用
       但依然由 maxAge决定是否走网络，所以 精良让maxAge<maxStale 避免返回 空结果

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
     * 另外：Cache－Control 区分 client  与 server,
     * 也就是服务器对应的 expires 有效时间   client max-age
     * 并且由 client 为准 server 为辅
     *
     * max-age client自主决定缓存有效期 超过有效期才请求网络
     * 但是如果服务器依然返回304 则将继续使用缓存
     *
     * @return
     */
    public static String normal () {
        return new CacheControl.Builder()
                .maxAge(30, TimeUnit.SECONDS)
                .build().toString();
    }

    /**
     * 针对近似永久缓存数据，使用如下策略
     * 接口配置 只使用缓存，加入无缓存 504 拦截器
     * 为此次任务重新配置cache-control 在首次读取时从网络下载一次
     * @return
     */
    public static String forever () {
        return new CacheControl.Builder()
                .maxAge(1, TimeUnit.SECONDS)
                .maxStale(Integer.MAX_VALUE,TimeUnit.SECONDS)
                .build().toString();
    }
}
