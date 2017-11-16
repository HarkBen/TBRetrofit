package com.tb.tbretrofit.rx_retrofit.http_contact;

import android.util.Log;

import com.tb.tbretrofit.rx_retrofit.http_excuter.HttpClientFactory;
import com.tb.tbretrofit.rx_retrofit.http_excuter.RxApiService;
import com.tb.tbretrofit.rx_retrofit.http_excuter.JsonBody;
import com.tb.tbretrofit.rx_retrofit.http_excuter.RetrofitFactory;
import com.tb.tbretrofit.rx_retrofit.http_reception.HttpResponseListener;
import com.tb.tbretrofit.rx_retrofit.tools.RxHttpLog;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @描述： -具体实现请求交接和返回值传递
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午4:21
 * @最后更新时间：17/11/15 下午4:21
 */
public class RxHttpExecuteImpl implements RxHttpExecuteI {


    private RxApiService apiService;
    private HttpTaskManagement httpTaskManagement;

    public RxHttpExecuteImpl ( ) {
        apiService = RetrofitFactory.getInstance().createService(RxApiService.class);
        httpTaskManagement = RxHttpTaskManagement.getINSTANCE();

    }

    public static RxHttpExecuteI create( ){
        return new RxHttpExecuteImpl();
}




    private <T>  void subscribe (final Observable<Response<String>> observable,final HttpResponseListener responseListener) {
        if (null == observable) return;

        final Subscriber<Response<String>> subscriber = new Subscriber<Response<String>>() {


            @Override
            public void onStart () {
                responseListener.onStart();
            }

            //遇到异常以后不会调用onComplete
            @Override
            public void onCompleted () {
                responseListener.onFinish();
            }

            @Override
            public void onError (Throwable e) {
                if(e instanceof IOException){
                    responseListener.onFailure(HttpCode.CODE_INTENET_IS_NOT_AVALIABLE,"no network");
                }
                responseListener.onFinish();


            }

            @Override
            public void onNext (Response<String> response) {
                RxHttpLog.printI("RxHttpExecuteImpl","code:"+response.code());
                RxHttpLog.printI("RxHttpExecuteImpl","finally Response:"+response.body());
                RxHttpLog.printI("RxHttpExecuteImpl","network Response:"+response.raw().networkResponse());
                RxHttpLog.printI("RxHttpExecuteImpl","cache Response:"+response.raw().cacheResponse());
                responseListener.onResponse(response);

            }


        };

        //剩余补充，缓存函数，token重新获取函数
        observable.subscribeOn(Schedulers.io())//被观察者创建线程 事件产生的线程 变量X
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//观察者接受回调线程 事件接受线程 应变量Y
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call () {
                        httpTaskManagement.addSubscription(responseListener.getContext(),subscriber);
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call () {
                        httpTaskManagement.remove(responseListener.getContext());
                    }
                })
                .subscribe(subscriber);



    }

    @Override
    public void get (String url,HttpResponseListener responseListener) {
        subscribe(apiService.get(url),responseListener);
    }

    @Override
    public void get (String url, String[] values,HttpResponseListener responseListener) {
        if (null == values || values.length == 0) {
            get(url,responseListener);
        } else {
            String va = "";
            for (String value : values) {
                va += "/" + value;
            }
            url = url + va;
            get(url,responseListener);
        }

    }

    @Override
    public void get (String url, Map<String, Object> map,HttpResponseListener responseListener) {
        subscribe(apiService.get(url,map),responseListener);
    }


    @Override
    public void postJson (String url, JsonBody json,HttpResponseListener responseListener) {
        subscribe(apiService.postJson(url,json),responseListener);
    }

    @Override
    public void postJson (String url, String json,HttpResponseListener responseListener) {
        subscribe(apiService.postJson(url,json),responseListener);
    }

    @Override
    public void postRequestBody (String url, RequestBody body,HttpResponseListener responseListener) {
        subscribe(apiService.postIndependent(url,body),responseListener);
    }

    @Override
    public void postFormData (String url, Map<String, Object> map,HttpResponseListener responseListener) {
        subscribe(apiService.postForm(url,map),responseListener);

    }

    @Override
    public void postFormDataFiles (String url, Map<String, Object> map, List<File> files, MediaType contentType,HttpResponseListener responseListener) {
        if (null == files) {
            throw new NullPointerException("files is null!");
        }
        if (files.size() == 0) {
            throw new IllegalArgumentException("files size  equal 0,You must  include at least one file!");
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            RequestBody body = RequestBody.create(contentType, file);
            builder.addFormDataPart("files", file.getName(), body);
        }
        if (null != map && !map.isEmpty()) {
            Iterator<Map.Entry<String, Object>> keys = map.entrySet().iterator();
            while (keys.hasNext()) {
                Map.Entry<String, Object> entry = keys.next();
                builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        MultipartBody multipartBody = builder.build();
        subscribe(apiService.postFormDataFiles(url,multipartBody),responseListener);
    }

}
