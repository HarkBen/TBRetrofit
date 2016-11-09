package com.tb.tbretrofit.httputils.factory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tb.tbretrofit.httputils.exception.RepeatBuildException;
import com.tb.tbretrofit.httputils.model.TBRetrofitService;
import com.tb.tbretrofit.httputils.model.TBTranslateService;

import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * Create on 2016/9/27.
 * github  https://github.com/HarkBen
 * Description:
 * -----将请求任务按需交给合适的retrofitService执行 ------
 * author Ben
 * Last_Update - 2016/9/27
 */
public class TBTranslateFactory implements TBTranslateService {

    private static TBRetrofitService tBRetrofitService;

    private static TBTranslateService tBTranslateService;

    private TBTranslateFactory(TBRetrofitFactory tbRetrofitFactory) {
        tBRetrofitService = tbRetrofitFactory.createService(TBRetrofitService.class);
    }

    /**
     * 保证内部的RetrofitService 单例
     */
    public static TBTranslateService build(TBRetrofitFactory tbRetrofitFactory) {
        if (null == tBTranslateService) {
            synchronized (TBTranslateFactory.class) {
                if (null == tBTranslateService)
                  return   tBTranslateService = new TBTranslateFactory(tbRetrofitFactory);
            }
        }
        throw  new RepeatBuildException();
    }

    public static TBTranslateService getInstance() {
        if (null == tBTranslateService) {
            throw new NullPointerException(TBTranslateFactory.class.getPackage() + ".TBTranslateFactory does not build!");
        } else {
            return tBTranslateService;
        }
    }

    //GET 请求--------------------------------------

    /**
     * 无参
     *
     * @param url
     * @param callBack
     */
    @Override
    public void get(String url, Callback<String> callBack) {
        tBRetrofitService.get(url).enqueue(callBack);
    }

    /**
     * REST 模式
     *
     * @param url
     * @param callBack
     * @param values
     * @RequestMapping（/users/{name}/{id}）
     */
    @Override
    public void get(String url, String[] values, Callback<String> callBack) {
        if (null == values || values.length == 0) {
            get(url, callBack);
        } else {
            String va = "";
            for (String value : values) {
                va += "/" + value;
            }
            url = url + va;
            get(url, callBack);
        }
    }

    /**
     * 普通模式
     *
     * @param url
     * @param map
     * @param callBack
     * @RequestMapping（/users/?name=xx&id=xx）
     */
    @Override
    public void get(String url, Map<String, Object> map, Callback<String> callBack) {
        tBRetrofitService.get(url, map).enqueue(callBack);
    }


    //POST---------------------------------------------------------------------------

    /**
     * 接口泛型 String
     *
     * @param url
     * @param json
     * @param callBack
     */
    @Override
    public void postJson(String url, JSONObject json, Callback<String> callBack) {
        tBRetrofitService.postJson(url, json.toString()).enqueue(callBack);
    }

    /**
     * 接口泛型 RequestBody
     *
     * @param url
     * @param body
     * @param callBack
     */
    @Override
    public void postRequestBody(String url, RequestBody body, Callback<String> callBack) {
        tBRetrofitService.post(url, body).enqueue(callBack);
    }


    /**
     * 单纯表单提交
     * Content-Type:application/x-www-form-urlencoded
     *
     * @param url
     * @param map
     * @param callBack
     */
    @Override
    public void postFormData(String url, Map<String, Object> map, Callback<String> callBack) {
        tBRetrofitService.postForm(url, map).enqueue(callBack);
    }


    @Override
    public void postFormDataFiles(String url, @Nullable Map<String, Object> map, @NonNull List<File> files, MediaType contentType, Callback<String> callBack) {
        if (null == files) {
            throw new NullPointerException("files is null!");
        }
        if (files.size() == 0) {
            throw new IllegalArgumentException("files size  equal 0,You must  include at least one file!");
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            RequestBody body = RequestBody.create(contentType, file);
            builder.addFormDataPart("files", file.getName(), body);
        }
        if (null != map && !map.isEmpty()) {
            Iterator<Map.Entry<String, Object>> keys = map.entrySet().iterator();
            while (keys.hasNext()) {
                Map.Entry<String, Object> entry = keys.next();
                builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        MultipartBody multipartBody = builder.build();
        tBRetrofitService.postFormDataFiles(url, multipartBody).enqueue(callBack);
    }


}
