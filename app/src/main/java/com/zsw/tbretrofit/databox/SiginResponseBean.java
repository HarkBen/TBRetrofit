package com.zsw.tbretrofit.databox;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/21 上午11:41
 * @最后更新时间：17/11/21 上午11:41
 */
public class SiginResponseBean {

    /**
     * teamName : 华东泰克西第六党支部
     * userImageUrl : http://180.166.66.226:43230/downloadFile/2016-12-16/image/7f6aaf95-6436-4731-9314-ce9b7680ee92-2615-min.png
     * code : 10000
     * userName : 孙小圣
     * version : 2.0
     * newVersionState : 2
     * apkpath : http://180.166.66.226:43230/downloadFile/client/ShangQiProj.apk
     * clientDesc : 1、评议模块优化
     2、优化交互体验
     * phoneNumber : null
     * integral : 0
     * loginName : sunkui@visionet.com.cn
     * teamId : 402890da58670cb10158671166700147
     * id : 15242
     * isParty : 0
     * currentVersionState : 2
     */

    private String teamName;
    private String userImageUrl;
    private String code;
    private String userName;
    private String version;
    private int newVersionState;
    private String apkpath;
    private String clientDesc;
    private Object phoneNumber;
    private int integral;
    private String loginName;
    private String teamId;
    private String id;
    private String isParty;
    private int currentVersionState;

    public String getTeamName () {
        return teamName;
    }

    public void setTeamName (String teamName) {
        this.teamName = teamName;
    }

    public String getUserImageUrl () {
        return userImageUrl;
    }

    public void setUserImageUrl (String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getVersion () {
        return version;
    }

    public void setVersion (String version) {
        this.version = version;
    }

    public int getNewVersionState () {
        return newVersionState;
    }

    public void setNewVersionState (int newVersionState) {
        this.newVersionState = newVersionState;
    }

    public String getApkpath () {
        return apkpath;
    }

    public void setApkpath (String apkpath) {
        this.apkpath = apkpath;
    }

    public String getClientDesc () {
        return clientDesc;
    }

    public void setClientDesc (String clientDesc) {
        this.clientDesc = clientDesc;
    }

    public Object getPhoneNumber () {
        return phoneNumber;
    }

    public void setPhoneNumber (Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIntegral () {
        return integral;
    }

    public void setIntegral (int integral) {
        this.integral = integral;
    }

    public String getLoginName () {
        return loginName;
    }

    public void setLoginName (String loginName) {
        this.loginName = loginName;
    }

    public String getTeamId () {
        return teamId;
    }

    public void setTeamId (String teamId) {
        this.teamId = teamId;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getIsParty () {
        return isParty;
    }

    public void setIsParty (String isParty) {
        this.isParty = isParty;
    }

    public int getCurrentVersionState () {
        return currentVersionState;
    }

    public void setCurrentVersionState (int currentVersionState) {
        this.currentVersionState = currentVersionState;
    }

    @Override
    public String toString () {
        return "SiginResponseBean{" +
                "teamName='" + teamName + '\'' +
                ", userImageUrl='" + userImageUrl + '\'' +
                ", code='" + code + '\'' +
                ", userName='" + userName + '\'' +
                ", version='" + version + '\'' +
                ", newVersionState=" + newVersionState +
                ", apkpath='" + apkpath + '\'' +
                ", clientDesc='" + clientDesc + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", integral=" + integral +
                ", loginName='" + loginName + '\'' +
                ", teamId='" + teamId + '\'' +
                ", id='" + id + '\'' +
                ", isParty='" + isParty + '\'' +
                ", currentVersionState=" + currentVersionState +
                '}';
    }
}
