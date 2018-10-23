package com.ysbd.beijing.ui.bean;

import java.io.Serializable;

/**
 * Created by lcjing on 2018/7/7.
 */

public class OpinionModel implements Serializable{

    private String opinionFrameName;
    private String date;
    private boolean isParent;
    private boolean editable;
    private boolean addable;
    private boolean hasRole;
    private String id;
    private String tenantId;
    private String opinionFrameMark;
    private String processSerialNumber;
    private String processInstanceId;
    private String taskId;
    private String content;
    private String userId;
    private String userName;
    private String createDate;
    private String modifyDate;
    private String signValue;
    private String sealguid;
    private int isSign;
    private String userParentId;

    public String getUserParentId() {
        return userParentId;
    }

    public void setUserParentId(String userParentId) {
        this.userParentId = userParentId;
    }

    public OpinionModel() {
    }

    public OpinionModel(String name, String mark) {
        this.opinionFrameName = name;
        this.isParent = true;
        this.opinionFrameMark = mark;
    }

    public boolean isHasRole() {
        return hasRole;
    }

    public void setHasRole(boolean hasRole) {
        this.hasRole = hasRole;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getOpinionFrameName() {
        return opinionFrameName;
    }

    public void setOpinionFrameName(String opinionFrameName) {
        this.opinionFrameName = opinionFrameName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isAddable() {
        return addable;
    }

    public void setAddable(boolean addable) {
        this.addable = addable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getOpinionFrameMark() {
        return opinionFrameMark;
    }

    public void setOpinionFrameMark(String opinionFrameMark) {
        this.opinionFrameMark = opinionFrameMark;
    }

    public String getProcessSerialNumber() {
        return processSerialNumber;
    }

    public void setProcessSerialNumber(String processSerialNumber) {
        this.processSerialNumber = processSerialNumber;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }

    public String getSealguid() {
        return sealguid;
    }

    public void setSealguid(String sealguid) {
        this.sealguid = sealguid;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }
}
