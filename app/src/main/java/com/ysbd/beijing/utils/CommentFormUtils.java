package com.ysbd.beijing.utils;

import com.ysbd.beijing.ui.bean.OpinionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcjing on 2018/8/23.
 */

public class CommentFormUtils {

    public static String getFormId(String formName){
        switch (formName){
            case "一般发文":
                return "{A9522310-FFFF-FFFF-8778-020E0000008B}";
            case "主办文":
                return "{A9522312-FFFF-FFFF-9538-050B00000002}";
            case "市转文":
                return "{A952230B-FFFF-FFFF-8121-FCF400000001}";
            case "指标文":
                return "{A952230B-0000-0000-7D54-8A5700000335}";
            case "局内传文":
                return "{A9522312-FFFF-FFFF-9529-B92C00000001}";
            case "结余资金转文":
            case "结余资金":
                return "{0A2FCA25-0000-0000-1BC0-3262FFFF982E}";
        }
        return "";
    }

    public static List<OpinionModel> getComments(String formId){
        switch (formId){
            case "{A9522310-FFFF-FFFF-8778-020E0000008B}":
                return getYbfw();
            case "{A9522312-FFFF-FFFF-9538-050B00000002}":
                return getZhubw();
            case "{A952230B-FFFF-FFFF-8121-FCF400000001}":
                return getShizhw();
            case "{A952230B-0000-0000-7D54-8A5700000335}":
                return getZhibiaow();
            case "{A9522312-FFFF-FFFF-9529-B92C00000001}":
                return getJncw();
            case "{0A2FCA25-0000-0000-1BC0-3262FFFF982E}":
                return getJyzj();
        }
        return getYbfw();
    }
    public static List<OpinionModel> getCommentsByName(String formName){
        switch (formName){
            case "一般发文":
                return getYbfw();
            case "主办文":
                return getZhubw();
            case "市转文":
                return getShizhw();
            case "指标文":
                return getZhibiaow();
            case "局内传文":
                return getJncw();
            case "结余资金":
            case "结余资金转文":
                return getJyzj();
        }
        return getYbfw();
    }

    public static String getCommentId(String formId,String commentName){
        List<OpinionModel> opinionModels=getCommentsByName(formId);
        for (int i = 0; i < opinionModels.size(); i++) {
            if(opinionModels.get(i).getOpinionFrameName().equals(commentName))
                return opinionModels.get(i).getOpinionFrameMark();
        }
        return "";
    }

    public static String getCommentFrame(String formId,String commentId){
        List<OpinionModel> opinionModels=getCommentsByName(formId);
        for (int i = 0; i < opinionModels.size(); i++) {
            if(opinionModels.get(i).getOpinionFrameMark().equals(commentId))
                return opinionModels.get(i).getOpinionFrameName();
        }
        return "";
    }

    public static List<OpinionModel> getYbfw(){
        List<OpinionModel> commentBeans=new ArrayList<>();

//        commentBeans.add(new OpinionModel("签发","{0A2FCA25-0000-0000-2749-37BFFFFFDE4D}"));
        commentBeans.add(new OpinionModel("秘书审核","{0A2FCA25-FFFF-FFFF-94A4-7B0CFFFFECBA}"));
        commentBeans.add(new OpinionModel("合法性审核","{A952230B-0000-0000-6B33-BF150000005B}"));
        commentBeans.add(new OpinionModel("局内会签","{A952230B-FFFF-FFFF-CCD5-3B4F00000009}"));
        commentBeans.add(new OpinionModel("主任核稿","{A952230B-FFFF-FFFF-CCD3-FF3100000008}"));
        commentBeans.add(new OpinionModel("处室核稿","{A952230B-FFFF-FFFF-CCD3-D90B00000007}"));
        commentBeans.add(new OpinionModel("签发","{A952230B-FFFF-FFFF-CCD4-79ED00000005}"));
        commentBeans.add(new OpinionModel("意见栏","{A952230B-0000-0000-4FBE-7ECC000001BF}"));
        commentBeans.add(new OpinionModel("处长意见","{A952230B-FFFF-FFFF-BD15-3ECF00000A8C}"));
        return commentBeans;
    }

    public static List<OpinionModel> getZhubw(){
        List<OpinionModel> commentBeans=new ArrayList<>();
        commentBeans.add(new OpinionModel("其他人意见","{A9522312-FFFF-FFFF-9EAA-247B00000003}"));
        commentBeans.add(new OpinionModel("预算处意见","{A9522312-FFFF-FFFF-9EA6-655000000002}"));
        commentBeans.add(new OpinionModel("预算处处长意见","{A9522312-FFFF-FFFF-9EA1-B6D900000001}"));
        commentBeans.add(new OpinionModel("处长签字","{A9522312-FFFF-FFFF-D3F4-C0290000004F}"));
//        {A9522312-FFFF-FFFF-D3F4-C0290000004F}
        commentBeans.add(new OpinionModel("处长批示","{A9522312-FFFF-FFFF-9C1B-8B7D00000559}"));
        commentBeans.add(new OpinionModel("局领导意见1","{A9522312-0000-0000-0BF7-FBC300000102}"));
        commentBeans.add(new OpinionModel("局领导意见","{A9522312-FFFF-FFFF-9C15-975100000549}"));
        commentBeans.add(new OpinionModel("拟办意见","{0A2FCA25-FFFF-FFFF-E85D-CEDA00032DF7}"));
        return commentBeans;
    }

    public static List<OpinionModel> getShizhw(){
        List<OpinionModel> commentBeans=new ArrayList<>();
        commentBeans.add(new OpinionModel("局领导意见","{A9522312-FFFF-FFFF-9A8B-88E300000099}"));
        commentBeans.add(new OpinionModel("处长签字","{0A2FF41F-0000-0000-0023-1165000002E0}"));
        commentBeans.add(new OpinionModel("其他人意见","{A952230B-FFFF-FFFF-A132-1C6AFFFFFFA9}"));
        commentBeans.add(new OpinionModel("处长意见","{A952230B-FFFF-FFFF-A130-E98DFFFFFFA8}"));

        commentBeans.add(new OpinionModel("承办人意见","{0A2FF41F-FFFF-FFFF-FF34-CB8C0000000C}"));
        commentBeans.add(new OpinionModel("拟办意见","{0A2FCA25-FFFF-FFFF-E865-EDBB000330B0}"));
        return commentBeans;
    }

    public static List<OpinionModel> getZhibiaow(){
        List<OpinionModel> commentBeans=new ArrayList<>();
        commentBeans.add(new OpinionModel("其他意见","{A952230B-FFFF-FFFF-8174-C48100000020}"));
        commentBeans.add(new OpinionModel("处室核稿","{A952230B-FFFF-FFFF-CA4E-2E770000003A}"));
        commentBeans.add(new OpinionModel("主任核稿","{A952230B-FFFF-FFFF-CA48-2E9D00000035}"));
        commentBeans.add(new OpinionModel("处长意见","{A952230B-FFFF-FFFF-CEFD-70C60000015A}"));
        commentBeans.add(new OpinionModel("局领导批示","{A952230B-FFFF-FFFF-CA4F-4F480000003B}"));
        commentBeans.add(new OpinionModel("局内会签","{A952230B-FFFF-FFFF-CA46-F29D00000034}"));
        commentBeans.add(new OpinionModel("意见栏","{A952230E-0000-0000-7EC2-AA5F00000078}"));
        commentBeans.add(new OpinionModel("秘书意见","{0A2FCA25-0000-0000-1C56-B923000165E9}"));
        return commentBeans;
    }

    public static List<OpinionModel> getJncw(){
        List<OpinionModel> commentBeans=new ArrayList<>();
//        {A9522312-0000-0000-05F1-F175FFFFFFA2}
        commentBeans.add(new OpinionModel("处长批示","{A9522312-0000-0000-05F1-B769FFFFFFA1}"));
        commentBeans.add(new OpinionModel("局领导批示","{A952230E-0000-0000-6F46-7AB800000018}"));
        commentBeans.add(new OpinionModel("处长签字","{A9522312-0000-0000-20D9-42970000006A}"));
        commentBeans.add(new OpinionModel("其他人意见","{A9522312-0000-0000-05F1-F175FFFFFFA2}"));
        return commentBeans;
    }

    public static List<OpinionModel> getJyzj(){
        List<OpinionModel> commentBeans=new ArrayList<>();
//        commentBeans.add(new OpinionModel("签发","{0A2FCA25-0000-0000-1BCE-98DAFFFF9971}"));
        commentBeans.add(new OpinionModel("局内会签","{0A2FCA25-0000-0000-1BCF-5D54FFFF9961}"));
        commentBeans.add(new OpinionModel("签发","{0A2FCA25-0000-0000-1BCE-57F1FFFF9950}"));
        commentBeans.add(new OpinionModel("秘书核稿","{0A2FCA25-0000-0000-1CFF-8ABEFFFF9D83}"));
        commentBeans.add(new OpinionModel("主任核稿","{0A2FCA25-0000-0000-1BCF-14D6FFFF9956}"));
        commentBeans.add(new OpinionModel("处室核稿","{0A2FCA25-0000-0000-1BCD-CC87FFFF9952}"));
        commentBeans.add(new OpinionModel("意见栏","{0A2FCA25-0000-0000-1BCC-D3B3FFFF9936}"));
        commentBeans.add(new OpinionModel("处长核稿","{0A2FCA25-0000-0000-1BCC-A7E7FFFF9934}"));
        return commentBeans;
    }

}
