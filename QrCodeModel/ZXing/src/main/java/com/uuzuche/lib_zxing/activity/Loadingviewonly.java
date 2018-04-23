package com.uuzuche.lib_zxing.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzuche.lib_zxing.R;
import com.yalantis.ucrop.dialog.SpinView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/18.
 */

public class Loadingviewonly extends RelativeLayout implements View.OnClickListener {
    private final RelativeLayout rootview;
    private final Context mcontext;
    private View view;
    private SpinView spview;
    private TextView title_text;



    public Loadingviewonly(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
        LayoutInflater.from(context).inflate(R.layout.loadviewlayout, this);
        spview = (SpinView) findViewById(R.id.spview);
        title_text = (TextView) findViewById(R.id.title_text);
        rootview = (RelativeLayout) findViewById(R.id.rootview);
        setloading();
    }


    public void setloading() {
        spview.setVisibility(VISIBLE);
        title_text.setText("图片识别中...");

    }

    public void setloadfailed(String s) {
        spview.setVisibility(INVISIBLE);
        title_text.setText(s);
    }

    public void setdissmiss() {
        rootview.setVisibility(GONE);
    }


    @Override
    public void onClick(View view) {

    }

}
