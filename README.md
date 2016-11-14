![pppm.jpg](picture/pppm.png)


# TBRetrofit 1.3
#### Top：基于square 出的 Retrofit 底层框架封装的 http请求库


**说明:**
    <p> 1. 对各个初始化Factory进行封装，统一配置，更换日志打印拦截器，提供debug模式 </p>
    <p> 2. 对Retrofit 接口定义进行了常用模式归纳，具体请求模式限定和抽象 </p>
    <p> 3. 对请求参数统一格式化(GET , JSON,两种 FormData),保持入口参数设置方式相同，方便使用 </p>
    <p> 4. 对CallBack返回统一使用String泛型，替换GsonConveter 为StringConverter </p>
    <p> 5. 除了CallBack<String> 固定其他Factory类都可以解耦单独立使用 </p>
    
  

  
## Gradle
> Step1. 在你的**根build.gradle**文件中增加JitPack仓库依赖。

```gradle
         allprojects {
                repositories {
                 jcenter()
                 maven { url "https://jitpack.io" }
                }
            }
```  

> Step2. 在你的module的build.gradle文件中增加TBRetrofit依赖。

```gradle
        dependencies {
	          compile 'com.github.HarkBen:TBRetrofit:1.4'
	    }   
```   

## 代码示例

#### 1.初始化
```java
          OkHttpClient okHttpClient =  TBOkHttpClientFactory.Builder.create()
                          .setDebug(true)
                          .syncCookie(this)
                          .setTimeout_connection(10)
                          .setTimeout_read(10)
                          .setTimeout_write(10)
                          .build();
                  TBRetrofitFactory.Builder.create()
                          .setBaseUrl(API.BASEURL)
                          .setOkHttpClient(okHttpClient)
                          .builder();
          
                  //自己写个工具类对初始化构建进行封装就好。
                  //对于转译工厂TBTranslateFactory，对TBRetrofitFactory是强制依赖的。
                  //关于拓展，需要更换CallBack返回，或者增加delete put insert接口时，只需要写自己的
                  //Service 并继承 TBRetrofitService ,按需拓展TBTranslateFactory
                  
```

 > 初始化需要使用三个工厂来构建，这样的好处是解除隐藏关联
   坏处是，构建的代码多一点，但是自己写个工具类对初始化构建进行封装就好。
   


#### 2.发送请求

 > TBRequest 对接口操纵的简化只是提供一个思路，你完全可以按自己的方式来包装。

**1.GET--NORMAL** 

```java
        void GETByNormal() {
                TBRequest.create()
                        .put("user", "HarkBen")
                        .get(API.GITHUB_NORMAL, new TBCallBack() {
                            @Override
                            public void onSuccess(int code, String body) {
                                showResult(code + "--" + body);
                            }
        
                            @Override
                            public void onFailed(String errorMsg) {
                                showResult(errorMsg);
        
                            }
        
                        });
            }

 
```

**2.GET--RESTFUL**

```java
        void GETByRestful() {
                TBRequest.create()
                        .get(API.GITHUB_RESTFUL, new String[]{"Harkben"}, new TBCallBack() {
                            @Override
                            public void onSuccess(int code, String body) {
                                showResult(code + "--" + body);
                            }
        
                            @Override
                            public void onFailed(String errorMsg) {
                                showResult(errorMsg);
                            }
                        });
            }


```

**3.POST--Json**

```java
         void POSTByJson() {
                String url = API.LOGINTOBR;
                TBRequest.create()
                        .put("username", "tigger")
                        .put("password", "111111")
                        .put("weight", "130Kg")
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

**4.POST--FormData**

```java
         void POSTFormData(){
                String url = API.FORMDATA;
                TBRequest.create()
                        .put("username", "cat")
                        .put("password", "222222")
                        .put("weight", "4Kg")
                        .postFormData(url, new TBCallBack() {
                            @Override
                            public void onSuccess(int code, String body) {
                                showResult(code + "--" + body);
                            }
        
                            @Override
                            public void onFailed(String errorMsg) {
                                showResult("onFailed=="+errorMsg);
                            }
                        });
            }


```

**5.POST--Multipart**

```java
         void uploadFiles(){
                File file1 = new File(parentPath+"291739323217867.png");
                File file2 = new File(parentPath+"291733413425432.png");
                File file3 = new File(parentPath+"222004413632569.png");
                List<File> files = new ArrayList<>();
                files.add(file1);
                files.add(file2);
                files.add(file3);
                TBRequest.create()
                        .put("name","小二郎他爸")
                        .put("arg",54)
                        .put("gender","男")
                        .postFormDataFiles(API.UPLOADFILE, files, new TBCallBack() {
                            @Override
                            public void onSuccess(int code, String body) {
                                showResult(code+"--"+body);
                            }
        
                            @Override
                            public void onFailed(String errorMsg) {
                                showResult("onFailed--"+errorMsg);
                            }
                        });
            }

```

>PS:除了GET的REST 模式 在其他的请求设置参数时都可以使用   TBRequest.create().setParams(map)

<br>

---
 **关于拓展：**
 
关于拓展，需要更换CallBack返回，或者增加delete put insert接口时，只需要写自己的
   Service 并继承 TBRetrofitService ,继承TBRequestExecute类重写createService方法，创建自己的Service即可   

** 最后附上后台接口示例代码：[后台测试接口示例](https://github.com/HarkBen/TBRetrofit/tree/master/server);