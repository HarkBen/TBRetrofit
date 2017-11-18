package com.tb.tbretrofit.rx_retrofit.http_contact;

import com.tb.tbretrofit.rx_retrofit.http_excuter.JsonBody;
import com.tb.tbretrofit.rx_retrofit.http_receiver.HttpResponseListener;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @描述： -对外接受参数规范定义
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:56
 * @最后更新时间：17/11/15 下午3:56
 */
public interface HttpContactI {


    void get(String url,HttpResponseListener responseListener);

    void get(String url, String[] values,HttpResponseListener responseListener);

    void get(String url, Map<String, Object> map,HttpResponseListener responseListener);

    void postJson(String url, JsonBody json,HttpResponseListener responseListener);

    void postJson(String url,  String json,HttpResponseListener responseListener);

    void postRequestBody(String url, RequestBody body,HttpResponseListener responseListener);

    void postFormData(String url, Map<String, Object> map ,HttpResponseListener responseListener);

    void postFormDataFiles(String url, Map<String, Object> map, List<File> files, MediaType contentType,HttpResponseListener responseListener);


}
