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
import com.ysbd.beijing.ui.bean.form.ZhibiaowenBean;
import com.ysbd.beijing.ui.bean.form.ZhuBanwenBean;
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

public class ZhibiaowenFragment extends BaseFormFragment {

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
    @BindView(R.id.huanji)
    TextView huanji;
    @BindView(R.id.xianbanriqi)
    TextView xianbanriqi;
    @BindView(R.id.xiadaleixing)
    TextView xiadaleixing;
    @BindView(R.id.xinxigongkai)
    TextView xinxigongkai;
    @BindView(R.id.cl_qianfa)
    CommentLinearLayout clQianfa;
    @BindView(R.id.cl_huiqian)
    CommentLinearLayout clHuiqian;
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
    @BindView(R.id.cl_yijianlan)
    CommentLinearLayout clYijianlan;
    @BindView(R.id.chengwenriqi)
    TextView chengwenriqi;
    @BindView(R.id.cl_attachment)
    CommentLinearLayout clAttachment;


    @BindView(R.id.zhengwen)
    View zhengwen;
    @BindView(R.id.chengbaoneirong)
    View chengbaoneirong;

    Unbinder unbinder;

    public static ZhibiaowenFragment getInstance(String guid,String actor) {
        ZhibiaowenFragment fragment = new ZhibiaowenFragment();
        Bundle args = new Bundle();
        args.putString("guid", guid);
        args.putString("actor", actor);
        fragment.setArguments(args);
        return fragment;
    }

    private String guid;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhibiaowen, null);
        guid = getArguments().getString("guid");
        actor = getArguments().getString("actor");
        unbinder = ButterKnife.bind(this, view);
        initComment();
        getData();
        return view;
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
                mHandler.obtainMessage(1, data).sendToTarget();
                ZhibiaowenBean banwenBean = new Gson().fromJson(data, ZhibiaowenBean.class);
                try {
                    if (banwenBean.getMenus()!=null&&getActivity()!=null) {
                        ((FormActivity) getActivity()).addMenus(banwenBean.getMenus());
                    }
                    if (banwenBean.getActors()!=null&&getActivity()!=null) {
                        ((FormActivity) getActivity()).setActors(banwenBean.getActors());
                    }
                    initCommentDate(banwenBean.getCurrentComment(), banwenBean.getComment());

                } catch (Exception e) {
                    mHandler.obtainMessage(2,"获取详情失败").sendToTarget();
                    App.catchE(e);
                }
            }
        }).start();
    }

    private String formName = "指标文";

    private void initComment() {
        Map<String, CommentLinearLayout> layoutMap = new HashMap<>();

        layoutMap.put("处室核稿", clChushihegao);
        layoutMap.put("主任核稿", clZhurenhegao);
        layoutMap.put("局内会签", clHuiqian);
        layoutMap.put("处长意见", clChuzhanghegao);
        layoutMap.put("局领导批示", clQianfa);
        layoutMap.put("意见栏", clYijianlan);
        layoutMap.put("秘书意见", clMishuhegao);
        List<String> frames = new ArrayList<>();
        frames.add("处室核稿");
        frames.add("主任核稿");
        frames.add("局内会签");
        frames.add("处长意见");
        frames.add("局领导批示");
        frames.add("意见栏");
        frames.add("秘书意见");
        initData(layoutMap, frames, guid, formName);
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
                    String data = msg.obj.toString();
                    if (data.length() > 4) {
                        ZhibiaowenBean banwenBean = new Gson().fromJson(data, ZhibiaowenBean.class);
                        setData(banwenBean);
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 2:
                    ToastUtil.show(msg.obj.toString(), getContext());
                    break;
            }
        }
    };

    private void setData(final ZhibiaowenBean bean) {
        yudenghao.setText(bean.getYdwwenhao());
        type.setText(bean.getZi());
        num.setText(bean.getHao());
        typeHao.setText(bean.getChushi_zi());
        hao.setText(bean.getHqzbw_chushi_hao());
        zhusong.setText(bean.getZhusong());

        nigaoren.setText(bean.getNigao()+" "+DateFormatUtil.subDate(bean.getNigaoriqi()));
        jiaoduiren.setText(bean.getJiaodui()+" "+DateFormatUtil.subDate(bean.getJiaoduiriqi()));
        shouji.setText(bean.getJiaoduidianhua());

        bangongshifenshu.setText(bean.getBangongshifenshu());
        baoguanqixian.setText(bean.getBaoguanqixian());

        if (bean.getBiaoti()!=null) {
            biaoti.setText(Html.fromHtml(bean.getBiaoti()));
        }
        initAttachment(bean.getAttachment(), clAttachment);
        chaosong.setText(bean.getChaosong());
        chengwenriqi.setText(DateFormatUtil.subDate(bean.getChengwenriqi()));
        chushifenshu.setText(bean.getChushifenshu());
        xinxigongkai.setText(bean.getGongkaishuxing());
        huanji.setText(bean.getHuanji());
        miji.setText(bean.getMiji());
        nian.setText(bean.getNian());
//                * nigao : 张久亮
        nigaochushi.setText(bean.getNigaodanwei());
//                * nigaodanwei : 预算处(预算编审中心)
        dianhua.setText(bean.getNigaodianhua());
//                * nigaodianhua : 4
//                * nigaoriqi : 2014-12-25 00:00:00.0
//                * qianfa :
        xianbanriqi.setText(DateFormatUtil.subDate(bean.getXianbanshijian()));
        daziyuan.setText(bean.getYinzhi());
//     * yuanyincontent : 无
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

    private boolean viewDestroyed=false;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        viewDestroyed=true;
    }
}