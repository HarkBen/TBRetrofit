package com.tb.rx_retrofit.tools;

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

    //访问的目标主机不存在
    final public static int CODE_UNKNOW_HOST = -104;

    //有网但请求失败包含：网络未授权访问外部地址，只读缓存时缓存区无缓存，等。。。
    final public static int CODE_RESPONSE_ERROR = -105;

    //本地使用gson 解析数据 错误
    final public static int CODE_DATA_FORMAT_FAILURE = -106;

    //请求成功但 没有数据返回
    final public static int CODE_DATA_FORMAT_FAILURE_NO_DATA = -107;

    //未知网络错误
    final public static int CODE_UNKNOW = -108;

    //网络请求错误 状态码 网络错误 40x 50x
    final public static int CODE_REQUEST_ERROR = -109;
}
