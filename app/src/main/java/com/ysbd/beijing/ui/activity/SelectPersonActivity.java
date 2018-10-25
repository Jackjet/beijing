package com.ysbd.beijing.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.R;
import com.ysbd.beijing.adapter.MultiListAdapter;
import com.ysbd.beijing.bean.MultiBean;
import com.ysbd.beijing.bean.SendNodeBean;
import com.ysbd.beijing.bean.SendUserBean;
import com.ysbd.beijing.ui.bean.SelectPersonBean;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.WebServiceManager;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPersonActivity extends BaseActivity {


    @BindView(R.id.textView4)
    TextView tvOk;
    @BindView(R.id.listview)
    ListView listview;
    private List<MultiBean> multiBeanList, mu, selectMu;
    private SendUserBean.DateBean dateBean;
    private int clickPosition = 0;
    private MultiListAdapter adapter;
    private ProgressDialog p;

    private String actionId, id;
    private boolean single = true;//是否只发送给一个人

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);
        ButterKnife.bind(this);
        tvOk.setVisibility(View.INVISIBLE);
        actionId = getIntent().getStringExtra("actionId");
        if (actionId.length()<2) {
            //抄送
        }
        id = getIntent().getStringExtra("id");
        p = new ProgressDialog(this);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        multiBeanList = new ArrayList<>();//展示出来的列表
        mu = new ArrayList<>();//列表展开 合并时动态变化的部分
        selectMu = new ArrayList<>();//选中的列表
        adapter = new MultiListAdapter(this, multiBeanList, 1);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                clickPosition = position;
                if (multiBeanList.get(position).isParent()) {//点击部门
                    if (multiBeanList.get(position).isOpen()) {//如果是展开的 将其关闭
                        multiBeanList.get(position).setOpen(false);
                        for (int i = position + 1; i < multiBeanList.size(); i++) {//将列表项 后边属于他的子项清除
                            if (multiBeanList.get(i).getLevel() > multiBeanList.get(position).getLevel()) {
                                mu.add(multiBeanList.get(i));
                            } else
                                break;
                        }
                        multiBeanList.removeAll(mu);
                        mu.clear();
                        handler.sendEmptyMessage(2);
                    } else {//如果是关闭的 将其展开
                        multiBeanList.get(position).setOpen(true);

                        for (int i = 0; i < dateBean.getUser().size(); i++) {
                            if (dateBean.getUser().get(i).getParentNodeGuid().equals(multiBeanList.get(position).getId())) {
                                MultiBean multiBean = new MultiBean();
                                multiBean.setOpen(false);
                                multiBean.setLevel(multiBeanList.get(position).getLevel() + 1);
                                multiBean.setName(dateBean.getUser().get(i).getNodeName());
                                multiBean.setParent(false);
                                multiBean.setId(dateBean.getUser().get(i).getNodeGuid());
                                multiBean.setIndex(dateBean.getUser().get(i).getIndex());
                                multiBean.setDuty(dateBean.getUser().get(i).getJobtitles());
                                multiBean.setpName("");
                                multiBean.setOrgId(dateBean.getUser().get(i).getParentNodeGuid());
                                mu.add(multiBean);
                            }
                        }
                        Collections.sort(mu, new Comparator<MultiBean>() {
                            @Override
                            public int compare(MultiBean o1, MultiBean o2) {
                                return o1.getIndex()-o2.getIndex();
                            }
                        });
                        multiBeanList.addAll(clickPosition + 1, mu);
                        handler.sendEmptyMessage(2);

                    }
                } else {//点击联系人
                    if (single) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(SelectPersonActivity.this)
                                .setTitle("发送给")
                                .setMessage(multiBeanList.get(position).getName())
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        send(multiBeanList.get(position).getId());
                                    }
                                });
                        dialog.show();
                    } else {
                        if (multiBeanList.get(position).isSelect()) {
                            multiBeanList.get(position).setSelect(false);
                            selectMu.remove(multiBeanList.get(position));
                        } else {
                            multiBeanList.get(position).setSelect(true);
                            selectMu.add(multiBeanList.get(position));
                        }
                        handler.sendEmptyMessage(2);
                    }


                }
            }
        });
        initData1();
    }

    private void initData1() {
        p.setMessage("正在获取发送信息...");
        p.show();

        new Thread() {
            @Override
            public void run() {
                super.run();
                String data = WebServiceUtils.getInstance().sendInstanceUser(id, actionId);
//                String data= DBUtils.getSender();
                SendUserBean bean = null;
                if (data.length() > 2) {
                    try {
                        bean = new Gson().fromJson(data, SendUserBean.class);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    if (bean == null) {
                        return;
                    }
                    dateBean = bean.getDate();
                    if (dateBean.getUser().size() > 1) {// || dateBean.getDirectSend() == null
                        for (int i = 0; i < dateBean.getUser().size(); i++) {
                            if (dateBean.getDept()!=null) {
                                SendNodeBean departBean = dateBean.getDept().get(dateBean.getUser().get(i).getParentNodeGuid());
                                if (departBean != null) {
                                    MultiBean multiBean = new MultiBean();
                                    multiBean.setOpen(false);
                                    multiBean.setLevel(1);
                                    multiBean.setName(departBean.getNodeName());
                                    multiBean.setParent(true);
                                    multiBean.setId(departBean.getNodeGuid());
                                    multiBean.setpName("");
                                    multiBean.setOrgId(departBean.getParentNodeGuid());
                                    multiBean.setIndex(departBean.getIndex());
                                    mu.add(multiBean);
                                }
                            }else {
                                SendNodeBean userBean = dateBean.getUser().get(i);
                                MultiBean multiBean = new MultiBean();
                                multiBean.setOpen(false);
                                multiBean.setLevel(1);
                                multiBean.setName(userBean.getNodeName());
                                multiBean.setParent(false);
                                multiBean.setId(userBean.getNodeGuid());
                                multiBean.setpName("");
                                multiBean.setIndex(userBean.getIndex());
                                multiBean.setDuty(userBean.getJobtitles());
                                multiBean.setOrgId(userBean.getParentNodeGuid());
                                mu.add(multiBean);
                            }

                        }
                        Collections.sort(mu, new Comparator<MultiBean>() {
                            @Override
                            public int compare(MultiBean o1, MultiBean o2) {
                                return o1.getIndex()-o2.getIndex();
                            }
                        });
                        multiBeanList.addAll(mu);
                        handler.sendEmptyMessage(2);
                    } else {
                        if (dateBean.getUser().size() > 0) {
                            //直接发送
                            send(dateBean.getUser().get(0).getNodeGuid());
                        }
                    }
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    private void send(final String ids) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String data = WebServiceUtils.getInstance().sendInstance(id, actionId, ids);
                //{"succ":{"info":"王栋发送GUID为{BFA7DE01-FFFF-FFFF-83DF-EC5F0000001F}的工作流实例。","state":"true"}}
                SendRes res = new Gson().fromJson(data, SendRes.class);
                String state=res.getSucc().getState();
                Log.d(WebServiceManager.TAG,state);
                if (state.equals("true")) {
                    handler.sendEmptyMessage(3);
                } else {
                    handler.sendEmptyMessage(4);
                }
            }
        }.start();
    }

    private void initData() {
        p.setMessage("正在获取发送信息...");
        p.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                String data = WebServiceUtils.getInstance().getactionguid(actionId, id);

                if (data.length() > 2) {
                    SelectPersonBean selectPersonBean = new Gson().fromJson(data, SelectPersonBean.class);
                    if (selectPersonBean.getRouteType().contains("0") || selectPersonBean.getRouteType().contains("1")
                            || selectPersonBean.getRouteType().contains("2") || selectPersonBean.getRouteType().contains("5")) {
                        //单人
                        adapter.setType(1);
                    } else {//1、4、6、13
                        //多人
                        adapter.setType(2);
                    }
                    for (int i = 0; i < selectPersonBean.getNode().size(); i++) {
                        MultiBean multiBean = new MultiBean();
                        multiBean.setOpen(false);
                        multiBean.setLevel(1);
                        multiBean.setName(selectPersonBean.getNode().get(i).getName());
                        multiBean.setParent(selectPersonBean.getNode().get(i).getIconSkin().contains("p"));
                        multiBean.setId(selectPersonBean.getNode().get(i).getId());
                        multiBean.setpName("");
                        multiBean.setIndex(selectPersonBean.getNode().get(i).getIndex());
                        multiBean.setDuty(selectPersonBean.getNode().get(i).getJobtitles());
//                if(sp.getString(MyApplication.DEPARTMENT_NAME,"0").equals("0")&&!perms.get(i).isParent()&&perms.get(i).getId().equals(sp.getString(MyApplication.PERSON_ID,"")))
//                {
//                    sp.edit().putString(MyApplication.DEPARTMENT_NAME,department.getName()).apply();
//                }
                        multiBean.setOrgId("");

                        mu.add(multiBean);
                    }
                    Collections.sort(mu, new Comparator<MultiBean>() {
                        @Override
                        public int compare(MultiBean o1, MultiBean o2) {
                            return o1.getIndex()-o2.getIndex();
                        }
                    });
                    multiBeanList.addAll(mu);
                    handler.sendEmptyMessage(2);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtil.show("未获取到发送人员", SelectPersonActivity.this);
                    p.dismiss();
//                    finish();
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    p.dismiss();
                    mu.clear();
                    break;
                case 3://发送成功
                    setResult(1);
                    finish();
                    break;
                case 4://发送失败
                    setResult(2);
                    finish();
                    break;
            }
        }
    };


    @OnClick({R.id.fl_back, R.id.textView4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_back:
                finish();
//                setResult(1);
                break;
            case R.id.textView4:
                //发送
                break;
        }
    }

    public static class SendRes {

        /**
         * succ : {"info":"王栋发送GUID为{BFA7DE01-FFFF-FFFF-83DF-EC5F0000001F}的工作流实例。","state":"true"}
         */

        private SuccBean succ;

        public SuccBean getSucc() {
            return succ;
        }

        public void setSucc(SuccBean succ) {
            this.succ = succ;
        }

        public static class SuccBean {
            /**
             * info : 王栋发送GUID为{BFA7DE01-FFFF-FFFF-83DF-EC5F0000001F}的工作流实例。
             * state : true
             */

            private String info;
            private String state;

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
