package com.tb.rx_retrofit.http_contact;

import com.tb.rx_retrofit.fuction.RetryWhenTimeout;
import com.tb.rx_retrofit.http_excuter.ResponseHandler;
import com.tb.rx_retrofit.http_excuter.JsonBody;
import com.tb.rx_retrofit.http_excuter.RetrofitFactory;
import com.tb.rx_retrofit.http_excuter.RxApiService;
import com.tb.rx_retrofit.http_receiver.HttpResponseListener;
import com.tb.rx_retrofit.tools.CacheModel;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * @描述： -负责选择执行的具体任务及获取任务返回
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午4:21
 * @最后更新时间：17/11/15 下午4:21
 */
final public class RxHttpContactImpl implements HttpContactI {
    private final static String TAG = "RxHttpContactImpl";
    final private RxApiService apiService;
    final private HttpTaskManagement httpTaskManagement;

    public RxHttpContactImpl(HttpTaskManagement httpTaskManagement) {
        apiService = RetrofitFactory.getInstance().createService(RxApiService.class);
        this.httpTaskManagement = httpTaskManagement;
    }

    private  void subscribe (final Observable<Response<String>> observable, final HttpResponseListener responseListener) {
        if (null == observable) return;

        final Subscriber<Response<String>> subscriber = new ResponseHandler(responseListener);

        observable.subscribeOn(Schedulers.io())//被观察者创建线程 事件产生的线程 变量X
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//观察者接受回调线程 事件接受线程 应变量Y
//                .concatMap(new Func1<Response<String>, Observable<Response<String>>>() {
//                    @Override
//                    public Observable<Response<String>> call (Response<String> response) {
//                        if(response.raw().code() == 302){
//                         return apiService.get("","")
//                                 .concatMap(new Func1<Response<String>, Observable<? extends Response<String>>>() {
//
//                             @Override
//                             public Observable<? extends Response<String>> call (Response<String> response) {
//
//                                 return observable;
//                             }
//                         });
//
//                        }
//                        return null;
//                    }
//                })
                .retryWhen(new RetryWhenTimeout())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call () {
                        httpTaskManagement.addSubscription(responseListener.getContext(), subscriber);
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


    private String checkCacheModel(CacheModel cacheModel){
        if(null == cacheModel){
            cacheModel =  CacheModel.NORMAL;
        }
            return cacheModel.getValue();

    }



    @Override
    public void get (String url, HttpResponseListener responseListener) {
        subscribe(apiService.get(checkCacheModel(responseListener.cacheModel()),url), responseListener);
    }

    @Override
    public void get (String url, String[] values, HttpResponseListener responseListener) {
        if (null == values || values.length == 0) {
            get(url, responseListener);
        } else {
            String va = "";
            for (String value : values) {
                va += "/" + value;
            }
            url = url + va;
            get(url, responseListener);
        }

    }

    @Override
    public void get (String url, Map<String, Object> map, HttpResponseListener responseListener) {
        subscribe(apiService.get(checkCacheModel(responseListener.cacheModel()),url, map), responseListener);
    }


    @Override
    public void postJson (String url, JsonBody json, HttpResponseListener responseListener) {
        subscribe(apiService.postJson(url, json), responseListener);
    }

    @Override
    public void postJson (String url, String json, HttpResponseListener responseListener) {
        subscribe(apiService.postJson(checkCacheModel(responseListener.cacheModel()),url, json), responseListener);
    }


    @Override
    public void postRequestBody (String url, RequestBody body, HttpResponseListener responseListener) {
        subscribe(apiService.postIndependent(checkCacheModel(responseListener.cacheModel()),url, body), responseListener);
    }

    @Override
    public void postFormData (String url, Map<String, Object> map, HttpResponseListener responseListener) {
        subscribe(apiService.postForm(checkCacheModel(responseListener.cacheModel()),url, map), responseListener);

    }

    @Override
    public void postFormDataFiles (String url, Map<String, Object> map, List<File> files, MediaType contentType, HttpResponseListener responseListener) {
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
        subscribe(apiService.postFormDataFiles(checkCacheModel(responseListener.cacheModel()),url, multipartBody), responseListener);
    }

}
