package com.ysbd.beijing.ui.fragment.form;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ysbd.beijing.App;
import com.ysbd.beijing.BaseFragment;
import com.ysbd.beijing.R;
import com.ysbd.beijing.ui.activity.CommentActivity;
import com.ysbd.beijing.ui.activity.FormActivity;
import com.ysbd.beijing.ui.adapter.CommentAdapter;
import com.ysbd.beijing.ui.bean.FileIdBean;
import com.ysbd.beijing.ui.bean.OpinionModel;
import com.ysbd.beijing.ui.bean.form.JuNeiChuanWenBean;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.WebServiceUtils;
import com.ysbd.beijing.view.CommentLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ysbd.beijing.utils.CommentFormUtils.getCommentId;

/**
 * Created by lcjing on 2018/8/20.
 */

public class JuneichuanwenFragment extends BaseFragment implements CommentAdapter.CommentClick {


    @BindView(R.id.cl_julingdao)
    CommentLinearLayout clJulingdao;
    @BindView(R.id.cl_chuzhangpishi)
    CommentLinearLayout clChuzhangpishi;
    @BindView(R.id.cl_qitarenyijian)
    CommentLinearLayout clQitarenyijian;
    @BindView(R.id.cl_xiebanchushiyijian)
    CommentLinearLayout clXiebanchushiyijian;
    @BindView(R.id.chuzhangqianzi)
    CommentLinearLayout clChuzhangqianzi;
    Unbinder unbinder;
    private List<OpinionModel> julingdao;
    private List<OpinionModel> chuzhangqianzi;
    private List<OpinionModel> chuzhangpishi;
    private List<OpinionModel> qitaren;

    private CommentAdapter julingdaoAdapter;
    private CommentAdapter chuzhangqianziAdapter;
    private CommentAdapter chuzhangpishiAdapter;
    private CommentAdapter qitarenAdapter;

    public static JuneichuanwenFragment getInstance(String guid) {
        JuneichuanwenFragment fragment = new JuneichuanwenFragment();
        Bundle args = new Bundle();
        args.putString("guid", guid);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_juneichuanwen, null);
        unbinder = ButterKnife.bind(this, view);
        guId = getArguments().getString("guid");
        initComment();
        getData();
        return view;
    }

    String guId;
    String formName = "局内传文";

    private void getData() {

        FileIdBean fileIdBean = new FileIdBean();
        fileIdBean.setInstanceguid(guId);
        fileIdBean.setUserid(getContext().getSharedPreferences(Constants.SP, Context.MODE_PRIVATE).getString(Constants.USER_ID, ""));
        final String jsonData = new Gson().toJson(fileIdBean);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = WebServiceUtils.getInstance().findToDoFileInfo(jsonData);
                JuNeiChuanWenBean banwenBean = null;
                try {
                    banwenBean = new Gson().fromJson(data, JuNeiChuanWenBean.class);
                } catch (Exception e) {

                }
                if (banwenBean == null) {
                    return;
                }
                try {
                    ((FormActivity) getActivity()).addMenus(banwenBean.getMenus());
                    ((FormActivity) getActivity()).setActors(banwenBean.getActors());
                } catch (Exception e) {
                    App.catchE(e);
                }
                if (banwenBean.getComment() != null) {
                    for (int i = 0; i < banwenBean.getComment().size(); i++) {
                        OpinionModel opinionModel = new OpinionModel();
                        opinionModel.setParent(false);
                        opinionModel.setOpinionFrameMark(banwenBean.getComment().get(i).getRow_guid());
                        opinionModel.setAddable(false);
                        opinionModel.setId(banwenBean.getComment().get(i).getComment_guid());
                        opinionModel.setContent(banwenBean.getComment().get(i).getComment_content());
                        opinionModel.setUserName(banwenBean.getComment().get(i).getComment_person());
                        if (banwenBean.getComment().get(i).getComment_guid().equals(getCommentId(formName, "处长批示"))) {
                            chuzhangpishi.add(opinionModel);
                        } else if (banwenBean.getComment().get(i).getComment_guid().equals(getCommentId(formName, "局领导意见"))) {
                            julingdao.add(opinionModel);
                        } else if (banwenBean.getComment().get(i).getComment_guid().equals(getCommentId(formName, "处长签字"))) {
                            chuzhangqianzi.add(opinionModel);
                        } else if (banwenBean.getComment().get(i).getComment_guid().equals(getCommentId(formName, "其他人意见"))) {
                            qitaren.add(opinionModel);
                        }

                    }
                }
                if (banwenBean.getCurrentComment() != null) {
                    for (int i = 0; i < banwenBean.getCurrentComment().size(); i++) {
                        OpinionModel opinionModel = new OpinionModel();
                        opinionModel.setParent(true);
                        opinionModel.setOpinionFrameMark(banwenBean.getCurrentComment().get(i).getAction_guid());
                        opinionModel.setAddable(true);
                        opinionModel.setId(banwenBean.getCurrentComment().get(i).getComment_guid());
                        if (banwenBean.getCurrentComment().get(i).getComment_guid().equals(getCommentId(formName, "处长批示"))) {
                            opinionModel.setOpinionFrameName("处长批示");
                            boolean in = false;
                            for (int i1 = 0; i1 < chuzhangpishi.size(); i1++) {
                                if (banwenBean.getComment().get(i).getComment_guid().equals(chuzhangpishi.get(i1).getId())) {
                                    chuzhangpishi.get(i1).setEditable(true);
                                    in = true;
                                    break;
                                }
                            }
                            if (!in) {
                                chuzhangpishi.add(opinionModel);
                            }

                        } else if (banwenBean.getCurrentComment().get(i).getComment_guid().equals(getCommentId(formName, "局领导意见"))) {
                            opinionModel.setOpinionFrameName("局领导意见");
                            boolean in = false;
                            for (int i1 = 0; i1 < julingdao.size(); i1++) {
                                if (banwenBean.getComment().get(i).getComment_guid().equals(julingdao.get(i1).getId())) {
                                    julingdao.get(i1).setEditable(true);
                                    in = true;
                                    break;
                                }
                            }
                            if (!in) {
                                julingdao.add(opinionModel);
                            }
                        } else if (banwenBean.getCurrentComment().get(i).getComment_guid().equals(getCommentId(formName, "处长签字"))) {
                            boolean in = false;
                            for (int i1 = 0; i1 < chuzhangqianzi.size(); i1++) {
                                if (banwenBean.getComment().get(i).getComment_guid().equals(chuzhangqianzi.get(i1).getId())) {
                                    chuzhangqianzi.get(i1).setEditable(true);
                                    in = true;
                                    break;
                                }
                            }
                            if (!in) {
                                chuzhangqianzi.add(opinionModel);
                            }
                        } else if (banwenBean.getCurrentComment().get(i).getComment_guid().equals(getCommentId(formName, "其他人意见"))) {
                            opinionModel.setOpinionFrameName("其他人意见");
                            boolean in = false;
                            for (int i1 = 0; i1 < qitaren.size(); i1++) {
                                if (banwenBean.getComment().get(i).getComment_guid().equals(qitaren.get(i1).getId())) {
                                    qitaren.get(i1).setEditable(true);
                                    in = true;
                                    break;
                                }
                            }
                            if (!in) {
                                qitaren.add(opinionModel);
                            }
                        }
                    }
                }
                handler.obtainMessage(1, data).sendToTarget();
            }
        }).start();
    }

    private void initComment() {
        julingdao = new ArrayList<>();
        chuzhangqianzi = new ArrayList<>();
        chuzhangpishi = new ArrayList<>();
        qitaren = new ArrayList<>();
        julingdaoAdapter = new CommentAdapter(getContext(), julingdao, "局领导意见");
        chuzhangqianziAdapter = new CommentAdapter(getContext(), chuzhangqianzi, "拟办意见");
        chuzhangpishiAdapter = new CommentAdapter(getContext(), chuzhangpishi, "处长批示");
        qitarenAdapter = new CommentAdapter(getContext(), qitaren, "其他人意见");
        julingdaoAdapter.setCommentClick(this);
        chuzhangqianziAdapter.setCommentClick(this);
        qitarenAdapter.setCommentClick(this);
        chuzhangpishiAdapter.setCommentClick(this);
        clJulingdao.setAdapter(julingdaoAdapter);
        clChuzhangqianzi.setAdapter(chuzhangqianziAdapter);
        clChuzhangpishi.setAdapter(chuzhangpishiAdapter);
        clQitarenyijian.setAdapter(qitarenAdapter);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    julingdaoAdapter.notifyDataSetChanged();
                    chuzhangqianziAdapter.notifyDataSetChanged();
                    chuzhangpishiAdapter.notifyDataSetChanged();
                    qitarenAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    public void edit(int position, String type) {
        Intent intent = new Intent(getContext(), CommentActivity.class);
        switch (type) {
            case "处长批示":
                intent.putExtra("id", chuzhangpishi.get(position).getId());
                break;
            case "局领导批示":
                intent.putExtra("id", julingdao.get(position).getId());
                break;
            case "处长签字":
                intent.putExtra("id", chuzhangqianzi.get(position).getId());
                break;
            case "其他人意见":
                intent.putExtra("id", qitaren.get(position).getId());
                break;
        }
        intent.putExtra("documentId", guId);
        intent.putExtra("instanceguid", guId);
        intent.putExtra("isAdd", false);
        intent.putExtra("title", "修改" + type);
        startActivityForResult(intent, 1);
    }

    String commentId = "";

    @Override
    public void delete(int position, String type) {
        switch (type) {
            case "处长批示":
                commentId = chuzhangpishi.get(position).getId();
                break;
            case "局领导批示":
                commentId = julingdao.get(position).getId();
                break;
            case "处长签字":
                commentId = chuzhangqianzi.get(position).getId();
                break;
            case "其他人意见":
                commentId = qitaren.get(position).getId();
                break;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                String msg = WebServiceUtils.getInstance().deleteComment(guId, commentId);
                handler.obtainMessage(2, msg).sendToTarget();
            }
        }.start();
    }


    @Override
    public void add(int position, String type) {
        Intent intent = new Intent(getContext(), CommentActivity.class);
        switch (type) {
            case "处长批示":
                intent.putExtra("id", chuzhangpishi.get(position).getId());
                break;
            case "局领导批示":
                intent.putExtra("id", julingdao.get(position).getId());
                break;
            case "处长签字":
                intent.putExtra("id", chuzhangqianzi.get(position).getId());
                break;
            case "其他人意见":
                intent.putExtra("id", qitaren.get(position).getId());
                break;
        }
        intent.putExtra("documentId", guId);
        intent.putExtra("instanceguid", guId);
        intent.putExtra("isAdd", true);
        intent.putExtra("title", "新建" + type);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
