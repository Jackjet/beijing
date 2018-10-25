package com.ysbd.beijing.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.ui.bean.MenuBean;

import java.io.Serializable;
import java.util.List;


public class MenuDialog extends DialogFragment {
    private RecyclerView list;
    List<MenuBean> menuBeans;

    private RecyclerViewAdapter menuAdapter;

    public MenuDialog() {
    }

    public static MenuDialog getInstance(List<MenuBean> menuBeans){
        MenuDialog menuDialog=new MenuDialog();
        Bundle args=new Bundle();
        args.putSerializable("menuBeans", (Serializable) menuBeans);
        menuDialog.setArguments(args);
        return menuDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = inflater.inflate(R.layout.list_layout, container, false);
        list = inflate.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        menuBeans= (List<MenuBean>)getArguments().get("menuBeans");
        menuAdapter = new RecyclerViewAdapter(menuBeans, R.layout.item_todo, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                TextView textView = itemView.findViewById(R.id.item_todo_text);
                textView.setText(menuBeans.get(position).getName());
            }
        });
        menuAdapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
//                Intent intent=new Intent(MainActivity.this,FormActivity.class);
//                String tar=showList.get(position).getTODO_TARGETURL();
//                int startTag = tar.indexOf("instanceGUID=") + 13;
//                String guid="";
//                if (startTag>13) {
//                    guid=tar.substring(startTag);
//                }
//
//                intent.putExtra("instanceguid",guid);
//                startActivity(intent);
                click.click(menuBeans.get(position));
            }
        });
        list.setAdapter(menuAdapter);

        return inflate;
    }


    public void show(FragmentManager manager) {
//        menuAdapter.notifyDataSetChanged();
        show(manager, "");
    }

    Click click;

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public interface Click{
        void click(MenuBean menuBean);
    }

}
