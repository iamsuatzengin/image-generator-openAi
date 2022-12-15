package com.suatzengin.imagegeneratorwithopenai.ui.image;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.suatzengin.imagegeneratorwithopenai.databinding.FragmentImageDetailBinding;
import com.suatzengin.imagegeneratorwithopenai.util.StatusNotification;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImageDetailFragment extends Fragment {

    private FragmentImageDetailBinding binding;
    private ImageDetailViewModel viewModel;

    private ActivityResultLauncher<String> permissionLauncher;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentImageDetailBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ImageDetailViewModel.class);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageDetailFragmentArgs bundle = ImageDetailFragmentArgs.fromBundle(getArguments());
        String url = bundle.getUrl();
        String prompt = bundle.getPrompt();
        binding.textviewSecond.setText(prompt);

        loadImage(url);

        buttonShare();

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if(!result){
                        Toast.makeText(requireContext(), "Needed permission!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    addImageToGallery();
                });

        binding.btnSaveToGallery.setOnClickListener(btnSaveView -> {
            permissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            );
        });

        binding.btnLike.setOnClickListener(btnLikeView -> {
            viewModel.insertImage(prompt, url);
        });

        viewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        });
    }


    private void addImageToGallery() {

        Drawable mDrawable = binding.ivImage.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

        MediaStore.Images.Media.insertImage(
                requireActivity().getContentResolver(), mBitmap, "Image", null);
        StatusNotification.makeStatusNotification(requireContext());

    }

    private void loadImage(String url) {
        Glide.with(this)
                .load(url)
                .centerInside()
                .into(binding.ivImage);
    }

    private void buttonShare() {

        binding.btnShare.setOnClickListener(view -> {
            Drawable mDrawable = binding.ivImage.getDrawable();
            Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
            String path = MediaStore.Images.Media.insertImage(
                    requireActivity().getContentResolver(), mBitmap, "Image", null);
            Uri uri = Uri.parse(path);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Share Image"));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}

