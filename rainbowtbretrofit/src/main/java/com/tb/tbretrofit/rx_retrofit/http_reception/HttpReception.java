package com.tb.tbretrofit.rx_retrofit.http_reception;


import com.tb.tbretrofit.rx_retrofit.http_contact.HttpExcuterContactI;

/**
 * @描述： -
 * - 网络请求 对外直接使用接口
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:14
 * @最后更新时间：17/11/15 下午3:14
 */
 public class HttpReception {



    private HttpReception(){


    }

    public static  HttpReception  create(){
        return new HttpReception();
    }


    public <T> void  get(String url ,HttpResponseListener<T> listener){


    }


    public <T> void post(String url,String json,HttpResponseListener<T> listener){


    }




}
