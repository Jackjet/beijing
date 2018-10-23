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
import android.widget.TextView;

import com.google.gson.Gson;
import com.ysbd.beijing.App;
import com.ysbd.beijing.R;
import com.ysbd.beijing.ui.activity.FormActivity;
import com.ysbd.beijing.ui.bean.FileIdBean;
import com.ysbd.beijing.ui.bean.form.YibanfawenBean;
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

public class YibanfawenFragment extends BaseFormFragment {

    @BindView(R.id.qicaoriqi)
    TextView qicaoriqi;
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
    @BindView(R.id.xinxigongkai)
    TextView xinxigongkai;
    @BindView(R.id.xinxifabu)
    TextView xinxifabu;
    @BindView(R.id.baoguanqixian)
    TextView baoguanqixian;
    @BindView(R.id.guifanwenjian)
    TextView guifanwenjian;
    @BindView(R.id.huanji)
    TextView huanji;
    @BindView(R.id.xianbanriqi)
    TextView xianbanriqi;
    @BindView(R.id.zhusong)
    TextView zhusong;
    @BindView(R.id.chaosong)
    TextView chaosong;
    @BindView(R.id.nigaochushi)
    TextView nigaochushi;
    @BindView(R.id.nigaoren)
    TextView nigaoren;
    @BindView(R.id.dianhua)
    TextView dianhua;
    @BindView(R.id.daziyuan)
    TextView daziyuan;
    @BindView(R.id.jiaoduiren)
    TextView jiaoduiren;
    @BindView(R.id.shouji)
    TextView shouji;
    @BindView(R.id.bangongshifenshu)
    TextView bangongshifenshu;
    @BindView(R.id.chushifenshu)
    TextView chushifenshu;
    @BindView(R.id.biaoti)
    TextView biaoti;
    @BindView(R.id.huiqian)
    TextView huiqian;
    @BindView(R.id.huiqian2)
    TextView huiqian2;
    @BindView(R.id.qianfa)
    TextView qianfa;
    @BindView(R.id.cl_yijianlan)
    CommentLinearLayout clYijianlan;
    @BindView(R.id.chengwenriqi)
    TextView chengwenriqi;
    @BindView(R.id.cl_qianfa)
    CommentLinearLayout clQianfa;
    @BindView(R.id.cl_huiqian)
    CommentLinearLayout clHuiqian;
    @BindView(R.id.cl_zhurenhegao)
    CommentLinearLayout clZhurenhegao;
    @BindView(R.id.cl_mishuhegao)
    CommentLinearLayout clMishuhegao;
    @BindView(R.id.cl_chuzhanghegao)
    CommentLinearLayout clChuzhanghegao;
    @BindView(R.id.cl_chushihegao)
    CommentLinearLayout clChushihegao;


    Unbinder unbinder;
    @BindView(R.id.cl_attachment)
    CommentLinearLayout clAttachment;
    @BindView(R.id.zhengwen)
    View zhengwen;
    @BindView(R.id.chengbaoneirong)
    View chengbaoneirong;

    private LoadingDialog loadingDialog;

    public static YibanfawenFragment getInstance(String guid, String actor) {
        YibanfawenFragment fragment = new YibanfawenFragment();
        Bundle args = new Bundle();
        args.putString("guid", guid);
        args.putString("actor", actor);
        fragment.setArguments(args);
        return fragment;
    }

    String guid;
    String formName = "一般发文";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yibanfawen, null);
        guid = getArguments().getString("guid");
        actor = getArguments().getString("actor");
        unbinder = ButterKnife.bind(this, view);
        initComment();
        getData();

        return view;
    }

    private void initComment() {

        Map<String, CommentLinearLayout> layoutMap = new HashMap<>();
        layoutMap.put("秘书审核", clMishuhegao);
//        layoutMap.put("合法性审核", );
        layoutMap.put("局内会签", clHuiqian);
        layoutMap.put("主任核稿", clZhurenhegao);
        layoutMap.put("处室核稿", clChushihegao);
        layoutMap.put("签发", clQianfa);
        layoutMap.put("意见栏", clYijianlan);
        layoutMap.put("处长意见", clChuzhanghegao);
        List<String> frames = new ArrayList<>();
        frames.add("秘书审核");
        frames.add("局内会签");
        frames.add("主任核稿");
        frames.add("处室核稿");
        frames.add("签发");
        frames.add("意见栏");
        frames.add("处长意见");
        initData(layoutMap, frames, guid, formName);
    }


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


                try {
                    if (data.length() < 5) {
                        mHandler.obtainMessage(2, data).sendToTarget();
                    } else {
                        YibanfawenBean banwenBean = new Gson().fromJson(data, YibanfawenBean.class);
                        mHandler.obtainMessage(1, banwenBean).sendToTarget();
                        if (banwenBean.getMenus()!=null&&getActivity()!=null) {
                            ((FormActivity) getActivity()).addMenus(banwenBean.getMenus());
                        }
                        if (banwenBean.getActors()!=null&&getActivity()!=null) {
                            ((FormActivity) getActivity()).setActors(banwenBean.getActors());
                        }
                        initCommentDate(banwenBean.getCurrentComment(), banwenBean.getComment());
//                        setQianfa(banwenBean.getJuwaihuiqian(), banwenBean.getChushihuiqian());

                    }
                } catch (Exception e) {
                    mHandler.obtainMessage(2, data).sendToTarget();
                    App.catchE(e);
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
                    try {
                        YibanfawenBean bean = (YibanfawenBean) msg.obj;
                        setFormData(bean);
                    } catch (Exception e) {
                        ToastUtil.show("表单数据异常!", getContext());
//                        getActivity().finish();
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 2:
//                    ToastUtil.show("表单数据异常!", getContext());
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
            }
        }
    };

    private void setFormData(final YibanfawenBean bean) {

//        bangongshilch :
        baoguanqixian.setText(bean.getBaoguanqixian());//        保管期限
//        biaoti.setText(bean.getBiaoti());
        if (bean.getBiaoti() != null) {
            biaoti.setText(Html.fromHtml(bean.getBiaoti()));
        }
        chushifenshu.setText(bean.getChushifenshu());
        typeHao.setText(bean.getChushi_zi());
        xinxigongkai.setText(bean.getGongkaishuxing());
        shouji.setText(bean.getJiaoduidianhua());
        hao.setText(bean.getYibanfawen_chushi_hao());
        huanji.setText(bean.getHuanji());
        shouji.setText(bean.getJiaoduidianhua());//机号/
        daziyuan.setText(bean.getLururen());
        miji.setText(bean.getMiji());
        nian.setText(bean.getNian());
        xianbanriqi.setText(DateFormatUtil.subDate(bean.getXianbanshijian()));// 办日期/
        nigaoren.setText(bean.getNigao() + " " + DateFormatUtil.subDate(bean.getNigaoriqi()));
        nigaochushi.setText(bean.getNigaodanwei());
        dianhua.setText(bean.getNigaodianhua());
        xinxifabu.setText(bean.getXinxifabu());
        type.setText(bean.getZi());
        qianfa.setText(bean.getQfbjczj());//        签发 文字+意见框/
        huiqian.setText(bean.getHuiqian());///
        if (bean.getChushihuiqian()!=null) {
            huiqian.setText(Html.fromHtml(bean.getChushihuiqian()));///会签文字
        }
        initAttachment(bean.getAttachment(), clAttachment);
        initHuiqian2(bean.getWorkflow_sub());///会签框

        guifanwenjian.setText(bean.getGuifanwenjian());
        chengwenriqi.setText(DateFormatUtil.subDate(bean.getChengwenriqi()));
        bangongshifenshu.setText(bean.getBangongshifenshu());
        daziyuan.setText(bean.getYinzhi());
        jiaoduiren.setText(bean.getJiaodui() + " " + DateFormatUtil.subDate(bean.getJiaoduiriqi()));
        num.setText(bean.getYibanfawen_hao());
        zhusong.setText(bean.getZhusong());//        主送/
        chaosong.setText(bean.getChaosong());//        抄送
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
        huiqian2.setText(sb.toString());
    }


    private boolean viewDestroyed=false;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        viewDestroyed=true;
    }
}
