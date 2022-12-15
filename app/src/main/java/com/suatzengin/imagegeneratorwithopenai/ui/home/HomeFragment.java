package com.suatzengin.imagegeneratorwithopenai.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;
import com.suatzengin.imagegeneratorwithopenai.databinding.FragmentHomeBinding;
import com.suatzengin.imagegeneratorwithopenai.ui.home.recycler_view.DiffCallback;
import com.suatzengin.imagegeneratorwithopenai.ui.home.recycler_view.HomeRecyclerAdapter;
import com.suatzengin.imagegeneratorwithopenai.util.ClickListener;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private String prompt;
    private HomeRecyclerAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        viewModel.getImagesFromDb();

        binding.btnGenerateImage.setOnClickListener(btnView -> {

            prompt = binding.textField.getEditText().getText().toString();

            viewModel.load(prompt);
        });
        viewModel.getImages().observe(getViewLifecycleOwner(), data -> {
            String url = data.get(0).getUrl();
            if (prompt == null || url.isEmpty()) {
                return;
            }
            navigateToDetail(prompt, url);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

        viewModel.getErrorTextField().observe(getViewLifecycleOwner(), message -> {
            binding.textField.setError(message);
        });
        viewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        });

        viewModel.observeImagesFromDb().observe(getViewLifecycleOwner(), list -> {
            adapter.submitList(list);
        });
    }

    private void setupRecyclerView() {
        DiffCallback callback = new DiffCallback();
        adapter = new HomeRecyclerAdapter(callback, new ClickListener() {
            @Override
            public void onCardClick(int id, String prompt, String url) {
                navigateToDetail(prompt, url);
            }

            @Override
            public void onUnlikeButtonClick(MyImageEntity imageEntity) {
                viewModel.deleteImage(imageEntity);
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    public void navigateToDetail(String prompt, String url) {
        HomeFragmentDirections.ActionHomeToImageFragment action =
                HomeFragmentDirections.actionHomeToImageFragment(url, prompt);
        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}