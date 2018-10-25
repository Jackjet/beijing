package com.ysbd.beijing.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${LCJ} on 2017/1/4.
 */
//组织架构的多级菜单子项
public class MultiBean implements Serializable {

    private String name; // 本级菜单的名字
    private String id;
    private boolean isParent; // 是否有下一级菜单
    private boolean isOpen; // 菜单是否展开
    private int level; // 所在层级
    private String orgId;//上级菜单id，无论返回上下级菜单关系，原理是一样的
    private String pName;
    private boolean isSelect;
    private String avator;
    //父单位 所属的单位集合
    private List<String> pIds;
    private int index;
    private String duty;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<String> getPIds() {
        return pIds;
    }

    public void setPIds(List<String> pIds) {
        this.pIds = pIds;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        if(id!=null&&((MultiBean)obj).getId()!=null&&id.equals(((MultiBean)obj).getId()))
            return true;
        else
            return false;
    }
}
