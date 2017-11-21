package com.tb.rx_retrofit.tools.exception;

/**
 * Create on 2016/11/9.
 * github  https://github.com/HarkBen
 * Description:
 * -----重复构建异常------
 * author Ben
 * Last_Update - 2016/11/9
 */
public class RepeatBuildException extends RuntimeException {
    public RepeatBuildException(){
        this("Client was builded.In an application you can hold only one okHttpClient instance");
    }
    public RepeatBuildException(String errorMsg){
        super(errorMsg);
    }

}
