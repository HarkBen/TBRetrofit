package com.tb.rx_retrofit.http_excuter;


import com.tb.rx_retrofit.exception.RepeatBuildException;
import com.tb.rx_retrofit.tools.StringConverterFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @描述： -配置Retrofit的工程
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:37
 * @最后更新时间：17/11/15 下午3:37
 */
public class RetrofitFactory {

    private static Retrofit retrofit;
    private volatile static RetrofitFactory retrofitFactory;

    private RetrofitFactory () {
    }


    public static class Builder {

        private String baseUrl;
        private OkHttpClient okHttpClient;

        private static List<Converter.Factory> bConverterFactorys;
        private static List<CallAdapter.Factory> bCallAdapterFactorys;

        private Builder() {

        }

        /**
         * 如果这里的 RetrofitFactory 需要单独使用，
         * 不使用{@link RxApiService}的接口定义 这里的baseUrl 则需要规范传入 http://www.xxx.com/
         * 否则传入一个假的即可 http://xx.x.com/
         *
         * @return
         */
        public Builder setBaseUrl(String bl) {
            this.baseUrl = bl;
            return this;
        }

        public Builder setOkHttpClient(OkHttpClient oc) {
            this.okHttpClient = oc;
            return this;
        }

        public Builder addConverFactory(Converter.Factory cf) {
            bConverterFactorys.add(cf);
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory caf) {
            bCallAdapterFactorys.add(caf);
            return this;
        }

        /**
         * Remmber!,You must be  set baseUrl !
         */
        public static Builder create() {
            bConverterFactorys = new ArrayList<>();
            bCallAdapterFactorys = new ArrayList<>();
            return new Builder();
        }

        public RetrofitFactory init() {
            if (null == retrofitFactory) {
                synchronized ((RetrofitFactory.class)) {
                    if (null == retrofitFactory) {
                        Retrofit.Builder builder = new Retrofit.Builder();
                        builder.client(okHttpClient);
                        builder.baseUrl(baseUrl);
                        for (Converter.Factory factory : bConverterFactorys) {
                            builder.addConverterFactory(factory);
                        }
                        for (CallAdapter.Factory factory : bCallAdapterFactorys) {
                            builder.addCallAdapterFactory(factory);
                        }
                        //添加其他适配和转换器，放在我们设置的后面防止被覆盖
                        builder.addConverterFactory(StringConverterFactory.create());
                        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                        retrofit = builder.build();
                      return   retrofitFactory = new RetrofitFactory();
                    }
                }
            }
            throw  new RepeatBuildException();
        }
    }


    public static RetrofitFactory getInstance() {
        if (null == retrofitFactory) {
            throw new NullPointerException("uh~,you didn't init RetrofitFactory ");
        }
        return retrofitFactory;
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }

}
