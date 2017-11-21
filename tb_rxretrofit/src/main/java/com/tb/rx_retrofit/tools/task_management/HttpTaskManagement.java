package com.tb.rx_retrofit.tools.task_management;

import rx.Subscription;

/**
 * @描述： - 管理
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 下午2:18
 * @最后更新时间：17/11/16 下午2:18
 */
public interface HttpTaskManagement<T> {

    void addSubscription(T tag, Subscription subscription);
    void unSubscribe(T tag);
    void unSubscribeAll();
    void remove(T tag);
    void abandonAll();

}
