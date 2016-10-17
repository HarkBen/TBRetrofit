package com.zsw.tbretrofit;



/**
 * Create on 2016/9/22.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/9/22
 */

public class API {
  private static   String getURL(String name){
        return BASEURL+name;
    }

    public static final String BASEURL ="http://192.168.0.165:8080/";

    public static final String UPLOADFILE = getURL("upload");

    public static final String TEST_302 = getURL("test302");

    public static final String LOGINTOBR = getURL("json");

    public static final String FORMDATA = getURL("formdata");

    public static final String GITHUB_RESTFUL = "https://api.github.com/users";

    public static final String GITHUB_NORMAL = "https://api.github.com/users";

}
