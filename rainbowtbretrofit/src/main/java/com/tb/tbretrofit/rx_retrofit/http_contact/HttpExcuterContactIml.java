package com.tb.tbretrofit.rx_retrofit.http_contact;

import android.support.annotation.NonNull;

import com.tb.tbretrofit.rx_retrofit.http_excuter.ApiService;
import com.tb.tbretrofit.rx_retrofit.http_excuter.JsonBody;
import com.tb.tbretrofit.rx_retrofit.http_excuter.RetrofitFactory;
import com.tb.tbretrofit.rx_retrofit.http_reception.HttpResponseListener;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * @描述： -具体实现请求交接和返回值传递
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午4:21
 * @最后更新时间：17/11/15 下午4:21
 */
public class HttpExcuterContactIml implements HttpExcuterContactI {


    private ApiService apiService;
    private HttpResponseListener responseListener;


    public HttpExcuterContactIml (HttpResponseListener responseListener) {
        this.responseListener = responseListener;
        apiService = RetrofitFactory.getInstance().createService(ApiService.class);

    }


    private void subscribe (Observable<String> observable) {
        if (null == observable) return;

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onStart () {
                responseListener.onStart();
            }

            @Override
            public void onCompleted () {
                responseListener.onFinish();
            }

            @Override
            public void onError (Throwable e) {



            }

            @Override
            public void onNext (String s) {

                responseListener.onSuccess(s);
            }
        };

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call (Integer integer, Throwable throwable) {

                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(subscriber);




    }

    @Override
    public void get (String url) {
        Observable<String> observable = apiService.get(url);
        subscribe(observable);
    }

    @Override
    public void get (String url, String[] values) {


    }

    @Override
    public void get (String url, Map<String, Object> map) {

    }


    @Override
    public void postJson (String url, JsonBody json) {

    }

    @Override
    public void postRequestBody (String url, RequestBody body) {

    }

    @Override
    public void postFormData (String url, Map<String, Object> map) {

    }

    @Override
    public void postFormDataFiles (String url, Map<String, Object> map, List<File> files, MediaType contentType) {

    }
}
