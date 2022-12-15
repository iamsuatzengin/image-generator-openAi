package com.suatzengin.imagegeneratorwithopenai.data;

import com.suatzengin.imagegeneratorwithopenai.data.local.ImageDao;
import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;
import com.suatzengin.imagegeneratorwithopenai.data.network.ApiService;
import com.suatzengin.imagegeneratorwithopenai.data.network.model.ImageRequest;
import com.suatzengin.imagegeneratorwithopenai.data.network.model.ImageResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class Repository {

    private final ImageDao dao;
    private final ApiService apiService;

    @Inject
    public Repository(ImageDao imageDao, ApiService apiService) {
        this.dao = imageDao;
        this.apiService = apiService;
    }

    public Observable<ImageResponse> getImage(ImageRequest imageRequest) {
        return apiService.getImage(imageRequest);
    }

    public Completable insertImage(MyImageEntity imageEntity) {
        return dao.insertImage(imageEntity);
    }

    public Completable deleteImage(MyImageEntity imageEntity){
        return dao.deleteImage(imageEntity);
    }
    public Flowable<List<MyImageEntity>> getImages() {
        return dao.getImages();
    }
}
