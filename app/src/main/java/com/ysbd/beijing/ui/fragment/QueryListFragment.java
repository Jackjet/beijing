package com.ysbd.beijing.ui.fragment;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ysbd.beijing.R;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.ui.activity.FormActivity;
import com.ysbd.beijing.ui.bean.QueryListBean;
import com.ysbd.beijing.ui.bean.TodoBean;
import com.ysbd.beijing.ui.fragment.list.BaseListFragment;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lcjing on 2018/9/7.
 */

public class QueryListFragment extends BaseListFragment {


    @BindView(R.id.page)
    TextView tvPage;
    @BindView(R.id.count)
    TextView tvCount;
    @BindView(R.id.juneichuanwen_bar)
    LinearLayout juneichuanwenBar;
    @BindView(R.id.shizhuanwen_bar)
    LinearLayout shizhuanwenBar;
    @BindView(R.id.zhubanwen_bar)
    LinearLayout zhubanwenBar;
    @BindView(R.id.yibanfawen_bar)
    LinearLayout yibanfawenBar;
    @BindView(R.id.zhibiaowen_bar)
    LinearLayout zhibiaowenBar;
    Unbinder unbinder;
    private RecyclerView recyclerView;
    private List<TodoBean> list;
    private RecyclerViewAdapter adapter;
    private String actor;
    private QueryListBean queryListBean;
    private Map<String, String> params;


    public static QueryListFragment getInstance(Map<String, String> params) {
        QueryListFragment queryListFragment = new QueryListFragment();
        Bundle args = new Bundle();
        args.putSerializable("params", (Serializable) params);
        queryListFragment.setArguments(args);
        return queryListFragment;
    }

    public void setActor(String actor) {
        list.clear();
        this.actor = actor;
        initView();
    }

    private String type;

    private String page="1";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        params = (Map<String, String>) getArguments().getSerializable("params");
        type = params.get("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_query_list, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        recyclerView = inflate.findViewById(R.id.recyclerView);
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

    private void initToDo1() {
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        initAdapter();
//        adapter = new RecyclerViewAdapter(list, R.layout.item_zhubanwen_list1, new OnBindView() {
//            @Override
//            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
//
//                TextView tvNum1 = itemView.findViewById(R.id.tv_num1);
//                TextView tvHao1 = itemView.findViewById(R.id.tv_hao1);
//                TextView tvLaiwenriqi1 = itemView.findViewById(R.id.tv_laiwenriqi1);
//                TextView tvXianbanriqi1 = itemView.findViewById(R.id.tv_xianbanriqi1);
//                TextView tvLaiwendanwei1 = itemView.findViewById(R.id.tv_laiwendanwei1);
//                TextView tvYuanwenzihao1 = itemView.findViewById(R.id.tv_yuanwenzihao1);
//                TextView tvBiaoti1 = itemView.findViewById(R.id.tv_biaoti1);
//                TextView tvZhubanchushi1 = itemView.findViewById(R.id.tv_zhubanchushi1);
//                TextView tvUser1 = itemView.findViewById(R.id.tv_user1);
//                ;
//                TextView tvZhongdianducha1 = itemView.findViewById(R.id.tv_zhongdianducha1);
//                TextView tvShifoufawen1 = itemView.findViewById(R.id.tv_shifoufawen1);
//                LinearLayout llRoot1 = itemView.findViewById(R.id.ll_root1);
//
//
//                if (position % 2 == 0) {
//                    llRoot1.setBackgroundColor(0xffffffff);
//                } else {
//                    llRoot1.setBackgroundColor(0xeeeeeeee);
//                }
//                switch (type) {
//                    case "主办文":
//                        tvNum1.setText("" + (position + 1));
//                        tvHao1.setText(list.get(position).getHAO());
//                        tvLaiwenriqi1.setText(DateFormatUtil.subDate1(list.get(position).getRECIEVEDATE()));
//                        tvXianbanriqi1.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANDATE()));
//                        tvLaiwendanwei1.setText(list.get(position).getLAIWENDANWEI());
//                        tvBiaoti1.setText(list.get(position).getWENJIANMINGCHENG());
//                        tvZhubanchushi1.setText(list.get(position).getZHUBANCHUSHI());
//                        tvUser1.setText(list.get(position).getDQNAME());
//                        break;
//                    case "一般发文":
//                        tvNum1.setText("" + (position + 1));
//                        tvHao1.setText(list.get(position).getHAO());
//                        tvLaiwenriqi1.setText(DateFormatUtil.subDate1(list.get(position).getRECIEVEDATE()));
//                        tvXianbanriqi1.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANDATE()));
//                        tvLaiwendanwei1.setText(list.get(position).getLAIWENDANWEI());
//                        tvBiaoti1.setText(list.get(position).getBIAOTI());
//                        tvZhubanchushi1.setText(list.get(position).getZHUBANCHUSHI());
//                        tvUser1.setText(list.get(position).getDQNAME());
//                        break;
//                    case "市转文":
//                        tvNum1.setText("" + (position + 1));
//                        tvHao1.setText(list.get(position).getHAO());
//                        tvLaiwenriqi1.setText(DateFormatUtil.subDate1(list.get(position).getRECIEVEDATE()));
//                        tvXianbanriqi1.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANDATE()));
//                        tvLaiwendanwei1.setText(list.get(position).getLAIWENDANWEI());
//                        tvBiaoti1.setText(list.get(position).getWENJIANMINGCHENG());
//                        tvZhubanchushi1.setText(list.get(position).getZHUBANCHUSHI());
//                        tvUser1.setText(list.get(position).getDQNAME());
//                        break;
//                    case "局内传文":
//                        tvNum1.setText("" + (position + 1));
//                        tvHao1.setText(list.get(position).getHAO());
//                        tvLaiwenriqi1.setText(DateFormatUtil.subDate1(list.get(position).getRECIEVEDATE()));
//                        tvXianbanriqi1.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANDATE()));
//                        tvLaiwendanwei1.setText(list.get(position).getLAIWENDANWEI());
//                        tvBiaoti1.setText(list.get(position).getWENJIANMINGCHENG());
//                        tvZhubanchushi1.setText(list.get(position).getZHUBANCHUSHI());
//                        tvUser1.setText(list.get(position).getDQNAME());
//                        break;
//                    case "指标文":
//                        tvNum1.setText("" + (position + 1));
//                        tvHao1.setText(list.get(position).getHAO());
//                        tvLaiwenriqi1.setText(DateFormatUtil.subDate1(list.get(position).getRECIEVEDATE()));
//                        tvXianbanriqi1.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANDATE()));
//                        tvLaiwendanwei1.setText(list.get(position).getLAIWENDANWEI());
//                        tvBiaoti1.setText(list.get(position).getBIAOTI());
//                        tvZhubanchushi1.setText(list.get(position).getZHUBANCHUSHI());
//                        tvUser1.setText(list.get(position).getDQNAME());
//                        break;
//                    case "结余资金":
//                        tvNum1.setText("" + (position + 1));
//                        tvHao1.setText(list.get(position).getHAO());
//                        tvLaiwenriqi1.setText(DateFormatUtil.subDate1(list.get(position).getRECIEVEDATE()));
//                        tvXianbanriqi1.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANDATE()));
//                        tvLaiwendanwei1.setText(list.get(position).getLAIWENDANWEI());
//                        tvBiaoti1.setText(list.get(position).getBIAOTI());
//                        tvZhubanchushi1.setText(list.get(position).getZHUBANCHUSHI());
//                        tvUser1.setText(list.get(position).getDQNAME());
//                        break;
//
//                }
//
//
//                tvYuanwenzihao1.setText("");
//
//            }
//        });
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                setLiveTime();
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("actor", "done");
                intent.putExtra("instanceguid", list.get(position).getWORKFLOWINSTANCE_GUID());
                startActivity(intent);
            }
        });
    }

    private void initAdapter(){
        switch (type){
            case "局内传文":
                juneichuanwenBar.setVisibility(View.VISIBLE);
                adapter = new RecyclerViewAdapter(list, R.layout.jncw_query_item, new OnBindView() {
                    @Override
                    public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                        TextView tvNum = itemView.findViewById(R.id.tv_jncw_num);
                        TextView tvHao = itemView.findViewById(R.id.tv_jncw_hao);
                        TextView tvBiaoti = itemView.findViewById(R.id.tv_jncw_biaoti);
                        TextView tvQicaoriqi = itemView.findViewById(R.id.tv_jncw_qicaoriqi);
                        TextView tvZhubanchushi = itemView.findViewById(R.id.tv_jncw_zhubanchushi);
                        TextView tvPerson = itemView.findViewById(R.id.tv_jncw_person);
                        TextView tvFw = itemView.findViewById(R.id.tv_jncw_fw);
                        LinearLayout root= itemView.findViewById(R.id.juneichuanwen_bar);
                        tvNum.setText(list.get(position).getRN());
                        tvHao.setText(list.get(position).getHAO());
                        tvZhubanchushi.setText(list.get(position).getZHUBANCHUSHI());
                        tvQicaoriqi.setText(DateFormatUtil.subDate1(list.get(position).getSHOUWENRIQI()));
                        tvBiaoti.setText(list.get(position).getWENJIANMINGCHENG());
                        tvPerson.setText(list.get(position).getDQNAME());
//                        tvFw.setText(list.get(position).getZHUBANCHUSHI());
                        if (position % 2 == 0) {
                            root.setBackgroundColor(0xffffffff);
                        } else {
                            root.setBackgroundColor(0xeeeeeeee);
                        }
                    }
                });
                break;
            case "市转文":
                shizhuanwenBar.setVisibility(View.VISIBLE);
                adapter = new RecyclerViewAdapter(list, R.layout.shizhuanwen_query_item, new OnBindView() {
                    @Override
                    public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                        TextView tvNum = itemView.findViewById(R.id.tv_szw_num);
                        TextView tvHao = itemView.findViewById(R.id.tv_szw_hao);
                        TextView tvLaiwenriqi = itemView.findViewById(R.id.tv_szw_laiwenriqi);
                        TextView tvXianbanriqi = itemView.findViewById(R.id.tv_szw_xianbanriqi);
                        TextView tvLaiwendanwei= itemView.findViewById(R.id.tv_szw_laiwendanwei);
                        TextView tvYuanwenzihao= itemView.findViewById(R.id.tv_szw_yuanwenzihao);
                        TextView tvBiaoti = itemView.findViewById(R.id.tv_szw_biaoti);
                        TextView tvZhongdianducha = itemView.findViewById(R.id.tv_szw_zhongdianducha);
                        TextView tvZhubanchushi = itemView.findViewById(R.id.tv_szw_zhubanchushi);
                        TextView tvUser = itemView.findViewById(R.id.tv_szw_user);
                        LinearLayout root= itemView.findViewById(R.id.shizhuanwen_bar);
                        tvYuanwenzihao.setText(list.get(position).getYUANWENZIHAO());
                        tvZhongdianducha.setText("");
                        tvNum.setText(list.get(position).getRN());
                        tvHao.setText(list.get(position).getHAO());
                        tvZhubanchushi.setText(list.get(position).getZHUBANCHUSHI());
                        tvLaiwenriqi.setText(DateFormatUtil.subDate1(list.get(position).getSHOUWENRIQI()));
                        tvXianbanriqi.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANSHIJIAN()));
                        tvLaiwendanwei.setText(list.get(position).getLAIWENDANWEI());
//                        tvYuanwenzihao.setText(list.get(position).get);
                        tvBiaoti.setText(list.get(position).getWENJIANMINGCHENG());
                        tvUser.setText(list.get(position).getDQNAME());

                        if (position % 2 == 0) {
                            root.setBackgroundColor(0xffffffff);
                        } else {
                            root.setBackgroundColor(0xeeeeeeee);
                        }
                    }
                });
                break;
            case "主办文":
                zhubanwenBar.setVisibility(View.VISIBLE);
                adapter = new RecyclerViewAdapter(list, R.layout.zhubanwen_query_item, new OnBindView() {
                    @Override
                    public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                        TextView tvNum = itemView.findViewById(R.id.tv_num);
                        TextView tvHao = itemView.findViewById(R.id.tv_hao);
                        TextView tvLaiwenriqi = itemView.findViewById(R.id.tv_laiwenriqi);
                        TextView tvXianbanriqi = itemView.findViewById(R.id.tv_xianbanriqi);
                        TextView tvLaiwendanwei= itemView.findViewById(R.id.tv_laiwendanwei);
                        TextView tvYuanwenzihao= itemView.findViewById(R.id.tv_yuanwenzihao);
                        TextView tvBiaoti = itemView.findViewById(R.id.tv_biaoti);
                        TextView tvZhubanchushi = itemView.findViewById(R.id.tv_zhubanchushi);
                        TextView tvUser = itemView.findViewById(R.id.tv_user);
                        LinearLayout root= itemView.findViewById(R.id.zhubanwen_bar);

                        tvNum.setText(list.get(position).getRN());
                        tvHao.setText(list.get(position).getHAO());
                        tvZhubanchushi.setText(list.get(position).getZHUBANCHUSHI());
                        tvLaiwenriqi.setText(DateFormatUtil.subDate1(list.get(position).getRECIEVEDATE()));
                        tvXianbanriqi.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANSHIJIAN()));
                        tvLaiwendanwei.setText(list.get(position).getLAIWENDANWEI());
                        tvYuanwenzihao.setText(list.get(position).getYUANWENZIHAO());
                        tvBiaoti.setText(list.get(position).getWENJIANMINGCHENG());
                        tvUser.setText(list.get(position).getDQNAME());

                        if (position % 2 == 0) {
                            root.setBackgroundColor(0xffffffff);
                        } else {
                            root.setBackgroundColor(0xeeeeeeee);
                        }
                    }
                });
                break;
            case "一般发文":
            case "结余资金":
                yibanfawenBar.setVisibility(View.VISIBLE);
                adapter = new RecyclerViewAdapter(list, R.layout.yibanfawen_query_item, new OnBindView() {
                    @Override
                    public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                        TextView tvNum = itemView.findViewById(R.id.tv_num);
                        TextView tvNian = itemView.findViewById(R.id.tv_nian);
                        TextView tvHao = itemView.findViewById(R.id.tv_hao);
                        TextView tvBiaoti = itemView.findViewById(R.id.tv_biaoti);
                        TextView tvChushizi= itemView.findViewById(R.id.tv_chushizi);
                        TextView tvChushihao= itemView.findViewById(R.id.tv_chushihao);
                        TextView tvNigaoren= itemView.findViewById(R.id.tv_nigaoren);
                        TextView tvUser = itemView.findViewById(R.id.tv_user);
                        TextView tvNigaoriqi = itemView.findViewById(R.id.tv_nigaoriqi);
                        TextView tvXianbanriqi = itemView.findViewById(R.id.tv_xianbanriqi);
                        LinearLayout root= itemView.findViewById(R.id.yibanfawen_bar);
                        tvNum.setText(list.get(position).getRN());
                        tvHao.setText(list.get(position).getYIBANFAWEN_HAO());
                        tvNian.setText(list.get(position).getNIAN());
                        tvChushizi.setText(list.get(position).getCHUSHI_ZI());
                        tvChushihao.setText(list.get(position).getYIBANFAWEN_CHUSHI_HAO());
                        tvNigaoren.setText(list.get(position).getNIGAO());
                        tvNigaoriqi.setText(DateFormatUtil.subDate1(list.get(position).getNIGAORIQI()));
                        tvXianbanriqi.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANSHIJIAN()));
                        tvBiaoti.setText(list.get(position).getBIAOTI());
                        tvUser.setText(list.get(position).getDQNAME());
                        if (position % 2 == 0) {
                            root.setBackgroundColor(0xffffffff);
                        } else {
                            root.setBackgroundColor(0xeeeeeeee);
                        }
                    }
                });
                break;
            case "指标文":
                zhibiaowenBar.setVisibility(View.VISIBLE);
                adapter = new RecyclerViewAdapter(list, R.layout.zhibiaowen_query_item, new OnBindView() {
                    @Override
                    public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                        TextView tvNum = itemView.findViewById(R.id.tv_num);
                        TextView tvNian = itemView.findViewById(R.id.tv_nian);
                        TextView tvHao = itemView.findViewById(R.id.tv_hao);
                        TextView tvBiaoti = itemView.findViewById(R.id.tv_biaoti);
                        TextView tvChushizi= itemView.findViewById(R.id.tv_chushizi);
                        TextView tvChushihao= itemView.findViewById(R.id.tv_chushihao);
                        TextView tvNigaoren= itemView.findViewById(R.id.tv_nigaoren);
                        TextView tvUser = itemView.findViewById(R.id.tv_user);
                        TextView tvYudeng = itemView.findViewById(R.id.tv_yudengwen_hao);
                        TextView tvNigaoriqi = itemView.findViewById(R.id.tv_nigaoriqi);
                        TextView tvXianbanriqi = itemView.findViewById(R.id.tv_xianbanriqi);
                        LinearLayout root= itemView.findViewById(R.id.zhibiaowen_bar);
                        tvNum.setText(list.get(position).getRN());
                        tvHao.setText(list.get(position).getHAO());
                        tvNian.setText(list.get(position).getNIAN());
                        tvChushizi.setText(list.get(position).getCHUSHI_ZI());
                        tvChushihao.setText(list.get(position).getHQZBW_CHUSHI_HAO());
                        tvNigaoren.setText(list.get(position).getNIGAO());
                        tvNigaoriqi.setText(DateFormatUtil.subDate1(list.get(position).getNIGAORIQI()));
                        tvXianbanriqi.setText(DateFormatUtil.subDate1(list.get(position).getXIANBANSHIJIAN()));
                        tvBiaoti.setText(list.get(position).getBIAOTI());
                        tvUser.setText(list.get(position).getDQNAME());
                        tvYudeng.setText(list.get(position).getYDWWENHAO());
                        if (position % 2 == 0) {
                            root.setBackgroundColor(0xffffffff);
                        } else {
                            root.setBackgroundColor(0xeeeeeeee);
                        }
                    }
                });
                break;
        }

    }



    private void initView() {
        list.clear();
        new Thread() {
            @Override
            public void run() {
                super.run();
                String s = WebServiceUtils.getInstance().queryForm(params);
                queryListBean = new Gson().fromJson(s, QueryListBean.class);
//                List<TodoBean> todoBeans = new Gson().fromJson(s, new TypeToken<List<TodoBean>>() {
//                }.getType());

                if (queryListBean != null && queryListBean.getList() != null) {
                    list.addAll(queryListBean.getList());
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
                    adapter.notifyDataSetChanged();
                    tvPage.setText(page);
                    tvCount.setText("共"+queryListBean.getCunt()+"条");
                    break;
                case 2:
                    tvCount.setText("共0条");
                    break;
            }
        }
    };

    private boolean viewDestroyed=false;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        viewDestroyed=true;
    }

    @OnClick({R.id.last, R.id.next})
    public void onViewClicked(View view) {
        int p=Integer.parseInt(page);
        switch (view.getId()) {
            case R.id.last:

                if (p>1) {
                    p--;
                    page=p+"";
                    params.put("stalength",page);
                    initView();
                }
                break;
            case R.id.next:
                int totals=Integer.parseInt(queryListBean.getCunt());
                if (totals-10*p>0) {
                    p++;
                    page=p+"";
                    params.put("stalength",page);
                    initView();
                }
                break;
        }
    }
}
