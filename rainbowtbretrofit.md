# TBRequest 的使用说明
*   提供OkHttp和Retrofit初始化配置
    对 GET POST 请求 文件上传 简化封装

    **代码示例**
   
    ```java
         void POSTByJson() {
                String url = API.LOGINTOBR;
                TBRequest.create()
                        .put("username", "zhusw")
                        .put("password", "333333")
                        .put("client_flag", "android")
                        .put("model", "SCL-AL00")
                        .put("locale", "zh")
                        .postJson(url, new TBCallBack() {
                            @Override
                            public void onSuccess(int code, String body) {
                                showResult(code + "--" + body);
                            }
        
                            @Override
                            public void onFailed(String errorMsg) {
                                showResult("error==" + errorMsg);
                            }
                        });
            }
    
    
    ```