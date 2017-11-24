package com.tb.rx_retrofit.tools.converter;

import com.tb.rx_retrofit.tools.RxHttpLog;

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
        //这里不用拦截 contentLength ＝＝－1？： 源码中转string 示已经判断了
        RxHttpLog.printI("StringResponseBodyConverter","ResponseBody:"+value.contentLength());
        try {
            return value.string();
        } finally {
            value.close();

        }


    }
}
