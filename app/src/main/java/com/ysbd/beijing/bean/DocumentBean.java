package com.ysbd.beijing.bean;

/**
 * Created by lcjing on 2018/8/31.
 */
//正文
public class DocumentBean {

    /**
     * fileguid : {BFA7DE01-FFFF-FFFF-8E06-086400000014}
     * name : cb正文
     * url : http://218.60.41.112:9998/risenetoabjcz/riseoffice/MobileDoccbServlet?instanceGuid={BFA7DE01-FFFF-FFFF-8E03-FB130000000D}&documentGUid={BFA7DE01-FFFF-FFFF-8E06-086400000014}
     * type : doc
     */

    private String fileguid;
    private String name;
    private String url;

    /**
     * WORKFLOW_GUID : {A9522312-FFFF-FFFF-9529-B92C00000001}
     * DOCUMENTROW_GUID : {BFA7DE01-FFFF-FFFF-8E06-086400000014}
     * STEP : 2
     * DOCID : chengbaoneirongjncwnewBFA7820DFFFFFFFFF16C479000000025
     * TEMPLATE_NAME : chengbaoneirongjncwnew
     * TEMPLATE_GUID : {0A2FCA25-FFFF-FFFF-8FF1-6949FFFF9C5A}
     * WORKFLOWINSTANCE_GUID : {BFA7DE01-FFFF-FFFF-8E03-FB130000000D}
     * UPLOADPERSON_GUID : {BFA7820D-FFFF-FFFF-F16B-BA46000006FA}
     * UPLOADDATE : 2018-08-31 07:59:04.0
     * DOCUMENTTITLE : {BFA7DE01-FFFF-FFFF-8E06-086400000014}
     * DOCUMENTFILENAME : {0A2FCA25-FFFF-FFFF-8FF1-6949FFFF9C5A}.doc
     */

    private String WORKFLOW_GUID;
    private String DOCUMENTROW_GUID;
    private String STEP;
    private String DOCID;
    private String TEMPLATE_NAME;
    private String TEMPLATE_GUID;
    private String WORKFLOWINSTANCE_GUID;
    private String UPLOADPERSON_GUID;
    private String UPLOADDATE;
    private String DOCUMENTTITLE;
    private String DOCUMENTFILENAME;


    public String getFileguid() {
        return fileguid;
    }

    public void setFileguid(String fileguid) {
        this.fileguid = fileguid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getWORKFLOW_GUID() {
        return WORKFLOW_GUID;
    }

    public void setWORKFLOW_GUID(String WORKFLOW_GUID) {
        this.WORKFLOW_GUID = WORKFLOW_GUID;
    }

    public String getDOCUMENTROW_GUID() {
        return DOCUMENTROW_GUID;
    }

    public void setDOCUMENTROW_GUID(String DOCUMENTROW_GUID) {
        this.DOCUMENTROW_GUID = DOCUMENTROW_GUID;
    }

    public String getSTEP() {
        return STEP;
    }

    public void setSTEP(String STEP) {
        this.STEP = STEP;
    }

    public String getDOCID() {
        return DOCID;
    }

    public void setDOCID(String DOCID) {
        this.DOCID = DOCID;
    }

    public String getTEMPLATE_NAME() {
        return TEMPLATE_NAME;
    }

    public void setTEMPLATE_NAME(String TEMPLATE_NAME) {
        this.TEMPLATE_NAME = TEMPLATE_NAME;
    }

    public String getTEMPLATE_GUID() {
        return TEMPLATE_GUID;
    }

    public void setTEMPLATE_GUID(String TEMPLATE_GUID) {
        this.TEMPLATE_GUID = TEMPLATE_GUID;
    }

    public String getWORKFLOWINSTANCE_GUID() {
        return WORKFLOWINSTANCE_GUID;
    }

    public void setWORKFLOWINSTANCE_GUID(String WORKFLOWINSTANCE_GUID) {
        this.WORKFLOWINSTANCE_GUID = WORKFLOWINSTANCE_GUID;
    }

    public String getUPLOADPERSON_GUID() {
        return UPLOADPERSON_GUID;
    }

    public void setUPLOADPERSON_GUID(String UPLOADPERSON_GUID) {
        this.UPLOADPERSON_GUID = UPLOADPERSON_GUID;
    }

    public String getUPLOADDATE() {
        return UPLOADDATE;
    }

    public void setUPLOADDATE(String UPLOADDATE) {
        this.UPLOADDATE = UPLOADDATE;
    }

    public String getDOCUMENTTITLE() {
        return DOCUMENTTITLE;
    }

    public void setDOCUMENTTITLE(String DOCUMENTTITLE) {
        this.DOCUMENTTITLE = DOCUMENTTITLE;
    }

    public String getDOCUMENTFILENAME() {
        return DOCUMENTFILENAME;
    }

    public void setDOCUMENTFILENAME(String DOCUMENTFILENAME) {
        this.DOCUMENTFILENAME = DOCUMENTFILENAME;
    }
}
