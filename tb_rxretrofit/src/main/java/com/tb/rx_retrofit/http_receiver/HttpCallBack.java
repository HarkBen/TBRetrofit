package com.tb.rx_retrofit.http_receiver;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tb.rx_retrofit.tools.HttpCode;
import com.tb.rx_retrofit.tools.cache.CacheModel;
import com.tb.rx_retrofit.tools.RxHttpLog;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * @描述： -针对具体数据模型解析
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 上午11:13
 * @最后更新时间：17/11/16 上午11:13
 */
public abstract class  HttpCallBack<T> implements HttpResponseListener{
    final private static String TAG= "HttpCallBack";

    public Type respType;

    private WeakReference<Context> contextWeakReference;


    public HttpCallBack(Context  context){

        this.contextWeakReference  = new WeakReference<>(context);

        //获取接口泛型T的class，Type，必须要在子类才能获取Interface的T
        ParameterizedType genType = getParameterizedType(getClass());
        if (genType != null) {
            Type[] params = genType.getActualTypeArguments();
            if (params != null && params.length > 0)
                this.respType = params[0];
        }
//        Class<T> entityClass = (Class) Classparams[0]; //获取 class可以用此代码
    }



    @Override
    public void onStart () {

    }

    private void formatData(Response<String> response){
        if (null != response.body()) {
            try {
                T t = new Gson().fromJson(response.body(), respType);
                onSuccess(t);
            } catch (JsonSyntaxException jse) {
                onFailure(HttpCode.CODE_DATA_FORMAT_FAILURE, jse.getMessage());
            }
        } else {
            onFailure(HttpCode.CODE_DATA_FORMAT_FAILURE_NO_DATA, "没有数据返回");
        }
    }

    /**
     * 处理数据分发
     * @param response
     */
    @Override
    public void onResponse (Response<String> response) {
        RxHttpLog.printI(TAG, "onResponse");
        switch (response.code()){
            case 200:
                formatData(response);
                break;
            case 304:
                formatData(response);
                break;
            case 404:
            case 400:
            case 405:
            case 500:
            default:
                onFailure(HttpCode.CODE_REQUEST_ERROR, "网络请求错误："+response.code());
                break;
        }
    }

    @Override
    public void onFailure (int errorCode, String message) {


    }

    @Override
    public void onFinish () {

    }

    @Override
    public Context getContext () {
        return contextWeakReference.get();
    }



    public abstract void onSuccess(T t);

    @Override
    public CacheModel cacheModel () {
        return CacheModel.NORMAL;
    }

    /**
     * 获取泛型type
     *
     * @param genType
     * @return
     */
    public static ParameterizedType getParameterizedType(Class genType) {
        if (genType == null) {
            return null;
        }
        if (genType.getGenericSuperclass() instanceof ParameterizedType) {
            return (ParameterizedType) genType.getGenericSuperclass();
        } else if ((genType = genType.getSuperclass()) != null && genType != Object.class) {
            return getParameterizedType(genType);
        } else {
            return null;
        }
    }
}
