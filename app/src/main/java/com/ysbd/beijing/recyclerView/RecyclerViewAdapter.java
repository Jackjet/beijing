package com.ysbd.beijing.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private RecyclerView mRecyclerView;

    private List mList;
    private int mResource;
    OnBindView mBindView;



    private OnViewClickListener.OnChildViewClickListener onChildViewClickListener;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;


    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;

    public RecyclerViewAdapter(List list, int resource, OnBindView bindView) {
        this.mList = list;
        this.mResource = resource;
        this.mBindView = bindView;
    }

    public RecyclerViewAdapter(List list, int resource, OnBindView bindView, OnViewClickListener.OnChildViewClickListener childViewClickListener) {
        this.mList = list;
        this.mResource = resource;
        this.mBindView = bindView;
        this.onChildViewClickListener = childViewClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (haveHeaderView() && haveFooterView()) {
            if (position != 0 && position != getItemCount() - 1) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null)
                            onItemClickListener.onViewClick(position - 1);
                    }
                });
                mBindView.bindView(position - 1, mList.get(position - 1), holder.itemView, onChildViewClickListener);
            }
        } else if (haveHeaderView() && !haveFooterView()) {
            if (position != 0) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null)
                            onItemClickListener.onViewClick(position - 1);
                    }
                });
                mBindView.bindView(position - 1, mList.get(position - 1), holder.itemView, onChildViewClickListener);
            }
        } else if (!haveHeaderView() && haveFooterView()) {
            if (position != getItemCount() - 1) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null)
                            onItemClickListener.onViewClick(position);
                    }
                });
                mBindView.bindView(position, mList.get(position), holder.itemView, onChildViewClickListener);
            }
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null)
                        onItemClickListener.onViewClick(position);
                }
            });
            mBindView.bindView(position, mList.get(position), holder.itemView, onChildViewClickListener);
        }
    }

    public <T extends View> T getView(View itemView, int id) {
        return itemView.findViewById(id);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        int count = (mList == null ? 0 : mList.size());
        if (VIEW_FOOTER != null) {
            count++;
        }
        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new ViewHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new ViewHolder(VIEW_HEADER);
        } else {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(mResource, viewGroup, false));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClick(OnItemClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    /**
     * item中子view的点击事件（回调）
     */
    public interface OnItemClickListener {
        /**
         * @param position adapter_recycler_view_item position
         */
        void onViewClick(int position);
    }


}
