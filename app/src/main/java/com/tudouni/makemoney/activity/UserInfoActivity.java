package com.tudouni.makemoney.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.text.TextUtils;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.Logger;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.DateUtils;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.CommonUtil;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.LoadingUtils;
import com.tudouni.makemoney.utils.StringUtil;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.utils.UserInfoHelper;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.utils.upload.UploadUtils;
import com.tudouni.makemoney.view.MyTitleBar;
import com.tudouni.makemoney.widget.callBack.ApiCallback;
import com.tudouni.makemoney.widget.callBack.ServiceException;
import com.tudouni.makemoney.widget.dialog.DialogChooseSex;
import com.tudouni.makemoney.widget.picker.DatePickDialog;
import com.tudouni.makemoney.widget.picker.OnSureLisener;
import com.tudouni.makemoney.widget.picker.bean.DateType;
import com.tudouni.makemoney.widget.picker.citypickerview.widget.CityPicker;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 编辑资料
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "UserInfoActivity";
    @InjectView(id = R.id.title_bar)
    private MyTitleBar title_bar;
    @InjectView(id = R.id.llUser)
    LinearLayout llUser;
    @InjectView(id = R.id.llSex)
    LinearLayout llSex;
    @InjectView(id = R.id.llBirthday)
    LinearLayout llBirthday;
    @InjectView(id = R.id.llCity)
    LinearLayout llCity;
    @InjectView(id = R.id.etName)
    EditText etName;
    @InjectView(id = R.id.etSign)
    EditText etSign;
    @InjectView(id = R.id.tvSex)
    TextView tvSex;
    @InjectView(id = R.id.tvCity)
    TextView tvCity;
    @InjectView(id = R.id.tvId)
    TextView tvId;
    @InjectView(id = R.id.tvBirthday)
    TextView tvBirthday;
    @InjectView(id = R.id.ivPhoto)
    ImageView ivPhoto;

    private String photoPath;
    private User user;
    private int IMAGE_PICKER = 555;

    private LoadingUtils dialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initData();
    }

    protected void initData() {
        if (null != MyApplication.getLoginUser()) {
            User user = MyApplication.getLoginUser();
            GlideUtil.getInstance().loadImage(this, user.getPhoto(), ivPhoto, R.mipmap.default_head);
            etName.setText(user.getNickName());
            //昵称光标右边
            etName.setSelection(etName.length());
            if (user.getCity() == null) {
                tvCity.setText("火星");
            } else {
                tvCity.setText(user.getCity());
            }
            String sign = user.getSignature();
            if (!TextUtils.isEmpty(sign)) {
                etSign.setText(sign);
            }
            //个签光标右边
            etSign.setSelection(etSign.length());
            tvId.setText(user.getUnumber());
            tvBirthday.setText(user.getBirthday());
            tvSex.setText(user.getSex().equals("0") ? "女" : "男");
        }

        etSign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    String sign = etSign.getText().toString();
                    if ("这个家伙有点懒~".equals(sign)) {
                        etSign.setText("");
                    }
                }
            }
        });

        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etName.setText("");
                }
            }
        });


    }


    protected void initView() {
        title_bar.setMiddleText(getResources().getString(R.string.upUserInfo));
        title_bar.setRightText("保存");
        title_bar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });

        llUser.setOnClickListener(this);
        llSex.setOnClickListener(this);
        llCity.setOnClickListener(this);
        llBirthday.setOnClickListener(this);
    }

    private void saveUserInfo() {
        if (StringUtil.isNullOrEmpty(etName.getText().toString())) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String sign = etSign.getText().toString();
        if (null == sign || "".equals(sign)) {
            sign = "这个家伙有点懒~";
        }
        user = new User();
        user.setBirthday(tvBirthday.getText().toString());
        user.setCity(tvCity.getText().toString());
        user.setNickName(etName.getText().toString());
        user.setPhoto(photoUrl);
        user.setSex(tvSex.getText().toString().equals("男") ? "1" : "0");
        user.setSignature(sign);

        Map<String, String> params = new HashMap<>();
        params.put("birthday", tvBirthday.getText().toString());
        params.put("city", tvCity.getText().toString());
        params.put("nickName", etName.getText().toString());
        if (!StringUtil.isEmpty(photoUrl)) {
            params.put("photo", photoUrl);
        }
        params.put("sex", tvSex.getText().toString().equals("男") ? "1" : "0");
        params.put("singtrue", sign);
        CommonScene.setUserInfo(tvBirthday.getText().toString(), tvCity.getText().toString(),
                etName.getText().toString(), photoUrl, tvSex.getText().toString().equals("男") ? "1" : "0", sign, new BaseObserver<Object>() {
                    @Override
                    public void OnSuccess(Object o) {
                        User userNew = UserInfoHelper.getUserDatas(UserInfoActivity.this);
                        userNew.setBirthday(user.getBirthday());
                        userNew.setCity(user.getCity());
                        userNew.setNickName(user.getNickName());
                        userNew.setSex(user.getSex());
                        userNew.setSignature(user.getSignature());
                        userNew.setPhoto(photoUrl);
                        MyApplication.saveLoginUser(userNew);
                        Logger.e(userNew.toString() + "@@", MyApplication.getLoginUser().toString());
                        setResult(2);
                        UserInfoActivity.this.finish();
                    }

                    @Override
                    public void OnFail(int code, String err) {
                        super.OnFail(code, err);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSex:
                CommonUtil.hideKeyBoard(this, etName);
                upSex();
                break;
            case R.id.llUser:
                initDialogOpenAvatar();
                break;
            case R.id.llBirthday:
                DatePickDialog dialog = new DatePickDialog(this);
                dialog.setYearLimt(70);
                dialog.setTitle("选择时间");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setStartDate(new Date(System.currentTimeMillis() - 525600000));
                dialog.setMessageFormat("yyyy-MM-dd");
                dialog.setOnChangeLisener(null);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        tvBirthday.setText(DateUtils.dateToStr(date, "yyyy-MM-dd"));
                    }
                });
                dialog.show();
                break;
            case R.id.llCity:
                CityPicker cityPicker = new CityPicker.Builder(this)
                        .textSize(16)
                        .title("地址选择")
                        .titleBackgroundColor("#ffffff")
                        .titleTextColor("#000000")
                        .confirTextColor("#000000")
                        .cancelTextColor("#000000")
                        .province("北京")
                        .city("北京")
                        .district("北京")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(true)
                        .build();
                cityPicker.show();
                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //城市
                        /*** @@@@ modify by 24020 at 2017/3/28 21:58@@@@@*****/
//                        String city = citySelected[0] + citySelected[1];
                        String city = citySelected[1];
                        /*** @@@@ modify by 24020 at 2017/3/28 21:58@@@@@*****/
                        tvCity.setText(city);
                    }
                });
                break;

        }
    }

    //选择性别。
    private void upSex() {
        DialogChooseSex dialogChoosePick = new DialogChooseSex(UserInfoActivity.this, new DialogChooseSex.Dialogcallback() {
            @Override
            public void pickWeightResult(String sex) {

                tvSex.setText(sex);
            }
        });
        dialogChoosePick.show();
    }

    private void initDialogOpenAvatar() {
        ImagePicker.getInstance().setCrop(true);
        ImagePicker.getInstance().setMultiMode(false);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }


    private String photoUrl = MyApplication.getLoginUser().getPhoto();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                photoPath = images.get(0).path;
                Log.e("", "地址：" + images.get(0).path);
                showLoading("上传中");
                UploadUtils.uploadImageForUser(photoPath, new BaseObserver<String>() {

                    @Override
                    public void OnSuccess(String data) {
                        photoUrl = data;
                        GlideUtil.getInstance().loadImage(UserInfoActivity.this, photoUrl, ivPhoto, R.mipmap.default_head);
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

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (write != PackageManager.PERMISSION_GRANTED || read != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
            } else {
                String name = "CrashDirectory";
                File file1 = new File(Environment.getExternalStorageDirectory(), name);
                if (file1.mkdirs()) {
                    TuDouLogUtils.i("wytings", "permission -------------> " + file1.getAbsolutePath());
                } else {
                    TuDouLogUtils.i("wytings", "permission -------------fail to make file ");
                }
            }
        } else {
            TuDouLogUtils.i("wytings", "------------- Build.VERSION.SDK_INT < 23 ------------");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 300) {
            TuDouLogUtils.i("wytings", "--------------requestCode == 300->" + requestCode + "," + permissions.length + "," + grantResults.length);
        } else {
            TuDouLogUtils.i("wytings", "--------------requestCode != 300->" + requestCode + "," + permissions + "," + grantResults);
        }
    }

    public void showLoading() {
        showLoading("");
    }

    public void showLoading(String text) {
        dialogUtils = new LoadingUtils(this);
        dialogUtils.showLoading(text);
    }

    public void dismissLoading() {
        if (dialogUtils == null) return;
        dialogUtils.dismissLoading();
    }

}

