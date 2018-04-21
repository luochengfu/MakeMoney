package com.tudouni.makemoney.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.utils.AutoHideKeyboard;
import com.tudouni.makemoney.utils.MeizuSmartBarUtils;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.base.ViewAnnotationUtil;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class BaseActivity extends AppCompatActivity {

    protected boolean mIsActivityPaused = true;
    protected Handler mHandler = new Handler();

    public boolean isPaused() {
        return mIsActivityPaused;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置LayoutInflater Factory，需在onCreate前执行，因为AppCompatActivity.onCreate里也设了
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                View view = getDelegate().createView(parent, name, context, attrs); //调用AppCompatDelegate创建view，保持AppCompat的特性
                if (view instanceof TextView) {
                    view.setTextDirection(View.TEXT_DIRECTION_LTR);     //文字方向从左到右
                }
                return view;
            }
        });


        super.onCreate(savedInstanceState);

        AutoHideKeyboard.init(this);
//        App.addActivity(this);
        if (MeizuSmartBarUtils.hasSmartBar()) {
            View decorView = getWindow().getDecorView();
            MeizuSmartBarUtils.hide(decorView);
        }

    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        ViewAnnotationUtil.autoInjectAllField(this, view);
        this.setContentView(view);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
//        Bugtags.onResume(this);
        mIsActivityPaused = false;
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
//        Bugtags.onPause(this);
        super.onPause();
        mIsActivityPaused = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        dissLoad();
//        App.removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
//            Bugtags.onDispatchTouchEvent(this, ev);
            return super.dispatchTouchEvent(ev);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private CenterLoadingView loading;

    public void loading(String title) {
        if (null == loading) {
            loading = new CenterLoadingView(BaseActivity.this);
        }
        loading.setTitle(title);
        loading.show();
    }

    public void dissLoad() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    protected void showToast(String txt) {
        ToastUtil.show(txt);
    }


    //https://www.tapd.cn/20512601/bugtrace/bugs/view?bug_id=1120512601001000275&url_cache_key=56d54a5079534c7c272d4a313e8d852d
    //http://blog.csdn.net/edisonchang/article/details/49873669

    private Method noteStateNotSavedMethod;
    private Object fragmentMgr;
    private String[] activityClassName = {"Activity", "FragmentActivity"};

    private void invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod.invoke(fragmentMgr);
                return;
            }
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!(activityClassName[0].equals(cls.getSimpleName())
                    || activityClassName[1].equals(cls.getSimpleName())));

            Field fragmentMgrField = prepareField(cls, "mFragments");
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this);
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr, "noteStateNotSaved");
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod.invoke(fragmentMgr);
                }
            }

        } catch (Exception ignored) {
        }
    }

    private Field prepareField(Class<?> c, String fieldName) throws NoSuchFieldException {
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } finally {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }

    private Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

}
