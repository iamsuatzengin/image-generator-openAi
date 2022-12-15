package com.suatzengin.imagegeneratorwithopenai.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;

@Database(entities = {MyImageEntity.class}, version = 2, exportSchema = false)
public abstract class ImageDatabase extends RoomDatabase {

    public abstract ImageDao imageDao();
}
