package com.tb.tbretrofit.httputils.factory;


import com.tb.tbretrofit.httputils.exception.RepeatBuildException;
import com.tb.tbretrofit.httputils.service.TBRetrofitService;
import com.tb.tbretrofit.httputils.tools.StringConverterFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Create on 2016/8/19.
 *
 * @author Ben
 *         Description-
 *         <p>
 *         github  https://github.com/HarkBen
 * @Last_update time - 2016年9月19日14:33:21
 */
public class TBRetrofitFactory {

    private static Retrofit retrofit;
    private volatile static TBRetrofitFactory retorfitManager;

    private TBRetrofitFactory() {
    }


    public static class Builder {

        private String baseUrl;
        private OkHttpClient okHttpClient;

        private static List<Converter.Factory> bConverterFactorys;
        private static List<CallAdapter.Factory> bCallAdapterFactorys;

        private Builder() {

        }

        /**
         * 如果这里的 TBRetrofitFactory 需要单独使用，
         * 不使用{@link TBRetrofitService}的接口定义 这里的baseUrl 则需要规范传入 http://www.xxx.com/
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
         * Remmber!,You must be  set baseUrl，please !
         */
        public static Builder create() {
            bConverterFactorys = new ArrayList<>();
            bCallAdapterFactorys = new ArrayList<>();
            return new Builder();
        }

        public TBRetrofitFactory builder(OkHttpClient okHttpClient) {
            if (null == retorfitManager) {
                synchronized ((TBRetrofitFactory.class)) {
                    if (null == retorfitManager) {
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
                      return   retorfitManager = new TBRetrofitFactory();
                    }
                }
            }
            throw  new RepeatBuildException();
        }
    }


    /**
     * 这里使用耦合性很强的组合关系，因为这里我不想retrofit被单独解耦使用。
     * 因为一旦那样，后面对接口和回掉的封装就毫无意义，相对来说因为只会使用
     * createService方法，所以后续对retrofit的增持不会对下有影响，依赖关系单一。
     *
     * @return
     */
    public static TBRetrofitFactory getInstance() {
        if (null == retorfitManager) {
            throw new NullPointerException("uh~,you didn't build TBRetrofitFactory ");
        }
        return retorfitManager;
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }

}
