package com.tudouni.makemoney.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.adapter.MessageAdapter;
import com.tudouni.makemoney.model.DeleteSyaMsgRequestBean;
import com.tudouni.makemoney.model.MessageResponsBean;
import com.tudouni.makemoney.model.MineMessage;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.DialogUtils;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.view.MyDecoration;

import java.util.ArrayList;

/**
 * 消息列表界面
 */
public class MessageActivity extends AppCompatActivity {
    private String TAG = "MessageActivity";
    private RecyclerView mRcMessage;
    private MessageAdapter mMessageAdapter;
    private ArrayList<MineMessage> mData;
    private ArrayList<MineMessage> mDataForGsys;//全局的消息
    private ArrayList<MineMessage> mDataForSys;//非全局消息
    private int msgpage = 1;
    private int gmsgpage = 1;
    private ArrayList<Integer> deletePositionArray;//要删除的下标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initData();
        initView();
    }

    private void initData() {
        if (mData == null)
            mData = new ArrayList<>();
        if (mDataForSys == null)
            mDataForSys = new ArrayList<>();
        if (mDataForGsys == null)
            mDataForGsys = new ArrayList<>();
        if (deletePositionArray == null)
            deletePositionArray = new ArrayList<>();
        CommonScene.getSysMsg(MyApplication.getLoginUser().getUid(), msgpage, gmsgpage, new BaseObserver<MessageResponsBean>() {
            @Override
            public void OnSuccess(MessageResponsBean messageResponsBean) {
                if (messageResponsBean == null) return;
                ArrayList<MineMessage> mmData = new ArrayList<>();
                if (msgpage + gmsgpage == 2) {
                    updateMsgReadInfo(messageResponsBean);
                }
                if (messageResponsBean.getGsysmsg() != null && !messageResponsBean.getGsysmsg().isEmpty()) {
                    mDataForGsys.addAll(messageResponsBean.getGsysmsg());
                    gmsgpage++;
                }

                if (messageResponsBean.getSysmsg() != null && !messageResponsBean.getSysmsg().isEmpty()) {
                    mDataForSys.addAll(messageResponsBean.getSysmsg());
                    msgpage++;
                }
                mData.clear();
                mData.addAll(mDataForGsys);
                mData.addAll(mDataForSys);
                if (mData != null && !mData.isEmpty())
                    for (MineMessage mDatum : mData) {
                        mDatum.setShowTitle();
                    }
                mMessageAdapter.addData(mData);
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
                ToastUtil.showError(err, code);
            }
        });
    }

    private void initView() {
        mRcMessage = (RecyclerView) findViewById(R.id.rc_message);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        mRcMessage.setLayoutManager(layoutmanager);
        //设置Adapter
        mMessageAdapter = new MessageAdapter(this);
        mRcMessage.setAdapter(mMessageAdapter);
        mRcMessage.addItemDecoration(new MyDecoration(this, MyDecoration.HORIZONTAL_LIST));
        mMessageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String para = "?uid=" + MyApplication.getLoginUser().getUid() + "&token=" + MyApplication.getLoginUser().getToken() + "&unionid=" + MyApplication.getLoginUser().getUnionid();
                switch (mData.get(position).getType()) {
                    case 1:
                        ForwardUtils.target(MessageActivity.this, mData.get(position).getUrl());
                        break;
                    case 2:
                        ForwardUtils.target(MessageActivity.this, Constants.h5_mall_grade + para);
                        break;
                    case 3:
                        ForwardUtils.target(MessageActivity.this, Constants.h5_makemoney + para);
                        break;
                }
            }

            @Override
            public void onLongClick(int position) {
                deletePositionArray.add(position);
                DialogUtils.showCommonDialog(MessageActivity.this, "确定要删除本条消息？", "确定", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteMsg();
                    }
                }, null);
            }
        });
        mRcMessage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    if (lastItemPosition < mData.size() - 4) {
                        initData();
                    }
                }
            }
        });
//        mRcMessage.setIte
    }

    /**
     * 删除消息
     */
    private void deleteMsg() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int integer : deletePositionArray)
            arrayList.add(mData.get(integer).getType() + ":" + mData.get(integer).getId());
        CommonScene.deleteSysMsg(new DeleteSyaMsgRequestBean(MyApplication.getLoginUser().getUid(), arrayList), new BaseObserver<Object>() {
//        CommonScene.deleteSysMsgNew(MyApplication.getLoginUser().getUid(), arrayList, new BaseObserver<Object>() {

            @Override
            public void OnSuccess(Object o) {
                ToastUtil.show("删除成功！");
                for (Integer position : deletePositionArray) {
                    mData.remove(position);
                    if (position < mDataForGsys.size())
                        mDataForGsys.remove(position);
                    else
                        mDataForSys.remove(position - mDataForGsys.size());
                    mMessageAdapter.removeIteam(position);
                }
                deletePositionArray.clear();
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
                ToastUtil.show("删除失败！");
            }
        });
    }


    /**
     * 上报已读消息
     *
     * @param messageResponsBean
     */
    private void updateMsgReadInfo(MessageResponsBean messageResponsBean) {
        if (messageResponsBean == null) return;
        long sysmsgtime = (messageResponsBean.getSysmsg() != null && messageResponsBean.getSysmsg().size() > 0) ? (messageResponsBean.getSysmsg().get(0).getTime()) : 0;
        long gsysmsgtime = (messageResponsBean.getGsysmsg() != null && messageResponsBean.getGsysmsg().size() > 0) ? (messageResponsBean.getGsysmsg().get(0).getTime()) : 0;
        CommonScene.updateMsgReadInfo(sysmsgtime, gsysmsgtime, new BaseObserver<Object>() {
            @Override
            public void OnSuccess(Object o) {
                TuDouLogUtils.i(TAG, "updateMsgReadInfo secuss");
            }

            @Override
            public void OnFail(int code, String err) {
                ToastUtil.showError(err, code);
            }
        });
    }


}
