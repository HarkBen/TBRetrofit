![pppm.jpg](picture/pppm.png)

# TBRetrofit 2.0
#### Tips:2.0版本已完成。完整替换为Rxjava＋retrofit模式。
[![](https://www.jitpack.io/v/HarkBen/Tbretrofit.svg)](https://www.jitpack.io/#HarkBen/Tbretrofit)

**V 2.0
    1.对外保持 通用API 及回调,get,postJson,formData,files
    2.缓存插拔式使用－支持永久缓存 并自动初始化首次数据
    3.cookie session token 过期自动刷新策略
    4.超时自动重试
    5.自动于Context绑定，无需担心释放
    6.集中处理所望网络异常 并分类下发






  
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
        	        compile 'com.github.HarkBen:Tbretrofit:2.0'
        	}
```   




** 最后附上后台接口示例代码  **：[后台测试接口示例](https://github.com/HarkBen/TBRetrofit/tree/master/server);
