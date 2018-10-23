package com.ysbd.beijing.ui.bean;

/**
 * Created by lcjing on 2018/8/28.
 */

public class SelectPersonNodeBean {
    /**
     * id : {BFA7820D-FFFF-FFFF-F16C-487100000013}
     * pId : {BFA7820D-FFFF-FFFF-F16C-484200000006}
     * name : 局领导
     * iconSkin : picon02
     * nodeType : 0
     * open : true
     */
    private String id;
    private String pId;
    private String name;
    private String iconSkin;//picon02 部门。icon03人员
    private String nodeType;
    private boolean open;
    private int index;
    private String jobtitles;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getJobtitles() {
        return jobtitles;
    }

    public void setJobtitles(String jobtitles) {
        this.jobtitles = jobtitles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
