package com.ysbd.beijing.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.R;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.ui.bean.LingdaoRichengBean;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeanderScheduleActivity extends BaseActivity {
    @BindView(R.id.fl_back)
    FrameLayout flBack;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rv_schedule)
    RecyclerView rvSchedule;
    private RecyclerViewAdapter adapter;
    private List<ScheduleBean> scheduleBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leander_schedule);
        ButterKnife.bind(this);

        rvSchedule.setLayoutManager(new LinearLayoutManager(this));
        scheduleBeans = new ArrayList<>();
        adapter = new RecyclerViewAdapter(scheduleBeans, R.layout.item_schedule, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
//                TextView textView = itemView.findViewById(R.id.item_history);
//                textView.setText(String.valueOf(data));
                TextView tvName = itemView.findViewById(R.id.tv_name);
                TextView tvLocation = itemView.findViewById(R.id.tv_location);
                TextView tvContent = itemView.findViewById(R.id.tv_content);
                TextView tvHoster = itemView.findViewById(R.id.tv_hoster);
                TextView tvDepart = itemView.findViewById(R.id.tv_depart);
                TextView tvRemark = itemView.findViewById(R.id.tv_remark);
                tvName.setText(scheduleBeans.get(position).name);
                tvLocation.setText(scheduleBeans.get(position).location);
                tvContent.setText(scheduleBeans.get(position).content);
                tvHoster.setText(scheduleBeans.get(position).hoster);
                tvDepart.setText(scheduleBeans.get(position).depart);
                tvRemark.setText(scheduleBeans.get(position).remark);
            }
        });
        rvSchedule.setAdapter(adapter);
        getData();
        adapter.notifyDataSetChanged();
    }

    private void getData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String data=WebServiceUtils.getInstance().LeadershipAgenda();
                try {
                    List<Map<String ,String >> rs=new Gson().fromJson(data,new TypeToken< List<Map<String ,String >>>(){}.getType());
                    Map<String ,String > map=rs.get(0);
                    mHandler.obtainMessage(2,map.get("RIQI")).sendToTarget();
                    List<String> names=getNames();
                    ScheduleBean scheduleBean;
                    for (int i = 0; i < names.size(); i++) {
                        scheduleBean=new ScheduleBean();
                        scheduleBean.name = names.get(i);
                        scheduleBean.location = map.get(getPinyin(names.get(i))+"_SHIJIANDIDIAN");
                        scheduleBean.content = map.get(getPinyin(names.get(i))+"_NEIRONG");
                        scheduleBean.depart = map.get(getPinyin(names.get(i))+"_DANWEI_XINGMING");
                        scheduleBean.hoster = map.get(getPinyin(names.get(i))+"_ZHUCHIREN");
                        scheduleBean.remark = map.get(getPinyin(names.get(i))+"_BEIZHU");
                        scheduleBeans.add(scheduleBean);
                    }
                    mHandler.sendEmptyMessage(1);
                }catch (Exception e){
                    mHandler.sendEmptyMessage(3);
                }

            }
        }.start();
    }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    tvDate.setText("日期："+msg.obj.toString());
                    break;
                case 3:
                    ToastUtil.show("获取数据异常",LeanderScheduleActivity.this);
                    break;
            }
        }
    };

    @OnClick(R.id.fl_back)
    public void onViewClicked() {
        finish();
    }

    private String getPinyin(String name){
        switch (name){
            case "吴素芳":
                return "WSF";
            case "徐蘅":
                return "XU2";
            case "王婴":
                return "WANGYING";
            case "于学强":
                return "YUXUEQIANG";
            case "赵彦明":
                return "ZHAOYANMING";
            case "韩杰":
                return "HANJIE";
            case "师淑英":
                return "SHISHUYING";
            case "张宏宇":
                return "ZHANGHONGYU";
            case "汪钢":
                return "WANGGANG";
        }
        return "";
    }

    private List<String> getNames(){
        List<String> names=new ArrayList<>();
        names.add("吴素芳");
        names.add("徐蘅");
        names.add("王婴");
        names.add("于学强");
        names.add("赵彦明");
        names.add("韩杰");
        names.add("师淑英");
        names.add("张宏宇");
        names.add("汪钢");
        return names;
    }

    class ScheduleBean {
        String name;
        String location;
        String content;
        String hoster;
        String depart;
        String remark;
    }
}
