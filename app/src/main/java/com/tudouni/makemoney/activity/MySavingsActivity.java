package com.tudouni.makemoney.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ActivitySavingsBinding;

public class MySavingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySavingsBinding savingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_savings);
    }
}
