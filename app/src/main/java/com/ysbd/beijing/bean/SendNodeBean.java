package com.ysbd.beijing.bean;

/**
 * Created by lcjing on 2018/8/29.
 */

public class SendNodeBean {

    /**
     * imageUrl : icon03
     * index : 1
     * level : -1
     * nodeGuid : {BFA7820D-FFFF-FFFF-F16B-BA4600000804}
     * nodeName : 余志强
     * nodeType : 1
     * parentNodeGuid : {BFA7820D-FFFF-FFFF-F16C-488100000020}
     */
//    {
//        "imageUrl":"picon02",
//            "index":9,
//            "level":2,
//            "nodeGuid":"{BFA7820D-FFFF-FFFF-F16C-487100000014}",
//            "nodeName":"税政处",
//            "nodeType":0,
//            "parentNodeGuid":"{BFA7820D-FFFF-FFFF-F16C-484200000006}"
//    }
    private String imageUrl;
    private int index;
    private int level;
    private String nodeGuid;
    private String nodeName;
    private int nodeType;
    private String parentNodeGuid;
    private String jobtitles;

    public String getJobtitles() {
        return jobtitles;
    }

    public void setJobtitles(String jobtitles) {
        this.jobtitles = jobtitles;
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
}
