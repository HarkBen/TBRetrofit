```java
   /**
         *这里使用的LogInterceptor 内部采用的也是日志类 TbLog　
         *对于 Tblog的 debug 模式的更改这里统一受约束
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


```