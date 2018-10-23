package com.ysbd.beijing.ui.bean;

/**
 * Created by lcjing on 2018/8/30.
 */

public class AttachmentBean {

    /**
     * attachment_name : 市转文x.xls
     * url : http://218.60.41.112:9998/risenetoabjcz/riseoffice/MobileFileServlet?method=mobileLoadFile&appname=workflow&fileboxname=fujian&savemode=fs&majorversion=1&ishandleextends=true&keepminimalversion=true&streamhandles=&attachmentrow_guid={0A2FF324-FFFF-FFFF-CF4E-1E6C0000000C}&workflowinstance_guid={0A2FF324-FFFF-FFFF-CF4D-4C6000000008}&attachment_name=市转文x.xls
     * attachment_extension : xls
     * attachmentrow_guid : {0A2FF324-FFFF-FFFF-CF4E-1E6C0000000C}
     * modified
     */

    private String attachment_name;
    private String url;
    private String attachment_extension;
    private String attachmentrow_guid;
    private String attachment_lastmodified;
    private String department_shortdn;
    private String attachment_description;

    public String getAttachment_lastmodified() {
        return attachment_lastmodified;
    }

    public void setAttachment_lastmodified(String attachment_lastmodified) {
        this.attachment_lastmodified = attachment_lastmodified;
    }

    public String getDepartment_shortdn() {
        return department_shortdn;
    }

    public void setDepartment_shortdn(String department_shortdn) {
        this.department_shortdn = department_shortdn;
    }

    public String getAttachment_description() {
        return attachment_description;
    }

    public void setAttachment_description(String attachment_description) {
        this.attachment_description = attachment_description;
    }

    public String getAttachment_name() {
        return attachment_name;
    }

    public void setAttachment_name(String attachment_name) {
        this.attachment_name = attachment_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAttachment_extension() {
        return attachment_extension;
    }

    public void setAttachment_extension(String attachment_extension) {
        this.attachment_extension = attachment_extension;
    }

    public String getAttachmentrow_guid() {
        return attachmentrow_guid;
    }

    public void setAttachmentrow_guid(String attachmentrow_guid) {
        this.attachmentrow_guid = attachmentrow_guid;
    }
}
