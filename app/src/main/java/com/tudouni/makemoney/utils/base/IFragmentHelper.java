package com.tudouni.makemoney.utils.base;


import android.support.v4.app.Fragment;

/**
 * 业务实现此接口，相当于重写BaseFragment
 */
public interface IFragmentHelper {


    void onCreate(Fragment f);

    void onViewCreated(Fragment f);

    void onStart(Fragment f);

    void onResume(Fragment f);

    void onPause(Fragment f);

    void onStop(Fragment f);

    void onDestroyView(Fragment f);

    void onDestroy(Fragment f);
}
