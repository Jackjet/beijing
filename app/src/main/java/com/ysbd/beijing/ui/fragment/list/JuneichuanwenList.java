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

public class JuneichuanwenList extends BaseListFragment {


    @BindView(R.id.page)
    TextView tvPage;
    @BindView(R.id.count)
    TextView tvCount;
    Unbinder unbinder;
    @BindView(R.id.nian)
    EditText nian;
    @BindView(R.id.zi)
    EditText zi;
    @BindView(R.id.hao)
    EditText hao;
    @BindView(R.id.biaoti)
    EditText biaoti;
    @BindView(R.id.qicaoriqi)
    EditText qicaoriqi;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    private RecyclerView recyclerView;
    private List<TodoBean> list;
    private RecyclerViewAdapter adapter;
    private String type, id;
    private String actor;

    public static JuneichuanwenList getInstance(String type, String actor) {
        JuneichuanwenList fragment = new JuneichuanwenList();
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
        View inflate = inflater.inflate(R.layout.fragment_juneichuanwen_list, container, false);
        recyclerView = inflate.findViewById(R.id.recyclerView);
        unbinder = ButterKnife.bind(this, inflate);
        initToDo();
        initView();

        return inflate;
    }

    private void initToDo() {
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(list, R.layout.item_juneichuanwen_list, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {

                TextView tvNum = itemView.findViewById(R.id.tv_num);
                TextView tvZi = itemView.findViewById(R.id.tv_zi);
                TextView tvHao = itemView.findViewById(R.id.tv_hao);
                TextView tvTitle = itemView.findViewById(R.id.tv_title);
                TextView tvDate = itemView.findViewById(R.id.tv_date);
                TextView tvZhuban = itemView.findViewById(R.id.tv_zhuban);
                TextView tvSender = itemView.findViewById(R.id.tv_sender);
                TextView tvFawen = itemView.findViewById(R.id.tv_fawen);
                LinearLayout root = itemView.findViewById(R.id.root);
                tvZi.setText(list.get(position).getCHUSHI_ZI());
                tvFawen.setText("");
                if (position % 2 == 0) {
                    root.setBackgroundColor(0xffffffff);
                } else {
                    root.setBackgroundColor(0xeeeeeeee);
                }
                tvNum.setText(list.get(position).getRN());
                tvTitle.setText(list.get(position).getWENJIANMINGCHENG());
                tvZhuban.setText(list.get(position).getZHUBANCHUSHI());
                tvHao.setText(list.get(position).getHAO());
                tvDate.setText(DateFormatUtil.subDate(list.get(position).getSHOUWENRIQI()));
                tvSender.setText(list.get(position).getGUIDANGREN());

                tvSender.setVisibility(View.GONE);

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
//        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.head_todo_list, null);
//        adapter.addHeaderView(inflate);

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
        map.put("viewGUID", "{0A2FF40B-FFFF-FFFF-A033-B83600000005}");
        map.put("workflowGUID", "{A9522312-FFFF-FFFF-9529-B92C00000001}");
        map.put("WENJIANMINGCHENG", biaoti.getText().toString());
        map.put("CHUSHIMINGCHENG", "");
        map.put("HAO", hao.getText().toString());
        map.put("NIAN", nian.getText().toString());
        map.put("QICAORIQI", qicaoriqi.getText().toString());
        map.put("type", type);
        map.put("userid", SpUtils.getInstance().getUserId());
        map.put("stalength", "1");
        map.put("leixing", type);
        map.put("returnlength", "10");
        if (queryHelper != null) {
            queryHelper.query(map);
        }
    }


}