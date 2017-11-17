package com.tb.tbretrofit.rx_retrofit.tools;

import okhttp3.Cache;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/17 下午2:38
 * @最后更新时间：17/11/17 下午2:38
 */
public enum CacheModel {

    FORCE_NETWORK(CacheConfig.forceNetWork())
    ,FORCE_CACHE(CacheConfig.forceCache())
    ,FORCE_NETWORK_AND_NOSTRROE(CacheConfig.forceNetWorkAndNoStore())
    ,NORMAL(CacheConfig.normal());

    private String value;

    CacheModel(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
