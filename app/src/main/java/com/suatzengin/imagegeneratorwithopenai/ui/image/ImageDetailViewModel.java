package com.suatzengin.imagegeneratorwithopenai.ui.image;

import androidx.lifecycle.ViewModel;

import com.suatzengin.imagegeneratorwithopenai.data.Repository;
import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;
import com.suatzengin.imagegeneratorwithopenai.util.SingleLiveEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class ImageDetailViewModel extends ViewModel {

    private final Repository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final SingleLiveEvent<String> message = new SingleLiveEvent<>();

    @Inject
    public ImageDetailViewModel(Repository repository) {
        this.repository = repository;
    }

    public SingleLiveEvent<String> getMessage() {
        return message;
    }

    public void insertImage(String prompt, String url) {
        MyImageEntity image = new MyImageEntity(prompt, url);

        disposable.add(
                repository.insertImage(image)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            message.postValue("Added successfully!");
                        }, throwable -> {
                            message.postValue("Something went wrong! Try again!");
                        })
        );
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) disposable.dispose();
    }
}
