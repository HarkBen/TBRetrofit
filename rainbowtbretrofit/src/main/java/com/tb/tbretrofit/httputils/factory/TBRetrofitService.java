package com.tb.tbretrofit.httputils.factory;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Create on 2016/9/27.
 * github  https://github.com/HarkBen
 * Description:
 * 声明基本请求接口,不能保证所有后台的接口都是给你个对象，所以这里返回数据类型都为String，不再支持直接返回序列化的对象和集合
 * 后面会单独封一个解析器取名就叫ConverUtils.class吧
 * 参数包含 Get POST(map 表单 bean) 参数值为基本数据类型和String
 * @Warning 不包含File及其他流式
 * 额外的大体积的数据请求，可以创建单独新的API接口并使用{@link TBRetrofitFactory}，它已经被初始化过了
 * author Ben
 * Last_Update - 2016/9/27
 */
public interface TBRetrofitService {

    /**
     * RESTFUL 模式
     * @RequestMapping（/users/{name}/{id}）
     * @param url
     * @return
     */
    @GET
    Call<String> get(@Url String url);

    /**
     * 普通模式
     * @RequestMapping（/users/?name=xx&id=xx）
     * @param url
     * @param map
     * @return
     */
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> map);


    /**
     * 单纯表单
     * @param url
     * @param map
     * @return
     */
    @POST
    @FormUrlEncoded//单纯表单 Content-Type:application/x-www-form-urlencoded
    Call<String> postForm(@Url String url, @FieldMap Map<String, Object> map);

    /**
     * 提交json格式
     * @param url
     * @return
     */
    @POST
    @Headers("Content-Type:application/json")
    Call<String> postJson(@Url String url, @Body String body);

    /**
     * RequestBody 可以单独设置 content-type
     * @param url
     * @return
     */
    @POST
    Call<String> post(@Url String url, @Body RequestBody body);

    @POST
    Call<String> postFormDataFiles(@Url String url, @Body MultipartBody body);

//    @POST
//    @Multipart
//    Call<String> upLoadPartFiles(@Url String url,@Part List<MultipartBody.Part> parts);

}
