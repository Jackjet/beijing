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
import com.ysbd.beijing.ui.bean.form.JuNeiChuanWenBean;
import com.ysbd.beijing.ui.bean.form.YibanfawenBean;
import com.ysbd.beijing.ui.bean.form.ZhibiaowenBean;
import com.ysbd.beijing.ui.bean.form.ZhuBanwenBean;
import com.ysbd.beijing.utils.Constants;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.FileUtils;
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

public class JieyuzijinfawenFragment extends BaseFormFragment  {

    @BindView(R.id.yudenghao)
    TextView yudenghao;
    @BindView(R.id.type_hao)
    TextView typeHao;
    @BindView(R.id.hao)
    TextView hao;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.nian)
    TextView nian;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.miji)
    TextView miji;
    @BindView(R.id.baoguanqixian)
    TextView baoguanqixian;
    @BindView(R.id.xinxifabu)
    TextView xinxifabu;
    @BindView(R.id.guifanwenjian)
    TextView guifanwenjian;
    @BindView(R.id.qicaochushi)
    TextView qicaochushi;
    @BindView(R.id.xianbanriqi)
    TextView xianbanriqi;
    @BindView(R.id.gongkaishuxing)
    TextView gongkaishuxing;
    @BindView(R.id.cl_qianfa)
    CommentLinearLayout clQianfa;

    @BindView(R.id.cl_zhurenhegao)
    CommentLinearLayout clZhurenhegao;
    @BindView(R.id.cl_mishuhegao)
    CommentLinearLayout clMishuhegao;
    @BindView(R.id.zhusong)
    TextView zhusong;
    @BindView(R.id.chaosong)
    TextView chaosong;
    @BindView(R.id.nigaochushi)
    TextView nigaochushi;
    @BindView(R.id.hegaoren)
    TextView hegaoren;
    @BindView(R.id.dianhua)
    TextView dianhua;
    @BindView(R.id.daziyuan)
    TextView daziyuan;
    @BindView(R.id.jiaoduiren)
    TextView jiaoduiren;
    @BindView(R.id.shouji)
    TextView shouji;
    @BindView(R.id.cl_chuzhanghegao)
    CommentLinearLayout clChuzhanghegao;
    @BindView(R.id.cl_chushihegao)
    CommentLinearLayout clChushihegao;
    @BindView(R.id.bangongshifenshu)
    TextView bangongshifenshu;
    @BindView(R.id.chushifenshu)
    TextView chushifenshu;
    @BindView(R.id.biaoti)
    TextView biaoti;
    @BindView(R.id.chengwenriqi)
    TextView chengwenriqi;
    @BindView(R.id.cl_attachment)
    CommentLinearLayout clAttachment;
    @BindView(R.id.zhengwen)
    View zhengwen;
    @BindView(R.id.chengbaoneirong)
    View chengbaoneirong;

    @BindView(R.id.cl_yijianlan)
    CommentLinearLayout clYijianlan;
    @BindView(R.id.huiqian)
            TextView huiqian;
    Unbinder unbinder;

    public static JieyuzijinfawenFragment getInstance(String guid,String actor) {
        JieyuzijinfawenFragment fragment = new JieyuzijinfawenFragment();
        Bundle args = new Bundle();
        args.putString("guid", guid);
        args.putString("actor", actor);
        fragment.setArguments(args);
        return fragment;
    }

    private String formName="结余资金";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jieyuzijinfawen, null);
        guid = getArguments().getString("guid");
        actor = getArguments().getString("actor");
        unbinder = ButterKnife.bind(this, view);
        Map<String, CommentLinearLayout> layoutMap = new HashMap<>();
        layoutMap.put("处长核稿", clChuzhanghegao);
        layoutMap.put("处室核稿", clChushihegao);
        layoutMap.put("签发", clQianfa);
//        layoutMap.put("会签", clHuiqian);
        layoutMap.put("主任核稿", clZhurenhegao);
        layoutMap.put("秘书核稿", clMishuhegao);
        layoutMap.put("意见栏", clYijianlan);
        List<String> frames = new ArrayList<>();
        frames.add("处长核稿");
        frames.add("处室核稿");
        frames.add("签发");
//        frames.add("会签");
        frames.add("主任核稿");
        frames.add("秘书核稿");
        frames.add("意见栏");
        initData(layoutMap, frames, guid, formName);
        getData();

        return view;
    }

    String guid;

//    private void getData() {
//        FileIdBean fileIdBean = new FileIdBean();
//        fileIdBean.setInstanceguid(guid);
//        fileIdBean.setUserid(getContext().getSharedPreferences(Constants.SP, Context.MODE_PRIVATE).getString(Constants.USER_ID, ""));
//        final String jsonData = new Gson().toJson(fileIdBean);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String data = WebServiceUtils.getInstance().findToDoFileInfo(jsonData);
//                ZhuBanwenBean banwenBean = new Gson().fromJson(data, ZhuBanwenBean.class);
//                ((FormActivity) getActivity()).addMenus(banwenBean.getMenus());
//                if (banwenBean.getComment() != null) {
//                    for (int i = 0; i < banwenBean.getComment().size(); i++) {
//                        OpinionModel opinionModel = new OpinionModel();
//                        opinionModel.setParent(false);
//                        opinionModel.setOpinionFrameMark(banwenBean.getComment().get(i).getRow_guid());
//                        opinionModel.setAddable(false);
//                        opinionModel.setId(banwenBean.getComment().get(i).getComment_guid());
//                        switch (banwenBean.getComment().get(i).getRow_guid()) {
//                            case "签发":
////                                chuzhangpishi.add(opinionModel);
//                                break;
//                            case "会签":
////                                julingdao.add(opinionModel);
//                                break;
//                            case "主任审核":
////                                niban.add(opinionModel);
//                                break;
//                            case "秘书核稿":
////                                yusuanchuzhang.add(opinionModel);
//                                break;
//                            case "处长核稿":
////                                yusuanchu.add(opinionModel);
//                                break;
//                            case "处室核稿":
////                                qitaren.add(opinionModel);
//                                break;
//                            case "意见栏":
////                                qitaren.add(opinionModel);
//                                break;
//                        }
//                    }
//                }
//                if (banwenBean.getCurrentComment() != null) {
//                    for (int i = 0; i < banwenBean.getCurrentComment().size(); i++) {
//                        OpinionModel opinionModel = new OpinionModel();
//                        opinionModel.setParent(true);
//                        opinionModel.setOpinionFrameMark(banwenBean.getCurrentComment().get(i).getAction_guid());
//                        opinionModel.setOpinionFrameName(banwenBean.getCurrentComment().get(i).getAction_name());
//                        opinionModel.setAddable(true);
//                        opinionModel.setId(banwenBean.getCurrentComment().get(i).getComment_guid());
//                        switch (banwenBean.getCurrentComment().get(i).getAction_name()) {
//                            case "处长批示":
////                                chuzhangpishi.add(opinionModel);
//                                break;
//                            case "局领导批示":
////                                julingdao.add(opinionModel);
//                                break;
//                            case "拟办意见":
////                                niban.add(opinionModel);
//                                break;
//                            case "预算处处长意见":
////                                yusuanchuzhang.add(opinionModel);
//                                break;
//                            case "预算处意见（预登文）":
////                                yusuanchu.add(opinionModel);
//                                break;
//                            case "其他人意见":
////                                qitaren.add(opinionModel);
//                                break;
//                        }
//                    }
//                }
//
//                mHandler.obtainMessage(1, data).sendToTarget();
//            }
//        }).start();
//    }

    private void getData() {
//        loadingDialog = new LoadingDialog();
//        loadingDialog.show(getChildFragmentManager());
        FileIdBean fileIdBean = new FileIdBean();
        fileIdBean.setInstanceguid(guid);
        fileIdBean.setUserid(getContext().getSharedPreferences(Constants.SP, Context.MODE_PRIVATE).getString(Constants.USER_ID, ""));
        final String jsonData = new Gson().toJson(fileIdBean);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = WebServiceUtils.getInstance().findToDoFileInfo(jsonData);
                if (data.length() < 3) {
                    mHandler.sendEmptyMessage(3);
                    return;
                }
                YibanfawenBean banwenBean = null;
                try {
                    data = data.replace("<![CDATA[", "");
                    data = data.replace("]]>", "");
                    banwenBean = new Gson().fromJson(data, YibanfawenBean.class);
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(3);
                    App.catchE(e);
                    return;
                }
                if (banwenBean == null) {
                    mHandler.sendEmptyMessage(3);
                    return;
                }
                if (getActivity() == null) {
                    return;
                }
                try {
                    mHandler.obtainMessage(1, banwenBean).sendToTarget();
                    if (banwenBean.getMenus()!=null&&getActivity()!=null) {
                        ((FormActivity) getActivity()).addMenus(banwenBean.getMenus());
                    }
                    if (banwenBean.getActors()!=null&&getActivity()!=null) {
                        ((FormActivity) getActivity()).setActors(banwenBean.getActors());
                    }
                }catch (Exception e){App.catchE(e);}

                initCommentDate(banwenBean.getCurrentComment(), banwenBean.getComment());
            }
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (viewDestroyed) {
                return;
            }
            super.handleMessage(msg);
            YibanfawenBean bean = (YibanfawenBean) msg.obj;
            setFormData(bean);
        }
    };


    private void setFormData(final YibanfawenBean bean) {


//        bangongshilch :
        baoguanqixian.setText(bean.getBaoguanqixian());//        保管期限
//        biaoti.setText(bean.getBiaoti());
        if (bean.getBiaoti()!=null) {
            biaoti.setText(Html.fromHtml(bean.getBiaoti()));
        }
        chushifenshu.setText(bean.getChushifenshu());
        typeHao.setText(bean.getChushi_zi());
//        xinxigongkai.setText(bean.getGongkaishuxing());
        shouji.setText(bean.getJiaoduidianhua());
        hao.setText(bean.getYibanfawen_chushi_hao());
//        huanji.setText(bean.getHuanji());
        shouji.setText(bean.getJiaoduidianhua());//机号/
        daziyuan.setText(bean.getLururen());
        miji.setText(Html.fromHtml(bean.getMiji()));
        nian.setText(bean.getNian());
        xianbanriqi.setText(DateFormatUtil.subDate(bean.getXianbanshijian()));// 办日期/
//        nigaoren.setText(bean.getNigao() + " " + DateFormatUtil.subDate(bean.getNigaoriqi()));
        nigaochushi.setText(bean.getNigaodanwei());
        dianhua.setText(bean.getNigaodianhua());
        xinxifabu.setText(bean.getXinxifabu());
        type.setText(bean.getZi());
//        qianfa.setText(bean.getQfbjczj());//        签发 文字+意见框/
//        huiqian.setText(bean.getHuiqian());///

        initHuiqian2(bean.getWorkflow_sub());///会签框
        initAttachment(bean.getAttachment(), clAttachment);
        guifanwenjian.setText(bean.getGuifanwenjian());
        bangongshifenshu.setText(bean.getBangongshifenshu());
        daziyuan.setText(bean.getYinzhi());
        jiaoduiren.setText(bean.getJiaodui() + " " + DateFormatUtil.subDate(bean.getJiaoduiriqi()));
        num.setText(bean.getYibanfawen_hao());
        zhusong.setText(bean.getZhusong());//        主送/
        chaosong.setText(bean.getChaosong());//        抄送

        yudenghao.setText(bean.getYdwwenhao());
        gongkaishuxing.setText(bean.getGongkaishuxing());
        hegaoren.setText(bean.getNigao()+" "+DateFormatUtil.subDate(bean.getNigaoriqi()));
        chengwenriqi.setText(DateFormatUtil.subDate(bean.getChengwenriqi()));
        if (bean.getDocumentcb() != null) {
            zhengwen.setVisibility(View.VISIBLE);
            zhengwen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //                    String type=bean.getDocumentcb().getDOCUMENTFILENAME().substring(bean.getDocumentcb().getDOCUMENTFILENAME().lastIndexOf("."+1));
                    down(bean.getDocumentcb().getUrl(), bean.getDocumentcb().getName()
                            + ".doc", true);
                    setDocumentBean(bean.getDocumentcb());

                }
            });
            zhengwen.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 20102);
                    } else {
                        upLoadDocument(bean.getDocumentcb(),
                                FileUtils.getInstance().makeDocumentDir().getPath() + File.separator + "正文.doc");

                    }
                    return true;
                }
            });
        } else {
            zhengwen.setVisibility(View.INVISIBLE);
        }

        if (bean.getDocument() != null) {
            chengbaoneirong.setVisibility(View.VISIBLE);
            chengbaoneirong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //                    String type=bean.getDocumentcb().getDOCUMENTFILENAME().substring(bean.getDocumentcb().getDOCUMENTFILENAME().lastIndexOf("."+1));
                    down(bean.getDocument().getUrl(), bean.getDocument().getName()
                            + ".doc", true);
                    setDocumentBean(bean.getDocument());

                }
            });
            chengbaoneirong.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 20102);
                    } else {
                        upLoadDocument(bean.getDocument(),
                                FileUtils.getInstance().makeDocumentDir().getPath() + File.separator + "cb正文.doc");

                    }
                    return true;
                }
            });
        } else {
            chengbaoneirong.setVisibility(View.INVISIBLE);
        }


    }

    private void initHuiqian2(String sub){
        sub=Html.fromHtml(sub).toString();
        StringBuffer sb=new StringBuffer();
        String[] s=sub.split(";");
        if (s.length>0) {
            for (int i = 0; i < s.length; i++) {
                if (s[i].indexOf(">")>0) {
                    sb.append(s[i].substring(s[i].indexOf(">")+1));
                }
            }

        }
        huiqian.setText(sb.toString());
    }

    private boolean viewDestroyed=false;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        viewDestroyed=true;
    }
}
