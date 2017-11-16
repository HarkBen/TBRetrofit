package com.tb.tbretrofit.rx_retrofit.http_reception;


import com.tb.tbretrofit.rx_retrofit.http_contact.RxHttpExecuteI;
import com.tb.tbretrofit.rx_retrofit.http_contact.RxHttpExecuteImpl;
import com.tb.tbretrofit.rx_retrofit.http_excuter.JsonBody;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @描述： -
 * - 网络请求 对外直接使用接口
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:14
 * @最后更新时间：17/11/15 下午3:14
 */
 public class HttpReception {

    private RxHttpExecuteI httpExcuterContactI;

    private HttpReception(){
        httpExcuterContactI = new RxHttpExecuteImpl();
    }

    public static  HttpReception  create(){
        return new HttpReception();
    }


    public  void  get(String url ,HttpResponseListener listener){
        httpExcuterContactI.get(url,listener);

    }


    public void get (String url, Map<String, Object> map, HttpResponseListener responseListener) {
        httpExcuterContactI.get(url,map,responseListener);
    }


    public void postJson (String url, JsonBody json,HttpResponseListener responseListener) {
        httpExcuterContactI.postJson(url,json,responseListener);
    }

    public void postJson (String url, String json,HttpResponseListener responseListener) {
        httpExcuterContactI.postJson(url,json,responseListener);
    }





}
