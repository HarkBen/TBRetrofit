package com.tb.tbretrofit.httputils.factory;

import com.tb.tbretrofit.httputils.TBRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create on 2016/9/27.
 * github  https://github.com/HarkBen
 * Description:
 * -----在配置GsonConver时候我们覆盖了原来的泛型，只返回String
 * 所以我们直实现泛型为String的Callback好了-----
 * author Ben
 * Last_Update - 2016/9/30.
 */
public abstract class TBCallBack implements Callback<String> {

    @Override
    public void onResponse(final  Call<String> call, Response<String> response) {
        onSuccess(response.code(),response.body());


    }

    /**
     * 这里的异常处理 数据转换优先级 要比 onResponse更早调用
     * 所以 请求成功 但是数据转换错误就不会走onResponse
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<String> call, Throwable t) {
        onFailed(t.getMessage());
    }

    public abstract void onSuccess(int code,String body);

    public abstract  void onFailed(String errorMsg);

}
