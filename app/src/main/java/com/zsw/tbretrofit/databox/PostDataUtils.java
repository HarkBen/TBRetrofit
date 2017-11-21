package com.zsw.tbretrofit.databox;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/21 下午2:21
 * @最后更新时间：17/11/21 下午2:21
 */
public class PostDataUtils {

    public static SiginParameter getSiginParameter(){

        SiginParameter loginParameter = new SiginParameter();
        loginParameter.setClient_flag("android");
        loginParameter.setClientMobileVersion("1.0");
        loginParameter.setLocale("zh");
        loginParameter.setLoginName("sunkui@visionet.com.cn");
        loginParameter.setPassword("123456");
        loginParameter.setModel("Samsung Galaxy Note3 N9009");
        return loginParameter;
    }
    public static LearnParameter getLearnParameter(){

        LearnParameter learnParameter = new LearnParameter();
        learnParameter.setPageInfo(new LearnParameter.PageInfoBean(1,5));
        return learnParameter;
    }
}
