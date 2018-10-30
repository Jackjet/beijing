package com.ysbd.beijing.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.BaseFragment;
import com.ysbd.beijing.R;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.ui.fragment.QueryFragment;
import com.ysbd.beijing.ui.fragment.QueryListFragment;
import com.ysbd.beijing.ui.fragment.form.ZhibiaowenFragment;
import com.ysbd.beijing.ui.fragment.list.BaseListFragment;
import com.ysbd.beijing.ui.fragment.list.JieyuzijinList;
import com.ysbd.beijing.ui.fragment.list.JuneichuanwenList;
import com.ysbd.beijing.ui.fragment.list.ShizhuanwenList;
import com.ysbd.beijing.ui.fragment.list.ToDoFragment;
import com.ysbd.beijing.ui.fragment.list.YibanfawenListFragment;
import com.ysbd.beijing.ui.fragment.list.ZhibiaowenList;
import com.ysbd.beijing.ui.fragment.list.ZhubanwenList;
import com.ysbd.beijing.utils.QueryHelper;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataActivity extends BaseActivity implements QueryHelper {
    @BindView(R.id.dataClassRecyclerView)
    RecyclerView dataClassRecyclerView;
    @BindView(R.id.dataClassFrameLayout)
    FrameLayout dataClassFrameLayout;
    @BindView(R.id.tv_user)
    TextView tvUser;
    private RecyclerView classRecyclerView;
    private List<String> classTitle;
    private RecyclerViewAdapter classAdapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);
        classRecyclerView = findViewById(R.id.dataClassRecyclerView);
        type = getIntent().getStringExtra("type");
        tvUser.setText(SpUtils.getInstance().getUserName());
        initDataClass();
        chooseFragment("待办");
    }

    QueryFragment queryFragment;

    private void chooseFragment(String code) {
        if ("表单查询".equals(code)) {
            queryFragment = QueryFragment.getInstance(type);
            queryFragment.setQueryHelper(this);
            replaceFragment(queryFragment);
        } else {
            code = code.replace("文档", "");
            replaceFragment(getFragment(code));
        }
//        switch (code) {
//            case 0://待办
//                replaceFragment(getFragment("待办"));
//                break;
//            case 1://在办
//                replaceFragment(getFragment("在办"));
//                break;
//            case 2://已办
//                replaceFragment(getFragment("已办"));
//                break;
//            case 3://在办
//                replaceFragment(getFragment("监控在办"));
//                break;
//            case 4://已办
//                replaceFragment(getFragment("监控已办"));
//                break;
//            case 5:
////                if (queryFragment==null) {
//                queryFragment = QueryFragment.getInstance(type);
////                }
//                queryFragment.setQueryHelper(this);
//                replaceFragment(queryFragment);
//                break;
//        }
    }

    private BaseFragment getFragment(String actor) {
        switch (type) {
            case "主办文":
                ZhubanwenList zhubanwenList = ZhubanwenList.getInstance(type, actor);
                zhubanwenList.setQueryHelper(this);
                return zhubanwenList;
            case "局内传文":
                JuneichuanwenList juneichuanwenList = JuneichuanwenList.getInstance(type, actor);
                juneichuanwenList.setQueryHelper(this);
                return juneichuanwenList;
            case "一般发文":
                YibanfawenListFragment yibanfawenListFragment = YibanfawenListFragment.getInstance(type, actor);
                yibanfawenListFragment.setQueryHelper(this);
                return yibanfawenListFragment;
            case "指标文":
                return ZhibiaowenList.getInstance(type, actor);
            case "结余资金":
                return JieyuzijinList.getInstance(type, actor);
            case "市转文":
                return ShizhuanwenList.getInstance(type, actor);
            default:
                return ToDoFragment.getInstance(type, actor);
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.dataClassFrameLayout, fragment);
        ft.commit();
    }

    private int selectPosition = 0;

    private void initDataClass() {
        classTitle = new ArrayList<>();
        classTitle.add("待办文档");
        classTitle.add("在办文档");
        classTitle.add("已办文档");
//        classTitle.add("监控在办");
//        classTitle.add("监控已办");
//        classTitle.add("回收站");
//        classTitle.add("监控回收站");
        classTitle.add("表单查询");
//        classTitle.add("编号管理");
//        classTitle.add("出差委托设置");
//        classTitle.add("重点督办");
//        classTitle.add("原文登记（纸质）");
//        classTitle.add("在办纸质文件");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        classRecyclerView.setLayoutManager(linearLayoutManager);
        classAdapter = new RecyclerViewAdapter(classTitle, R.layout.item_data_class, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                TextView textView = itemView.findViewById(R.id.item_data_class_text);
                textView.setText(String.valueOf(data));
                if (selectPosition == position) {
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setTextSize(18);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.gainsboro));
                    textView.setTextSize(16);
                }
            }
        });
        classRecyclerView.setAdapter(classAdapter);
        View inflate = LayoutInflater.from(this).inflate(R.layout.head_data_class, null);
        final ImageView dataClassImg = inflate.findViewById(R.id.head_data_class_img);
        final TextView dataClassTitle = inflate.findViewById(R.id.head_data_class_title);
        dataClassTitle.setText(getIntent().getStringExtra("type"));
        classAdapter.addHeaderView(inflate);
        dataClassImg.setImageResource(R.mipmap.exchange);
//        dataClassTitle.setText(classTitle.get(0));
        classAdapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                selectPosition = position;
                chooseFragment(classTitle.get(position));
//                if (classTitle.get(position).equals("表单查询")) {
//                    chooseFragment(position);
//                }
                classAdapter.notifyDataSetChanged();
                String s = classTitle.get(position);
//                dataClassTitle.setText(s);
            }
        });
        getMenu();
    }

    private void getMenu() {
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                String deedboxGUID="";
//                switch (type){
//                    case "主办文":
//                        deedboxGUID="{A9522312-FFFF-FFFF-B234-4A0A00000107}";
//                        break;
//                    case "局内传文":
//                        deedboxGUID="{A9522312-0000-0000-069E-099B000000AB}";
//                        break;
//                    case "一般发文":
//                    case "指标文":
//                    case "结余资金":
//                        deedboxGUID="{A952230B-0000-0000-2D53-B16E00000023}";
//                        break;
//                    case "市转文":
//                        deedboxGUID="{0A2FF41F-FFFF-FFFF-E266-3CDB00000072}";
//                        break;
//                }
//                String data=WebServiceUtils.getInstance().getUserMenus(deedboxGUID);
//                if (data!=null) {
//                    if(data.contains("监控在办")){
//                        classTitle.add(3,"监控在办");
//                    }
//                    if(data.contains("监控已办")){
//                        classTitle.add(4,"监控已办");
//                    }
////                    if(data.contains("在办纸质文件")){
////                        classTitle.add("在办纸质文件");
////                    }
//                }
//                handler.sendEmptyMessage(1);
//            }
        if (SpUtils.getInstance().getUserName().equals("吴素芳")
                ||SpUtils.getInstance().getUserName().equals("刘海涛")
                ||SpUtils.getInstance().getUserName().equals("范永杰")
                ||SpUtils.getInstance().getUserName().equals("彭尚高")) {
            classTitle.add(3, "监控在办");
            classTitle.add(4, "监控已办");
        }
        handler.sendEmptyMessage(1);


    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    classAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @OnClick(R.id.dataClassBackClick)
    public void onViewClicked() {
        if (showQueryList) {
            showQueryList = false;
            if (queryFragment != null) {
                replaceFragment(queryFragment);
            }
        } else {
            finish();
        }
    }


    boolean showQueryList = false;

    @Override
    public void query(Map<String, String> map) {
        showQueryList = true;
        replaceFragment(QueryListFragment.getInstance(map));
    }
}
