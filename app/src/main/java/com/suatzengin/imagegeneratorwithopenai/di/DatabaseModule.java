package com.suatzengin.imagegeneratorwithopenai.di;

import android.content.Context;

import androidx.room.Room;

import com.suatzengin.imagegeneratorwithopenai.data.local.ImageDao;
import com.suatzengin.imagegeneratorwithopenai.data.local.ImageDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Singleton
    @Provides
    public static ImageDatabase provideImageDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                        context.getApplicationContext(),
                        ImageDatabase.class, "image_db"
                )
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public static ImageDao imageDao(ImageDatabase db) {
        return db.imageDao();
    }
}
