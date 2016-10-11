package com.tb.tbretrofit.httputils.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Create on 2016/9/27.
 * github  https://github.com/HarkBen
 * Description: String 转换器工厂
 * -----------
 * author Ben
 * Last_Update - 2016/9/27
 */
public class StringConverterFactory extends Converter.Factory {

    //抄源码的感觉 简直爽到不行----
    //我们照着GsonConverterFactory 写自己的String转换器
    //他需要2个转换器类 responseBodyConverter  和 requestBodyConverter

    public static StringConverterFactory create(){
        return new StringConverterFactory();
    }
    private  StringConverterFactory(){

    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new StringResponseBodyConverter();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new StringRequestBodyConverter();
    }
}
