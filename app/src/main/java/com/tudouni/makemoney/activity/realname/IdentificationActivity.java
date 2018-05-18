package com.tudouni.makemoney.activity.realname;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.activity.SubmitFinishActivity;
import com.tudouni.makemoney.activity.UserInfoActivity;
import com.tudouni.makemoney.activity.withdrawmoney.TelAuthenticationActivity;
import com.tudouni.makemoney.activity.withdrawmoney.WithdrawMoneyActivity;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.ColorUtil;
import com.tudouni.makemoney.utils.CommonUtil;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.LoadingUtils;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.utils.upload.UploadUtils;
import com.tudouni.makemoney.view.HintDialog;
import com.tudouni.makemoney.view.MyTitleBar;

import java.util.ArrayList;

public class IdentificationActivity extends BaseActivity {
    @InjectView(id = R.id.title_bar)
    private MyTitleBar title_bar;
    @InjectView(id = R.id.mEdName)
    private EditText mEdName;
    @InjectView(id = R.id.mIdcard)
    private EditText mIdcard;
    @InjectView(id = R.id.tvLogin)
    private TextView tvLogin;
    @InjectView(id = R.id.iv_upload_1)
    private ImageView iv_upload_1;
    @InjectView(id = R.id.iv_upload_2)
    private ImageView iv_upload_2;

    private static int IMAGE_PICKER_1 = 551;
    private static int IMAGE_PICKER_2 = 552;
    private String photoPath;
    private LoadingUtils dialogUtils;
    private String photo_1;
    private String photo_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        initview();
    }

    private void initview() {
        title_bar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HintDialog hintDialog = new HintDialog(IdentificationActivity.this);
                hintDialog.setMessage("确定退出实名认证吗？");
                hintDialog.setLinstener(new HintDialog.OnDialogClickLinstener() {
                    @Override
                    public void onPositiveClick() {
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
                hintDialog.show();
            }
        });

        tvLogin.setOnClickListener(view -> {
            String idNumber = mIdcard.getText().toString().trim();
            if (!CommonUtil.isIDCard(idNumber)) {
                Toast.makeText(IdentificationActivity.this, "请输入正确的身份证号", Toast.LENGTH_LONG).show();
                return;
            }

            startAuthentication();
        });

        mEdName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viabilityAuthentication();
            }
        });

        mIdcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viabilityAuthentication();
            }
        });

        iv_upload_1.setOnClickListener(view -> {
            initDialogOpenAvatar(IMAGE_PICKER_1);
        });

        iv_upload_2.setOnClickListener(view -> {
            initDialogOpenAvatar(IMAGE_PICKER_2);
        });
    }

    private void initDialogOpenAvatar(int imagePicker) {
        ImagePicker.getInstance().setCrop(true);
        ImagePicker.getInstance().setMultiMode(false);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, imagePicker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && (requestCode == IMAGE_PICKER_1 || requestCode == IMAGE_PICKER_2)) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                photoPath = images.get(0).path;
                Log.e("", "地址：" + images.get(0).path);
                showLoading("上传中");
                UploadUtils.uploadFileForType(Constants.UPTYPE_IMGCOMMON, photoPath, new BaseObserver<String>() {

                    @Override
                    public void OnSuccess(String data) {
                        String photo = data;
                        ImageView iv_upload;

                        if (IMAGE_PICKER_1 == requestCode) {
                            photo_1 = data;
                            iv_upload = iv_upload_1;
                        } else {
                            photo_2 = data;
                            iv_upload = iv_upload_2;
                        }

                        GlideUtil.getInstance().loadImage(IdentificationActivity.this, photo, iv_upload, R.mipmap.default_head);
                        viabilityAuthentication();
                        dismissLoading();
                    }

                    @Override
                    public void OnFail(int code, String err) {
                        super.OnFail(code, err);
                        dismissLoading();
                    }
                });

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showLoading(String text) {
        dialogUtils = new LoadingUtils(this);
        dialogUtils.showLoading(text);
    }

    public void dismissLoading() {
        if (dialogUtils == null) return;
        dialogUtils.dismissLoading();
    }

    private void viabilityAuthentication() {
        boolean viability = false;
        do {
            String realname = mEdName.getText().toString().trim();
            String idNumber = mIdcard.getText().toString().trim();
            if (TextUtils.isEmpty(realname)) {
                break;
            }
            if (!CommonUtil.isIDCard(idNumber)) {
                break;
            }

            if (photo_1 == null || photo_2 == null) {
                break;
            }
            viability = true;
        } while (false);


        if (viability) {
            tvLogin.setBackgroundResource(R.drawable.login_btn_back_03);
            tvLogin.setTextColor(Color.WHITE);
        } else {
            tvLogin.setBackgroundResource(R.drawable.login_btn_back_01);
            tvLogin.setTextColor(ColorUtil.black());
        }
    }

    public void startAuthentication() {
        showLoading("");
        String realname = mEdName.getText().toString().trim();
        String idNumber = mIdcard.getText().toString().trim();

        CommonScene.setAuthInfo(realname, idNumber, photo_1, photo_2, new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                dismissLoading();
                Intent intent = new Intent(IdentificationActivity.this, SubmitFinishActivity.class);
                intent.putExtra("title", "认证成功");
                intent.putExtra("msg", "身份信息已提交，请等待工作人员审核");
                IdentificationActivity.this.startActivity(intent);
                finish();
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
                Toast.makeText(IdentificationActivity.this, "上传认证信息失败", Toast.LENGTH_LONG).show();
                dismissLoading();
            }
        });
    }
}
