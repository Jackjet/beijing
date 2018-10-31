package com.ysbd.beijing.ui.activity;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.ysbd.beijing.App;
import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.R;
import com.ysbd.beijing.directorDesktop.Desk2Activity;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.ui.bean.TodoBean;
import com.ysbd.beijing.utils.update.CheckVersionUtil;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private List<String> titleList;
    private RecyclerView classTitleRecyclerView;
    private RecyclerViewAdapter classTitleAdapter;

    private TextView lingdaoricheng;


    private RecyclerViewAdapter todoAdapter;
    private RecyclerView todoRecyclerView;
    private MaterialRefreshLayout todoRefresh;
    private int todoPage = 1;

    private List<String> bbsList;
    private RecyclerViewAdapter bbsAdapter;
//    private RecyclerView bbsRecyclerView;


    private TextView tvAddress, tvZhuomian;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SpUtils.getInstance().setTime(System.currentTimeMillis());
        Configuration config = getResources().getConfiguration();
        int smallestScreenWidth = config.smallestScreenWidthDp;
        SpUtils.getInstance().setScreenWidth(smallestScreenWidth);
        tvAddress = findViewById(R.id.tv_address_book);
        tvZhuomian = findViewById(R.id.tv_zhuomian);
        todoRefresh = findViewById(R.id.refresh);
        lingdaoricheng = findViewById(R.id.tv_richeng);
        tvAddress.setOnClickListener(this);
        classTitleRecyclerView = findViewById(R.id.classTitleRecyclerView);
        todoRecyclerView = findViewById(R.id.todoRecyclerView);
//        bbsRecyclerView = findViewById(R.id.bbsRecyclerView);
        webView = findViewById(R.id.webView);
        todoRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                todoPage = 1;
                getTodoData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                todoPage++;
                getTodoData();
            }
        });
        String userName = SpUtils.getInstance().getUserName();
        if (!TextUtils.isEmpty(userName)) {
            if (userName.equals("马祥伟") || userName.equals("胡志华") || userName.equals("段超") ||
                    userName.equals("吴素芳") || userName.equals("徐蘅") || userName.equals("王婴") ||
                    userName.equals("于学强") || userName.equals("赵彦明") || userName.equals("韩杰") ||
                    userName.equals("师淑英") || userName.equals("汪刚") || userName.equals("张宏宇")) {
                lingdaoricheng.setVisibility(View.VISIBLE);
            }
            if (userName.equals("吴素芳") || userName.equals("徐蘅") || userName.equals("王婴") ||
                    userName.equals("于学强") || userName.equals("赵彦明") || userName.equals("韩杰") ||
                    userName.equals("师淑英") || userName.equals("汪刚") || userName.equals("张宏宇")) {
                tvZhuomian.setVisibility(View.VISIBLE);
            }
        }


        initClassTitle();
        initTodo();
        initBbs();
//        checkVersion(true);
        CheckVersionUtil.getInstance().check(new CheckVersionUtil.VersionCall() {

            @Override
            public void update(final CheckVersionUtil checkVersionUtil, final String url) {
                Log.e("版本更新", url);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("新版本提示")
                        .setMessage("是否下载更新？")
                        .setPositiveButton(
                                "确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog, int which) {
                                        checkVersionUtil.downApk(url, "北京财政.apk", MainActivity.this);
                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                handler.sendEmptyMessage(0);
                            }
                        }).show();

            }

            @Override
            public void notUpdate() {
                Log.e("版本更新", "无需更新");
            }
        });
    }


    public void checkVersion(final boolean auto) {
//        showProgress("正在获取版本信息...");
        PgyUpdateManager.register(this,
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {
                        handler.sendEmptyMessageDelayed(0, 100);
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        if (!auto || !appBean.getVersionName().equals(sp.getString(Constants.IGNORE_VERSION, ""))) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("新版本提示")
                                    .setMessage("版本号：" + appBean.getVersionName() +
                                            "\n新版本特性：\n" + appBean.getReleaseNote()
                                            + "\n是否下载更新？")
                                    .setPositiveButton(
                                            "确定",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    startDownloadTask(
                                                            MainActivity.this,
                                                            appBean.getDownloadURL());
                                                }
                                            })
//                                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            dialogInterface.dismiss();
//                                            handler.sendEmptyMessage(0);
//                                        }
//                                    })
                                    .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            sp.edit().putString(Constants.IGNORE_VERSION, appBean.getVersionName()).apply();
                                            dialogInterface.dismiss();
                                            handler.sendEmptyMessage(0);
                                        }
                                    }).show();
                        }

                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        handler.sendEmptyMessage(0);
                        if (!auto) {
                            handler.sendEmptyMessageDelayed(1, 200);
                        }
                    }
                });
    }

    private void initClassTitle() {
        titleList = new ArrayList<>();
        titleList.add("一般发文");
        titleList.add("市转文");
        titleList.add("主办文");
        titleList.add("局内传文");
        titleList.add("指标文");
        titleList.add("结余资金");//发文
//        titleList.add("网上审批(待上线)");
//        titleList.add("政府采购投诉处理(待上线)");
//        titleList.add("信访事项(待上线)");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        classTitleRecyclerView.setLayoutManager(linearLayoutManager);
        classTitleAdapter = new RecyclerViewAdapter(titleList, R.layout.item_class_title, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                TextView textView = itemView.findViewById(R.id.item_class_title_text);
                textView.setText(String.valueOf(data));
            }
        });
        classTitleAdapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                if (position > 5) {
                    return;
                }
//                Intent intent = new Intent(MainActivity.this, FormActivity.class);
//                String tar = showList.get(position).getTODO_TARGETURL();
//                int startTag = tar.indexOf("instanceGUID=") + 13;
//                String guid = "";
//                if (startTag > 13) {
//                    guid = tar.substring(startTag);
//                }
//
//                intent.putExtra("type", titleList.get(position));
//                intent.putExtra("instanceguid", guid);
//                startActivityForResult(intent,START_TODO_INFO);
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("type", titleList.get(position));
                startActivity(intent);
            }
        });
        classTitleRecyclerView.setAdapter(classTitleAdapter);
    }

    List<TodoBean> showList;

    public static final int START_TODO_INFO = 1;

    private void initTodo() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        showList = new ArrayList<>();
        todoRecyclerView.setLayoutManager(linearLayoutManager);
        todoAdapter = new RecyclerViewAdapter(showList, R.layout.item_todo, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                TextView textView = itemView.findViewById(R.id.item_todo_text);
                TextView tvTime = itemView.findViewById(R.id.item_time);
                ImageView image = itemView.findViewById(R.id.item_todo_img);

                //显示红绿灯
//                GlideUtils.getInstence().load(MainActivity.this, R.drawable.hongdeng, image);


                String title = showList.get(position).getTODO_TITLE();
                int l = showList.get(position).getTODO_MODULENAME().length();
                if (title != null && (title.length() + l) > 22) {
                    title = showList.get(position).getTODO_TITLE().substring(0, 22 - l) + "...";
                }
                String d = "[" + showList.get(position).getTODO_MODULENAME() + "]" + title + "(" +
                        showList.get(position).getTODO_SENDPERSONDEPT() + ":" + showList.get(position).getTODO_SENDPERSONNAME() + ")"
                        + DateFormatUtil.subDate(showList.get(position).getTODO_SUBMITDATE());
                d = d.replaceAll("北京市财政局,", "");
                textView.setText(d);
                tvTime.setText("");
                if (showList.get(position).getLook().equals("红灯")) {
                    image.setImageResource(R.drawable.hongdeng);
                } else if (showList.get(position).getLook().equals("黄灯")) {
                    image.setImageResource(R.drawable.huangdeng);
                } else if (showList.get(position).getLook().equals("绿灯")) {
                    image.setImageResource(R.drawable.lvdeng);
                }
            }
        });
        todoRecyclerView.setAdapter(todoAdapter);
        todoAdapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                if (position + 1 > showList.size()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                String tar = showList.get(position).getTODO_TARGETURL();
                int startTag = tar.indexOf("instanceGUID=") + 13;
                String guid = "";
                if (startTag > 13) {
                    guid = tar.substring(startTag);
                }

                intent.putExtra("type", showList.get(position).getTODO_MODULENAME());
                intent.putExtra("instanceguid", guid);
                startActivityForResult(intent, START_TODO_INFO);
            }
        });
        todoPage = 1;
        getTodoData();
    }

    private void getTodoData() {
        if (todoPage == 1) {
            showList.clear();
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                String s = WebServiceUtils.getInstance().findTodoFiles(todoPage);
                List<TodoBean> todoBeans = new Gson().fromJson(s, new TypeToken<List<TodoBean>>() {
                }.getType());
                if (todoBeans == null) {
                    handler.sendEmptyMessage(1);
                    handler.sendEmptyMessage(2);
                    return;
                }
                String instanceGuid = "";
                String userId = "";
                for (int i = 0; i < todoBeans.size(); i++) {
                    String todoTargeturl = todoBeans.get(i).getTODO_TARGETURL();
                    String substring = todoTargeturl.substring(todoTargeturl.indexOf("instanceGUID="));
                    String sub = substring.substring(substring.indexOf("{"));
                    if (TextUtils.isEmpty(instanceGuid)) {
                        instanceGuid = instanceGuid + sub;
                    } else {
                        instanceGuid = instanceGuid + "," + sub;
                    }
                    if (TextUtils.isEmpty(userId)) {
                        userId = userId + todoBeans.get(i).getTODO_SENDPERSONGUID();
                    } else {
                        userId = userId + "," + todoBeans.get(i).getTODO_SENDPERSONGUID();
                    }

                    switch (todoBeans.get(i).getTODO_MODULENAME()) {
                        case "主办文":
                        case "局内传文":
                        case "局内传文(纸质)":
                        case "一般发文":
                        case "市转文":
                        case "指标文":
                        case "结余资金发文":
                            showList.add(todoBeans.get(i));
                            break;
                    }
                }
                handler.sendEmptyMessage(1);
                if (todoBeans.size() == 0) {
                    handler.sendEmptyMessage(2);
                }
//                getTodoRedGreenData(instanceGuid,userId);
            }
        }.start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    todoRefresh.finishRefresh();
                    todoRefresh.finishRefreshLoadMore();
//                    todoRefresh.finishRefreshing();
                    todoAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    ToastUtil.show("已加载全部数据", MainActivity.this);
                    break;
            }
        }
    };

    private void initBbs() {
//        bbsList = DBUtils.getBbsList();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        bbsRecyclerView.setLayoutManager(gridLayoutManager);
//        bbsAdapter = new RecyclerViewAdapter(bbsList, R.layout.item_bbs, new OnBindView() {
//            @Override
//            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
//                TextView textView = itemView.findViewById(R.id.item_bbs_text);
//                textView.setText(String.valueOf(data));
//            }
//        });
//        bbsRecyclerView.setAdapter(bbsAdapter);
        WebSettings settings = webView.getSettings();
        //支持JavaScript
        settings.setJavaScriptEnabled(true);
//        webView.loadUrl("http://10.123.27.193:80//jntz/index_1077.htm");
        webView.loadUrl("http://10.123.27.193/jntz/index_1077.htm");
        //自己使用屏幕大小
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                return true;
            }
        });

    }


    @OnClick({R.id.logoutClick, R.id.tv_todo_more, R.id.tv_zhuomian, R.id.tv_richeng, R.id.set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logoutClick:
                finish();
                SharedPreferences sp = getSharedPreferences(Constants.SP, MODE_PRIVATE);
                sp.edit().putBoolean(Constants.IS_LOGIN, false).apply();
                break;
            case R.id.tv_todo_more:
                Intent intent = new Intent(this, DataActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);
                break;
            case R.id.tv_richeng:
                startActivity(new Intent(this, LeanderScheduleActivity.class));
                break;
            case R.id.tv_zhuomian:
                //局长桌面
                try {
                    Intent intent1 = new Intent();
                    ComponentName cmp = new ComponentName("com.ysbd.financialdesk", "com.ysbd.financialdesk.directorDesktop.Desk2Activity");
                    intent1.setAction(Intent.ACTION_MAIN);
                    intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setComponent(cmp);
                    startActivity(intent1);
                } catch (Exception e) {

                }
                break;
            case R.id.set:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address_book:
//                startActivity(new Intent(this, SearchAddressActivity.class));
                if (SpUtils.getInstance().getAddressVisiable()) {
                    startActivity(new Intent(this, AddressActivity.class));
                } else {
                    ToastUtil.show("数据加载中...", this);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case START_TODO_INFO:
                if (resultCode == 1) {
                    initTodo();
                }
                break;
        }
    }
}
