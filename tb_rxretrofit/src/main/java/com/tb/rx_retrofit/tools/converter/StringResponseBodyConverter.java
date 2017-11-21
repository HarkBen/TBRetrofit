package com.tb.rx_retrofit.tools.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @描述： － 响应 String 转换器
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 上午11:13
 * @最后更新时间：17/11/16 上午11:13
 */
public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert (ResponseBody value) throws IOException {

        if (null == value || value.contentLength() < 1) {
            return "";
        }
        try {
            return value.string();
        } finally {
            value.close();

        }


    }
}
