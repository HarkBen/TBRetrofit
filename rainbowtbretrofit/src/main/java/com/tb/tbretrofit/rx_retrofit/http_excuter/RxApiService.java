package com.tb.tbretrofit.rx_retrofit.http_excuter;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @描述： -针对请求类型 和请求参数类型的接口
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:37
 * @最后更新时间：17/11/15 下午3:37
 */
public interface RxApiService {

    /**
     * @RequestMapping（/users/{name}/{id}）
     * @param url
     * @return
     */
    @GET
    Observable<Response<String>> get(@Header("Cache-Control") String cacheConfig, @Url String url);


    /**
     * 普通模式
     * @RequestMapping（/users/?name=xx&id=xx）
     * @param url
     * @param map
     * @return
     */
    @GET
    Observable<Response<String>> get(@Url String url, @QueryMap Map<String, Object> map);


    /**
     * 单纯表单-不带文件
     * @param url
     * @param map
     * @return
     */
    @POST
    @FormUrlEncoded
    //自动设置 Content-Type:application/x-www-form-urlencoded
    Observable<Response<String>> postForm(@Url String url, @FieldMap Map<String, Object> map);

    /**
     * 直接提交json字符串
     * @param url
     * @return
     */
    @POST
    @Headers("Content-Type:application/json")
    Observable<Response<String>> postJson(@Url String url, @Body String body);

    /**
     * 直接提交entity
     * @param url
     * @return
     */
    @POST
    @Headers("Content-Type:application/json")
    Observable<Response<String>> postJson(@Url String url, @Body JsonBody body);


    /**
     * 独立的参数类型，针对Content－type特别变更时使用
     * RequestBody 可以单独设置 content-type
     * @param url
     * @return
     */
    @POST
    Observable<Response<String>> postIndependent(@Url String url, @Body RequestBody body);


    @POST
    Observable<Response<String>> postFormDataFiles(@Url String url, @Body MultipartBody body);


}
