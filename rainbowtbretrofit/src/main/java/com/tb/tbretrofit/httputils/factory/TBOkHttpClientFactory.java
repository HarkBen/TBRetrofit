package com.tb.tbretrofit.httputils.factory;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tb.tbretrofit.TbLog;
import com.tb.tbretrofit.httputils.tools.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Create on 2016/8/19.
 *
 * @author Ben
 *         Description-
 *         <p>
 *         github  https://github.com/HarkBen
 * @Last_update time - 2016年10月8日14:38:32
 */
public class TBOkHttpClientFactory {

    private static OkHttpClient okHttpClient;


    public static final class Builder {

        private boolean isDebug = false;

        /**
         * 读取超时
         */
        private int TIMEOUT_READ = 25;
        /**
         * 链接超时
         */
        private int TIMEOUT_CONNECTION = 25;
        /**
         * 写入超时
         * -这玩意儿为啥一般不会出现
         */
        private int TIMEOUT_WRITE = 25;
        private Context context;
        private Builder(){

        }
        public static final Builder create(){
            return new Builder();
        }

        public Builder setDebug(boolean isDebug){
            this.isDebug = isDebug;
            return this;
        }

        public Builder setTimeout_read(int timeout_read) {
            this.TIMEOUT_READ = timeout_read;
            return this;
        }

        public Builder setTimeout_connection(int timeout_connection) {
            this.TIMEOUT_CONNECTION = timeout_connection;
            return this;
        }

        public Builder setTimeout_write(int TIMEOUT_WRITE) {
            this.TIMEOUT_WRITE = TIMEOUT_WRITE;
            return this;
        }

        public Builder syncCookie(Context context) {
            this.context = context;
            return this;
        }

        /**
         *这里使用的LogInterceptor 内部采用的也是日志类 Ｌ　
         *对于 L的 debug 模式的更改这里统一受约束
         * @return
         */
        public  void build() {
            if (null == okHttpClient) {
                synchronized (TBOkHttpClientFactory.class) {
                    if (null == okHttpClient) {
                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        builder.connectTimeout(this.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
                        builder.readTimeout(this.TIMEOUT_READ, TimeUnit.SECONDS);
                        builder.writeTimeout(this.TIMEOUT_WRITE, TimeUnit.SECONDS);
                        if (null != context) {
                            ClearableCookieJar cookieJar =
                                    new PersistentCookieJar(new SetCookieCache(),
                                            new SharedPrefsCookiePersistor(context));
                            builder.cookieJar(cookieJar);
                        }
                           LogInterceptor logInterceptor = new LogInterceptor();
                            logInterceptor.setLevel(LogInterceptor.Level.BODY);
                            builder.addInterceptor(logInterceptor);
                            TbLog.setDeBug(isDebug);
                        okHttpClient = builder.build();
                    }
                }
            }
        }
    }


    public static final OkHttpClient getOkHttpClient() {
        if (null == okHttpClient) {
            throw new NullPointerException("uh~. When you initialize  TBOkHttpClientFactory,you didn't build OkClient");
        }else{
            return okHttpClient;
        }
    }
}
