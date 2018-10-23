package com.ysbd.beijing.ui.bean;

/**
 * Created by lcjing on 2018/8/20.
 */

public class CommentBean {
    private String row_guid;
    private String comment_guid;
    private String comment_content;
    private String comment_person;
    private String comment_date;
    private String person_guid;

    public String getPerson_guid() {
        return person_guid;
    }

    public void setPerson_guid(String person_guid) {
        this.person_guid = person_guid;
    }

    public CommentBean() {
    }


    public CommentBean(String comment_guid) {
        this.comment_guid = comment_guid;
    }

    public String getRow_guid() {
        return row_guid;
    }

    public void setRow_guid(String row_guid) {
        this.row_guid = row_guid;
    }

    public String getComment_guid() {
        return comment_guid;
    }

    public void setComment_guid(String comment_guid) {
        this.comment_guid = comment_guid;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_person() {
        return comment_person;
    }

    public void setComment_person(String comment_person) {
        this.comment_person = comment_person;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}
