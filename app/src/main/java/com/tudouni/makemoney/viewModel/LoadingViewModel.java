package com.tudouni.makemoney.viewModel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

/**
 * @author Jaron.Wu 18/3/22 下午3:09.
 */
@SuppressWarnings("WeakerAccess")
public class LoadingViewModel extends ViewModel {

    public LoadingViewModel() {
    }


    public final ObservableBoolean showRetry = new ObservableBoolean(false);
    public ObservableField<Boolean> showLoading = new ObservableField<>(false);
    public final ObservableBoolean showNoData = new ObservableBoolean(false);

    public void resetStatus(){
        showRetry.set(false);
        showLoading.set(false);
        showNoData.set(false);
    }

    public void showLoading() {
        resetStatus();
        showLoading.set(true);
    }

    public void showRetry() {
        resetStatus();
        showRetry.set(true);
    }

    public void showNoData(){
        resetStatus();
        showNoData.set(true);
    }

    public void dismissLoading(){
        showLoading.set(false);
        showLoading.get();
    }

}
