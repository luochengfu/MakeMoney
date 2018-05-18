package com.tudouni.makemoney.activity;

import android.databinding.generated.callback.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.view.MyTitleBar;

public class SubmitFinishActivity extends BaseActivity {
    @InjectView(id = R.id.title_bar)
    private MyTitleBar myTitleBar;
    @InjectView(id = R.id.tv_msg)
    private TextView tvMsg;
    @InjectView(id = R.id.tv_submit)
    private TextView tvSubmit;

    private String title;
    private String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_finish);
        Bundle extras = null;
        try {
            extras = getIntent().getExtras();
        } catch (Exception e) {
            finish();
            return;
        }
        if (extras == null) {
            finish();
            return;
        }
        title = extras.getString("title");
        msg = extras.getString("msg");
        initview();
    }

    private void initview() {
        if (null != title) {
            myTitleBar.setMiddleText(title);
        }
        if (null != msg) {
            tvMsg.setText(msg);
        }
        tvSubmit.setOnClickListener(view -> {
            finish();
        });
    }
}
