package com.ysbd.beijing.ui.bean.form;

import com.ysbd.beijing.ui.bean.MenuBean;

import java.util.List;

/**
 * Created by lcjing on 2018/8/22.
 */

public class YibanfawenBean extends BaseFormBean {

    /**
     * currentComment : [{"action_name":"处长批示","action_guid":"{B0832517-8927-417A-9948-266082698AC9}","comment_guid":"{A9522312-FFFF-FFFF-9C1B-8B7D00000559}"},{"action_name":"处长批示归档","action_guid":"{1D3C9DA1-D962-4B61-9B9F-ED558EA8BE3C}","comment_guid":"{A9522312-FFFF-FFFF-D3F4-C0290000004F}"}]
     * bangongshilch :
     * baoguanqixian :
     * beizhu :
     * chengbaoneirong :
     * chengbaoren : 梁策(信息处（信息中心）)
     * chushilch :
     * chuzhangyijian :
     * cuibantianshu :
     * czqianzi :
     * dubanjilu :
     * formguid :
     * guidangriqi :
     * hao : 3947
     * huanji :
     * jianyaoqingkuang :
     * juneiducha :
     * juzhangpishi :
     * laiwendanwei : 测试
     * lianxidianhua : 0000
     * lingdaopishi :
     * lookydwurl :
     * lururen : 马荣丽
     * miji :
     * mijiwen :
     * nian : 2015
     * qitarenyijian :
     * recievedate : 2015-01-09&#32;00:00:00.0
     * riseword :
     * shangjiducha :
     * shifoufawen :
     * subwokflow_fawen :
     * subworkflow_state :
     * subworkflow_zhibiao :
     * wenjianmingcheng : 测试处长更换承办人
     * workflowinstance_guid : {0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}
     * workflow_main :
     * workflow_sub : {0A2FF324-0000-0000-7B0E-3A6E00000020},国库处(国库支付中心);{0A2FF324-0000-0000-7B0E-3D4600000024},资产处（资产中心）;{0A2FF324-0000-0000-7B60-66CE0000004B},资产处（资产中心）;
     * xianbandate : 2015-01-23&#32;00:00:00.0
     * xianbanriqi :
     * xiebanchushi :
     * yuandate :
     * yuanwenzihao :
     * yusuanchuyijian :
     * yusuanchuzhangyijian :
     * zhongdianduban : 否
     * zhongdianducha :
     * zhubanchushi : 信息处（信息中心）
     * zhutobianhan :
     * zhutofa :
     * zhutojieyu :
     * zhutozhi :
     * comment : [{"row_guid":"{0A2FF324-FFFF-FFFF-F1F1-166CFFFFFF9B}","comment_guid":"{A9522312-FFFF-FFFF-9C1B-8B7D00000559}","comment_content":"管理员重定位到邢璐，","comment_person":"郭树忠","comment_date":"2015-08-03&#32;00:00:00.0"},{"row_guid":"{0A2FF324-FFFF-FFFF-F1F4-A6DBFFFFFFB4}","comment_guid":"{A9522312-FFFF-FFFF-9C1B-8B7D00000559}","comment_content":"主办文，在处长待办下都有\u201c更换&#13;&#10;承办人\u201d按钮；原承办人是梁策，&#13;&#10;管理员重定位承办人办理到邢璐那&#13;&#10;里，邢璐发送到处长后，处长再次&#13;&#10;发送到承办人办理，还是邢璐，而&#13;&#10;不是梁策。但是局内传文不同，他&#13;&#10;是拟稿人，即使重定位，还是改变&#13;&#10;不了拟稿人。搞不懂","comment_person":"郭树忠","comment_date":"2015-08-03&#32;00:00:00.0"},{"row_guid":"{0A2FF324-FFFF-FFFF-F1ED-4C740000006B}","comment_guid":"{A9522312-FFFF-FFFF-9C1B-8B7D00000559}","comment_content":"梁策","comment_person":"郭树忠","comment_date":"2015-08-03&#32;00:00:00.0"}]
     * attachment : []
     * menus : [{"name":"起草结余资金","actionguid":"{5A292830-D942-4249-8D81-885F2AB9253D}","action_status":"1"},{"name":"送主管局长批示","actionguid":"{798BF9A3-B3EB-4E3C-86BF-C9C1523BE094}","action_status":"1"},{"name":"送主管处长","actionguid":"{2A90DF32-51C8-4A96-A945-EAF52D85D8C9}","action_status":"1"},{"name":"送承办人办理","actionguid":"{ED869C4A-D8BA-4308-8F9B-7B43534C583D}","action_status":"1"},{"name":"发起协办","actionguid":"{EDE7B5AD-DBFD-4A88-A84C-35C32555F850}","action_status":"1"},{"name":"更换承办人","actionguid":"{812873DD-DA31-49BF-A69E-1D5B1FECB868}","action_status":"1"},{"name":"送内勤归档","actionguid":"{F8D44264-BC0C-40F5-9198-DBE042AA32CE}","action_status":"1"},{"name":"送其他人传阅","actionguid":"{9C54ADEF-8A06-4DA6-92ED-64BE63E847CE}","action_status":"1"},{"name":"返回文件管理员","actionguid":"{7069E7A8-8550-44F4-A6C8-A0602C9BE6E0}","action_status":"1"},{"name":"起草一般发文","actionguid":"{113BEE29-AB96-4591-907E-02368DCA8F77}","action_status":"1"},{"name":"起草指标文","actionguid":"{89F72FA7-5C2A-4846-B0BD-172EB6725024}","action_status":"1"},{"name":"起草便函","actionguid":"{C57943E7-F02F-473C-B6BE-C5DE1878A7E9}","action_status":"1"}]
     * actors : []
     */



    private String bangongshilch;
    private String baoguanqixian;
    private String beizhu;
    private String chengbaoneirong;
    private String chengbaoren;
    private String chushilch;
    private String chuzhangyijian;
    private String cuibantianshu;
    private String czqianzi;
    private String dubanjilu;
    private String formguid;
    private String guidangriqi;
    private String hao;
    private String huanji;
    private String jianyaoqingkuang;
    private String juneiducha;
    private String juzhangpishi;
    private String laiwendanwei;
    private String lianxidianhua;
    private String lingdaopishi;
    private String lookydwurl;
    private String lururen;
    private String miji;
    private String mijiwen;
    private String nian;
    private String qitarenyijian;
    private String recievedate;
    private String riseword;
    private String shangjiducha;
    private String shifoufawen;
    private String subwokflow_fawen;
    private String subworkflow_state;
    private String subworkflow_zhibiao;
    private String wenjianmingcheng;
    private String workflowinstance_guid;
    private String workflow_main;
    private String workflow_sub;//会签 框
    private String xianbandate;
    private String xianbanriqi;
    private String xiebanchushi;
    private String yuandate;
    private String yuanwenzihao;
    private String yusuanchuyijian;
    private String yusuanchuzhangyijian;
    private String zhongdianduban;
    private String zhongdianducha;
    private String zhubanchushi;
    private String zhutobianhan;
    private String zhutofa;
    private String zhutojieyu;
    private String zhutozhi;
    private String biaoti;
    private String chushifenshu;
    private String chushi_zi;
    private String gongkaishuxing;
    private String jiaoduidianhua;
    private String nigao;
    private String nigaoriqi;
    private String nigaodanwei;
    private String nigaodianhua;
    private String xinxifabu;
    private String yibanfawen_chushi_hao;
    private String zi;
    private String xianbanshijian;
    private String zhusong;
    private String chaosong;
    private String qfbjczj;//签发
    private String huiqian;//会签
    private String chushihuiqian;//会签 框
    private String juwaihuiqian;//签发 框

    private String guifanwenjian;
    private String chengwenriqi;
    private String bangongshifenshu;
    private String jiaodui;
    private String jiaoduiriqi;
    private String yinzhi;
    private String yibanfawen_hao;

//    private String workflow_sub;

    private String ydwwenhao;

    public String getYdwwenhao() {
        return ydwwenhao;
    }

    public void setYdwwenhao(String ydwwenhao) {
        this.ydwwenhao = ydwwenhao;
    }

    public String getYibanfawen_hao() {
        return yibanfawen_hao;
    }

    public void setYibanfawen_hao(String yibanfawen_hao) {
        this.yibanfawen_hao = yibanfawen_hao;
    }

    public String getJiaodui() {
        return jiaodui;
    }

    public void setJiaodui(String jiaodui) {
        this.jiaodui = jiaodui;
    }

    public String getJiaoduiriqi() {
        return jiaoduiriqi;
    }

    public void setJiaoduiriqi(String jiaoduiriqi) {
        this.jiaoduiriqi = jiaoduiriqi;
    }

    public String getYinzhi() {
        return yinzhi;
    }

    public void setYinzhi(String yinzhi) {
        this.yinzhi = yinzhi;
    }

    public String getBangongshifenshu() {
        return bangongshifenshu;
    }

    public void setBangongshifenshu(String bangongshifenshu) {
        this.bangongshifenshu = bangongshifenshu;
    }

    public String getChengwenriqi() {
        return chengwenriqi;
    }

    public void setChengwenriqi(String chengwenriqi) {
        this.chengwenriqi = chengwenriqi;
    }

    public String getGuifanwenjian() {
        return guifanwenjian;
    }

    public void setGuifanwenjian(String guifanwenjian) {
        this.guifanwenjian = guifanwenjian;
    }

    public String getChushihuiqian() {
        return chushihuiqian;
    }

    public void setChushihuiqian(String chushihuiqian) {
        this.chushihuiqian = chushihuiqian;
    }

    public String getJuwaihuiqian() {
        return juwaihuiqian;
    }

    public void setJuwaihuiqian(String juwaihuiqian) {
        this.juwaihuiqian = juwaihuiqian;
    }

    public String getQfbjczj() {
        return qfbjczj;
    }

    public void setQfbjczj(String qfbjczj) {
        this.qfbjczj = qfbjczj;
    }

    public String getHuiqian() {
        return huiqian;
    }

    public void setHuiqian(String huiqian) {
        this.huiqian = huiqian;
    }

    public String getChaosong() {
        return chaosong;
    }

    public void setChaosong(String chaosong) {
        this.chaosong = chaosong;
    }

    public String getZhusong() {
        return zhusong;
    }

    public void setZhusong(String zhusong) {
        this.zhusong = zhusong;
    }

    public String getXianbanshijian() {
        return xianbanshijian;
    }

    public void setXianbanshijian(String xianbanshijian) {
        this.xianbanshijian = xianbanshijian;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public String getYibanfawen_chushi_hao() {
        return yibanfawen_chushi_hao;
    }

    public void setYibanfawen_chushi_hao(String yibanfawen_chushi_hao) {
        this.yibanfawen_chushi_hao = yibanfawen_chushi_hao;
    }

    public String getXinxifabu() {
        return xinxifabu;
    }

    public void setXinxifabu(String xinxifabu) {
        this.xinxifabu = xinxifabu;
    }

    public String getNigaodianhua() {
        return nigaodianhua;
    }

    public void setNigaodianhua(String nigaodianhua) {
        this.nigaodianhua = nigaodianhua;
    }

    public String getNigaodanwei() {
        return nigaodanwei;
    }

    public void setNigaodanwei(String nigaodanwei) {
        this.nigaodanwei = nigaodanwei;
    }

    public String getNigaoriqi() {
        return nigaoriqi;
    }

    public void setNigaoriqi(String nigaoriqi) {
        this.nigaoriqi = nigaoriqi;
    }

    public String getNigao() {
        return nigao;
    }

    public void setNigao(String nigao) {
        this.nigao = nigao;
    }

    public String getJiaoduidianhua() {
        return jiaoduidianhua;
    }

    public void setJiaoduidianhua(String jiaoduidianhua) {
        this.jiaoduidianhua = jiaoduidianhua;
    }

    public String getGongkaishuxing() {
        return gongkaishuxing;
    }

    public void setGongkaishuxing(String gongkaishuxing) {
        this.gongkaishuxing = gongkaishuxing;
    }

    public String getChushi_zi() {
        return chushi_zi;
    }

    public void setChushi_zi(String chushi_zi) {
        this.chushi_zi = chushi_zi;
    }

    public String getChushifenshu() {
        return chushifenshu;
    }

    public void setChushifenshu(String chushifenshu) {
        this.chushifenshu = chushifenshu;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getBangongshilch() {
        return bangongshilch;
    }

    public void setBangongshilch(String bangongshilch) {
        this.bangongshilch = bangongshilch;
    }

    public String getBaoguanqixian() {
        return baoguanqixian;
    }

    public void setBaoguanqixian(String baoguanqixian) {
        this.baoguanqixian = baoguanqixian;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getChengbaoneirong() {
        return chengbaoneirong;
    }

    public void setChengbaoneirong(String chengbaoneirong) {
        this.chengbaoneirong = chengbaoneirong;
    }

    public String getChengbaoren() {
        return chengbaoren;
    }

    public void setChengbaoren(String chengbaoren) {
        this.chengbaoren = chengbaoren;
    }

    public String getChushilch() {
        return chushilch;
    }

    public void setChushilch(String chushilch) {
        this.chushilch = chushilch;
    }

    public String getChuzhangyijian() {
        return chuzhangyijian;
    }

    public void setChuzhangyijian(String chuzhangyijian) {
        this.chuzhangyijian = chuzhangyijian;
    }

    public String getCuibantianshu() {
        return cuibantianshu;
    }

    public void setCuibantianshu(String cuibantianshu) {
        this.cuibantianshu = cuibantianshu;
    }

    public String getCzqianzi() {
        return czqianzi;
    }

    public void setCzqianzi(String czqianzi) {
        this.czqianzi = czqianzi;
    }

    public String getDubanjilu() {
        return dubanjilu;
    }

    public void setDubanjilu(String dubanjilu) {
        this.dubanjilu = dubanjilu;
    }

    public String getFormguid() {
        return formguid;
    }

    public void setFormguid(String formguid) {
        this.formguid = formguid;
    }

    public String getGuidangriqi() {
        return guidangriqi;
    }

    public void setGuidangriqi(String guidangriqi) {
        this.guidangriqi = guidangriqi;
    }

    public String getHao() {
        return hao;
    }

    public void setHao(String hao) {
        this.hao = hao;
    }

    public String getHuanji() {
        return huanji;
    }

    public void setHuanji(String huanji) {
        this.huanji = huanji;
    }

    public String getJianyaoqingkuang() {
        return jianyaoqingkuang;
    }

    public void setJianyaoqingkuang(String jianyaoqingkuang) {
        this.jianyaoqingkuang = jianyaoqingkuang;
    }

    public String getJuneiducha() {
        return juneiducha;
    }

    public void setJuneiducha(String juneiducha) {
        this.juneiducha = juneiducha;
    }

    public String getJuzhangpishi() {
        return juzhangpishi;
    }

    public void setJuzhangpishi(String juzhangpishi) {
        this.juzhangpishi = juzhangpishi;
    }

    public String getLaiwendanwei() {
        return laiwendanwei;
    }

    public void setLaiwendanwei(String laiwendanwei) {
        this.laiwendanwei = laiwendanwei;
    }

    public String getLianxidianhua() {
        return lianxidianhua;
    }

    public void setLianxidianhua(String lianxidianhua) {
        this.lianxidianhua = lianxidianhua;
    }

    public String getLingdaopishi() {
        return lingdaopishi;
    }

    public void setLingdaopishi(String lingdaopishi) {
        this.lingdaopishi = lingdaopishi;
    }

    public String getLookydwurl() {
        return lookydwurl;
    }

    public void setLookydwurl(String lookydwurl) {
        this.lookydwurl = lookydwurl;
    }

    public String getLururen() {
        return lururen;
    }

    public void setLururen(String lururen) {
        this.lururen = lururen;
    }

    public String getMiji() {
        return miji;
    }

    public void setMiji(String miji) {
        this.miji = miji;
    }

    public String getMijiwen() {
        return mijiwen;
    }

    public void setMijiwen(String mijiwen) {
        this.mijiwen = mijiwen;
    }

    public String getNian() {
        return nian;
    }

    public void setNian(String nian) {
        this.nian = nian;
    }

    public String getQitarenyijian() {
        return qitarenyijian;
    }

    public void setQitarenyijian(String qitarenyijian) {
        this.qitarenyijian = qitarenyijian;
    }

    public String getRecievedate() {
        return recievedate;
    }

    public void setRecievedate(String recievedate) {
        this.recievedate = recievedate;
    }

    public String getRiseword() {
        return riseword;
    }

    public void setRiseword(String riseword) {
        this.riseword = riseword;
    }

    public String getShangjiducha() {
        return shangjiducha;
    }

    public void setShangjiducha(String shangjiducha) {
        this.shangjiducha = shangjiducha;
    }

    public String getShifoufawen() {
        return shifoufawen;
    }

    public void setShifoufawen(String shifoufawen) {
        this.shifoufawen = shifoufawen;
    }

    public String getSubwokflow_fawen() {
        return subwokflow_fawen;
    }

    public void setSubwokflow_fawen(String subwokflow_fawen) {
        this.subwokflow_fawen = subwokflow_fawen;
    }

    public String getSubworkflow_state() {
        return subworkflow_state;
    }

    public void setSubworkflow_state(String subworkflow_state) {
        this.subworkflow_state = subworkflow_state;
    }

    public String getSubworkflow_zhibiao() {
        return subworkflow_zhibiao;
    }

    public void setSubworkflow_zhibiao(String subworkflow_zhibiao) {
        this.subworkflow_zhibiao = subworkflow_zhibiao;
    }

    public String getWenjianmingcheng() {
        return wenjianmingcheng;
    }

    public void setWenjianmingcheng(String wenjianmingcheng) {
        this.wenjianmingcheng = wenjianmingcheng;
    }

    public String getWorkflowinstance_guid() {
        return workflowinstance_guid;
    }

    public void setWorkflowinstance_guid(String workflowinstance_guid) {
        this.workflowinstance_guid = workflowinstance_guid;
    }

    public String getWorkflow_main() {
        return workflow_main;
    }

    public void setWorkflow_main(String workflow_main) {
        this.workflow_main = workflow_main;
    }

    public String getWorkflow_sub() {
        return workflow_sub;
    }

    public void setWorkflow_sub(String workflow_sub) {
        this.workflow_sub = workflow_sub;
    }

    public String getXianbandate() {
        return xianbandate;
    }

    public void setXianbandate(String xianbandate) {
        this.xianbandate = xianbandate;
    }

    public String getXianbanriqi() {
        return xianbanriqi;
    }

    public void setXianbanriqi(String xianbanriqi) {
        this.xianbanriqi = xianbanriqi;
    }

    public String getXiebanchushi() {
        return xiebanchushi;
    }

    public void setXiebanchushi(String xiebanchushi) {
        this.xiebanchushi = xiebanchushi;
    }

    public String getYuandate() {
        return yuandate;
    }

    public void setYuandate(String yuandate) {
        this.yuandate = yuandate;
    }

    public String getYuanwenzihao() {
        return yuanwenzihao;
    }

    public void setYuanwenzihao(String yuanwenzihao) {
        this.yuanwenzihao = yuanwenzihao;
    }

    public String getYusuanchuyijian() {
        return yusuanchuyijian;
    }

    public void setYusuanchuyijian(String yusuanchuyijian) {
        this.yusuanchuyijian = yusuanchuyijian;
    }

    public String getYusuanchuzhangyijian() {
        return yusuanchuzhangyijian;
    }

    public void setYusuanchuzhangyijian(String yusuanchuzhangyijian) {
        this.yusuanchuzhangyijian = yusuanchuzhangyijian;
    }

    public String getZhongdianduban() {
        return zhongdianduban;
    }

    public void setZhongdianduban(String zhongdianduban) {
        this.zhongdianduban = zhongdianduban;
    }

    public String getZhongdianducha() {
        return zhongdianducha;
    }

    public void setZhongdianducha(String zhongdianducha) {
        this.zhongdianducha = zhongdianducha;
    }

    public String getZhubanchushi() {
        return zhubanchushi;
    }

    public void setZhubanchushi(String zhubanchushi) {
        this.zhubanchushi = zhubanchushi;
    }

    public String getZhutobianhan() {
        return zhutobianhan;
    }

    public void setZhutobianhan(String zhutobianhan) {
        this.zhutobianhan = zhutobianhan;
    }

    public String getZhutofa() {
        return zhutofa;
    }

    public void setZhutofa(String zhutofa) {
        this.zhutofa = zhutofa;
    }

    public String getZhutojieyu() {
        return zhutojieyu;
    }

    public void setZhutojieyu(String zhutojieyu) {
        this.zhutojieyu = zhutojieyu;
    }

    public String getZhutozhi() {
        return zhutozhi;
    }

    public void setZhutozhi(String zhutozhi) {
        this.zhutozhi = zhutozhi;
    }


//    public static class CurrentCommentBean {
//        /**
//         * action_name : 处长批示
//         * action_guid : {B0832517-8927-417A-9948-266082698AC9}
//         * comment_guid : {A9522312-FFFF-FFFF-9C1B-8B7D00000559}
//         */
//
//        private String action_name;
//        private String action_guid;
//        private String comment_guid;
//
//        public String getAction_name() {
//            return action_name;
//        }
//
//        public void setAction_name(String action_name) {
//            this.action_name = action_name;
//        }
//
//        public String getAction_guid() {
//            return action_guid;
//        }
//
//        public void setAction_guid(String action_guid) {
//            this.action_guid = action_guid;
//        }
//
//        public String getComment_guid() {
//            return comment_guid;
//        }
//
//        public void setComment_guid(String comment_guid) {
//            this.comment_guid = comment_guid;
//        }
//    }
//
//    public static class CommentBean {
//        /**
//         * row_guid : {0A2FF324-FFFF-FFFF-F1F1-166CFFFFFF9B}
//         * comment_guid : {A9522312-FFFF-FFFF-9C1B-8B7D00000559}
//         * comment_content : 管理员重定位到邢璐，
//         * comment_person : 郭树忠
//         * comment_date : 2015-08-03&#32;00:00:00.0
//         */
//
//        private String row_guid;
//        private String comment_guid;
//        private String comment_content;
//        private String comment_person;
//        private String comment_date;
//
//        public String getRow_guid() {
//            return row_guid;
//        }
//
//        public void setRow_guid(String row_guid) {
//            this.row_guid = row_guid;
//        }
//
//        public String getComment_guid() {
//            return comment_guid;
//        }
//
//        public void setComment_guid(String comment_guid) {
//            this.comment_guid = comment_guid;
//        }
//
//        public String getComment_content() {
//            return comment_content;
//        }
//
//        public void setComment_content(String comment_content) {
//            this.comment_content = comment_content;
//        }
//
//        public String getComment_person() {
//            return comment_person;
//        }
//
//        public void setComment_person(String comment_person) {
//            this.comment_person = comment_person;
//        }
//
//        public String getComment_date() {
//            return comment_date;
//        }
//
//        public void setComment_date(String comment_date) {
//            this.comment_date = comment_date;
//        }
//    }

}
