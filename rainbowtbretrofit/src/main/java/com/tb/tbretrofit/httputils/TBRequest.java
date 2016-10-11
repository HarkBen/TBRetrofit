package com.tb.tbretrofit.httputils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tb.tbretrofit.httputils.factory.RequestInterface;
import com.tb.tbretrofit.httputils.factory.TBCallBack;
import com.tb.tbretrofit.httputils.factory.TBRequestFactory;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    private RequestInterface request;
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
     * @param tBCallBack
     */
    public void get(String url, TBCallBack tBCallBack){
        if(null == params || params.isEmpty()){
            request.get(url,tBCallBack);
        }else {
            request.get(url, params,tBCallBack);
        }
    }

    /**
     * RESTFUL  模式
     * @param url
     * @param values
     * @param tBCallBack
     */
    public void get(String url,String[] values, TBCallBack tBCallBack){
        request.get(url, values,tBCallBack);
    }


    // POST

    /**
     * 将map参数转换为 JSON格式提交
     * Content-Type: application/json
     * @param url
     * @param tbCallBack
     */
    public void postJson(String url,TBCallBack tbCallBack){
        request.postJson(url,map2JSONObject(),tbCallBack);
    }


    /**
     * 在外部构建参数 RequestBody
     * 设置参数类型和Content-Type
     * @param url
     * @param body
     * @param tbCallBack
     */
    public void postRequestBody(String url,RequestBody body,TBCallBack tbCallBack){
        request.postRequestBody(url,body,tbCallBack);
    }


    /**
     * 单纯表单
     * Content-Type: application/x-www-form-urlencoded
     * @param url
     * @param tBCallBack
     */
    public void postFormData(String url,TBCallBack tBCallBack){
        request.postFormData(url,params, tBCallBack);
    }

    public void postFormDataFile(String url,@NonNull File file ,TBCallBack tbCallBack){
        postFormDataFile(url,file,null,tbCallBack);
    }
    public void postFormDataFiles(String url, @NonNull List<File> files, TBCallBack tbCallBack){
        postFormDataFiles(url,files,null,tbCallBack);
    }

    public void postFormDataFile(String url, @NonNull  File file, @Nullable String contentType, TBCallBack tBCallBack) {
        if(null == file) throw new NullPointerException("Hi Man!  the file is null!");
        if(file.isDirectory()) throw new NullPointerException("oh Shit! the file is Directory,don't use floder!");
        List<File> files = new ArrayList<>();
        files.add(file);
        postFormDataFiles(url,files,contentType,tBCallBack);
    }

    /**
     * 上传多个文件
     * @Body MultipartBody body);
     * @param url
     * @param files
     * @param contentType
     * @param tbCallBack
     */
    public void postFormDataFiles(String url, List<File> files, @Nullable String contentType, TBCallBack tbCallBack){
        MediaType mediatype = null;
        if(null != contentType && !contentType.equals(""))
            mediatype = MediaType.parse(contentType);
        else
            mediatype = MediaType.parse("octet-stream");

        request.postFormDataFiles(url,params,files,mediatype,tbCallBack);
    }


}
