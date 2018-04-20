package com.tudouni.makemoney.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.tudouni.makemoney.utils.AutoHideKeyboard;
import com.tudouni.makemoney.utils.LoadingUtils;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.base.ViewAnnotationUtil;
import com.tudouni.makemoney.utils.base.BaseViewHelper;
import com.tudouni.makemoney.utils.base.IFragmentHelper;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * 整合基础Fragment
 * Created by huang on 2017/5/17.
 */

public abstract class BaseFragment extends Fragment implements FragmentLifecycleProvider {

    protected String TAG;
    protected View mContentView;
    protected LoadingUtils dialogUtils;
    private IFragmentHelper mFragmentHelper = null;
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    protected Handler mHandler = new Handler();
    private boolean isOnResumeCalled;

    /** 初始化视图 */
    protected abstract int getContentView();
    /** 初始化布局 */
    protected abstract void initView(View view);
    /** 初始化数据 */
    protected abstract void initData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    protected View getLayoutView(){
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        lifecycleSubject.onNext(FragmentEvent.CREATE);
        mFragmentHelper = BaseViewHelper.getInstance().getFragmentHelper();
        if (mFragmentHelper != null) {
            mFragmentHelper.onCreate(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("BaseFragment","oncreateview  " + this.getClass().getSimpleName());
        if (this.mContentView == null) {
            this.mContentView = createContentView(getContentView());
            initView(this.mContentView);
            initData();
            AutoHideKeyboard.init(getActivity());
        }else{
            ViewGroup parent = (ViewGroup) this.mContentView.getParent();
            if (parent != null) {
                parent.removeView(this.mContentView);
            }
        }
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        return this.mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mFragmentHelper != null) {
            mFragmentHelper.onViewCreated(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
        if (mFragmentHelper != null) mFragmentHelper.onStart(this);
    }

    @Override
    public void onResume() {
        filterOnResume();
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
        if (mFragmentHelper != null) mFragmentHelper.onResume(this);
    }

    @Override
    public void onPause() {
        resetOnResume();
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        if (mFragmentHelper != null) mFragmentHelper.onPause(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycleSubject.onNext(FragmentEvent.STOP);
        if (mFragmentHelper != null) mFragmentHelper.onStop(this);
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        if (mFragmentHelper != null) mFragmentHelper.onDestroyView(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        if (mFragmentHelper != null) mFragmentHelper.onDestroy(this);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    @NonNull
    public View getView(){
        return mContentView;
    }

    public View createContentView(int layoutResID) {
        if (layoutResID == 0) {
            return getLayoutView();
        }
        View view = getActivity().getLayoutInflater().inflate(layoutResID, null);
        ViewAnnotationUtil.autoInjectAllField(this, view);
        return view;
    }

    public void showToast(String text) {
        ToastUtil.show(text);
    }

    public void showLoading(String title) {
        dialogUtils = new LoadingUtils(getContext());
        dialogUtils.showLoading(title);
    }

    public void dismissLoading() {
        dialogUtils.dismissLoading();
    }

    public Context getContext(){
        return getActivity();
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bindFragment(lifecycleSubject);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            filterOnResume();
        } else {
            resetOnResume();
        }
    }

    /**
     * 过滤onResume调用，如果已经调用过，不再重复调用。否则调用onFragResume();
     */
    private void filterOnResume(){
        if (!isOnResumeCalled) {
            onFragResume();
            isOnResumeCalled = true;
        }
    }

    /**
     * 在页面不可见后，重置onResume的调用状态。并调用onFragPause();
     */
    private void resetOnResume(){
        onFragPause();
        isOnResumeCalled = false;
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            filterOnResume();
        } else {
            resetOnResume();
        }
    }

    /**
     * Fragment onResume
     */
    protected void onFragResume(){

    }


    /**
     * Fragment onPause
     */
    protected void onFragPause(){

    }
}
