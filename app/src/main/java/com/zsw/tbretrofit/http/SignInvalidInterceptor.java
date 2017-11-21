package com.zsw.tbretrofit.http;

import android.content.Context;

import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.tb.rx_retrofit.http_excuter.HttpClientFactory;
import com.tb.rx_retrofit.http_excuter.RetrofitFactory;
import com.tb.rx_retrofit.http_excuter.RxApiService;
import com.tb.rx_retrofit.http_presenter.JsonBody;
import com.tb.rx_retrofit.tools.RxHttpLog;
import com.tb.rx_retrofit.tools.cache.CacheModel;
import com.tb.rx_retrofit.tools.fuction.RetryWhenTimeout;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/21 下午1:51
 * @最后更新时间：17/11/21 下午1:51
 */
public class SignInvalidInterceptor implements Interceptor {
    public static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    final private static String TAG = "SignInvalidInterceptor";
    private JsonBody signinParameter;
    private String abslouteUrl;
    private WeakReference<Context> contextWeakReference;
    public SignInvalidInterceptor (Context context,String abslouteUrl, JsonBody signinParameter) {
        this.signinParameter = signinParameter;
        this.abslouteUrl = abslouteUrl;
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    public Response intercept (Chain chain) throws IOException {

        Request originalRequest = chain.request();//原始接口请求
        Response originalResponse = chain.proceed(originalRequest);//原始接口结果
        if (originalResponse.code() == 302 || Sessioninvalid(originalResponse)) {
//            originalResponse.body().close();//关闭资源，不再向下分发
            if (siginSuccess()) { //登陆成功
                RxHttpLog.printI(TAG,"自动登陆后 准备重现设置cookies 重现请求 ");
                SharedPrefsCookiePersistor cookieSp = new SharedPrefsCookiePersistor(contextWeakReference.get());
                List<Cookie> cookies = cookieSp.loadAll();
                StringBuffer stringBuffer = new StringBuffer();
                if(null != cookies && cookies.size()>0){
                    for(int i = 0 ; i<cookies.size();i++){
                        stringBuffer.append(cookies.get(i).toString());
                    }
                }
                RxHttpLog.printI(TAG,"登陆后 cookies 信息＝"+stringBuffer.toString());
                Request newRequest = chain.request()
                        .newBuilder()
                        .header("Cookie", stringBuffer.toString())
                        .build();
                return chain.proceed(newRequest);//重新执行
            }else{
                RxHttpLog.printI(TAG,"登陆失败：  ");
            }

        }
        return originalResponse;

    }

    public boolean  siginSuccess () {
        boolean siginSuccess = false;
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MEDIATYPE_JSON,gson.toJson(signinParameter));
        Request request = new Request.Builder()
                .url(abslouteUrl)
                .post(requestBody)
                .header("Cache-Control",CacheModel.FORCE_NETWORK.getValue())
                .build();

        try {
            Response response = HttpClientFactory
                    .getOkHttpClient()
                    .newCall(request)
                    .execute();
            if(null != response &&response.code() == 200){
                siginSuccess = true;
                RxHttpLog.printI(TAG,"登陆返回 code ="+response.code());
            }
        } catch (IOException e) {
            RxHttpLog.printI(TAG," 请求登陆失败");
            siginSuccess = false;
        }finally {
            return siginSuccess;
        }

    }



    public boolean Sessioninvalid (Response response) {
        String sessionInvalid = response.headers().get("sessionInvalid");
        if (null != sessionInvalid && "true".equals(sessionInvalid)) {
            RxHttpLog.printI(TAG,"sessionInvalid="+true);
            return true;
        }
        return false;
    }
}
