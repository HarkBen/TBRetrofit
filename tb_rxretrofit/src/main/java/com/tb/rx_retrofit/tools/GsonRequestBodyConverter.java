package com.tb.rx_retrofit.tools;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.tb.rx_retrofit.http_excuter.RxApiService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * @描述： － 请求  转换器
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 上午11:13
 * @最后更新时间：17/11/16 上午11:13
 */
public class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final Charset UTF_8 = Charset.forName("utf-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonRequestBodyConverter (Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        RxHttpLog.printI("GsonRequestBodyConverter","buffer size="+buffer.size());
        RxHttpLog.printI("GsonRequestBodyConverter",buffer.readUtf8());
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE,buffer.readString(UTF_8));
        return requestBody;//RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }


}
