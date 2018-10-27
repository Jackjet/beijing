package com.ysbd.beijing.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public List list;
    public Context context;
    public OnItemClickListener mOnItemClickListener;
    public OnChildViewClickListener onChildViewClickListener;

    public BaseRecyclerViewAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public BaseRecyclerViewAdapter(Context context, List list, OnChildViewClickListener onChildViewClickListener) {
        this.context = context;
        this.list = list;
        this.onChildViewClickListener = onChildViewClickListener;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, final int position) {
        holder.itemView.setOnClickListener(new OnViewClickListener(onChildViewClickListener,position,0));
        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    /**
     * item的点击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
    /**
     * item中子view的点击事件（回调）
     */
    public interface OnChildViewClickListener {
        /**
         * @param position adapter_recycler_view_item position
         * @param id 点击的view的id，调用时根据不同的view传入不同的id加以区分
         */
        void onViewClick(int position, int id);
    }
    /**
     * view的点击事件
     */
    public class OnViewClickListener implements View.OnClickListener {
        OnChildViewClickListener onViewClickListener;
        int position;
        int id;
        public OnViewClickListener(OnChildViewClickListener onViewClickListener, int position, int id) {
            this.onViewClickListener = onViewClickListener;
            this.position = position;
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            onViewClickListener.onViewClick(position, id);
        }
    }
    /**
     * item中子view的点击事件（回调）
     * 传递当前item的View布局，在activity中可操作子View
     */
    public interface OnChildViewItemClickListener {
        /**
         * @param itemView RecyclerView的item的布局View
         * @param position adapter_recycler_view_item position
         * @param id 点击的view的id，调用时根据不同的view传入不同的id加以区分
         */
        void onViewClick(View itemView, int position, int id);
    }
    /**
     * view的点击事件
     * 传递当前item的View布局，在activity中可操作子View
     */
    public class OnViewItemClickListener implements View.OnClickListener {
        OnChildViewItemClickListener onChildViewItemClickListener;
        View itemView;
        int position;
        int id;
        public OnViewItemClickListener(OnChildViewItemClickListener onChildViewItemClickListener, View itemView, int position, int id) {
            this.onChildViewItemClickListener = onChildViewItemClickListener;
            this.itemView = itemView;
            this.position = position;
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            onChildViewItemClickListener.onViewClick(itemView, position, id);
        }
    }
    public View getView(int resource, ViewGroup parent){
        return LayoutInflater.from(context).inflate(resource,parent,false);
    }
}