package com.suatzengin.imagegeneratorwithopenai.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyImageEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "image_prompt")
    private final String imagePrompt;

    @ColumnInfo(name = "image_url")
    private final String imageUrl;

    public MyImageEntity(String imagePrompt, String imageUrl) {
        this.imagePrompt = imagePrompt;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getImagePrompt() {
        return imagePrompt;
    }


    public String getImageUrl() {
        return imageUrl;
    }

}
