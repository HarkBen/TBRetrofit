
# RainbowTBRetrofit
#### Top：基于square 出的 Retrofit 底层框架封装的 http请求库


**说明:**
    <p> 1. 对各Factory进行初始化封装，更换日志打印拦截器，提供debug模式 </p>
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

> Step2. 在你的module的build.gradle文件中增加RainbowLibrary依赖。
```gradle
        dependencies {
	          compile 'com.github.HarkBen:Tbretrofit:1.0'
	    }   
```   

## 代码示例

#### 1.初始化
```java
            public class CustomAPL extends Application {
            
                @Override
                public void onCreate() {
                    super.onCreate();
                    TBOkHttpClientFactory.Builder.create()
                            .setDebug(true)
                            .syncCookie(this)
                            .setTimeout_connection(10)
                            .setTimeout_read(10)
                            .setTimeout_write(10)
                            .build();
                    TBRequestFactory.build(TBRetrofitFactory.getInstance(API.BASEURL));
            
                }
            }
```

#### 2.发送请求
>
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

>PS:除了GET的RESTFUL 模式 在其他的请求设置参数时都可以使用   TBRequest.create().setParams(map)

