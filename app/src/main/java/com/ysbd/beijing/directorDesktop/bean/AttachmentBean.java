package com.ysbd.beijing.directorDesktop.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by lcjing on 2018/7/23.
 */

public class AttachmentBean extends DataSupport {

    private String name;//文件显示的名称
    private String fileName;//文件名
    private String rout;//文件的绝对路径
    private String parent;//父 文件夹路径
    private String catalog;//目录
    private boolean file;
    private String extension;



    @Override
    public synchronized boolean save() {
        List<AttachmentBean> attachmentBean = DataSupport.select("rout").where("rout = ?", rout).find(AttachmentBean.class);
        if (attachmentBean.size()>0) {
            return false;
        }else {
            return super.save();
        }

    }


    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRout() {
        return rout;
    }

    public void setRout(String rout) {
        this.rout = rout;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }
}
