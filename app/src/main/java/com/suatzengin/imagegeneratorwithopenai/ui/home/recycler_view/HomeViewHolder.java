package com.suatzengin.imagegeneratorwithopenai.ui.home.recycler_view;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;
import com.suatzengin.imagegeneratorwithopenai.databinding.ImageRvItemBinding;
import com.suatzengin.imagegeneratorwithopenai.util.ClickListener;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    private final ImageRvItemBinding binding;

    public HomeViewHolder(ImageRvItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(MyImageEntity image, ClickListener clickListener) {
        Glide.with(itemView)
                .load(image.getImageUrl())
                .into(binding.ivItemImage);
        binding.tvPrompt.setText(image.getImagePrompt());

        binding.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onCardClick(
                        image.getId(),
                        image.getImagePrompt(),
                        image.getImageUrl()
                );
            }
        });
        binding.btnUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onUnlikeButtonClick(image);
            }
        });
    }
}
