package com.tudouni.makemoney.activity.withdrawmoney;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.model.Withdraw;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.ColorUtil;
import com.tudouni.makemoney.utils.CommonUtil;
import com.tudouni.makemoney.view.mmlist.LoadMoreListener;
import com.tudouni.makemoney.view.mmlist.MmListView;
import com.tudouni.makemoney.view.mmlist.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class IncomeActivity extends BaseActivity {
    public class IncomeAdapter extends BaseAdapter{

        private Context context;
        private List<Withdraw> list;
        private LayoutInflater mLayoutInflater;

        public IncomeAdapter(Context context, List<Withdraw> list) {
            super();
            this.context = context;
            this.list = list;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_income, parent, false);
            }

            Withdraw withdraw = list.get(position);
            String strStatus = "提现失败";
            String strMoney = new StringBuilder("¥").append(CommonUtil.getMoney(withdraw.getAmount())).toString();
            switch (withdraw.getReviewStatus()) {
                case "1":
                    strStatus = "待审核";
                    strMoney = "+" + strMoney;
                    break;
                case "2":
                    strStatus = "提现成功";
                    strMoney = "+" + strMoney;
                    break;
                default:
                    break;
            }

            TextView tvMoney = ViewHolder.get(convertView, R.id.tv_money);
            tvMoney.setText(strMoney);

            TextView tvTime = ViewHolder.get(convertView, R.id.tv_time);
            tvTime.setText(withdraw.getCreateTime());

            TextView tvStatus = ViewHolder.get(convertView, R.id.tv_status);
            tvStatus.setText(strStatus);

            if (withdraw.getReviewStatus().equals("1")) {
                tvMoney.setTextColor(ColorUtil.gray02());
                tvTime.setTextColor(ColorUtil.gray02());
                tvStatus.setTextColor(ColorUtil.gray02());
            }

            return convertView;
        }

    }

    private MmListView myListView;
    private List<Withdraw> list = new ArrayList();
    private IncomeAdapter adapter;

    private boolean loadFinish = false;
    private int incomePage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        myListView = (MmListView) findViewById(R.id.lv_income);
        adapter = new IncomeAdapter(this, list);
        myListView.setAdapter(adapter);

        generateData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        /**
         * 上拉加载更多
         */
        myListView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!loadFinish) {
                    generateData();
                }
            }
        });
    }

    /**
     * 生成数据
     */
    private void generateData() {
        ++incomePage;
        CommonScene.incomeHistory(String.valueOf(incomePage), new BaseObserver<List<Withdraw>>() {
            @Override
            public void OnSuccess(List<Withdraw> withdraws) {
                if (withdraws.size() > 0) {
                    list.addAll(withdraws);
                    //设置loading完成
                    myListView.loadMoreComplete();
                    adapter.notifyDataSetChanged();
                } else {
                    //设置加载到底
                    myListView.loadMoreEnd();
                    loadFinish = true;
                }
            }

            @Override
            public void OnFail(int code, String err) {
                //设置loading完成
                myListView.loadMoreComplete();
                super.OnFail(code, err);
            }
        });
    }
}
