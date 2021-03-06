package com.tudouni.makemoney.activity.realname;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.TuDouLogUtils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RealnameFinalActivity extends BaseActivity {
    @InjectView(id = R.id.realname)
    private TextView realname;
    @InjectView(id = R.id.etCode)
    private TextView etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_final);
        CommonScene.getUserInfo(new BaseObserver<User>() {
            @Override
            public void OnSuccess(User user) {
                realname.setText(user.getRealname());
                etCode.setText(user.getIdNumber());
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code,err);
            }
        });
    }
}
