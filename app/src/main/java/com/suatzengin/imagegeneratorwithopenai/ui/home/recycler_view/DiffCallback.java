package com.suatzengin.imagegeneratorwithopenai.ui.home.recycler_view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;

public class DiffCallback extends DiffUtil.ItemCallback<MyImageEntity> {
    @Override
    public boolean areItemsTheSame(@NonNull MyImageEntity oldItem, @NonNull MyImageEntity newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull MyImageEntity oldItem, @NonNull MyImageEntity newItem) {
        return oldItem.id == newItem.id;
    }
}