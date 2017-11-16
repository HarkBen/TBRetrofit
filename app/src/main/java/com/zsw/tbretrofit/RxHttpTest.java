package com.zsw.tbretrofit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tb.tbretrofit.rx_retrofit.http_contact.RxHttpTaskManagement;
import com.tb.tbretrofit.rx_retrofit.http_excuter.HttpClientFactory;
import com.tb.tbretrofit.rx_retrofit.http_excuter.RetrofitFactory;
import com.tb.tbretrofit.rx_retrofit.http_reception.HttpCallBack;
import com.tb.tbretrofit.rx_retrofit.http_reception.HttpReception;




import okhttp3.OkHttpClient;


/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午6:01
 * @最后更新时间：17/11/15 下午6:01
 */
public class RxHttpTest extends AppCompatActivity{
    public static final String GITHUB_RESTFUL = "https://www.baidu.com/search/harkben";
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_rxhttp_test);
        initHttpSystem();


        Button request = (Button) findViewById(R.id.art_request);

        Button cancelRequest = (Button) findViewById(R.id.art_unSubscriber);


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                HttpReception.create().get(GITHUB_RESTFUL, new HttpCallBack<GithubEntity>(RxHttpTest.this) {


                    @Override
                    public void onSuccess (GithubEntity githubEntity) {

                        printLog("githubEntity:"+githubEntity.getName());

                    }


                });

            }
        });

        cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                RxHttpTaskManagement.getINSTANCE().unSubscribe(RxHttpTest.this);
            }
        });

    }

    public void printLog(String msg){
        Log.d("RxHttpTest",msg);
    }


    private void initHttpSystem(){
        OkHttpClient client = new HttpClientFactory.Builder()
                .setDebug(true)
                .autoCache(this)
                .build();
        RetrofitFactory.Builder.create()
                .setBaseUrl("http://www.aa.com")
                .setOkHttpClient(client)
                .init();
    }

}
