package com.ysbd.beijing.ui.bean.form;

import com.ysbd.beijing.bean.ActorsBean;
import com.ysbd.beijing.bean.DocumentBean;
import com.ysbd.beijing.ui.bean.AttachmentBean;
import com.ysbd.beijing.ui.bean.CommentBean;
import com.ysbd.beijing.ui.bean.CurrentCommentBean;
import com.ysbd.beijing.ui.bean.MenuBean;

import java.util.List;

/**
 * Created by lcjing on 2018/8/10.
 */

public class ZhuBanwenBean {

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
     * shifoufawen :
     * subwokflow_fawen :
     * subworkflow_state :
     * subworkflow_zhibiao :
     * wenjianmingcheng : 测试处长更换承办人
     * workflowinstance_guid : {0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}
     * workflow_main :
     * workflow_sub :
     * xianbandate : 2015-01-23&#32;00:00:00.0
     * xianbanriqi :
     * xiebanchushi :
     * yuandate :
     * yuanwenzihao :
     * yusuanchuyijian :
     * yusuanchuzhangyijian :
     * zhongdianduban : 否
     * zhubanchushi : 信息处（信息中心）
     * zhutobianhan :
     * zhutofa :
     * zhutojieyu :
     * zhutozhi :
     * comment : [{"row_guid":"{0A2FF324-FFFF-FFFF-F1F1-166CFFFFFF9B}","comment_guid":"{A9522312-FFFF-FFFF-9C1B-8B7D00000559}","comment_content":"管理员重定位到邢璐，","comment_person":"郭树忠","comment_date":"2015-08-03&#32;00:00:00.0"},{"row_guid":"{0A2FF324-FFFF-FFFF-F1F4-A6DBFFFFFFB4}","comment_guid":"{A9522312-FFFF-FFFF-9C1B-8B7D00000559}","comment_content":"主办文，在处长待办下都有\u201c更换&#13;&#10;承办人\u201d按钮；原承办人是梁策，&#13;&#10;管理员重定位承办人办理到邢璐那&#13;&#10;里，邢璐发送到处长后，处长再次&#13;&#10;发送到承办人办理，还是邢璐，而&#13;&#10;不是梁策。但是局内传文不同，他&#13;&#10;是拟稿人，即使重定位，还是改变&#13;&#10;不了拟稿人。搞不懂","comment_person":"郭树忠","comment_date":"2015-08-03&#32;00:00:00.0"},{"row_guid":"{0A2FF324-FFFF-FFFF-F1ED-4C740000006B}","comment_guid":"{A9522312-FFFF-FFFF-9C1B-8B7D00000559}","comment_content":"梁策","comment_person":"郭树忠","comment_date":"2015-08-03&#32;00:00:00.0"}]
     */

    private String bangongshilch;//办公室历程？
    private String baoguanqixian;//保管期限
    private String beizhu;//备注
    private String chengbaoneirong;//呈报内容
    private String chengbaoren;//呈报人
    private String chushilch;//处室历程
    private String chuzhangyijian;//处长意见
    private String cuibantianshu;//催办天数
    private String czqianzi;//
    private String dubanjilu;//督办记录
    private String formguid;//
    private String guidangriqi;//归档日期
    private String hao;//号
    private String huanji;//缓急
    private String jianyaoqingkuang;//简要情况
    private String juzhangpishi;//局长批示
    private String laiwendanwei;//来文单位
    private String lianxidianhua;//联系电话
    private String lingdaopishi;//领导批示
    private String lookydwurl;//
    private String lururen;//录入人
    private String miji;//密级
    private String mijiwen;//
    private String nian;//年
    private String qitarenyijian;//其他人意见
    private String recievedate;//收文日期
    private String riseword;//
    private String shifoufawen;//是否发文
    private String subwokflow_fawen;//
    private String subworkflow_state;//
    private String subworkflow_zhibiao;//
    private String wenjianmingcheng;//文件名称
    private String workflowinstance_guid;//
    private String workflow_main;//
    private String workflow_sub;
    private String xianbandate;//限办日期
    private String xianbanriqi;//限办日期
    private String xiebanchushi;//协办处室
    private String yuandate;//原文日期
    private String yuanwenzihao;//原文字号
    private String yusuanchuyijian;//预算处意见
    private String yusuanchuzhangyijian;//预算处长意见
    private String zhongdianduban;//重点督办
    private String zhubanchushi;//主办处室
    private String zhutobianhan;//
    private String zhongdianducha;//重点督查
    private String zhutofa;//
    private String zhutojieyu;//
    private String zhutozhi;//
    private List<CurrentCommentBean> currentComment;
    private List<CommentBean> comment;
    private List<MenuBean> menus;
    private List<AttachmentBean> attachment;
    private DocumentBean documentcb;//呈报内容
    private DocumentBean document;//正文
    private List<ActorsBean> actors;

    private String guidangren;


    public List<ActorsBean> getActors() {
        return actors;
    }

    public void setActors(List<ActorsBean> actors) {
        this.actors = actors;
    }

    public DocumentBean getDocumentcb() {
        return documentcb;
    }

    public void setDocumentcb(DocumentBean documentcb) {
        this.documentcb = documentcb;
    }

    public DocumentBean getDocument() {
        return document;
    }

    public void setDocument(DocumentBean document) {
        this.document = document;
    }

    public List<AttachmentBean> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentBean> attachment) {
        this.attachment = attachment;
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

    public String getZhongdianducha() {
        return zhongdianducha;
    }

    public void setZhongdianducha(String zhongdianducha) {
        this.zhongdianducha = zhongdianducha;
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

    public List<CurrentCommentBean> getCurrentComment() {
        return currentComment;
    }

    public void setCurrentComment(List<CurrentCommentBean> currentComment) {
        this.currentComment = currentComment;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
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
//
//
//
//    }

    public List<MenuBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuBean> menus) {
        this.menus = menus;
    }

}
