package com.tb.rx_retrofit.tools.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;



/**
 * @描述： － 植入String 转换器
 * 参照GsonConverter 源码 来实现的 入参json，返回String
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 上午11:13
 * @最后更新时间：17/11/16 上午11:13
 */
public class StringConverterFactory extends Converter.Factory {


    public static StringConverterFactory create () {
        return new StringConverterFactory();
    }

    private final Gson gson;
    private StringConverterFactory () {
        gson = new Gson();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter (Type type, Annotation[] annotations,
                                                             Retrofit retrofit) {
        return new StringResponseBodyConverter();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }
}

