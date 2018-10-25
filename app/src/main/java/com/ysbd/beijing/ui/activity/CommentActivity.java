package com.ysbd.beijing.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.R;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.ui.bean.OpinionModel;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lcjing on 2018/8/16.
 */
//意见界面  用来添加或修改意见

public class CommentActivity extends BaseActivity {

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_content)
    ImageView ivContent;
    @BindView(R.id.ivName)
    ImageView ivName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.lv_l)
    RecyclerView recyclerView;
    @BindView(R.id.commentsLayout)
    LinearLayout commentsLayout;
    String id;//意见框id(新建)
    String rowId = "";// 修改意见时是对应的意见id
    String documentId;
    boolean isAdd;
    @BindView(R.id.bt_add_watermark)
    Button btAddWatermark;
    @BindView(R.id.bt_add)
    Button btAdd;
    String content = "";

    private String guid;

    private RecyclerViewAdapter adapter;
    private List<String> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        guid = getIntent().getStringExtra("instanceguid");
        documentId = getIntent().getStringExtra("documentId");
        tvTime.setText(DateFormatUtil.currentDate());
        isAdd = getIntent().getBooleanExtra("isAdd", false);
        if (!isAdd) {
            content = getIntent().getStringExtra("content");
            rowId = getIntent().getStringExtra("rowId");
        }
        if (content != null) {
            etContent.setText(Html.fromHtml(content));
        }
        list = new ArrayList<>();
        adapter = new RecyclerViewAdapter(list, R.layout.item_comments_list, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                TextView textView = itemView.findViewById(R.id.itemCommentsList_text);
                textView.setText(data.toString());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                //可把常用语添加到意见框
               etContent.setText(etContent.getText().toString()+list.get(position));
            }
        });
        if (!TextUtils.isEmpty(guid) && !TextUtils.isEmpty(sp.getString(Constants.USER_ID, ""))) {
            thread.start();
        }
    }

    @OnClick({R.id.rlBack, R.id.bt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.bt_add:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        content = etContent.getText().toString();
                        String res = WebServiceUtils.getInstance().writeComment(documentId, content, id, rowId);
                        handler.obtainMessage(1, res).sendToTarget();
                    }
                }.start();
                break;
        }

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String res = msg.obj.toString();
                    CommentRes commentRes = new Gson().fromJson(res, CommentRes.class);
                    ToastUtil.show(commentRes.getSucc().getInfo(), CommentActivity.this);
                    if (commentRes.getSucc().getInfo().contains("成功")) {
                        Intent intent = new Intent();
                        OpinionModel opinionModel = new OpinionModel();
                        opinionModel.setId(commentRes.getSucc().getRow_guid());
                        opinionModel.setOpinionFrameMark(id);
                        opinionModel.setUserName(sp.getString(Constants.USER_NAME, ""));
                        opinionModel.setUserId(sp.getString(Constants.USER_ID, ""));
                        opinionModel.setCreateDate(DateFormatUtil.currentDate());
                        opinionModel.setEditable(true);
                        opinionModel.setContent(content);
                        SpUtils.getInstance().setCommentEditable(commentRes.getSucc().getRow_guid(), true);
                        intent.putExtra("opinion", opinionModel);
                        setResult(1, intent);
                        finish();
                    }

                    break;
                case 2:
                    break;
                case 3:
//                    已办理完毕。,已阅。,请处长批示,已存,已阅存
                    String string = msg.obj.toString();
                    if (!TextUtils.isEmpty(string)) {
                        String[] split = string.split(",");
                        List<String> lists = Arrays.asList(split);
                        list.addAll(lists);
                        adapter.notifyDataSetChanged();
                    }

                    break;
            }
        }
    };
    Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            String res = WebServiceUtils.getInstance().getCommentList(guid, sp.getString(Constants.USER_ID, ""));
            Log.e("意见常用语", "---" + res + "+++");
            if (TextUtils.isEmpty(res)) {
                handler.obtainMessage(3, "").sendToTarget();
            } else {
                handler.obtainMessage(3, res).sendToTarget();
            }
        }
    };

    public static class CommentRes {

        /**
         * succ : {"info":"移动办公刘海涛填写意见成功","state":"false"}
         */

        private SuccBean succ;

        public SuccBean getSucc() {
            return succ;
        }

        public void setSucc(SuccBean succ) {
            this.succ = succ;
        }

        public class SuccBean {
            /**
             * info : 移动办公刘海涛填写意见成功
             * state : false
             */

            private String info;
            private String row_guid;
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

            public String getRow_guid() {
                return row_guid;
            }

            public void setRow_guid(String row_guid) {
                this.row_guid = row_guid;
            }
        }
    }


}
