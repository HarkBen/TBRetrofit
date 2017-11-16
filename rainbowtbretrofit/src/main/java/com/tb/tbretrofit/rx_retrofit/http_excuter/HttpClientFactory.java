package com.tb.tbretrofit.rx_retrofit.http_excuter;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tb.tbretrofit.rx_retrofit.tools.CacheInterceptor;
import com.tb.tbretrofit.rx_retrofit.tools.LogInterceptor;
import com.tb.tbretrofit.rx_retrofit.tools.RxHttpLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午3:58
 * @最后更新时间：17/11/15 下午3:58
 */
public class HttpClientFactory {
    private static OkHttpClient okHttpClient;

    private HttpClientFactory(){

    }

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

        private boolean syncCookie = false;
        private Context context = null;

        private static List<Interceptor> mInterceptors;

        private boolean autoCache = false;
        private Cache cache = null;




        public Builder() {
            mInterceptors = new ArrayList<>();
        }



        public HttpClientFactory.Builder addInterceptor(Interceptor interceptor) {
            mInterceptors.add(interceptor);
            return this;
        }

        public HttpClientFactory.Builder autoCache(Context context){
            this.autoCache = true;
            //使用加密缓存区
            File cacheFile = new File(context.getExternalCacheDir(),"cache");
            int cacheSize  = 10*1024*1024;
            cache = new Cache(cacheFile,cacheSize);
            return this;
        }

        public HttpClientFactory.Builder setDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public HttpClientFactory.Builder setTimeout_read(int timeout_read) {
            this.TIMEOUT_READ = timeout_read;
            return this;
        }

        public HttpClientFactory.Builder setTimeout_connection(int timeout_connection) {
            this.TIMEOUT_CONNECTION = timeout_connection;
            return this;
        }

        public HttpClientFactory.Builder setTimeout_write(int TIMEOUT_WRITE) {
            this.TIMEOUT_WRITE = TIMEOUT_WRITE;
            return this;

        }

        public HttpClientFactory.Builder syncCookie(Application context) {
            if(null == context){
                syncCookie = false;
                return null;
            }else {
                syncCookie = true;
                this.context = context;
                return this;
            }
        }

        /**
         * 这里使用的LogInterceptor 内部采用的也是日志类 RxHttpLog
         * 对于 Tblog的 debug 模式的更改这里统一受约束
         * 保证所有Client统一性，不允许重复调用build()方法
         * @return
         */
        public OkHttpClient  build() {
            if (null == okHttpClient) {
                synchronized (HttpClientFactory.class) {
                    if (null == okHttpClient) {
                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        builder.connectTimeout(this.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
                        builder.readTimeout(this.TIMEOUT_READ, TimeUnit.SECONDS);
                        builder.writeTimeout(this.TIMEOUT_WRITE, TimeUnit.SECONDS);
                        if ( syncCookie && null != context) {
                            ClearableCookieJar cookieJar =
                                    new PersistentCookieJar(new SetCookieCache(),
                                            new SharedPrefsCookiePersistor(context));
                            builder.cookieJar(cookieJar);
                        }
                        LogInterceptor logInterceptor = new LogInterceptor();
                        //打印全部信息 包括报文和具体响应体，
                        logInterceptor.setLevel(LogInterceptor.Level.BODY);
                        builder.addInterceptor(logInterceptor);
                        if(autoCache){
                            //添加缓存支持
                            builder.cache(cache);
//                            builder.addInterceptor(new CacheInterceptor());

                        }

                        for (Interceptor interceptor : mInterceptors) {
                            builder.addInterceptor(interceptor);
                        }

                        okHttpClient = builder.build();
                        RxHttpLog.setDeBug(isDebug);
                    }
                }
            }
            return okHttpClient;
        }
    }


    public static final OkHttpClient getOkHttpClient() {
        if (null == okHttpClient) {
            throw new NullPointerException("uh~. When you initializing  TBOkHttpClientFactory you didn't build okHttpClient");
        } else {
            return okHttpClient;
        }
    }

}
