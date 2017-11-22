package com.tb.rx_retrofit.http_receiver;

import com.tb.rx_retrofit.http_presenter.HttpApi;
import com.tb.rx_retrofit.http_presenter.RxHttpApiImpl;
import com.tb.rx_retrofit.tools.task_management.RxHttpTaskManagement;

/**
 * @描述： -
 * - 获取 Presenter 对象
 * @作者：zhusw
 * @创建时间：17/11/21 上午10:31
 * @最后更新时间：17/11/21 上午10:31
 */
public class HttpUtils {
    public static HttpApi getHttpApi(){
        return new RxHttpApiImpl(RxHttpTaskManagement.getINSTANCE());
    }

}
