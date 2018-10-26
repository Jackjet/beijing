package com.ysbd.beijing.ui.bean;

/**
 * Created by lcjing on 2018/8/10.
 */

public class TodoBean {

    /**
     * TODO_GUID : {0A2FCA0A-FFFF-FFFF-9665-F8C800001230}
     * TODO_SUBMITDATE : 2008-03-10 00:00:00.0
     * TODO_UNIQUEID : 31262
     * TODO_RECIEVEPERSONGUID : {BFA7820D-FFFF-FFFF-F16B-BA17000006C3}
     * TODO_SENDPERSONNAME : 吴路岩
     * TODO_TARGETURL : http://10.48.202.10:7000/pepres/monthchk/sectionToAuditAction.do?loginame=吴素芳
     * TODO_APPNAME : monthchk
     * TODO_MODULENAME : 月度考核
     * TODO_TITLE : 侯雪梅上报2008年2月月度考核, 主管领导吴路岩已审核!
     * TODO_CONTENT : 侯雪梅上报2008年2月月度考核, 主管领导吴路岩已审核!
     * TODO_PRIORITY : -1
     * TODO_RECIEVEPERSONNAME : 张博
     * TODO_SENDPERSONGUID : {BFA7820D-FFFF-FFFF-F16B-B89A000005EF}
     * TODO_SENDPERSONDEPT : 北京市财政局,采购
     * TODO_DATE : 1969-12-31 18:00:00.0
     * 【主办文】关于修改印发2018年度一般企业财务报表格式的通知（打字室：王燕）08-07
     */

    private String TODO_GUID;
    private String TODO_SUBMITDATE;
    private String TODO_UNIQUEID;
    private String TODO_RECIEVEPERSONGUID;
    private String TODO_SENDPERSONNAME;
    private String TODO_TARGETURL;
    private String TODO_APPNAME;
    private String TODO_MODULENAME;
    private String TODO_TITLE;
    private String TODO_CONTENT;
    private String TODO_PRIORITY;
    private String TODO_RECIEVEPERSONNAME;
    private String TODO_SENDPERSONGUID;
    private String TODO_SENDPERSONDEPT;
    private String TODO_DATE;
    private String RN;
    //主办文
    private String WENJIANMINGCHENG;
    private String LAIWENDANWEI;
    private String ZHUBANCHUSHI;
    private String HAO;
    private String RECIEVEDATE;
    private String XIANBANDATE;
    private String DQNAME;
    private String WORKFLOWINSTANCE_GUID;
    private String YUANWENZIHAO;
    private String XIANBANRIQI;
    private String LURUREN;
    //局内传文
    private String XIANBANSHIJIAN;
    private String GUIDANGREN;
    //    private String ZHUBANCHUSHI;
    private String SHOUWENRIQI;
    //一般发文
    private String BIAOTI;
    private String NIGAODANWEI;
    private String NIGAO;
    private String ZI;
    private String NIAN;
    private String CHUSHI_ZI;
    private String YIBANFAWEN_CHUSHI_HAO;
    private String YIBANFAWEN_HAO;
    //    private String XIANBANSHIJIAN;
    private String NIGAORIQI;
//    private String BIAOTI;

    //指标文
    private String TIQIANXIADA;
    private String HQZBW_CHUSHI_HAO;
    private String YDWWENHAO;

    private String status;
    private String look;

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLURUREN() {
        return LURUREN;
    }

    public void setLURUREN(String LURUREN) {
        this.LURUREN = LURUREN;
    }

    public String getYDWWENHAO() {
        return YDWWENHAO;
    }

    public void setYDWWENHAO(String YDWWENHAO) {
        this.YDWWENHAO = YDWWENHAO;
    }

    public String getHQZBW_CHUSHI_HAO() {
        return HQZBW_CHUSHI_HAO;
    }

    public void setHQZBW_CHUSHI_HAO(String HQZBW_CHUSHI_HAO) {
        this.HQZBW_CHUSHI_HAO = HQZBW_CHUSHI_HAO;
    }

    public String getYIBANFAWEN_HAO() {
        return YIBANFAWEN_HAO;
    }

    public void setYIBANFAWEN_HAO(String YIBANFAWEN_HAO) {
        this.YIBANFAWEN_HAO = YIBANFAWEN_HAO;
    }

    public String getGUIDANGREN() {
        return GUIDANGREN;
    }

    public void setGUIDANGREN(String GUIDANGREN) {
        this.GUIDANGREN = GUIDANGREN;
    }

    public String getXIANBANRIQI() {
        return XIANBANRIQI;
    }

    public void setXIANBANRIQI(String XIANBANRIQI) {
        this.XIANBANRIQI = XIANBANRIQI;
    }

    public String getRN() {
        return RN;
    }

    public void setRN(String RN) {
        this.RN = RN;
    }

    public String getYUANWENZIHAO() {
        return YUANWENZIHAO;
    }

    public void setYUANWENZIHAO(String YUANWENZIHAO) {
        this.YUANWENZIHAO = YUANWENZIHAO;
    }

    public String getTIQIANXIADA() {
        return TIQIANXIADA;
    }

    public void setTIQIANXIADA(String TIQIANXIADA) {
        this.TIQIANXIADA = TIQIANXIADA;
    }

    public String getYIBANFAWEN_CHUSHI_HAO() {
        return YIBANFAWEN_CHUSHI_HAO;
    }

    public void setYIBANFAWEN_CHUSHI_HAO(String YIBANFAWEN_CHUSHI_HAO) {
        this.YIBANFAWEN_CHUSHI_HAO = YIBANFAWEN_CHUSHI_HAO;
    }

    public String getCHUSHI_ZI() {
        return CHUSHI_ZI;
    }

    public void setCHUSHI_ZI(String CHUSHI_ZI) {
        this.CHUSHI_ZI = CHUSHI_ZI;
    }

    public String getNIGAODANWEI() {
        return NIGAODANWEI;
    }

    public void setNIGAODANWEI(String NIGAODANWEI) {
        this.NIGAODANWEI = NIGAODANWEI;
    }

    public String getNIGAO() {
        return NIGAO;
    }

    public void setNIGAO(String NIGAO) {
        this.NIGAO = NIGAO;
    }

    public String getZI() {
        return ZI;
    }

    public void setZI(String ZI) {
        this.ZI = ZI;
    }

    public String getNIAN() {
        return NIAN;
    }

    public void setNIAN(String NIAN) {
        this.NIAN = NIAN;
    }

    public String getNIGAORIQI() {
        return NIGAORIQI;
    }

    public void setNIGAORIQI(String NIGAORIQI) {
        this.NIGAORIQI = NIGAORIQI;
    }

    public String getWORKFLOWINSTANCE_GUID() {
        return WORKFLOWINSTANCE_GUID;
    }

    public void setWORKFLOWINSTANCE_GUID(String WORKFLOWINSTANCE_GUID) {
        this.WORKFLOWINSTANCE_GUID = WORKFLOWINSTANCE_GUID;
    }

    public String getBIAOTI() {
        return BIAOTI;
    }

    public void setBIAOTI(String BIAOTI) {
        this.BIAOTI = BIAOTI;
    }

    public String getXIANBANSHIJIAN() {
        return XIANBANSHIJIAN;
    }

    public void setXIANBANSHIJIAN(String XIANBANSHIJIAN) {
        this.XIANBANSHIJIAN = XIANBANSHIJIAN;
    }

    public String getSHOUWENRIQI() {
        return SHOUWENRIQI;
    }

    public void setSHOUWENRIQI(String SHOUWENRIQI) {
        this.SHOUWENRIQI = SHOUWENRIQI;
    }

    public String getHAO() {
        return HAO;
    }

    public void setHAO(String HAO) {
        this.HAO = HAO;
    }

    public String getRECIEVEDATE() {
        return RECIEVEDATE;
    }

    public void setRECIEVEDATE(String RECIEVEDATE) {
        this.RECIEVEDATE = RECIEVEDATE;
    }

    public String getXIANBANDATE() {
        return XIANBANDATE;
    }

    public void setXIANBANDATE(String XIANBANDATE) {
        this.XIANBANDATE = XIANBANDATE;
    }

    public String getDQNAME() {
        return DQNAME;
    }

    public void setDQNAME(String DQNAME) {
        this.DQNAME = DQNAME;
    }

    public String getZHUBANCHUSHI() {
        return ZHUBANCHUSHI;
    }

    public void setZHUBANCHUSHI(String ZHUBANCHUSHI) {
        this.ZHUBANCHUSHI = ZHUBANCHUSHI;
    }

    public String getLAIWENDANWEI() {
        return LAIWENDANWEI;
    }

    public void setLAIWENDANWEI(String LAIWENDANWEI) {
        this.LAIWENDANWEI = LAIWENDANWEI;
    }

    public String getWENJIANMINGCHENG() {
        return WENJIANMINGCHENG;
    }

    public void setWENJIANMINGCHENG(String WENJIANMINGCHENG) {
        this.WENJIANMINGCHENG = WENJIANMINGCHENG;
    }

    public String getTODO_GUID() {
        return TODO_GUID;
    }

    public void setTODO_GUID(String TODO_GUID) {
        this.TODO_GUID = TODO_GUID;
    }

    public String getTODO_SUBMITDATE() {
        return TODO_SUBMITDATE;
    }

    public void setTODO_SUBMITDATE(String TODO_SUBMITDATE) {
        this.TODO_SUBMITDATE = TODO_SUBMITDATE;
    }

    public String getTODO_UNIQUEID() {
        return TODO_UNIQUEID;
    }

    public void setTODO_UNIQUEID(String TODO_UNIQUEID) {
        this.TODO_UNIQUEID = TODO_UNIQUEID;
    }

    public String getTODO_RECIEVEPERSONGUID() {
        return TODO_RECIEVEPERSONGUID;
    }

    public void setTODO_RECIEVEPERSONGUID(String TODO_RECIEVEPERSONGUID) {
        this.TODO_RECIEVEPERSONGUID = TODO_RECIEVEPERSONGUID;
    }

    public String getTODO_SENDPERSONNAME() {
        return TODO_SENDPERSONNAME;
    }

    public void setTODO_SENDPERSONNAME(String TODO_SENDPERSONNAME) {
        this.TODO_SENDPERSONNAME = TODO_SENDPERSONNAME;
    }

    public String getTODO_TARGETURL() {
        return TODO_TARGETURL;
    }

    public void setTODO_TARGETURL(String TODO_TARGETURL) {
        this.TODO_TARGETURL = TODO_TARGETURL;
    }

    public String getTODO_APPNAME() {
        return TODO_APPNAME;
    }

    public void setTODO_APPNAME(String TODO_APPNAME) {
        this.TODO_APPNAME = TODO_APPNAME;
    }

    public String getTODO_MODULENAME() {
        return TODO_MODULENAME;
    }

    public void setTODO_MODULENAME(String TODO_MODULENAME) {
        this.TODO_MODULENAME = TODO_MODULENAME;
    }

    public String getTODO_TITLE() {
        return TODO_TITLE;
    }

    public void setTODO_TITLE(String TODO_TITLE) {
        this.TODO_TITLE = TODO_TITLE;
    }

    public String getTODO_CONTENT() {
        return TODO_CONTENT;
    }

    public void setTODO_CONTENT(String TODO_CONTENT) {
        this.TODO_CONTENT = TODO_CONTENT;
    }

    public String getTODO_PRIORITY() {
        return TODO_PRIORITY;
    }

    public void setTODO_PRIORITY(String TODO_PRIORITY) {
        this.TODO_PRIORITY = TODO_PRIORITY;
    }

    public String getTODO_RECIEVEPERSONNAME() {
        return TODO_RECIEVEPERSONNAME;
    }

    public void setTODO_RECIEVEPERSONNAME(String TODO_RECIEVEPERSONNAME) {
        this.TODO_RECIEVEPERSONNAME = TODO_RECIEVEPERSONNAME;
    }

    public String getTODO_SENDPERSONGUID() {
        return TODO_SENDPERSONGUID;
    }

    public void setTODO_SENDPERSONGUID(String TODO_SENDPERSONGUID) {
        this.TODO_SENDPERSONGUID = TODO_SENDPERSONGUID;
    }

    public String getTODO_SENDPERSONDEPT() {
        return TODO_SENDPERSONDEPT;
    }

    public void setTODO_SENDPERSONDEPT(String TODO_SENDPERSONDEPT) {
        this.TODO_SENDPERSONDEPT = TODO_SENDPERSONDEPT;
    }

    public String getTODO_DATE() {
        return TODO_DATE;
    }

    public void setTODO_DATE(String TODO_DATE) {
        this.TODO_DATE = TODO_DATE;
    }

    @Override
    public String toString() {
        return "TodoBean{" +
                "TODO_GUID='" + TODO_GUID + '\'' +
                ", TODO_SUBMITDATE='" + TODO_SUBMITDATE + '\'' +
                ", TODO_UNIQUEID='" + TODO_UNIQUEID + '\'' +
                ", TODO_RECIEVEPERSONGUID='" + TODO_RECIEVEPERSONGUID + '\'' +
                ", TODO_SENDPERSONNAME='" + TODO_SENDPERSONNAME + '\'' +
                ", TODO_TARGETURL='" + TODO_TARGETURL + '\'' +
                ", TODO_APPNAME='" + TODO_APPNAME + '\'' +
                ", TODO_MODULENAME='" + TODO_MODULENAME + '\'' +
                ", TODO_TITLE='" + TODO_TITLE + '\'' +
                ", TODO_CONTENT='" + TODO_CONTENT + '\'' +
                ", TODO_PRIORITY='" + TODO_PRIORITY + '\'' +
                ", TODO_RECIEVEPERSONNAME='" + TODO_RECIEVEPERSONNAME + '\'' +
                ", TODO_SENDPERSONGUID='" + TODO_SENDPERSONGUID + '\'' +
                ", TODO_SENDPERSONDEPT='" + TODO_SENDPERSONDEPT + '\'' +
                ", TODO_DATE='" + TODO_DATE + '\'' +
                ", RN='" + RN + '\'' +
                ", WENJIANMINGCHENG='" + WENJIANMINGCHENG + '\'' +
                ", LAIWENDANWEI='" + LAIWENDANWEI + '\'' +
                ", ZHUBANCHUSHI='" + ZHUBANCHUSHI + '\'' +
                ", HAO='" + HAO + '\'' +
                ", RECIEVEDATE='" + RECIEVEDATE + '\'' +
                ", XIANBANDATE='" + XIANBANDATE + '\'' +
                ", DQNAME='" + DQNAME + '\'' +
                ", WORKFLOWINSTANCE_GUID='" + WORKFLOWINSTANCE_GUID + '\'' +
                ", YUANWENZIHAO='" + YUANWENZIHAO + '\'' +
                ", XIANBANRIQI='" + XIANBANRIQI + '\'' +
                ", LURUREN='" + LURUREN + '\'' +
                ", XIANBANSHIJIAN='" + XIANBANSHIJIAN + '\'' +
                ", GUIDANGREN='" + GUIDANGREN + '\'' +
                ", SHOUWENRIQI='" + SHOUWENRIQI + '\'' +
                ", BIAOTI='" + BIAOTI + '\'' +
                ", NIGAODANWEI='" + NIGAODANWEI + '\'' +
                ", NIGAO='" + NIGAO + '\'' +
                ", ZI='" + ZI + '\'' +
                ", NIAN='" + NIAN + '\'' +
                ", CHUSHI_ZI='" + CHUSHI_ZI + '\'' +
                ", YIBANFAWEN_CHUSHI_HAO='" + YIBANFAWEN_CHUSHI_HAO + '\'' +
                ", YIBANFAWEN_HAO='" + YIBANFAWEN_HAO + '\'' +
                ", NIGAORIQI='" + NIGAORIQI + '\'' +
                ", TIQIANXIADA='" + TIQIANXIADA + '\'' +
                ", HQZBW_CHUSHI_HAO='" + HQZBW_CHUSHI_HAO + '\'' +
                ", YDWWENHAO='" + YDWWENHAO + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
