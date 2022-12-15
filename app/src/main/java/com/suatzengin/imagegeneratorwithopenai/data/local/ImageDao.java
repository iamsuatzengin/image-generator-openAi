package com.suatzengin.imagegeneratorwithopenai.data.local;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM MyImageEntity ORDER BY id DESC")
    Flowable<List<MyImageEntity>> getImages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertImage(MyImageEntity imageEntity);

    @Delete
    Completable deleteImage(MyImageEntity imageEntity);
}
