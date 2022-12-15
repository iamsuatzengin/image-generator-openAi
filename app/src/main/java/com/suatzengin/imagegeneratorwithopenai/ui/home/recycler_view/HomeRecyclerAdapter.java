package com.suatzengin.imagegeneratorwithopenai.ui.home.recycler_view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;
import com.suatzengin.imagegeneratorwithopenai.databinding.ImageRvItemBinding;
import com.suatzengin.imagegeneratorwithopenai.util.ClickListener;


public class HomeRecyclerAdapter extends ListAdapter<MyImageEntity, HomeViewHolder> {

    private final ClickListener clickListener;
    public HomeRecyclerAdapter(DiffCallback diffCallback, ClickListener clickListener) {
        super(diffCallback);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ImageRvItemBinding binding = ImageRvItemBinding.inflate(layoutInflater, parent, false);
        return new HomeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        MyImageEntity image = getItem(position);
        holder.bind(image, clickListener);
    }

}

