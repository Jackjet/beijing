package com.ysbd.beijing.ui.fragment.list;

import com.ysbd.beijing.BaseFragment;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;

/**
 * Created by lcjing on 2018/9/5.
 */

public class BaseListFragment extends BaseFragment {

    private RecyclerViewAdapter adapter;
    public String type,actor;

    public void getData(){

    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }


}
