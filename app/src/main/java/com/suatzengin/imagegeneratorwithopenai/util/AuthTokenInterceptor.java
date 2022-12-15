package com.suatzengin.imagegeneratorwithopenai.util;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthTokenInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest
                .newBuilder()
                .header("Authorization", Constants.TOKEN);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}