package com.ysbd.beijing.ui.fragment.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysbd.beijing.BaseFragment;
import com.ysbd.beijing.R;
import com.ysbd.beijing.recyclerView.OnBindView;
import com.ysbd.beijing.recyclerView.OnViewClickListener;
import com.ysbd.beijing.recyclerView.RecyclerViewAdapter;
import com.ysbd.beijing.ui.activity.FormActivity;
import com.ysbd.beijing.ui.bean.TodoBean;
import com.ysbd.beijing.utils.CommentFormUtils;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.WebServiceUtils;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private List<TodoBean> list;
    private RecyclerViewAdapter adapter;
    private String type,id;
    private String actor;

    public static ToDoFragment getInstance(String type,String actor){
        ToDoFragment fragment=new ToDoFragment();
        Bundle args=new Bundle();
        args.putSerializable("type",type);
        args.putSerializable("actor",actor);
        fragment.setArguments(args);
        return fragment;
    }

    public void setActor(String actor){
        list.clear();
        this.actor=actor;
        initView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getArguments().getString("type");
        actor=getArguments().getString("actor");
        id= CommentFormUtils.getFormId(type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_todo, container, false);
        recyclerView = inflate.findViewById(R.id.fragmentToDoRecyclerView);
        initToDo();
        initView();
        return inflate;
    }

    private void initToDo() {
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(list, R.layout.item_todo_list, new OnBindView() {
            @Override
            public void bindView(int position, Object data, View itemView, OnViewClickListener.OnChildViewClickListener viewClickListener) {
                TextView status = itemView.findViewById(R.id.item_todo_status);
                TextView code = itemView.findViewById(R.id.item_todo_code);
                TextView number = itemView.findViewById(R.id.item_todo_number);
                TextView time = itemView.findViewById(R.id.item_todo_time);
                TextView maxTime = itemView.findViewById(R.id.item_todo_maxTime);
                TextView unit = itemView.findViewById(R.id.item_todo_unit);
                TextView textNumber = itemView.findViewById(R.id.item_todo_textNumber);
                TextView title = itemView.findViewById(R.id.item_todo_title);
                TextView host = itemView.findViewById(R.id.item_todo_host);
                TextView user = itemView.findViewById(R.id.item_todo_user);
                TextView ducha = itemView.findViewById(R.id.item_todo_ducha);
                TextView isSend = itemView.findViewById(R.id.item_todo_isSend);

                status.setText("NEW");
                code.setText("" + (position + 1));
                number.setText("");
                if (list.get(position).getTODO_SUBMITDATE()!=null) {
                    time.setText(list.get(position).getTODO_SUBMITDATE().substring(0,10));
                    maxTime.setText(list.get(position).getTODO_SUBMITDATE().substring(0,10));
                }

                if (list.get(position).getTODO_SENDPERSONDEPT()!=null) {
                    unit.setText(list.get(position).getTODO_SENDPERSONDEPT());
                }

                textNumber.setText("");
                if(type.equals("主办文")){
                    title.setText(list.get(position).getWENJIANMINGCHENG());
                    host.setText(list.get(position).getZHUBANCHUSHI());
                    unit.setText(list.get(position).getLAIWENDANWEI());
                    number.setText(list.get(position).getHAO());
                    time.setText(DateFormatUtil.subDate(list.get(position).getRECIEVEDATE()));
                    maxTime.setText(DateFormatUtil.subDate(list.get(position).getXIANBANDATE()));
                    user.setText(list.get(position).getDQNAME());
                }else if(type.equals("局内传文")){
                    title.setText(list.get(position).getWENJIANMINGCHENG());
                    host.setText(list.get(position).getZHUBANCHUSHI());
                    unit.setText(list.get(position).getLAIWENDANWEI());
                    number.setText(list.get(position).getHAO());
                    time.setText(DateFormatUtil.subDate(list.get(position).getSHOUWENRIQI() ));
                    maxTime.setText(DateFormatUtil.subDate(list.get(position).getXIANBANSHIJIAN()));
                    user.setText(list.get(position).getDQNAME());
                }else if(type.equals("一般发文")||type.equals("指标文")){
                    title.setText(list.get(position).getBIAOTI());
                    host.setText(list.get(position).getZHUBANCHUSHI());
                    unit.setText(list.get(position).getLAIWENDANWEI());
                    number.setText(list.get(position).getHAO());
                    time.setText(DateFormatUtil.subDate(list.get(position).getSHOUWENRIQI() ));
                    maxTime.setText(DateFormatUtil.subDate(list.get(position).getXIANBANSHIJIAN()));
                    user.setText(list.get(position).getDQNAME());
                }else//市转文、指标文、结余资金
                {
                    title.setText(list.get(position).getTODO_TITLE());
                    host.setText(list.get(position).getTODO_SENDPERSONNAME());
                    user.setText(list.get(position).getTODO_RECIEVEPERSONNAME());
                }


                ducha.setText(null);
                isSend.setText(null);

            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                setLiveTime();
//                Intent intent=new Intent(getContext(),FormActivity.class);
//                String tar=list.get(position).getTODO_TARGETURL();
//                int startTag = tar.indexOf("instanceGUID=") + 13;
//                String guid="";
//                if (startTag>13) {
//                    guid=tar.substring(startTag);
//                }
//                intent.putExtra("instanceguid",guid);
//                startActivity(intent);
                Intent intent=new Intent(getContext(),FormActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("actor",actor);
                intent.putExtra("instanceguid",list.get(position).getWORKFLOWINSTANCE_GUID());
                startActivity(intent);
            }
        });
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.head_todo_list, null);
        adapter.addHeaderView(inflate);

    }

    private void initView(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String s=WebServiceUtils.getInstance().findTodoFiles(id,type,1+"",actor);
                List<TodoBean> todoBeans = new Gson().fromJson(s, new TypeToken<List<TodoBean>>() {
                }.getType());
                if (todoBeans!=null) {
                    list.addAll(todoBeans);
                    handler.sendEmptyMessage(1);
                }

            }
        }.start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
