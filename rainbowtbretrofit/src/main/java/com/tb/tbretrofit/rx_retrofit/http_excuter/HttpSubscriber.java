package com.tb.tbretrofit.rx_retrofit.http_excuter;

import com.tb.tbretrofit.rx_retrofit.http_reception.HttpResponseListener;
import com.tb.tbretrofit.rx_retrofit.tools.HttpCode;
import com.tb.tbretrofit.rx_retrofit.tools.RxHttpLog;

import java.io.IOException;

import javax.net.ssl.SSLException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/17 下午6:02
 * @最后更新时间：17/11/17 下午6:02
 */
public class HttpSubscriber extends Subscriber<Response<String>> {
    final private static String TAG = "HttpSubscriber";

    private HttpResponseListener responseListener;

    public HttpSubscriber (HttpResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void onStart () {
        super.onStart();
        responseListener.onStart();
        //判断网络 如果是必须使用用网络请求 直接取消 请求
        if (false) {
            responseListener.onFailure(HttpCode.CODE_NO_INTERNET, "无网络");
            unsubscribe();
            onCompleted();
        }

    }

    @Override
    public void onCompleted () {
        responseListener.onFinish();
    }

    @Override
    public void onError (Throwable e) {
        //此处可拦截到的网络异常有：服务器
        RxHttpLog.printI(TAG, "onError:" + e.getMessage());
        if (e instanceof HttpException) {
            //SSL 验证成功 有正常的 响应返回，服务器无包装时 协议码为 401 404 等。。。
            HttpException httpException = (HttpException) e;
            responseListener.onFailure(httpException.code(), httpException.message());

        } else if (e instanceof SSLException) {
            //无法链接目标主机
            responseListener.onFailure(HttpCode.CODE_INTENET_IS_ABNORMAL, "网络请求异常");

        } else if (e instanceof IOException) {
            //有网但请求失败包含：网络未授权访问外部地址，只读缓存时缓存区无缓存，等。。。
            responseListener.onFailure(HttpCode.CODE_UNKNOW, "网络请求异常");
        }
        responseListener.onFinish();
    }

    /*
    *这里比较怪：
    * 1.选择只读取缓存区时
    *
    *
    */
    @Override
    public void onNext (Response<String> response) {
        RxHttpLog.printI(TAG, "onNext");
        RxHttpLog.printI(TAG, "code:" + response.code());
        RxHttpLog.printI(TAG, "finally Response:" + response.body());
        RxHttpLog.printI(TAG, "network Response:" + response.raw().networkResponse());
        RxHttpLog.printI(TAG, "cache Response:" + response.raw().cacheResponse());
        responseListener.onResponse(response);
    }

}
