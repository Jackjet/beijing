package com.ysbd.beijing.utils.selectPhoto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.utils.Glide.GlideUtils;

import java.util.Calendar;
import java.util.List;

public class ImageViewerAdapter extends RecyclerView.Adapter<ImageViewerAdapter.ViewHolder>{
    public List<ImageViewerModel> list;
    public Context context;
    public OnItemClickListener mOnItemClickListener;
    public OnItemChildViewClickListener onItemChildViewClickListener;
    public ImageViewerAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }
    public ImageViewerAdapter(Context context, List<ImageViewerModel> list, OnItemChildViewClickListener onItemChildViewClickListener) {
        this.context = context;
        this.list = list;
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_imageviewer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
        ImageViewerModel model = list.get(position);
        GlideUtils.getInstence().load(context,model.getPath(),holder.imageView);
        if (model.isSelect()){
            holder.imageViewCover.setVisibility(View.VISIBLE);
            holder.select.setImageResource(R.drawable.imageviewer_select);
        }else {
            holder.imageViewCover.setVisibility(View.GONE);
            holder.select.setImageResource(R.drawable.imageviewer_un_select);
        }
        holder.select.setOnClickListener(new onChildViewClickListener(onItemChildViewClickListener,holder.itemView,position,0));
        holder.imageView.setOnClickListener(new onChildViewClickListener(onItemChildViewClickListener,holder.itemView,position,1));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView imageViewCover;
        private ImageView select;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_viewer);
            select = itemView.findViewById(R.id.select_viewer);
            imageViewCover = itemView.findViewById(R.id.image_cover_viewer);
        }
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }
    public interface OnItemChildViewClickListener {
        void onViewClick(View itemView, int position, int id);
    }
    /**
     * view的点击事件
     */
    public class onChildViewClickListener implements View.OnClickListener {

        OnItemChildViewClickListener onItemChildViewClickListener;
        int position;
        int viewtype;
        View view;
        public static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime = 0;

        public onChildViewClickListener(OnItemChildViewClickListener onChildViewClickListener, View view, int position, int viewtype) {
            this.onItemChildViewClickListener = onChildViewClickListener;
            this.view = view;
            this.position = position;
            this.viewtype = viewtype;
        }

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onItemChildViewClickListener.onViewClick(v, position, viewtype);
            }
        }
    }
}
