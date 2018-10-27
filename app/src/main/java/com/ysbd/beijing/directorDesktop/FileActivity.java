package com.ysbd.beijing.directorDesktop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.directorDesktop.adapter.AttachmentAdapter;
import com.ysbd.beijing.directorDesktop.bean.AttachmentBean;
import com.ysbd.beijing.directorDesktop.utils.FileQueryUtil;
import com.ysbd.beijing.fileEidter.FileReaderActivity;
import com.ysbd.beijing.utils.FileUtils;
import com.ysbd.beijing.view.SeekEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//这个页面用来显示文件（夹）列表
public class FileActivity extends AppCompatActivity implements SeekEditText.OnSeekClickListener, TextView.OnEditorActionListener {

    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.seekEditText)
    SeekEditText seekEditText;
    @BindView(R.id.flClose)
    FrameLayout flClose;
    @BindView(R.id.nullLayout)
    LinearLayout nullLayout;


    private String catalog="";
    private AttachmentAdapter adapter;
    private List<AttachmentBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        ButterKnife.bind(this);
        datas = new ArrayList<>();
        String title = getIntent().getStringExtra("name");
        if (title.equals("收入")|| title.equals("支出")) {
            catalog=title;
        }
        tvTitle.setText(title);
//        adapter = new AppListsAdapter(this, list, this);
        //页面要区分pad的手机
        adapter = new AttachmentAdapter(this, datas);
        nullLayout.setVisibility(datas.size()>0?View.GONE:View.VISIBLE);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (datas.get(position).isFile()) {
                    Intent intent = new Intent(FileActivity.this, FileActivity.class);
                    intent.putExtra("id", datas.get(position).getFileName());
                    intent.putExtra("name", datas.get(position).getName());
                    intent.putExtra("rout", datas.get(position).getRout());
                    startActivity(intent);
                } else {//打开文件
                    lookFile(datas.get(position));
                }
            }
        });
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
                    seek(s.toString());
                } else {
                    flClose.setVisibility(View.VISIBLE);
                    seek("");
                }
            }
        });
        flClose.setVisibility(View.GONE);
        flClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekEditText.setText("");
                seek("");
                flClose.setVisibility(View.GONE);
            }
        });
//        String dir = getIntent().getStringExtra("rout");
//        new File(dir)
        getFileDir();
    }


    public void lookFile(AttachmentBean bean) {
        String filePath = bean.getRout();
        Intent intent = new Intent(this, FileReaderActivity.class);
        intent.putExtra("filePath", filePath);
        intent.putExtra("fileName", bean.getFileName());
        intent.putExtra("delete", false);
        startActivity(intent);
    }

    public void getFileDir() {
        String dirPath = getIntent().getStringExtra("rout");
        File dir = new File(dirPath);
        try {
            // 列出所有文件
            File[] files = dir.listFiles();
            if (files!=null) {
                files=FileQueryUtil.order(files);
            }
            // 将所有文件存入list中
            if (files != null) {
                int count = files.length;// 文件个数
                AttachmentBean attachmentBean;
                for (int i = 0; i < count; i++) {
                    File file = files[i];
                    attachmentBean = new AttachmentBean();
                    attachmentBean.setFile(file.isDirectory());
                    attachmentBean.setName(file.getName());
                    attachmentBean.setRout(file.getAbsolutePath());
                    if (!attachmentBean.isFile()) {
                        String type = FileUtils.getInstance().getExtension(file.getName());
                        attachmentBean.setExtension(type);
                    } else {
                        attachmentBean.setExtension("dir");
                    }
                    datas.add(attachmentBean);
                }
            }
            adapter.notifyDataSetChanged();
            nullLayout.setVisibility(datas.size()>0?View.GONE:View.VISIBLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        finish();
    }



    private void seek(String s) {
        datas.clear();
        if (s.trim().length() > 0) {
            if (catalog.trim().length()>0) {
                datas.addAll(FileQueryUtil.getFiles(s, false,catalog));
            }else {
                datas.addAll(FileQueryUtil.getFiles(s, false));
            }
            adapter.setShowCatalog(true);
            adapter.notifyDataSetChanged();
            nullLayout.setVisibility(datas.size()>0?View.GONE:View.VISIBLE);
        } else {
            adapter.setShowCatalog(false);
            getFileDir();
        }
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
                flClose.setVisibility(View.VISIBLE);
                seek(s);
            } else {
                flClose.setVisibility(View.VISIBLE);
                seek("");
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
            seek(data);
        } else {
            flClose.setVisibility(View.VISIBLE);
            seek("");
        }
    }
}
