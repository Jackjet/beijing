package com.ysbd.beijing.ui.bean.form;

import com.ysbd.beijing.bean.ActorsBean;
import com.ysbd.beijing.bean.DocumentBean;
import com.ysbd.beijing.ui.bean.AttachmentBean;
import com.ysbd.beijing.ui.bean.CommentBean;
import com.ysbd.beijing.ui.bean.CurrentCommentBean;
import com.ysbd.beijing.ui.bean.MenuBean;

import java.util.List;

/**
 * Created by lcjing on 2018/8/27.
 */

public class BaseFormBean {
    private List<CurrentCommentBean> currentComment;
    private List<CommentBean> comment;
    private List<AttachmentBean> attachment;
    private List<MenuBean> menus;
    private List<ActorsBean> actors;
    private DocumentBean documentcb;//呈报内容
    private DocumentBean document;//正文

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

    public List<AttachmentBean> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentBean> attachment) {
        this.attachment = attachment;
    }

    public List<MenuBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuBean> menus) {
        this.menus = menus;
    }

    public List<ActorsBean> getActors() {
        return actors;
    }

    public void setActors(List<ActorsBean> actors) {
        this.actors = actors;
    }
}
