package com.tb.tbretrofit.rx_retrofit.http_contact;


import android.util.Log;

import com.tb.tbretrofit.rx_retrofit.tools.RxHttpLog;

import java.util.HashMap;

import java.util.Set;

import rx.Subscription;

/**
 * @描述： -网络请求线程管理
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 下午12:03
 * @最后更新时间：17/11/16 下午12:03
 */
public class RxHttpTaskManagement implements HttpTaskManagement<Object> {

    private static HttpTaskManagement INSTANCE = null;

    private HashMap<Object, Subscription> tasks;

    private RxHttpTaskManagement () {
        tasks = new HashMap<>();
    }

    public static HttpTaskManagement getINSTANCE () {
        if (null == INSTANCE) {
            synchronized (RxHttpTaskManagement.class) {
                if (null == INSTANCE) {
                    INSTANCE = new RxHttpTaskManagement();
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public void addSubscription (Object tag, Subscription subscription) {

        tasks.put(tag, subscription);
        RxHttpLog.printI("RxHttpTaskmanagement","记录新订阅 key:"+tag.hashCode());
    }


    @Override
    public void unSubscribe (Object tag) {
        if (null == tasks || tasks.isEmpty()) return;
       RxHttpLog.printI("RxHttpTaskmanagement","执行取消订阅 key:"+tag.hashCode());
        Subscription subscription = tasks.get(tag);
        if(null != subscription && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
            RxHttpLog.printI("RxHttpTaskmanagement","取消了订阅 key:"+tag.hashCode());
            tasks.remove(tag);

        }

    }


    @Override
    public void unSubscribeAll () {
        if (!tasks.isEmpty()) return;
        Set<Object> keys = tasks.keySet();
        for (Object tag : keys) {
            unSubscribe(tag);
        }
    }


    @Override
    public void remove (Object tag) {
        if (!tasks.isEmpty()) tasks.remove(tag);
        RxHttpLog.printI("RxHttpTaskmanagement","移除了订阅 key:"+tag.hashCode());
    }

    /**
     * 抛弃之前已添加的所有任务接收者,但不负责回收
     * －潜意识里觉得需要这么一个方法～～～
     */
    @Override
    public void abandonAll () {
        if (!tasks.isEmpty()) {
            tasks.clear();
        }
    }


}
