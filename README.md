![pppm.jpg](picture/pppm.png)

# TBRetrofit 2.2
#### Tips:2.2版本已完成。完整替换为Rxjava＋retrofit模式。
[![](https://www.jitpack.io/v/HarkBen/TBRetrofit.svg)](https://www.jitpack.io/#HarkBen/TBRetrofit)


### 框架图

![](picture/tbretrofitV2.0.png)

#### Gradle
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
        	        compile 'com.github.HarkBen:TBRetrofit:2.2'
        	}
```   

## 必要权限
```html
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
```

## 初始化
```java
OkHttpClient client = new HttpClientFactory.Builder()
                .setDebug(true)
                .autoCache(this)
                .syncCookie(this)
                //session cookie 自动更新
                .addInterceptor(new SignInvalidInterceptor(this,API.loginUrl,PostDataUtils.getSiginParameter()))
                .build();
        RetrofitFactory.Builder.create()
                .setBaseUrl("http://www.aa.com")
                .setOkHttpClient(client)
                .init();

```


## API 调用示例
```java
 HttpUtils.getHttpApi().get(url,listener);
 HttpUtils.getHttpApi().postJson(url,jsonEntity,listener);
```

 **没有专门为2.0版本写server，用的实际项目测试。这里是一个Spring boot 微框架，下载即用。**：[后台测试接口示例](https://github.com/HarkBen/TBRetrofit/tree/master/server);
抱歉我还有个问题，我想知道，jitpack引用对于gradle版本有要求吗，兼容性或者支持


### LICENSES

   Copyright {2017} {HarkBen}

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
