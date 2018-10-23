package com.ysbd.beijing.ui.fragment.form;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ysbd.beijing.App;
import com.ysbd.beijing.R;
import com.ysbd.beijing.ui.activity.FormActivity;
import com.ysbd.beijing.ui.bean.FileIdBean;
import com.ysbd.beijing.ui.bean.form.JuNeiChuanWenBean;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.FileUtils;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.WebServiceUtils;
import com.ysbd.beijing.view.CommentLinearLayout;
import com.ysbd.beijing.view.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lcjing on 2018/8/20.
 */

public class JuneichuanwenFragment1 extends BaseFormFragment {


    @BindView(R.id.cl_julingdao)
    CommentLinearLayout clJulingdao;
    @BindView(R.id.cl_chuzhangpishi)
    CommentLinearLayout clChuzhangpishi;
    @BindView(R.id.cl_qitarenyijian)
    CommentLinearLayout clQitarenyijian;
    @BindView(R.id.chuzhangqianzi)
    CommentLinearLayout clChuzhangqianzi;
    Unbinder unbinder;
    @BindView(R.id.qicaoriqi)
    TextView qicaoriqi;
    @BindView(R.id.nian)
    TextView nian;
    @BindView(R.id.hao)
    TextView hao;
    @BindView(R.id.zhubanchushi)
    TextView zhubanchushi;
    @BindView(R.id.chengsong)
    TextView chengsong;
    @BindView(R.id.qicaochushi)
    TextView qicaochushi;
    @BindView(R.id.xianbanriqi)
    TextView xianbanriqi;
    @BindView(R.id.zhongdianducha)
    TextView zhongdianducha;
    @BindView(R.id.wenjianmingcheng)
    TextView wenjianmingcheng;
    @BindView(R.id.chengbaoneirong)
    View chengbaoneirong;
    @BindView(R.id.chengbanren)
    TextView   chengbanren;
    @BindView(R.id.lianxidianhua)
    TextView lianxidianhua;
    @BindView(R.id.cl_xiebanchushiyijian)
    CommentLinearLayout clXiebanchushiyijian;
    @BindView(R.id.baoguanqixian)
    TextView baoguanqixian;
    @BindView(R.id.mijiwen)
    TextView mijiwen;
    @BindView(R.id.guidangren)
    TextView guidangren;
    @BindView(R.id.guidangqiri)
    TextView guidangqiri;
    @BindView(R.id.jianyaoqingkuang)
    TextView jianyaoqingkuang;

    @BindView(R.id.cl_attachment)
    CommentLinearLayout clAttachment;//附件列表

    private LoadingDialog loadingDialog;


    public static JuneichuanwenFragment1 getInstance(String guid,String actor) {
        JuneichuanwenFragment1 fragment = new JuneichuanwenFragment1();
        Bundle args = new Bundle();
        args.putString("guid", guid);
        args.putString("actor", actor);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_juneichuanwen, null);
        unbinder = ButterKnife.bind(this, view);
        guid = getArguments().getString("guid");
        actor= getArguments().getString("actor");
        Map<String, CommentLinearLayout> layoutMap = new HashMap<>();
        layoutMap.put("处长批示", clChuzhangpishi);
        layoutMap.put("局领导批示", clJulingdao);
        layoutMap.put("处长签字", clChuzhangqianzi);
        layoutMap.put("其他人意见", clQitarenyijian);
        List<String> frames = new ArrayList<>();
        frames.add("处长批示");
        frames.add("局领导批示");
        frames.add("处长签字");
        frames.add("其他人意见");
        initData(layoutMap, frames, guid, formName);
        getData();
        return view;
    }

    String guid;
    String formName = "局内传文";

    private void getData() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getChildFragmentManager());
        FileIdBean fileIdBean = new FileIdBean();
        fileIdBean.setInstanceguid(guid);
        fileIdBean.setUserid(getContext().getSharedPreferences(Constants.SP, Context.MODE_PRIVATE).getString(Constants.USER_ID, ""));
        final String jsonData = new Gson().toJson(fileIdBean);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = WebServiceUtils.getInstance().findToDoFileInfo(jsonData);
//                mHandler.obtainMessage(2, data).sendToTarget();
                if (data.length() < 3) {
                    mHandler.sendEmptyMessage(3);
                    return;
                }
                JuNeiChuanWenBean banwenBean = null;
                try {
                    data = data.replace("<![CDATA[", "");
                    data = data.replace("]]>", "");
                    banwenBean = new Gson().fromJson(data, JuNeiChuanWenBean.class);
                    mHandler.obtainMessage(1, banwenBean).sendToTarget();
                    if (banwenBean.getMenus()!=null&&getActivity()!=null) {
                        ((FormActivity) getActivity()).addMenus(banwenBean.getMenus());
                    }
                    if (banwenBean.getActors()!=null&&getActivity()!=null) {
                        ((FormActivity) getActivity()).setActors(banwenBean.getActors());
                    }
                    initCommentDate(banwenBean.getCurrentComment(), banwenBean.getComment());
                } catch (Exception e) {
                    App.catchE(e);
                    mHandler.sendEmptyMessage(3);
                    return;
                }
                if (banwenBean == null) {
                    mHandler.sendEmptyMessage(3);
                    return;
                }
                if (getActivity() == null) {
                    return;
                }

            }
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (viewDestroyed) {
                return;
            }
            switch (msg.what) {
                case 1:
                    JuNeiChuanWenBean bean = (JuNeiChuanWenBean) msg.obj;
                    setFormData(bean);
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 2:
                    ToastUtil.show(msg.obj.toString(),getContext());
                    break;
                case 3:
                    ToastUtil.show("未获取到该公文的详细信息！", getContext());
//                    getActivity().finish();
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
            }
        }
    };


    private void setFormData(final JuNeiChuanWenBean bean) {
        if (bean.getWenjianmingcheng()!=null) {
            wenjianmingcheng.setText(Html.fromHtml(bean.getWenjianmingcheng()));
        }
        baoguanqixian.setText(bean.getBaoguanqixian());
//        private String beizhu;
        chengbanren.setText(bean.getChengbanren());
//        chengbaoneirong.setText(bean.getChengbaoneirong());
        chengsong.setText(bean.getChengsong());
        qicaochushi.setText(bean.getChushimingcheng());
        qicaoriqi.setText(bean.getShouwenriqi());
//        private String chuanyueguocheng;
//        private String chushimingcheng;
//        private String chushi_zi;
//        private String chuzhangpishi;
//        private String chuzhangqianzi;
        lianxidianhua.setText(bean.getDianhua());
//        private String formguid;
        guidangren.setText(bean.getGuidangren());
        guidangqiri.setText(bean.getGuidangshijian());
        hao.setText(bean.getChushi_zi() + "   " + bean.getHao());
        if (bean.getJianyaoqingkuang()!=null) {
            jianyaoqingkuang.setText(Html.fromHtml(bean.getJianyaoqingkuang()));
        }

//        private String julingdaopishi;
//        private String juneitojieyu;
//        private String juneitoyiban;
//        private String jutobian;
//        private String jutozhi;
        mijiwen.setText(bean.getMiji());
        nian.setText(bean.getNian());
//        qicaochushi.setText(bean.getQicaochushi());
//        private String qitarenyijian;
//        private String riseword;
//        private String shifoufawen;
//        private String shouwenriqi;\
//        private String workflowinstance_guid;
//        private String workflow_sub;
        xianbanriqi.setText(DateFormatUtil.subDate(bean.getXianbanshijian()));
//        private String xianbanshijian;

//        private String xiebanyijian;
        zhongdianducha.setText(bean.getZhongdianducha());
        zhubanchushi.setText(bean.getZhubanchushi());
        initAttachment(bean.getAttachment(), clAttachment);

        if (bean.getDocumentcb() != null) {
            chengbaoneirong.setVisibility(View.VISIBLE);
            chengbaoneirong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //                    String type=bean.getDocumentcb().getDOCUMENTFILENAME().substring(bean.getDocumentcb().getDOCUMENTFILENAME().lastIndexOf("."+1));
                    down(bean.getDocumentcb().getUrl(), bean.getDocumentcb().getName()
                            + ".doc", true);
                    setDocumentBean(bean.getDocumentcb());
                }
            });
            chengbaoneirong.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 20102);
                    } else {
                        upLoadDocument(bean.getDocumentcb(),
                                FileUtils.getInstance().makeDocumentDir().getPath() + File.separator + "cb正文.doc");

                    }
                    return true;
                }
            });
        } else {
            chengbaoneirong.setVisibility(View.INVISIBLE);
        }
    }

    private boolean viewDestroyed=false;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        viewDestroyed=true;
    }
}
