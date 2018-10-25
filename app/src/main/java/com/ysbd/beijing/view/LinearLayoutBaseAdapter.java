package com.ysbd.beijing.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

public abstract class LinearLayoutBaseAdapter {
    public List<? extends Object> list;
    public Context context;
    private CommentLinearLayout.MNotifyDataSetChangedIF changedIF;
    public LinearLayoutBaseAdapter(Context context, List<? extends Object> list) {
        this.context = context;
        this.list = list;
    }

    public LayoutInflater getLayoutInflater() {
        if (context != null) {
            return LayoutInflater.from(context);
        }

        return null;
    }

    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    };

    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    };

    /**
     * 绑定adapter中的监听
     * @param changedIF
     */
    public void setNotifyDataSetChangedIF(CommentLinearLayout.MNotifyDataSetChangedIF changedIF){
        this.changedIF = changedIF;
    }
    /**
     * 数据刷新
     */
    public void notifyDataSetChanged(){
        if (changedIF != null) {
            changedIF.changed();
        }
    }
    /**
     * 供子类复写
     *
     * @param position
     * @return
     */
    public abstract View getView(int position);
}