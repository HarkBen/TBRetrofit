package com.tb.rx_retrofit.http_presenter;

import com.tb.rx_retrofit.http_receiver.HttpResponseListener;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @描述： -网络请求通用接口
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:56
 * @最后更新时间：17/11/15 下午3:56
 */
public interface HttpApi {


    void get(String url,HttpResponseListener responseListener);

    void get(String url, String[] values,HttpResponseListener responseListener);

    void get(String url, Map<String, Object> map,HttpResponseListener responseListener);

    void postJson(String url, JsonBody json,HttpResponseListener responseListener);

    void postRequestBody(String url, RequestBody body,HttpResponseListener responseListener);

    void postFormData(String url, Map<String, Object> map ,HttpResponseListener responseListener);

    void postFormDataFiles(String url, Map<String, Object> map, List<File> files, MediaType contentType,HttpResponseListener responseListener);


}
