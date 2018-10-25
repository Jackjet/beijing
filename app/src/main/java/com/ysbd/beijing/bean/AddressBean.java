package com.ysbd.beijing.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.mcxtzhang.indexlib.suspension.ISuspensionInterface;

import org.litepal.crud.DataSupport;

/**
 * Created by lcjing on 2018/8/30.
 */

public class AddressBean  extends BaseIndexPinyinBean {

    /**
     * imageUrl : icon03
     * index : 10
     * level : -1
     * nodeGuid : {BFA7820D-FFFF-FFFF-F16B-BEF30000114A}
     * nodeName : 陶占录
     * nodeType : 1
     * parentNodeGuid : {BFA7820D-FFFF-FFFF-F16C-48810000001C}
     */

    private String imageUrl;
    private int index;
    private int level;
    private String nodeGuid;
    private String nodeName;
    private String jobtitles;
    private int nodeType;
    private String parentNodeGuid;
    private boolean isParent;
    private boolean isOpen;
    private boolean isSelect;


    public String getJobtitles() {
        return jobtitles;
    }

    public void setJobtitles(String jobtitles) {
        this.jobtitles = jobtitles;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNodeGuid() {
        return nodeGuid;
    }

    public void setNodeGuid(String nodeGuid) {
        this.nodeGuid = nodeGuid;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getParentNodeGuid() {
        return parentNodeGuid;
    }

    public void setParentNodeGuid(String parentNodeGuid) {
        this.parentNodeGuid = parentNodeGuid;
    }
    @Override
    public boolean isNeedToPinyin() {
        return true;
    }


    @Override
    public boolean isShowSuspension() {
        return true;
    }
    @Override
    public String getTarget() {
        return nodeName;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "imageUrl='" + imageUrl + '\'' +
                ", index=" + index +
                ", level=" + level +
                ", nodeGuid='" + nodeGuid + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", jobtitles='" + jobtitles + '\'' +
                ", nodeType=" + nodeType +
                ", parentNodeGuid='" + parentNodeGuid + '\'' +
                ", isParent=" + isParent +
                ", isOpen=" + isOpen +
                ", isSelect=" + isSelect +
                '}';
    }
}
