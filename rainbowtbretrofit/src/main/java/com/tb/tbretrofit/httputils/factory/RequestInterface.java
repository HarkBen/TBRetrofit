package com.tb.tbretrofit.httputils.factory;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * Create on 2016/10/3.
 * github  https://github.com/HarkBen
 * Description:
 * -----构建参数和选择请求方式
 * 支持： GET 普通/REST FUL
 *       POST 表单/json/文件上传
 * ------
 * author Ben
 * Last_Update - 2016/10/3
 */
public interface RequestInterface {

    void get(String url, Callback<String> callBack);

    void get(String url, String[] values, Callback<String> callBack);

    void get(String url, Map<String, Object> map, Callback<String> callBack);

    void postJson(String url, JSONObject json, Callback<String> callBack);

    void postRequestBody(String url, RequestBody body, Callback<String> callBack);

    void postFormData(String url, Map<String, Object> map, Callback<String> callBack);

    void postFormDataFiles(String url, Map<String, Object> map, List<File> files, MediaType contentType, Callback<String> callBack);


}
