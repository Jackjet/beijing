package com.ysbd.beijing.recyclerView;

import android.view.View;

public interface OnBindView {
    void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener);
}
