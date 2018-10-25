package com.ysbd.beijing.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.R;
import com.ysbd.beijing.bean.AddressBean;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.utils.DBUtils;
import com.ysbd.beijing.utils.WebServiceUtils;
import com.ysbd.beijing.view.SeekEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchAddressActivity extends BaseActivity implements SeekEditText.OnSeekClickListener, TextView.OnEditorActionListener {
    @BindView(R.id.contactList_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contactList_indexBar)
    IndexBar indexBar;
    @BindView(R.id.contactList_barHint)
    TextView tvSideBarHint;
    @BindView(R.id.contactList_seekEditText)
    SeekEditText seekEditText;

    private RecyclerViewAdapter adapter;
    private List<AddressBean> mDatas = new ArrayList<>();
    private SuspensionDecoration mDecoration;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        new Thread() {
            @Override
            public void run() {
                super.run();
                WebServiceUtils.getInstance().getAddressBook();
            }
        }.start();
        mDatas.addAll(DBUtils.getAll());
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewAdapter(mDatas, R.layout.item_contact, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                TextView tvName = itemView.findViewById(R.id.tv_name);
                String name = mDatas.get(position).getNodeName();
                String depart = DBUtils.getPersonNameById(mDatas.get(position).getParentNodeGuid());
                if (depart.length() > 1) {
                    name = name + " (" + depart + ")";
                }
                tvName.setText(name);
            }
        });
        recyclerView = findViewById(R.id.contactList_recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmSourceDatas(mDatas)
                .setmLayoutManager(linearLayoutManager)
                .invalidate();//设置RecyclerView的LayoutManager
        seekEditText.setOnSeekButtonClickListener(this);
        seekEditText.setOnEditorActionListener(this);

    }

    @Override
    public void onSeekButtonClick(String data) {
        seek(data);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) seekEditText.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            String s = seekEditText.getText().toString();
            if (!TextUtils.isEmpty(s)) {
                //执行搜索逻辑
                seek(s);
            } else {
                getAll();
            }
            return true;
        }
        return false;
    }

    private void seek(String name) {
        mDatas.clear();
        mDatas.addAll(DBUtils.getPersonByName(name));
        adapter.notifyDataSetChanged();
        indexBar.setmSourceDatas(mDatas)
                .invalidate();
        mDecoration.setmDatas(mDatas);
    }

    private void getAll() {
        mDatas.clear();
        mDatas.addAll(DBUtils.getAll());
        adapter.notifyDataSetChanged();
        indexBar.setmSourceDatas(mDatas)
                .invalidate();
        mDecoration.setmDatas(mDatas);
    }

    @OnClick(R.id.fl_back)
    public void onViewClicked() {
        finish();
    }
}
