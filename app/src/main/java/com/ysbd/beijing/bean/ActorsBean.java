package com.ysbd.beijing.bean;

import java.io.Serializable;

/**
 * Created by lcjing on 2018/8/30.
 */

public class ActorsBean implements Serializable{

    /**
     * actionName : 送处室其他人
     * currentStep : 9
     * departName : 北京市财政局,信息处（信息中心）
     * preActionGUID :
     * proecssActor : {"actorsClassify":0,"handelStatus":2,"personGUID":"{BFA7820D-FFFF-FFFF-F16B-B8F700000689}","step":0,"updateDate":null}
     * recipientsActors : {}
     * taskName : 处室其他人员
     * updateDate : {"date":30,"day":4,"hours":10,"minutes":23,"month":7,"nanos":0,"seconds":57,"time":1535624637000,"timezoneOffset":0,"year":118}
     * updateDateString : 2018-08-30 10:23
     */

    private String actionName;
    private int currentStep;
    private String departName;
    private String preActionGUID;
    private ProecssActorBean proecssActor;
    private RecipientsActorsBean recipientsActors;
    private String taskName;
    private UpdateDateBean updateDate;
    private String updateDateString;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getPreActionGUID() {
        return preActionGUID;
    }

    public void setPreActionGUID(String preActionGUID) {
        this.preActionGUID = preActionGUID;
    }

    public ProecssActorBean getProecssActor() {
        return proecssActor;
    }

    public void setProecssActor(ProecssActorBean proecssActor) {
        this.proecssActor = proecssActor;
    }

    public RecipientsActorsBean getRecipientsActors() {
        return recipientsActors;
    }

    public void setRecipientsActors(RecipientsActorsBean recipientsActors) {
        this.recipientsActors = recipientsActors;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public UpdateDateBean getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(UpdateDateBean updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDateString() {
        return updateDateString;
    }

    public void setUpdateDateString(String updateDateString) {
        this.updateDateString = updateDateString;
    }

    public static class ProecssActorBean implements Serializable{
        /**
         * actorsClassify : 0
         * handelStatus : 2
         * personGUID : {BFA7820D-FFFF-FFFF-F16B-B8F700000689}
         * step : 0
         * updateDate : null
         */

        private String personGUID;

        public String getPersonGUID() {
            return personGUID;
        }

        public void setPersonGUID(String personGUID) {
            this.personGUID = personGUID;
        }

        private int actorsClassify;

        public int getActorsClassify() {
            return actorsClassify;
        }

        public void setActorsClassify(int actorsClassify) {
            this.actorsClassify = actorsClassify;
        }

        private int handelStatus;

        public int getHandelStatus() {
            return handelStatus;
        }

        public void setHandelStatus(int handelStatus) {
            this.handelStatus = handelStatus;
        }
    }

    public static class RecipientsActorsBean implements Serializable{
    }

    public static class UpdateDateBean implements Serializable{
        /**
         * date : 30
         * day : 4
         * hours : 10
         * minutes : 23
         * month : 7
         * nanos : 0
         * seconds : 57
         * time : 1535624637000
         * timezoneOffset : 0
         * year : 118
         */

        private int date;
        private int day;
        private int hours;
        private int minutes;
        private int month;
        private int nanos;
        private int seconds;
        private long time;
        private int timezoneOffset;
        private int year;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getNanos() {
            return nanos;
        }

        public void setNanos(int nanos) {
            this.nanos = nanos;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getTimezoneOffset() {
            return timezoneOffset;
        }

        public void setTimezoneOffset(int timezoneOffset) {
            this.timezoneOffset = timezoneOffset;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}
