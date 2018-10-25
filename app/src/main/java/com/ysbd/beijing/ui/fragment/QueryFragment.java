package com.ysbd.beijing.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysbd.beijing.BaseFragment;
import com.ysbd.beijing.R;
import com.ysbd.beijing.utils.DateFormatUtil;
import com.ysbd.beijing.utils.QueryHelper;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.WebServiceUtils;
import com.ysbd.beijing.view.CustomDatePicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by lcjing on 2018/8/9.
 */

public class QueryFragment extends BaseFragment implements View.OnClickListener{


    private String type;

    private QueryHelper queryHelper;

    public QueryFragment() {
    }


    public void setQueryHelper(QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    public static QueryFragment getInstance(String type){
        QueryFragment fragment=new QueryFragment();
        Bundle args=new Bundle();
        args.putString("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments()!=null&&getArguments().getString("type")!=null) {
            type=getArguments().getString("type");
        }else {
            type="主办文";
        }
        View view =null;
        if("一般发文".equals(type)||type.equals("结余资金")){
            view = inflater.inflate(R.layout.query_fragment1, null);
            initView1(view);
        }else if("指标文".equals(type)){
            view = inflater.inflate(R.layout.query_fragment2, null);
            initView1(view);
        } else {
            view = inflater.inflate(R.layout.query_fragment, null);
            initView(view);
        }
        return view;
    }


    private void initView(final View view){
        LinearLayout llQicaochushi=view.findViewById(R.id.ll_qicaochushi);
        LinearLayout llShizhengfubianhao=view.findViewById(R.id.ll_shizhenfubianhao);
        TextView tvWenhao=view.findViewById(R.id.tv_wenhao);
        TextView tvShouwenriqi=view.findViewById(R.id.tv_shouwenriqi);
        LinearLayout llYuanwenzihao=view.findViewById(R.id.ll_yuanwenzihao);
        LinearLayout llZhubanchushi=view.findViewById(R.id.ll_zhubanchushi);
        LinearLayout llLaiwendanwei=view.findViewById(R.id.ll_laiwendanwei);
        TextView tvQuery=view.findViewById(R.id.tv_query);
        tvQuery.setOnClickListener(this);
        view.findViewById(R.id.fl_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker((TextView) view.findViewById(R.id.tv_date));
            }
        });
        switch (type) {
            case "主办文":
                break;
            case "市转文":
                llShizhengfubianhao.setVisibility(View.VISIBLE);
                break;
            case "局内传文":
                llLaiwendanwei.setVisibility(View.GONE);
                llZhubanchushi.setVisibility(View.GONE);
                llYuanwenzihao.setVisibility(View.GONE);
                llQicaochushi.setVisibility(View.VISIBLE);
                tvShouwenriqi.setText("起草日期");
                tvWenhao.setText("处室号");
                break;
        }


    }

    private void initView1(final View view){

        LinearLayout llRoot=view.findViewById(R.id.ll_root);
        LinearLayout llYudengwenhao=view.findViewById(R.id.ll_yudengwenhao);
        LinearLayout llGuifanwenjian=view.findViewById(R.id.ll_guifanwenjian);
        TextView tvQuery=view.findViewById(R.id.tv_query);
        tvQuery.setOnClickListener(this);
        view.findViewById(R.id.fl_date_cw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker((TextView) view.findViewById(R.id.tv_chengwenriqi));
            }
        });
        view.findViewById(R.id.fl_date_ng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker((TextView) view.findViewById(R.id.tv_nigaoriqi));
            }
        });
        switch (type){
            case "一般发文":
                break;
            case "指标文":
                break;
            case "结余资金":
                llGuifanwenjian.setVisibility(View.GONE);
                break;
        }
    }


    private void initDatePicker(final TextView signTimeClick) {
        String maxTime = "2100-12-31 00:00";
        CustomDatePicker datePicker = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                signTimeClick.setText(time.split(" ")[0]);
            }
        }, "2000-01-01 00:00", maxTime);// 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        datePicker.showSpecificTime(false); // 不显示时和分
        datePicker.setIsLoop(false); // 不允许循环滚动
        if (signTimeClick.getText().toString().length()<5) {
            signTimeClick.setText(DateFormatUtil.currentDate());
        }
        datePicker.show(signTimeClick.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_query:
                if("一般发文".equals(type)||"指标文".equals(type)||"结余资金".equals(type))
                query1();
                else
                    query();
                break;
        }
    }

    private void query(){
        EditText etNian=getView().findViewById(R.id.et_nian);
        EditText etHao=getView().findViewById(R.id.et_hao);
        EditText etBiaoti=getView().findViewById(R.id.et_biaoti);
        EditText etLaiwendanwei=getView().findViewById(R.id.et_laiwendanwei);
        EditText etYuanwenzihao=getView().findViewById(R.id.et_yuanwenzihao);
        EditText etZhubanchushi=getView().findViewById(R.id.et_zhubanchushi);
        EditText etZhongdianducha=getView().findViewById(R.id.et_zhongdianducha);
        TextView etShouwenriqi=getView().findViewById(R.id.tv_date);
        EditText etShizhengfu=getView().findViewById(R.id.et_shizhengfubianhao);
        Map<String ,String > map=new HashMap<>();
        switch (type){
            case "市转文":
//                map.put("SHi",etShizhengfu.getText().toString());
                map.put("viewGUID","{A9522312-FFFF-FFFF-AF67-3D0B000001B0}");
                map.put("workflowGUID","{A952230B-FFFF-FFFF-8121-FCF400000001}");
                map.put("NIAN",etNian.getText().toString());
                map.put("HAO",etHao.getText().toString());
                map.put("WENJIANMINGCHENG",etBiaoti.getText().toString());
                map.put("SHOUWENRIQI",etShouwenriqi.getText().toString());//2018-09-06
                map.put("LAIWENDANWEI",etLaiwendanwei.getText().toString());
                map.put("SHIZHENGFUBIANHAO",etShizhengfu.getText().toString());
                map.put("YUANWENZIHAO",etYuanwenzihao.getText().toString());
                map.put("ZHUBANCHUSHI",etZhubanchushi.getText().toString());
                break;
            case "主办文":
                map.put("viewGUID","{0A2FF41F-FFFF-FFFF-B7C0-27C00000000D}");
                map.put("workflowGUID","{A9522312-FFFF-FFFF-9538-050B00000002}");
                map.put("NIAN",etNian.getText().toString());
                map.put("HAO",etHao.getText().toString());
                map.put("WENJIANMINGCHENG",etBiaoti.getText().toString());
                map.put("LAIWENDANWEI",etLaiwendanwei.getText().toString());
                map.put("YUANWENZIHAO",etYuanwenzihao.getText().toString());
                map.put("ZHUBANCHUSHI",etZhubanchushi.getText().toString());
                map.put("RECIEVEDATE",etShouwenriqi.getText().toString());//2018-09-06
                break;
            case "局内传文":
                map.put("viewGUID","{0A2FF40B-FFFF-FFFF-A033-B83600000005}");
                map.put("workflowGUID","{A9522312-FFFF-FFFF-9529-B92C00000001}");
                map.put("WENJIANMINGCHENG",etBiaoti.getText().toString());
                map.put("CHUSHIMINGCHENG","");
                map.put("HAO",etHao.getText().toString());
                map.put("NIAN",etNian.getText().toString());
                map.put("SHOUWENRIQI",etShouwenriqi.getText().toString());
//                AND OFFICE_VIEW_JUNEICHUANWEN.HAO LIKE '%ASDF%'
//                AND OFFICE_VIEW_JUNEICHUANWEN.NIAN LIKE '%ASDF%'
//                AND OFFICE_VIEW_JUNEICHUANWEN.SHOUWENRIQI LIKE '%2018-09-11%'
                break;
        }
        map.put("type",type);
        map.put("userid", SpUtils.getInstance().getUserId());
        map.put("stalength","1");
        map.put("leixing",type);
        map.put("returnlength","10");
        queryMap(map);

    }

    private void query1(){
        EditText etBiaoti=getView().findViewById(R.id.et_biaoti);
        EditText etChushiZi=getView().findViewById(R.id.et_chushizi);
        EditText etChushiHao=getView().findViewById(R.id.et_chushihao);
        EditText etNian=getView().findViewById(R.id.et_nian);
        EditText etHao=getView().findViewById(R.id.et_hao);
        EditText etZhusong=getView().findViewById(R.id.et_zhusong);
        EditText etNigaochushi=getView().findViewById(R.id.et_nigaochushi);
        TextView tvChengwenriqi=getView().findViewById(R.id.tv_chengwenriqi);
        TextView tvNigaoriqi=getView().findViewById(R.id.tv_nigaoriqi);
        Map<String ,String > map=new HashMap<>();
        switch (type){
            case "一般发文":
                map.put("viewGUID","{A952230B-0000-0000-7EE5-364900000020}");
                map.put("workflowGUID","{A9522310-FFFF-FFFF-8778-020E0000008B}");
                EditText etGuifanwenjian=getView().findViewById(R.id.et_guifanwenjian);
                map.put("GUIFANWENJIAN",etGuifanwenjian.getText().toString());
                map.put("nian",etNian.getText().toString());
                map.put("YIBANFAWEN_HAO",etHao.getText().toString());
                map.put("biaoti",etBiaoti.getText().toString());
                map.put("CHUSHI_ZI",etChushiZi.getText().toString());
                map.put("nigaodanwei",etNigaochushi.getText().toString());
                map.put("zhusong",etZhusong.getText().toString());
                map.put("yibanfawen_chushi_hao",etChushiHao.getText().toString());
                map.put("NIGAORIQI",tvNigaoriqi.getText().toString());//2018-09-06
                break;
            case "结余资金":
                map.put("viewGUID","{0A2FCA25-0000-0000-1BC0-3262FFFF982E}");
                map.put("workflowGUID","{0A2FCA25-0000-0000-1BC0-3262FFFF982E}");
                map.put("nian",etNian.getText().toString());
                map.put("YIBANFAWEN_HAO",etHao.getText().toString());
                map.put("biaoti",etBiaoti.getText().toString());
                map.put("CHUSHI_ZI",etChushiZi.getText().toString());
                map.put("nigaodanwei",etNigaochushi.getText().toString());
                map.put("zhusong",etZhusong.getText().toString());
                map.put("yibanfawen_chushi_hao",etChushiHao.getText().toString());
                map.put("NIGAORIQI",tvNigaoriqi.getText().toString());//2018-09-06
//                AND OFFICE_BJCZ_JIEYUZIJIN.BIAOTI like '%47598%'
//                AND OFFICE_BJCZ_JIEYUZIJIN.CHENGWENRIQI =
//                    to_date('2018-09-11', 'yyyy-mm-dd')
//                AND OFFICE_BJCZ_JIEYUZIJIN.CHUSHI_ZI like '%47598%'
//                AND OFFICE_BJCZ_JIEYUZIJIN.YIBANFAWEN_HAO like '%47598%'
//                AND OFFICE_BJCZ_JIEYUZIJIN.NIAN like '%47598%'
//                AND OFFICE_BJCZ_JIEYUZIJIN.NIGAODANWEI like '%47598%'
//                AND OFFICE_BJCZ_JIEYUZIJIN.NIGAORIQI =
//                    to_date('2018-09-11', 'yyyy-mm-dd')
//                AND OFFICE_BJCZ_JIEYUZIJIN.YIBANFAWEN_CHUSHI_HAO like '%47598%'
//                AND OFFICE_BJCZ_JIEYUZIJIN.ZHUSONG like '%47598%'
                break;
            case "指标文":
                map.put("viewGUID","{A952230B-FFFF-FFFF-8EA3-F93400000017}");
                map.put("workflowGUID","{A952230B-0000-0000-7D54-8A5700000335}");
                map.put("nian",etNian.getText().toString());
                map.put("YIBANFAWEN_HAO",etHao.getText().toString());
                map.put("biaoti",etBiaoti.getText().toString());
                map.put("CHUSHI_ZI",etChushiZi.getText().toString());
                map.put("nigaodanwei",etNigaochushi.getText().toString());
                map.put("zhusong",etZhusong.getText().toString());
                map.put("yibanfawen_chushi_hao",etChushiHao.getText().toString());
                map.put("NIGAORIQI",tvNigaoriqi.getText().toString());//2018-09-06
                EditText etYu=getView().findViewById(R.id.et_yudengwen);

//                AND OFFICE_BJCZ_ZHIBIAO.biaoti like '%sdasd%'
//                AND OFFICE_BJCZ_ZHIBIAO.chengwenriqi =
//                    to_date('2018-09-11', 'yyyy-mm-dd')
//                AND OFFICE_BJCZ_ZHIBIAO.CHUSHI_ZI like '%asd%'
//                AND OFFICE_BJCZ_ZHIBIAO.hao like '%asd%'
//                AND OFFICE_BJCZ_ZHIBIAO.nian like '%asd%'
//                AND OFFICE_BJCZ_ZHIBIAO.nigaodanwei like '%asd%'
//                AND OFFICE_BJCZ_ZHIBIAO.NIGAORIQI = to_date('2018-09-11', 'yyyy-mm-dd')
//                AND OFFICE_BJCZ_ZHIBIAO.HQZBW_CHUSHI_HAO like '%asd%'
//                AND OFFICE_BJCZ_ZHIBIAO.YDWWENHAO like '%asdasd%'
//                AND OFFICE_BJCZ_ZHIBIAO.zhusong like '%asd%'
                break;
        }
//        map.put("isop","1");//2、3



        map.put("type",type);
        map.put("userid", SpUtils.getInstance().getUserId());
        map.put("stalength","1");
        map.put("leixing",type);
        map.put("returnlength","10");
        queryMap(map);
    }



    private void queryMap(final Map<String,String> queryMap){
        if (queryHelper!=null) {
            queryHelper.query(queryMap);
        }

//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                WebServiceUtils.getInstance().queryForm(type,queryMap);
//            }
//        }.start();
    }
}
