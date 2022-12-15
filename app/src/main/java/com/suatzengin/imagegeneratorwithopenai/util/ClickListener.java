package com.suatzengin.imagegeneratorwithopenai.util;

import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;

public interface ClickListener{
    void onCardClick(int id, String prompt, String url);
    void onUnlikeButtonClick(MyImageEntity imageEntity);
}