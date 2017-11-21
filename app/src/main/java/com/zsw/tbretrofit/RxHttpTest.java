package com.zsw.tbretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tb.rx_retrofit.http_presenter.RxHttpPresenterImpl;
import com.tb.rx_retrofit.tools.task_management.RxHttpTaskManagement;
import com.tb.rx_retrofit.http_receiver.HttpCallBack;
import com.tb.rx_retrofit.http_receiver.HttpUtils;
import com.tb.rx_retrofit.tools.cache.CacheModel;
import com.zsw.tbretrofit.databox.GithubEntity;
import com.zsw.tbretrofit.databox.PostDataUtils;
import com.zsw.tbretrofit.databox.SiginResponseBean;
import com.zsw.tbretrofit.http.API;

import retrofit2.Response;


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

        new RxHttpPresenterImpl(RxHttpTaskManagement.getINSTANCE())
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



    private void loginOnDJ () {
        HttpUtils.createContact()
                .postJson(API.loginUrl, PostDataUtils.getSiginParameter(), new HttpCallBack<SiginResponseBean>(RxHttpTest.this) {

                    @Override
                    public void onSuccess (SiginResponseBean s) {

                        editText.setText(editText.getText().toString() + "\n"+s.toString());

                    }

                    @Override
                    public void onFailure (int errorCode, String message) {
                        super.onFailure(errorCode, message);
                        editText.setText("onFailure:"+errorCode +"  "+message);
                    }

                    @Override
                    public CacheModel cacheModel () {
                        return CacheModel.NORMAL;
                    }
                });
    }

    private void getDJData () {
        new RxHttpPresenterImpl(RxHttpTaskManagement.getINSTANCE())
                .postJson(API.dataUrl, PostDataUtils.getLearnParameter(), new HttpCallBack<Object>(RxHttpTest.this) {

                    @Override
                    public void onSuccess (Object s) {

                    }

                    @Override
                    public void onResponse (Response<String> response) {
                        super.onResponse(response);
                        editText.setText(editText.getText().toString() + "\n"+response.body().toString());
                    }
                });
    }

}
