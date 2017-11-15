package com.zsw.tbretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tb.tbretrofit.rx_retrofit.http_excuter.HttpClientFactory;
import com.tb.tbretrofit.rx_retrofit.http_excuter.RetrofitFactory;
import com.tb.tbretrofit.rx_retrofit.http_reception.HttpReception;
import com.tb.tbretrofit.rx_retrofit.http_reception.HttpResponseListener;

import okhttp3.OkHttpClient;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/15 下午6:01
 * @最后更新时间：17/11/15 下午6:01
 */
public class RxHttpTest extends AppCompatActivity{
    public static final String GITHUB_RESTFUL = "https://api.github.com/users/harkben";
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);

        setContentView(button);
        button.setText("Baidu");

        OkHttpClient client = new HttpClientFactory.Builder()
                .setDebug(true)
                .build();
        RetrofitFactory.Builder.create()
                .setBaseUrl("http://www.aa.com")
                .setOkHttpClient(client)
        .builder();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                HttpReception.create().get(GITHUB_RESTFUL, new HttpResponseListener<String>() {
                    @Override
                    public void onStart () {
                        printLog("onStart");
                    }

                    @Override
                    public void onSuccess (String s) {
                        printLog("onSuccess:"+s);
                    }

                    @Override
                    public void onFailure (int errorCode, String message) {
                        printLog("onFailure:");
                    }

                    @Override
                    public void onFinish () {
                        printLog("onFinish:");
                    }
                });

            }
        });

    }

    public void printLog(String msg){
        Log.d("RxHttpTest",msg);
    }

}
