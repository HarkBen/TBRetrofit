package com.tb.tbretrofit.httputils.exception;

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
        this("Client was builded.Don't repeat build." +
                "may you should invoking getInstance() ");
    }
    public RepeatBuildException(String errorMsg){
        super(errorMsg);
    }

}
