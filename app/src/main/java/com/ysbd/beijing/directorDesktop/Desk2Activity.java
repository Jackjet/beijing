package com.ysbd.beijing.directorDesktop;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.directorDesktop.adapter.Desk2Adapter;
import com.ysbd.beijing.directorDesktop.bean.AttachmentBean;
import com.ysbd.beijing.directorDesktop.bean.CatalogBean;
import com.ysbd.beijing.directorDesktop.utils.CatalogUtils;
import com.ysbd.beijing.directorDesktop.utils.Dbutil;
import com.ysbd.beijing.directorDesktop.utils.FileQueryUtil;
import com.ysbd.beijing.utils.CheckPermissionsUtil;
import com.ysbd.beijing.view.SeekEditText;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Desk2Activity extends AppCompatActivity implements SeekEditText.OnSeekClickListener, TextView.OnEditorActionListener{
    Desk2Adapter adapter;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlBack)
    View rlBack;
    @BindView(R.id.seekEditText)
    SeekEditText seekEditText;
    @BindView(R.id.flClose)
    FrameLayout flClose;
    @BindView(R.id.ll_search)
    LinearLayout search;

    @BindView(R.id.flAdd)
    View flAdd;
    private String[] permissions1 = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE
            , android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_WIFI_STATE
            , android.Manifest.permission.READ_LOGS};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !CheckPermissionsUtil.checkPermissions(this, permissions1)) {
            CheckPermissionsUtil checkPermissionsUtil = new CheckPermissionsUtil(this);
            checkPermissionsUtil.requestPermission(this, 4, permissions1);
        }
        setContentView(R.layout.activity_desk2);
        ButterKnife.bind(this);
        initDataView();
    }


    private void initDataView(){
        String path = Environment.getExternalStorageDirectory().toString()+File.separator + "bjczjdesk"+File.separator+"desk";
        String name=getIntent().getStringExtra("name");
        if (name!=null&&name.trim().length()>0) {
            path=getIntent().getStringExtra("rout");
            adapter=new Desk2Adapter(Dbutil.getDeskBeans(name),this);
            rlBack.setVisibility(View.VISIBLE);
            flAdd.setVisibility(View.INVISIBLE);
            adapter.setShowMore(false);
            if (name.length()>2) {
                try{
                    Integer.parseInt(name.substring(0,2));
                    name=name.substring(2);
                }catch (Exception ignore){

                }
            }
            tvTitle.setText(name);
        }else {
            DataSupport.deleteAll(AttachmentBean.class);
            List< CatalogBean > catalogBeans= CatalogUtils.getCatalogsFromFile();
            for (int i = 0; i < catalogBeans.size(); i++) {
                String cPath=path+File.separator+catalogBeans.get(i).getCatalog();
                FileQueryUtil.getAllFile(new File(cPath),catalogBeans.get(i).getName());
            }
            tvTitle.setText("局长桌面");
            adapter=new Desk2Adapter(Dbutil.getDeskBeans(),this);
            adapter.setShowMore(true);
        }
        list.setAdapter(adapter);
        searchFragment=SearchFragment.getInstance(path);
        seekEditText.setOnSeekButtonClickListener(this);
        seekEditText.setOnEditorActionListener(this);
        seekEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    //执行搜索逻辑
                    flClose.setVisibility(View.VISIBLE);
                    searchFragment.seek(s.toString());
                } else {
                    flClose.setVisibility(View.VISIBLE);
                    searchFragment.seek("");
                }
            }
        });
        flClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekEditText.setText("");
                searchFragment.seek("");
                flClose.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                list.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().remove(searchFragment).commit();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            initDataView();
    }

    @OnClick({R.id.rlBack, R.id.tvTitle,R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.tvTitle:
                break;
            case R.id.ll_search:
                search.setVisibility(View.GONE);
                seekEditText.requestFocus();
                getSupportFragmentManager().beginTransaction().replace(R.id.content,searchFragment).commit();
                list.setVisibility(View.GONE);
                break;
        }
    }
    SearchFragment searchFragment;
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
                flClose.setVisibility(View.VISIBLE);
                searchFragment.seek(s);
            } else {
                flClose.setVisibility(View.VISIBLE);
                searchFragment.seek("");
//                isSeek = false;
//                pullLayout.refresh(true);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onSeekButtonClick(String data) {
        if (!TextUtils.isEmpty(data)) {
            //执行搜索逻辑
            flClose.setVisibility(View.VISIBLE);
            searchFragment.seek(data);
        } else {
            flClose.setVisibility(View.VISIBLE);
            searchFragment.seek("");
        }
    }
}
