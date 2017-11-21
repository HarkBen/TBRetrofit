![pppm.jpg](picture/pppm.png)

# TBRetrofit 2.0
#### Tips:2.0版本已完成。完整替换为Rxjava＋retrofit模式，正在补充文档

**V 1.4 更新：**
     <p> 1. 支持添加多个拦截器 </p> 
     <p> 2. 支持添加多个转换器 </p> 
     <p> 3. 简化Service拓展 </p> 
     ***
**V 1.0 ：**
    <p> 1. 对各个初始化Factory进行封装，统一配置，更换日志打印拦截器，提供debug模式 </p>
    <p> 2. 对Retrofit 接口定义进行了常用模式归纳，具体请求模式限定和抽象 </p>
    <p> 3. 对请求参数统一格式化(GET , JSON,两种 FormData),保持入口参数设置方式相同，方便使用 </p>
    <p> 4. 对CallBack返回统一使用String泛型，替换GsonConveter 为StringConverter </p>
    <p> 5. 除了CallBack<String> 固定其他Factory类都可以解耦单独立使用 </p>
    ***

  
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



---

** 最后附上后台接口示例代码  **：[后台测试接口示例](https://github.com/HarkBen/TBRetrofit/tree/master/server);
