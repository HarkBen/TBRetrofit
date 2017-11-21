package com.tb.rx_retrofit.http_presenter;

import com.tb.rx_retrofit.http_receiver.HttpResponseListener;
import com.tb.rx_retrofit.tools.HttpCode;
import com.tb.rx_retrofit.tools.NetworkStatusUtils;
import com.tb.rx_retrofit.tools.RxHttpLog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @描述： -处理网络访问异常及缓存异常
 * -
 * @作者：zhusw
 * @创建时间：17/11/17 下午6:02
 * @最后更新时间：17/11/17 下午6:02
 */
final public class ResponseHandler extends Subscriber<Response<String>> {
    final private static String TAG = "ResponseHandler";

    private HttpResponseListener responseListener;

    public ResponseHandler (HttpResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void onStart () {
        super.onStart();
        responseListener.onStart();
        //拦截无网络可用
        if (!NetworkStatusUtils.networkIsConnected(responseListener.getContext())) {
            responseListener.onFailure(HttpCode.CODE_NO_INTERNET, "网络不可用");
            unsubscribe();
            onCompleted();
        }

    }

    @Override
    public void onCompleted () {
        responseListener.onFinish();
    }

    /**
     * 协议层 读存数据发生错误才会走这里
     *
     * @param e
     */
    @Override
    public void onError (Throwable e) {
        //此处可拦截到的异常均发生在请求发起以后
        RxHttpLog.printI(TAG, "onError Throwable subClass :" + e.getClass() + " errorMsg:"+e.getMessage());
        if(e instanceof NullPointerException){
              /*
               * 响应返回或者缓存返回为null
               *此处的空指针来自于StringConverterFactory中转换响应结果时发生，会先调用onResponse 才会掉onError
               * 所以这里不下发给onFailure
               */
        }else if(e instanceof UnknownHostException){
            responseListener.onFailure(HttpCode.CODE_UNKNOW_HOST, "访问的目标主机不存在");

        }else if (e instanceof HttpException) {
            //SSL 验证成功 有正常的 响应返回，服务器无包装时 协议码为标准 401 404 等。。。
            HttpException httpException = (HttpException) e;
            responseListener.onFailure(httpException.code(), httpException.message());

        } else if (e instanceof SSLException) {
            //无法链接目标主机-本地网络无法访问外部服务器地址-服务器地址无法连接
            responseListener.onFailure(HttpCode.CODE_INTENET_IS_ABNORMAL, "无法与目标主机建立链接");

        } else if (e instanceof ConnectException
                || e instanceof SocketTimeoutException
                || e instanceof TimeoutException) {
            responseListener.onFailure(HttpCode.CODE_TIME_OUT, "链接超时");

        } else  if (e instanceof IOException) {
            //有网但请求失败包含：网络未授权访问外部地址，只读缓存时缓存区无缓存，等。。。
            responseListener.onFailure(HttpCode.CODE_RESPONSE_ERROR, "有网但请求失败包含：网络未授权访问外部地址，只读缓存时缓存区无缓存，等。。。");

        } else{

            responseListener.onFailure(HttpCode.CODE_UNKNOW, "未知网络错误");
        }
        responseListener.onFinish();
    }

    /*
    * 注：
    * 1.如果此处处理 发生了异常 也会被onError捕捉到
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
