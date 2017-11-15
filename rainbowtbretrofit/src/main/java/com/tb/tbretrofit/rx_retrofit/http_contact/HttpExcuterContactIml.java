package com.tb.tbretrofit.rx_retrofit.http_contact;

import android.support.annotation.NonNull;
import android.text.AndroidCharacter;
import android.util.Log;

import com.tb.tbretrofit.rx_retrofit.http_excuter.ApiService;
import com.tb.tbretrofit.rx_retrofit.http_excuter.JsonBody;
import com.tb.tbretrofit.rx_retrofit.http_excuter.RetrofitFactory;
import com.tb.tbretrofit.rx_retrofit.http_reception.HttpResponseListener;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * @描述： -具体实现请求交接和返回值传递
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午4:21
 * @最后更新时间：17/11/15 下午4:21
 */
public class HttpExcuterContactIml implements HttpExcuterContactI{


    private ApiService apiService;
    private HttpResponseListener responseListener;


    public HttpExcuterContactIml (HttpResponseListener responseListener) {
        this.responseListener = responseListener;
        apiService = RetrofitFactory.getInstance().createService(ApiService.class);

    }

    public static HttpExcuterContactIml create(HttpResponseListener responseListener){
        return new HttpExcuterContactIml(responseListener);
    }


    private void subscribe (final Observable<Response<String>> observable) {
        if (null == observable) return;

        Subscriber<Response<String>> subscriber = new Subscriber<Response<String>>() {
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

                responseListener.onSuccess(response.body());
                Log.d("HttpExcuterContactIml","onNext:"+response.code());
            }
        };

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }

    @Override
    public void get (String url) {
        Observable<Response<String>> observable = apiService.get(url);
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
