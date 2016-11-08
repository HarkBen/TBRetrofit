package com.zsw.tbretrofit;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.tb.tbretrofit.TbLog;
import com.tb.tbretrofit.httputils.view.TBCallBack;
import com.tb.tbretrofit.httputils.view.TBRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zsw.tbretrofit.API.GITHUB_RESTFUL;

/**
 * Create on 2016/9/30.
 * github  https://github.com/HarkBen
 * Description:
 * ----提供TBRequest 各个请求的示例
 * 具体请求详情 直接看日志更清楚 接口内容和返回内容
 * 在 日志过滤器 选中debug 输入过滤 rainbow-------
 * author Ben
 * Last_Update - 2016/9/30
 */
public class HttpTestAct extends AppCompatActivity {
    private final static String TAG = "HttpTestAct";

    @Bind(R.id.att_log)
    EditText attLog;
    @Bind(R.id.att_restful)
    Button attRestful;
    @Bind(R.id.att_Normal)
    Button attNormal;
    @Bind(R.id.att_post_json)
    Button attPostTxt;
    @Bind(R.id.att_post_file)
    Button attPostFile;
    @Bind(R.id.att_post_txtAndFile)
    Button attPostTxtAndFile;
    @Bind(R.id.att_post_fileList)
    Button attPostFileList;
    @Bind(R.id.att_post_formData)
    Button attPostFormData;
    @Bind(R.id.att_post_test302)
    Button attPostTest302;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_http_test);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.att_restful,R.id.att_post_test302, R.id.att_post_formData,R.id.att_Normal, R.id.att_post_json, R.id.att_post_file, R.id.att_post_txtAndFile, R.id.att_post_fileList})
    public void onClick(View view) {
        attLog.setText("---------清除日志-------------");
        switch (view.getId()) {
            case R.id.att_restful:
                GETByRestful();
                break;
            case R.id.att_Normal:
                GETByNormal();
                break;
            case R.id.att_post_json:
                POSTByJson();
                break;
            case R.id.att_post_file:
                uploadFile();

                break;
            case R.id.att_post_txtAndFile:
                showResult("文件上传底层都支持附带其他表单参数，请参照 单文件 和多文件" +
                        "，但是请勿与postFormData 混淆，因为文件表单 和 文本表单的 ContentType不一样" +
                        "后台接收方式也不同");
                break;
            case R.id.att_post_fileList:
                uploadFiles();
                break;
            case R.id.att_post_formData:
                POSTFormData();
                break;
            case R.id.att_post_test302:
                request302();
                break;
        }
    }

    final static  Callback<String> callback = new Callback<String>() {
        @Override
        public void onResponse(final Call<String> tcall, Response<String> response) {
            int code = response.code();
            TbLog.printD(TAG,"code=="+code);
            String body = response.body();
            TbLog.printD(TAG,"body=="+body);
            String msg = response.message();
            TbLog.printD(TAG,"msg=="+msg);

            if(code == 302){
                TBRequest.create()
                        .postJson(API.LOGINTOBR, new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                int code = response.code();
                                TbLog.printD(TAG,"code=="+code);
                                String body = response.body();
                                TbLog.printD(TAG,"body=="+body);
                                tcall.clone().enqueue(callback);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });



            }

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

        }
    };
    void request302(){


        TBRequest.create()
                .postJson(API.TEST_302, callback);

    }

    void showResult(String result) {
        attLog.setText(result);
    }

    /**
     * GET
     * RESTFUL 接口模式调用
     */
    void GETByRestful() {
        TBRequest.create()
                .get(API.GITHUB_RESTFUL, new String[]{"Harkben"}, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code + "--" + body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult(errorMsg);
                    }
                });
    }

    void GETByNormal() {
        TBRequest.create()
                .put("user", "HarkBen")
                .get(API.GITHUB_NORMAL, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code + "--" + body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult(errorMsg);

                    }

                });
    }

    void POSTFormData(){
        String url = API.FORMDATA;
        TBRequest.create()
                .put("username", "cat")
                .put("password", "222222")
                .put("weight", "4Kg")
                .postFormData(url, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code + "--" + body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult("onFailed=="+errorMsg);
                    }
                });
    }

    void POSTByJson() {
        String url = API.LOGINTOBR;
        TBRequest.create()
                .put("username", "tigger")
                .put("password", "111111")
                .put("weight", "130Kg")
                .postJson(url, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code + "--" + body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult("error==" + errorMsg);
                    }
                });
    }

    void uploadFile(){
        File file = new File(parentPath+"291733413425432.png");
        TBRequest.create()
                .put("name","小二郎")
                .put("arg",34)
                .put("gender","男")
                .postFormDataFile(API.UPLOADFILE, file, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code+"--"+body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult("onFailed--"+errorMsg);
                    }
                });
    }

  static  String parentPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separatorChar
            +"tencent/Qtl/find/";

    void uploadFiles(){

        File file1 = new File(parentPath+"291739323217867.png");
        File file2 = new File(parentPath+"291733413425432.png");
        File file3 = new File(parentPath+"222004413632569.png");
        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);
        TBRequest.create()
                .put("name","小二郎他爸")
                .put("arg",54)
                .put("gender","男")
                .postFormDataFiles(API.UPLOADFILE, files, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code+"--"+body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult("onFailed--"+errorMsg);
                    }
                });
    }

}
