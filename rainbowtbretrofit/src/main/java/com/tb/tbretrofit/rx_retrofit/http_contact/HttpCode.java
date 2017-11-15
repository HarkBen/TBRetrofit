package com.tb.tbretrofit.rx_retrofit.http_contact;

/**
 * @描述： -本地需要拦截的网络状态码
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午4:29
 * @最后更新时间：17/11/15 下午4:29
 */
public class HttpCode {

    //无可用网络
    final public static int CODE_NOT_OPEN_INTENT = -101;

    //网络链接但不可用 1。链接了需要认证的无线网络（会被重定向返回html页面代码） 2。链接了无Intent的局域网
    final public static int CODE_INTENET_IS_NOT_AVALIABLE = -102;

    //请求超时
    final public static int CODE_TIME_OUT = -103;

    //服务器不可用
    final public static int CODE_SERVICE_IS_NOT_AVALIBALE = -104;



}
