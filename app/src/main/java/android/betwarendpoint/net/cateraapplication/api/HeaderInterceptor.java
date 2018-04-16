package android.betwarendpoint.net.cateraapplication.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor
        implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain)
            throws IOException {
        Request original = chain.request();
        Request  request = original.newBuilder()
                .addHeader("Access-Control-Allow-Credentials", "true")
                .addHeader("content-type" ,"application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Authorization","Basic Y2xpZW50LWFwcDp0ZXN0c2VjcmV0")
                .method( original.method()   , original.body())
                .build();

        return chain.proceed(request);
    }
}