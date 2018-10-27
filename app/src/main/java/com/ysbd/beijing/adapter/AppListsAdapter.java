package com.ysbd.beijing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.base.BaseRecyclerViewAdapter;
import com.ysbd.beijing.bean.AppListsModel;
import com.ysbd.beijing.view.ImageSquareView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppListsAdapter extends BaseRecyclerViewAdapter<AppListsAdapter.ViewHolder> {
    public AppListsAdapter(Context context, List list, OnChildViewClickListener onChildViewClickListener) {
        super(context, list, onChildViewClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        AppListsModel.AppModel model = (AppListsModel.AppModel) list.get(position);
        holder.title.setText(model.getItemName());
        holder.image.setImageResource(model.getAppLogo());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_app_lists, parent));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemAppLists_image)
        ImageSquareView image;
        @BindView(R.id.itemAppLists_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
