package com.ysbd.beijing.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.bean.ActorsBean;
import com.ysbd.beijing.bean.HistoryBean;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {
    private List<ActorsBean> actorsBeans;
    private List<HistoryBean> historyBeans;
    private RecyclerView rvHistory;
    private RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        rvHistory = findViewById(R.id.rv_history);
        actorsBeans = (List<ActorsBean>) getIntent().getSerializableExtra("actors");
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        historyBeans = new ArrayList<>();
        adapter = new RecyclerViewAdapter(historyBeans, R.layout.item_history, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
//                TextView textView = itemView.findViewById(R.id.item_history);
//                textView.setText(String.valueOf(data));
                TextView tvNum = itemView.findViewById(R.id.tv_num);
                TextView tvSender = itemView.findViewById(R.id.tv_sender);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                TextView tvReceiver = itemView.findViewById(R.id.tv_receiver);
                tvNum.setText(historyBeans.get(position).getStep());
                tvSender.setText(historyBeans.get(position).getSender());
                tvTime.setText(historyBeans.get(position).getSendTime());
                tvReceiver.setText(historyBeans.get(position).getReceiver());
            }
        });
        rvHistory.setAdapter(adapter);
        for (int i = 1; i < actorsBeans.size(); i++) {
            HistoryBean historyBean = new HistoryBean();
            String senderId = actorsBeans.get(i ).getProecssActor().getPersonGUID();
            String sendName = DBUtils.getPersonNameById(senderId);
            historyBean.setSender(sendName + "(" + actorsBeans.get(i ).getDepartName() + ")");
            String receiverId = actorsBeans.get(i-1).getProecssActor().getPersonGUID();
            String receiverName = DBUtils.getPersonNameById(receiverId);
            receiverName=receiverName + "(" + actorsBeans.get(i-1).getDepartName() + ")";

            if (i==1) {
                String status="";
                switch (actorsBeans.get(0).getProecssActor().getHandelStatus()) {
                    case 1:
                        status="待办";
                        break;
                    case 2:
                        status="已阅";
                        break;
                    case 3:
                        status="暂存";
                        break;
                    case 4:
                        status="办结";
                        break;
                }
                receiverName=receiverName+status;
            }
            historyBean.setReceiver(receiverName);
            historyBean.setStep(actorsBeans.get(i).getCurrentStep() + "");
            historyBean.setSendTime(actorsBeans.get(i-1).getUpdateDateString());
            historyBeans.add(0, historyBean);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fl_back)
    public void onViewClicked() {
        finish();
    }
}
