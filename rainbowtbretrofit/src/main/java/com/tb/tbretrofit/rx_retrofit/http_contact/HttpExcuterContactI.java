package com.tb.tbretrofit.rx_retrofit.http_contact;

import com.tb.tbretrofit.rx_retrofit.http_excuter.JsonBody;
import com.tb.tbretrofit.rx_retrofit.http_reception.HttpResponseListener;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * @描述： -对外接受参数规范定义
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:56
 * @最后更新时间：17/11/15 下午3:56
 */
public interface HttpExcuterContactI {

    void get(String url);

    void get(String url, String[] values);

    void get(String url, Map<String, Object> map);

    void postJson(String url, JsonBody json);

    void postRequestBody(String url, RequestBody body);

    void postFormData(String url, Map<String, Object> map );

    void postFormDataFiles(String url, Map<String, Object> map, List<File> files, MediaType contentType);


}
