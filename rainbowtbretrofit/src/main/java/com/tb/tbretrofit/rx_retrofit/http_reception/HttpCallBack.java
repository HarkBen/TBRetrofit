package com.tb.tbretrofit.rx_retrofit.http_reception;

import android.content.Context;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Response;

/**
 * @描述： -针对具体数据模型解析
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 上午11:13
 * @最后更新时间：17/11/16 上午11:13
 */
public abstract class  HttpCallBack<T> implements HttpResponseListener{

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
        //Class<T> entityClass = (Class) params[0]; //获取 class可以用此代码
    }



    @Override
    public void onStart () {

    }

    @Override
    public void onResponse (Response<String> response) {
        T t = new Gson().fromJson(response.body(),respType);
        onSuccess(t);
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



    /**
     * 获取泛型class
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
