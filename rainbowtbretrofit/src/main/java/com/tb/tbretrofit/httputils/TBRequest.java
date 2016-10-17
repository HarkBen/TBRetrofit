package com.tb.tbretrofit.httputils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tb.tbretrofit.httputils.factory.TBRequestFactory;
import com.tb.tbretrofit.httputils.factory.TBRequestService;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * Create on 2016/9/28.
 * github  https://github.com/HarkBen
 * Description:
 * -----构建参数和选择请求方式
 * 支持： GET 普通/REST FUL
 *       POST 表单/json/文件上传
 * ------
 * author Ben
 * Last_Update - 2016/9/29
 */
public class TBRequest {
    private Map<String,Object> params = null;
    private TBRequestService request;
    /**
     * 默认创建一个Obj Map
     * 填充基础参数
     */
    private TBRequest(){
        params = new HashMap<>();
        request = TBRequestFactory.getInstance();
    }

    public static TBRequest create(){
        return new TBRequest();
    }

    public TBRequest put(String key, Object value){
        params.put(key,value);
        return  this;
    }

    public TBRequest setParams(Map<String,Object> params){
        this.params = params;
        return  this;
    }

    private JSONObject map2JSONObject(){
        return new JSONObject(params);
    }

    /**
     * 普通模式
     * 拼接参数直接使用 map即可
     * @param url
     * @param callback
     */
    public void get(String url, Callback<String> callback){
        if(null == params || params.isEmpty()){
            request.get(url,callback);
        }else {
            request.get(url, params,callback);
        }
    }

    /**
     * RESTFUL  模式
     * @param url
     * @param values
     * @param callback
     */
    public void get(String url,String[] values, Callback<String> callback){
        request.get(url, values,callback);
    }


    // POST

    /**
     * 将map参数转换为 JSON格式提交
     * Content-Type: application/json
     * @param url
     * @param callback
     */
    public void postJson(String url,Callback<String> callback){
        request.postJson(url,map2JSONObject(),callback);
    }


    /**
     * 在外部构建参数 RequestBody
     * 设置参数类型和Content-Type
     * @param url
     * @param body
     * @param callback
     */
    public void postRequestBody(String url,RequestBody body,Callback<String> callback){
        request.postRequestBody(url,body,callback);
    }


    /**
     * 单纯表单
     * Content-Type: application/x-www-form-urlencoded
     * @param url
     * @param callback
     */
    public void postFormData(String url,Callback<String> callback){
        request.postFormData(url,params, callback);
    }

    public void postFormDataFile(String url,@NonNull File file ,Callback<String> callback){
        postFormDataFile(url,file,null,callback);
    }
    public void postFormDataFiles(String url, @NonNull List<File> files, Callback<String> callback){
        postFormDataFiles(url,files,null,callback);
    }

    public void postFormDataFile(String url, @NonNull  File file, @Nullable String contentType, Callback<String> callback) {
        if(null == file) throw new NullPointerException("Hi Man!  the file is null!");
        if(file.isDirectory()) throw new NullPointerException("oh Shit! the file is Directory,don't use floder!");
        List<File> files = new ArrayList<>();
        files.add(file);
        postFormDataFiles(url,files,contentType,callback);
    }

    /**
     * 上传多个文件
     * @Body MultipartBody body);
     * @param url
     * @param files
     * @param contentType
     * @param callback
     */
    public void postFormDataFiles(String url, List<File> files, @Nullable String contentType, Callback<String> callback){
        MediaType mediatype = null;
        if(null != contentType && !contentType.equals(""))
            mediatype = MediaType.parse(contentType);
        else
            mediatype = MediaType.parse("octet-stream");

        request.postFormDataFiles(url,params,files,mediatype,callback);
    }


}
