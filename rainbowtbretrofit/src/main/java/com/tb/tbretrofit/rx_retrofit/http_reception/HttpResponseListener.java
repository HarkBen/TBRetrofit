package com.tb.tbretrofit.rx_retrofit.http_reception;

/**
 * @描述： -网络请求面向请求发起者的 回调接口
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:18
 * @最后更新时间：17/11/15 下午3:18
 */
public interface HttpResponseListener<T> {
    void onStart();
    void onSuccess(T t);
    void onFailure(int errorCode,String message);
    void onFinish();
}
