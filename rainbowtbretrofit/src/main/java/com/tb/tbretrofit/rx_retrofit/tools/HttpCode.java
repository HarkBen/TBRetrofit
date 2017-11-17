package com.tb.tbretrofit.rx_retrofit.tools;

/**
 * @描述： -本地需要拦截的网络状态码
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午4:29
 * @最后更新时间：17/11/15 下午4:29
 */
public class HttpCode {

    //无可用网络
    final public static int CODE_NO_INTERNET = -101;

    //网络链接但不可用 1。链接了需要认证的无线网络（会被重定向返回html页面代码） 2。链接了无Intent的局域网
    final public static int CODE_INTENET_IS_ABNORMAL = -102;

    //请求超时
    final public static int CODE_TIME_OUT = -103;

    //服务器不可用
    final public static int CODE_SERVICE_IS_NOT_AVAILABLE = -104;


    //本地使用gson 解析数据 错误
    final public static int CODE_DATA_FORMAT_FAILURE = -105;

    //请求成功但 无数据
    final public static int CODE_DATA_FORMAT_FAILURE_NO_DATA = -106;

    //未知错误
    final public static int CODE_UNKNOW = -107;
}
