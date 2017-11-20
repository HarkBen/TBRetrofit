package com.zsw.tbretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tb.rx_retrofit.http_contact.RxHttpContactImpl;
import com.tb.rx_retrofit.http_contact.RxHttpTaskManagement;
import com.tb.rx_retrofit.http_excuter.JsonBody;
import com.tb.rx_retrofit.http_receiver.HttpCallBack;
import com.tb.rx_retrofit.tools.CacheModel;


/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午6:01
 * @最后更新时间：17/11/15 下午6:01
 */
public class RxHttpTest extends AppCompatActivity implements View.OnClickListener {
    public static final String GITHUB_RESTFUL = "https://api.github.com/users/Harkben";

    EditText editText;
    Button requestNormal, cancelRequest,
            requestForceCache, requestForceNetWork,
            requestNetWorkAndNoStroe, login, getData;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_rxhttp_test);
        loginJson = getString(R.string.loginJson);
        dataJson = getString(R.string.dataJson);
        requestNormal = (Button) findViewById(R.id.art_requestNormal);
        cancelRequest = (Button) findViewById(R.id.art_unSubscriber);
        requestForceCache = (Button) findViewById(R.id.art_request_ForceCache);
        requestForceNetWork = (Button) findViewById(R.id.art_request_requestForceNetWrok);
        requestNetWorkAndNoStroe = (Button) findViewById(R.id.art_request_netWorkAndNoStore);
        editText = (EditText) findViewById(R.id.art_log);
        login = (Button) findViewById(R.id.art_request_login);
        getData = (Button) findViewById(R.id.art_request_getData);
        login.setOnClickListener(this);
        getData.setOnClickListener(this);
        requestNetWorkAndNoStroe.setOnClickListener(this);
        requestNormal.setOnClickListener(this);
        requestForceCache.setOnClickListener(this);
        requestForceNetWork.setOnClickListener(this);
        cancelRequest.setOnClickListener(this);

    }

    private void getGitHubUser (CacheModel cacheModel) {

        new RxHttpContactImpl(RxHttpTaskManagement.getINSTANCE())
                .get(GITHUB_RESTFUL, new HttpCallBack<GithubEntity>(RxHttpTest.this) {
                    @Override
                    public void onSuccess (GithubEntity githubEntity) {

                        String msg = "githubEntity:" + githubEntity.getName()
                                + "\n" + githubEntity.getBlog()
                                + "\n" + githubEntity.getCompany()
                                + "\n cacheModel:" + cacheModel().getValue()
                                + "\n";

                        editText.setText(editText.getText().toString() + msg);

                        printLog("githubEntity:" + githubEntity.getName());

                    }

                    @Override
                    public void onFailure (int errorCode, String message) {
                        printLog("onFailure  errorCod:" + errorCode + "  errorMsg:" + message);
                        editText.setText(editText.getText().toString() + "errorCod:" + errorCode + "__errorMsg" + message + "\n");

                    }

                    @Override
                    public void onFinish () {
                        printLog("onFinish");
                    }

                    @Override
                    public CacheModel cacheModel () {
                        return cacheModel;
                    }


                });

    }

    public void printLog (String msg) {
        Log.d("RxHttpTest", msg);
    }


    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.art_requestNormal:

                getGitHubUser(CacheModel.NORMAL);

                break;
            case R.id.art_request_ForceCache:
                getGitHubUser(CacheModel.FORCE_CACHE);
                break;

            case R.id.art_request_requestForceNetWrok:
                getGitHubUser(CacheModel.FORCE_NETWORK);
                break;

            case R.id.art_request_netWorkAndNoStore:
                getGitHubUser(CacheModel.FORCE_NETWORK_AND_NOSTRROE);
                break;
            case R.id.art_unSubscriber:
                editText.setText("log:");
                RxHttpTaskManagement.getINSTANCE().unSubscribe(RxHttpTest.this);
                break;
            case R.id.art_request_login:
                loginOnDJ();
                break;

            case R.id.art_request_getData:
                getDJData();
                break;

            default:
                break;
        }
    }

    public static final String DJ_IP = "http://180.166.66.226:43230/baoshi";
    String loginUrl = DJ_IP + "/mobilelogin";
    String dataUrl = DJ_IP+"/materials/queryMaterials";


    String loginJson ;
    String dataJson;

    private void loginOnDJ () {
        LoginParameter loginParameter = new LoginParameter();
        loginParameter.setClient_flag("android");
        loginParameter.setClientMobileVersion("1.0");
        loginParameter.setLocale("zh");
        loginParameter.setLoginName("sunkui@visionet.com.cn");
        loginParameter.setPassword("123456");
        loginParameter.setModel("Samsung Galaxy Note3 N9009");
        new RxHttpContactImpl(RxHttpTaskManagement.getINSTANCE())
                .postJson(loginUrl, loginParameter, new HttpCallBack<String>(RxHttpTest.this) {

                    @Override
                    public void onSuccess (String s) {

                    }

                    @Override
                    public CacheModel cacheModel () {
                        return CacheModel.FORCE_NETWORK_AND_NOSTRROE;
                    }
                });
    }

    private void getDJData () {
        LearnParameter learnParameter = new LearnParameter();
        learnParameter.setPageInfo(new LearnParameter.PageInfoBean(1,5));
        new RxHttpContactImpl(RxHttpTaskManagement.getINSTANCE())
                .postJson(dataUrl, learnParameter, new HttpCallBack<String>(RxHttpTest.this) {

                    @Override
                    public void onSuccess (String s) {

                    }


                });
    }

}
