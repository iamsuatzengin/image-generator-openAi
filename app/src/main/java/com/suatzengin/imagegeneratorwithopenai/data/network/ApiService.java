package com.suatzengin.imagegeneratorwithopenai.data.network;

import com.suatzengin.imagegeneratorwithopenai.data.network.model.ImageRequest;
import com.suatzengin.imagegeneratorwithopenai.data.network.model.ImageResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("images/generations")
    Observable<ImageResponse> getImage(
            @Body ImageRequest imageRequest
    );
}
