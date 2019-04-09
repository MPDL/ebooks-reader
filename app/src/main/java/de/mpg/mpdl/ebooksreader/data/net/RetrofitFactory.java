package de.mpg.mpdl.ebooksreader.data.net;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitFactory {

    public static String BASE_URL = "https://ebooks4-qa.mpdl.mpg.de/";

    private static volatile RetrofitFactory sInstance;

    private Retrofit retrofit;

    private RetrofitFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(genericClient())
                .build();

    }

    public static RetrofitFactory getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitFactory.class) {
                if (null == sInstance) {
                    sInstance = new RetrofitFactory();
                }
            }
        }
        return sInstance;
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("charset", "UTF-8")
                    .build();
            return chain.proceed(request);
        }
    };

    private static HttpLoggingInterceptor initLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    private static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(initLoggingInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return onOnIntercept(chain);
                    }
                })
                .build();
        return httpClient;
    }

    private static Response onOnIntercept(Interceptor.Chain chain) throws IOException {
        Response response = null;
        try {
            response = chain.proceed(chain.request());
        }
        catch (SocketTimeoutException exception) {
            exception.printStackTrace();
        }

        return response;
    }
}

