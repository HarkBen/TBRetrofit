package com.tb.tbretrofit.httputils.excute;

import com.tb.tbretrofit.httputils.factory.TBRetrofitFactory;
import com.tb.tbretrofit.httputils.factory.TBTranslateFactory;
import com.tb.tbretrofit.httputils.service.TBRetrofitService;

/**
 * Create on 2016/11/10.
 * github  https://github.com/HarkBen
 * Description:
 * -----创建接口Service，
 * 拓展：继承后重写createService创建自己的Service,
 * 注！新接口必须直接或间接继承TBRetrofitService
 * 通过重写父类的方法来修改入参包装，例如：添加header，
 * -----
 * author Ben
 * Last_Update - 2016/11/10
 */
public class TBRequestExecute extends TBTranslateFactory {

    private static TBRetrofitService requestService;

    public TBRequestExecute() {
        super();
    }

    @Override
    public TBRetrofitService createService() {
        if (null == requestService) {
            return requestService = TBRetrofitFactory.getInstance()
                    .createService(TBRetrofitService.class);
        } else {
            return requestService;
        }

    }


}
