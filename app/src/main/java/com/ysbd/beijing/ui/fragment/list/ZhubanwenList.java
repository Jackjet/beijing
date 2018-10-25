package com.ysbd.beijing.ui.fragment.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysbd.beijing.R;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.ui.activity.FormActivity;
import com.ysbd.beijing.ui.bean.TodoBean;
import com.ysbd.beijing.utils.CommentFormUtils;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.QueryHelper;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by lcjing on 2018/9/5.
 */

public class ZhubanwenList extends BaseListFragment {


    @BindView(R.id.count)
    TextView tvCount;
    Unbinder unbinder;
    @BindView(R.id.page)
    TextView tvPage;
    @BindView(R.id.nian)
    EditText nian;
    @BindView(R.id.hao)
    EditText hao;
    @BindView(R.id.wenhao)
    EditText wenhao;
    @BindView(R.id.biaoti)
    EditText biaoti;
    @BindView(R.id.zhubanchushi)
    EditText zhubanchushi;
    @BindView(R.id.laiwendanwei)
    EditText laiwendanwei;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    private RecyclerView recyclerView;
    private List<TodoBean> list;
    private RecyclerViewAdapter adapter;
    private String type, id;
    private String actor;

    public static ZhubanwenList getInstance(String type, String actor) {
        ZhubanwenList fragment = new ZhubanwenList();
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        args.putSerializable("actor", actor);
        fragment.setArguments(args);
        return fragment;
    }


    public void setActor(String actor) {
        list.clear();
        this.actor = actor;
        initView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("type");
        actor = getArguments().getString("actor");
        id = CommentFormUtils.getFormId(type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_zhubanwen_list, container, false);
        recyclerView = inflate.findViewById(R.id.recyclerView);
        unbinder = ButterKnife.bind(this, inflate);
        initToDo1();
        initView();

        return inflate;
    }

    private void initToDo() {
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(list, R.layout.item_zhubanwen_list, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {

                TextView tvNum = itemView.findViewById(R.id.tv_num);
                TextView tvType = itemView.findViewById(R.id.tv_type);
                TextView tvTitle = itemView.findViewById(R.id.tv_title);
                TextView tvTask = itemView.findViewById(R.id.tv_task);
                TextView tvActor = itemView.findViewById(R.id.tv_actor);
                TextView tvStates = itemView.findViewById(R.id.tv_states);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                LinearLayout root = itemView.findViewById(R.id.root);
                tvTask.setText("");
                tvActor.setText("");
                tvStates.setText("");
                if (position % 2 == 0) {
                    root.setBackgroundColor(0xffffffff);
                } else {
                    root.setBackgroundColor(0xeeeeeeee);
                }
                tvNum.setText("" + (position + 1));
                tvType.setText("主办文");
                tvTitle.setText(list.get(position).getWENJIANMINGCHENG());
                tvTime.setText(DateFormatUtil.subDate(list.get(position).getRECIEVEDATE()));

            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                setLiveTime();
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("actor", actor);
                intent.putExtra("instanceguid", list.get(position).getWORKFLOWINSTANCE_GUID());
                startActivity(intent);
            }
        });
    }

    String page = "1";

    @OnClick({R.id.last, R.id.next})
    public void onViewClicked(View view) {
        int p = Integer.parseInt(page);
        switch (view.getId()) {
            case R.id.last:
                if (p > 1) {
                    p--;
                    page = p + "";
                    initView();
                }
                break;
            case R.id.next:
                if (list.size() >= 10) {
                    p++;
                    page = p + "";
                    initView();
                }
                break;
        }
    }

    private void initToDo1() {
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(list, R.layout.item_zhubanwen_list1, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {

                TextView tvNum1 = itemView.findViewById(R.id.tv_num1);
                TextView tvHao1 = itemView.findViewById(R.id.tv_hao1);
                TextView tvLaiwenriqi1 = itemView.findViewById(R.id.tv_laiwenriqi1);
                TextView tvXianbanriqi1 = itemView.findViewById(R.id.tv_xianbanriqi1);
                TextView tvLaiwendanwei1 = itemView.findViewById(R.id.tv_laiwendanwei1);
                TextView tvYuanwenzihao1 = itemView.findViewById(R.id.tv_yuanwenzihao1);
                TextView tvBiaoti1 = itemView.findViewById(R.id.tv_biaoti1);
                TextView tvZhubanchushi1 = itemView.findViewById(R.id.tv_zhubanchushi1);
                TextView tvUser1 = itemView.findViewById(R.id.tv_user1);
                ;
                TextView tvZhongdianducha1 = itemView.findViewById(R.id.tv_zhongdianducha1);
                TextView tvShifoufawen1 = itemView.findViewById(R.id.tv_shifoufawen1);
                LinearLayout llRoot1 = itemView.findViewById(R.id.ll_root1);


                if (position % 2 == 0) {
                    llRoot1.setBackgroundColor(0xffffffff);
                } else {
                    llRoot1.setBackgroundColor(0xeeeeeeee);
                }
                tvNum1.setText(list.get(position).getRN());
                tvHao1.setText(list.get(position).getHAO());
                tvLaiwenriqi1.setText(DateFormatUtil.subDate1(list.get(position).getRECIEVEDATE()));
                tvXianbanriqi1.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANDATE()));
                tvLaiwendanwei1.setText(list.get(position).getLAIWENDANWEI());
                tvBiaoti1.setText(list.get(position).getWENJIANMINGCHENG());
                tvZhubanchushi1.setText(list.get(position).getZHUBANCHUSHI());
                switch (actor) {
                    case "待办":
                    case "todo":
                        tvUser1.setText(list.get(position).getDQNAME());
                        break;
                    case "在办":
                    case "doing":
                        tvUser1.setText(list.get(position).getLURUREN());
                        break;
                    default:
                        tvUser1.setText(list.get(position).getXIANBANRIQI());
                }


                tvYuanwenzihao1.setText(list.get(position).getYUANWENZIHAO());

            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                setLiveTime();
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("actor", actor);
                intent.putExtra("instanceguid", list.get(position).getWORKFLOWINSTANCE_GUID());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String s = WebServiceUtils.getInstance().findTodoFiles(id, type, page, actor);
                List<TodoBean> todoBeans = new Gson().fromJson(s, new TypeToken<List<TodoBean>>() {
                }.getType());
                if (todoBeans != null) {
                    list.clear();
                    list.addAll(todoBeans);
                    handler.sendEmptyMessage(1);
                }else {
                    handler.sendEmptyMessage(2);
                }

            }
        }.start();
    }

    @OnClick(R.id.query)
    public void onViewClicked() {
        query();
    }

    private QueryHelper queryHelper;

    public void setQueryHelper(QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    private void query() {
        Map<String, String> map = new HashMap<>();
        map.put("viewGUID", "{0A2FF41F-FFFF-FFFF-B7C0-27C00000000D}");
        map.put("workflowGUID", "{A9522312-FFFF-FFFF-9538-050B00000002}");
        map.put("NIAN", nian.getText().toString());
        map.put("HAO", hao.getText().toString());
        map.put("WENJIANMINGCHENG", biaoti.getText().toString());
        map.put("LAIWENDANWEI", laiwendanwei.getText().toString());
        map.put("YUANWENZIHAO", wenhao.getText().toString());
        map.put("ZHUBANCHUSHI", zhubanchushi.getText().toString());
        map.put("type", type);
        map.put("userid", SpUtils.getInstance().getUserId());
        map.put("stalength", "1");
        map.put("leixing", type);
        map.put("returnlength", "10");
        if (queryHelper != null) {
            queryHelper.query(map);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (viewDestroyed) {
                return;
            }
            switch (msg.what) {
                case 1:
                    if (list.size()>0) {
                        llNull.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }else {
                        llNull.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                    tvPage.setText(page);
                    tvCount.setText(" ");
                    break;
                case 2:
                    if (list.size()>0) {
                        llNull.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }else {
                        llNull.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    private boolean viewDestroyed = false;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        viewDestroyed = true;
    }


}
