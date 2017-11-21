package com.zsw.tbretrofit.databox;

import com.tb.rx_retrofit.http_presenter.JsonBody;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/20 下午4:12
 * @最后更新时间：17/11/20 下午4:12
 */
public class SiginParameter implements JsonBody{

    /**
     * loginName : sunkui@visionet.com.cn
     * password : 123456
     * client_flag : android
     * model : Samsung Galaxy Note3 N9009
     * locale : zh
     * clientMobileVersion : 1.0
     */

    private String loginName;
    private String password;
    private String client_flag;
    private String model;
    private String locale;
    private String clientMobileVersion;

    public String getLoginName () {
        return loginName;
    }

    public void setLoginName (String loginName) {
        this.loginName = loginName;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getClient_flag () {
        return client_flag;
    }

    public void setClient_flag (String client_flag) {
        this.client_flag = client_flag;
    }

    public String getModel () {
        return model;
    }

    public void setModel (String model) {
        this.model = model;
    }

    public String getLocale () {
        return locale;
    }

    public void setLocale (String locale) {
        this.locale = locale;
    }

    public String getClientMobileVersion () {
        return clientMobileVersion;
    }

    public void setClientMobileVersion (String clientMobileVersion) {
        this.clientMobileVersion = clientMobileVersion;
    }
}
