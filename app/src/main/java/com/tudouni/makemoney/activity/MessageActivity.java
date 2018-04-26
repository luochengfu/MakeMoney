package com.tudouni.makemoney.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.adapter.MessageAdapter;
import com.tudouni.makemoney.model.MineMessage;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView mRcMessage;
    private MessageAdapter mMessageAdapter;
    private ArrayList<MineMessage> mData;

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
    }

    private void initView() {
        mRcMessage = (RecyclerView) findViewById(R.id.rc_message);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        mRcMessage.setLayoutManager(layoutmanager);
        //设置Adapter
        mMessageAdapter = new MessageAdapter();
        mRcMessage.setAdapter(mMessageAdapter);

//        mRcMessage.setIte
    }
}
