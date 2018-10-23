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
import com.ysbd.beijing.BaseFragment;
import com.ysbd.beijing.R;
import com.ysbd.beijing.ui.activity.FormActivity;
import com.ysbd.beijing.ui.adapter.CommentAdapter;
import com.ysbd.beijing.ui.bean.FileIdBean;
import com.ysbd.beijing.ui.bean.OpinionModel;
import com.ysbd.beijing.ui.bean.form.BaseFormBean;
import com.ysbd.beijing.ui.bean.form.ShizhuanwenBean;
import com.ysbd.beijing.ui.bean.form.YibanfawenBean;
import com.ysbd.beijing.ui.bean.form.ZhuBanwenBean;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.FileUtils;
import com.ysbd.beijing.utils.ToastUtil;
import com.ysbd.beijing.utils.WebServiceUtils;
import com.ysbd.beijing.view.CommentLinearLayout;

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

public class ShizhuanwenFragment extends BaseFormFragment {


    @BindView(R.id.shouwenriqi)
    TextView shouwenriqi;
    @BindView(R.id.nian)
    TextView nian;
    @BindView(R.id.hao)
    TextView hao;
    @BindView(R.id.laiwendanwei)
    TextView laiwendanwei;
    @BindView(R.id.yuanwenzihao)
    TextView yuanwenzihao;
    @BindView(R.id.bioaoti)
    TextView bioaoti;
    @BindView(R.id.zhubandanwei)
    TextView zhubandanwei;
    @BindView(R.id.xiebanchushi)
    TextView xiebanchushi;
    @BindView(R.id.xianbanriqi)
    TextView xianbanriqi;
    @BindView(R.id.zhongdianducha)
    TextView zhongdianducha;
    @BindView(R.id.shizhengfubianhao)
    TextView shizhengfubianhao;
    @BindView(R.id.miji)
    TextView miji;
    @BindView(R.id.shilingdaopishi)
    TextView shilingdaopishi;
    @BindView(R.id.cl_julingdao)
    CommentLinearLayout clJulingdao;
    @BindView(R.id.cl_nibanyijian)
    CommentLinearLayout clNibanyijian;
    @BindView(R.id.cl_chuzhangpishi)
    CommentLinearLayout clChuzhangpishi;
    @BindView(R.id.cl_qitarenyijian)
    CommentLinearLayout clQitarenyijian;
    @BindView(R.id.cl_chuzhangqianzi)
    CommentLinearLayout clChuzhangqianzi;
    @BindView(R.id.chushichengbanren)
    TextView chushichengbanren;
    @BindView(R.id.lianxidianhua)
    TextView lianxidianhua;
    @BindView(R.id.beizhu)
    TextView beizhu;
    @BindView(R.id.dubanjilu)
    TextView dubanjilu;
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
    //    @BindView(R.id.chuzhangqianzi)
//    TextView chuzhangqianzi;
    @BindView(R.id.cl_attachment)
    CommentLinearLayout clAttachment;//附件列表
    @BindView(R.id.chengbaoneirong)
    View chengbao;
    Unbinder unbinder;

    public static ShizhuanwenFragment getInstance(String guid, String actor) {
        ShizhuanwenFragment fragment = new ShizhuanwenFragment();
        Bundle args = new Bundle();
        args.putString("guid", guid);
        args.putString("actor", actor);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shizhuanwen, null);
        unbinder = ButterKnife.bind(this, view);
        guid = getArguments().getString("guid");
        actor = getArguments().getString("actor");
        initComment();
        getData();

        return view;
    }

    String guid;
    String formName = "市转文";


    private void initComment() {
        Map<String, CommentLinearLayout> layoutMap = new HashMap<>();
//        layoutMap.put("承办人意见", );
        layoutMap.put("局领导意见", clJulingdao);
        layoutMap.put("拟办意见", clNibanyijian);
        layoutMap.put("处长意见", clChuzhangpishi);
        layoutMap.put("处长签字", clChuzhangqianzi);
        layoutMap.put("其他人意见", clQitarenyijian);//????????????????????????????????????????????????????????/
        List<String> frames = new ArrayList<>();
        frames.add("局领导意见");
        frames.add("拟办意见");
        frames.add("处长意见");
        frames.add("处长签字");
        frames.add("其他人意见");
        initData(layoutMap, frames, guid, formName);
    }

    private void getData() {
        FileIdBean fileIdBean = new FileIdBean();
        fileIdBean.setInstanceguid(guid);
        fileIdBean.setUserid(getContext().getSharedPreferences(Constants.SP, Context.MODE_PRIVATE).getString(Constants.USER_ID, ""));
        final String jsonData = new Gson().toJson(fileIdBean);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = WebServiceUtils.getInstance().findToDoFileInfo(jsonData);
                try {
                    ShizhuanwenBean banwenBean = new Gson().fromJson(data, ShizhuanwenBean.class);
                    mHandler.obtainMessage(1, banwenBean).sendToTarget();
                    if (getActivity() != null) {
                        if (banwenBean.getMenus()!=null) {
                            ((FormActivity) getActivity()).addMenus(banwenBean.getMenus());
                        }
                        if (banwenBean.getActors()!=null) {
                            ((FormActivity) getActivity()).setActors(banwenBean.getActors());
                        }
                    }
                    initCommentDate(banwenBean.getCurrentComment(), banwenBean.getComment());

                }catch (Exception e)
                {
                    App.catchE(e);
                }

            }
        }).start();
    }

    private void initHuiqian2(String sub){
        StringBuffer sb=new StringBuffer();
        String[] s=sub.split(";");
        if (s.length>0) {
            for (int i = 0; i < s.length; i++) {
                String []ss=s[i].split(",");
                if (ss.length==2) {
                    sb.append(ss[1]).append("\n");
                }
            }

        }
        xiebanchushi.setText(sb.toString());
    }

    private void setFormData(final ShizhuanwenBean bean) {
        baoguanqixian.setText(bean.getBaoguanqixian());
        beizhu.setText(bean.getBeizhu());
//        chengbaoneirong.setText(bean.getChengbaoneirong());
        dubanjilu.setText(bean.getDubanjilu());
        guidangqiri.setText(bean.getGuidangriqi());
        hao.setText(bean.getHao());
        if (bean.getJianyaoqingkuang()!=null) {
            jianyaoqingkuang.setText(Html.fromHtml(bean.getJianyaoqingkuang()));
        }
        laiwendanwei.setText(bean.getLaiwendanwei());
        lianxidianhua.setText(bean.getLianxidianhua());
        miji.setText(bean.getMiji());
        mijiwen.setText(bean.getMijiwen());
        nian.setText(bean.getNian());
        shouwenriqi.setText(DateFormatUtil.subDate(bean.getShouwenriqi()));
        if (bean.getWenjianmingcheng() != null) {
            bioaoti.setText(Html.fromHtml(bean.getWenjianmingcheng()));
        }
        xianbanriqi.setText(DateFormatUtil.subDate(bean.getXianbanshijian()));
//        xiebanchushi.setText(bean.getXiebanchushi());
        initHuiqian2(bean.getWorkflow_sub());
        yuanwenzihao.setText(bean.getYuanwenzihao());
        zhongdianducha.setText(bean.getZhongdianducha());
        zhubandanwei.setText(bean.getZhubanchushi());

        shizhengfubianhao.setText(bean.getShizhengfubianhao());
        chushichengbanren.setText(bean.getChengbanren());
        guidangren.setText(bean.getGuidangren());
        if (bean.getShilingdaopishi()!=null) {
            shilingdaopishi.setText(Html.fromHtml(bean.getShilingdaopishi()));
        }


        initAttachment(bean.getAttachment(), clAttachment);

        if (bean.getDocumentcb() != null) {
            chengbao.setVisibility(View.VISIBLE);
            chengbao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //                    String type=bean.getDocumentcb().getDOCUMENTFILENAME().substring(bean.getDocumentcb().getDOCUMENTFILENAME().lastIndexOf("."+1));
                    setDocumentBean(bean.getDocumentcb());
                    down(bean.getDocumentcb().getUrl(), bean.getDocumentcb().getName()
                            + ".doc", true);

                }
            });
            chengbao.setOnLongClickListener(new View.OnLongClickListener() {
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
            chengbao.setVisibility(View.GONE);
        }
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
                    try {
                        ShizhuanwenBean bean = (ShizhuanwenBean) msg.obj;
                        setFormData(bean);
                    } catch (Exception e) {
                        ToastUtil.show("表单数据异常!", getContext());
                        getActivity().finish();
                    }
                    break;
            }
        }
    };


    private boolean viewDestroyed=false;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        viewDestroyed=true;
    }
}
