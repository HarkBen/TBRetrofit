package com.tb.rx_retrofit.fuction;

import com.tb.rx_retrofit.tools.RxHttpLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;

/**
 * @描述： -遇到异常后的 重试机制
 * - 只在超时时
 * @作者：zhusw
 * @创建时间：17/11/20 下午2:24
 * @最后更新时间：17/11/20 下午2:24
 */
public class RetryWhenTimeout implements Func1<Observable<? extends Throwable>, Observable<?>> {
    final public static String TAG = "RetryWhenTimeout";


    final static private int INTERVAL = 5;//单位 s
    final static private int RETRY_RANGE = 3;
    private int retryCount = 0;


    public RetryWhenTimeout () {
    }

    @Override
    public Observable<?> call (Observable<? extends Throwable> observable) {

        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call (Throwable throwable) {
                    if((++retryCount <= RETRY_RANGE)
                            && (throwable instanceof ConnectException
                            || throwable instanceof SocketTimeoutException
                            || throwable instanceof TimeoutException)){
                        RxHttpLog.printI(TAG,"超时链接  开启重试 － 当前次数:" + retryCount);
                        return Observable.timer(INTERVAL, TimeUnit.SECONDS);
                    }
                return Observable.error(throwable);
            }
        });
    }
}
