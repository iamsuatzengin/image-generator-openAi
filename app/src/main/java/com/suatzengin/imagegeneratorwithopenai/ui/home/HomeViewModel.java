package com.suatzengin.imagegeneratorwithopenai.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.suatzengin.imagegeneratorwithopenai.data.Repository;
import com.suatzengin.imagegeneratorwithopenai.data.local.entity.MyImageEntity;
import com.suatzengin.imagegeneratorwithopenai.data.network.ApiService;
import com.suatzengin.imagegeneratorwithopenai.data.network.model.Data;
import com.suatzengin.imagegeneratorwithopenai.data.network.model.ImageRequest;
import com.suatzengin.imagegeneratorwithopenai.util.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {


    private final Repository repository;
    private final SingleLiveEvent<List<Data>> images = new SingleLiveEvent<>();
    private final MutableLiveData<List<MyImageEntity>> imagesFromDb = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final SingleLiveEvent<String> errorTextField = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> message = new SingleLiveEvent<>();
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public HomeViewModel(Repository repository) {

        this.repository = repository;
    }

    public SingleLiveEvent<List<Data>> getImages() {
        return images;
    }

    public LiveData<List<MyImageEntity>> observeImagesFromDb() {
        return imagesFromDb;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean bool) {
        isLoading.postValue(bool);
    }

    public SingleLiveEvent<String> getErrorTextField() {
        return errorTextField;
    }

    public SingleLiveEvent<String> getMessage() {
        return message;
    }

    public void load(String prompt) {
        if (prompt == null || prompt.isEmpty()) {
            errorTextField.postValue("The prompt must not be empty!");
            return;
        }
        ImageRequest imageRequest = new ImageRequest(prompt, 1, "512x512");
        setIsLoading(true);

        disposable.add(
                repository.getImage(imageRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(item -> {
                            setIsLoading(false);
                            images.postValue(item.getData());
                        }, throwable -> {
                            message.postValue("Check your internet connection!");
                            setIsLoading(false);
                        })
        );
    }

    public void getImagesFromDb() {
        disposable.add(
                repository.getImages()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(imagesFromDb::postValue, throwable -> {
                            message.postValue(throwable.getLocalizedMessage());
                        })
        );
    }

    public void deleteImage(MyImageEntity imageEntity) {
        disposable.add(
                repository.deleteImage(imageEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            message.postValue("Deleted successfully!");
                        }, throwable -> {
                            message.postValue(throwable.getLocalizedMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) disposable.dispose();
    }

    public void onClear() {
        if (disposable != null) disposable.dispose();
    }
}
