package com.tb.tbretrofit.httputils.tools;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Create on 2016/9/27.
 * github  https://github.com/HarkBen
 * Description: response转换器- -！
 * -----------
 * author Ben
 * Last_Update - 2016/9/27
 */
public class StringResponseBodyConverter implements Converter<ResponseBody,String>{
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}
