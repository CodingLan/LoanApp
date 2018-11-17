package com.zhenxing.loanapp.util;

import android.text.TextUtils;

import com.zhenxing.loanapp.http.CoinDns;
import com.zhenxing.loanapp.http.ObserveOnMainCallAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;

/**
 * Created by Android on 2017/2/27.
 */

public class RetrofitUtil {

    String TAG = "RetrofitUtil";

    private static boolean PRINT_LOG = false;
    private static String BASE_URL;

    //币币
    private Retrofit mRetrofit = null;

    private OkHttpClient mOkHttpClient;
    private static RetrofitUtil sInstance;

    public static RetrofitUtil getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitUtil();
        }
        return sInstance;
    }

    private RetrofitUtil() {
        mOkHttpClient = getOkHttpClient("coin", 10, 15, 15);
        mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new ByteConvertFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ProtoConverterFactory.create())
            .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(mOkHttpClient)
            .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static OkHttpClient getOkHttpClient(String logTag, int connectionTimeout, int readTimeout,
        int writeTimeout) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor())
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .dns(new CoinDns());

        if (PRINT_LOG) {
            //TBHttpLoggingInterceptor loggingInterceptor = new TBHttpLoggingInterceptor(logTag);
            //HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            //builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    public static class CustomInterceptor implements Interceptor {
        String TAG = "CustomInterceptor";

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            HttpUrl originalHttpUrl = original.url();

            HttpUrl.Builder builder = originalHttpUrl.newBuilder();
            String currentUserInfo = "";// SpUtil.getString("user_info", "");

            String ACCESS_TOKEN = "";
            if (!TextUtils.isEmpty(currentUserInfo)) {
                try {
                    JSONObject jsonObject = new JSONObject(currentUserInfo);
                    ACCESS_TOKEN = jsonObject.getString("accessToken");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            HttpUrl url = builder.build();

            Request.Builder request = original.newBuilder().url(url);
            if (!TextUtils.isEmpty(ACCESS_TOKEN)) {
                request.addHeader("ACCESS_TOKEN", ACCESS_TOKEN);
            }
            request.addHeader("User-Agent", TBUtils.getUserAgent());
            okhttp3.Response response = chain.proceed(request.build());

            return response;
        }
    }

    public static class ByteConvertFactory extends Converter.Factory {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
            Retrofit retrofit) {
            if ("byte[]".equals(type + "")) {
                return (Converter<ResponseBody, byte[]>)value -> value.bytes();
            }
            return super.responseBodyConverter(type, annotations, retrofit);
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
            Annotation[] methodAnnotations, Retrofit retrofit) {
            if ("byte[]".equals(type + "")) {
                return (Converter<byte[], RequestBody>)value -> RequestBody.create(MEDIA_TYPE, value);
            }
            return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
        }
    }
}
