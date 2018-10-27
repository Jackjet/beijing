package com.ysbd.beijing.directorDesktop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.directorDesktop.adapter.AttachmentAdapter;
import com.ysbd.beijing.directorDesktop.bean.AttachmentBean;
import com.ysbd.beijing.directorDesktop.utils.FileQueryUtil;
import com.ysbd.beijing.fileEidter.FileReaderActivity;
import com.ysbd.beijing.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcjing on 2018/8/10.
 */

public class SearchFragment extends Fragment {


    ListView list;
    LinearLayout nullLayout;
    private String catalog = "";
    private AttachmentAdapter adapter;
    private List<AttachmentBean> datas;
    private String route;

    public static SearchFragment getInstance(String route) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("route", route);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        nullLayout = view.findViewById(R.id.nullLayout);
        list = view.findViewById(R.id.list);
        route = getArguments().getString("route");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datas = new ArrayList<>();
        adapter = new AttachmentAdapter(getContext(), datas);
        nullLayout.setVisibility(datas.size() > 0 ? View.GONE : View.VISIBLE);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (datas.get(position).isFile()) {
                    Intent intent = new Intent(getContext(), FileActivity.class);
                    intent.putExtra("id", datas.get(position).getFileName());
                    intent.putExtra("name", datas.get(position).getName());
                    intent.putExtra("rout", datas.get(position).getRout());
                    startActivity(intent);
                } else {//打开文件
                    lookFile(datas.get(position));
                }
            }
        });

//        String dir = getIntent().getStringExtra("rout");
//        new File(dir)
        getFileDir();
    }

    public void lookFile(AttachmentBean bean) {
        String filePath = bean.getRout();
        Intent intent = new Intent(getContext(), FileReaderActivity.class);
        intent.putExtra("filePath", filePath);
        intent.putExtra("fileName", bean.getFileName());
        intent.putExtra("delete", false);
        startActivity(intent);
    }

    public void getFileDir() {
//        String dirPath = Environment.getExternalStorageDirectory().toString() + File.separator + "bjczjdesk" + File.separator + "desk";
        String dirPath = route;
        File dir = new File(dirPath);
        try {
            // 列出所有文件
            File[] files = dir.listFiles();
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
            nullLayout.setVisibility(datas.size() > 0 ? View.GONE : View.VISIBLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void seek(String s) {
        datas.clear();
        if (s.trim().length() > 0) {
            if (catalog.trim().length() > 0) {
                datas.addAll(FileQueryUtil.getFiles(s, false, catalog));
            } else {
                datas.addAll(FileQueryUtil.getFiles(s, false));
            }
            adapter.setShowCatalog(true);
            adapter.notifyDataSetChanged();
            nullLayout.setVisibility(datas.size() > 0 ? View.GONE : View.VISIBLE);
        } else {
            adapter.setShowCatalog(false);
            getFileDir();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
