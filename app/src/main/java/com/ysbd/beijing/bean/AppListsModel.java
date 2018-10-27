package com.ysbd.beijing.bean;

import java.util.ArrayList;

public class AppListsModel {
    private boolean success;
    private String msg;
    private ArrayList<AppModel> itemList;

    public AppListsModel() {
    }

    public AppListsModel(boolean success, String msg, ArrayList<AppModel> itemList) {
        this.success = success;
        this.msg = msg;
        this.itemList = itemList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<AppModel> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<AppModel> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "AppListsModel{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", itemList=" + itemList +
                '}';
    }

    public static class AppModel {
        private String itemId;
        private String itemName;
        private String appIcon;
        private String isOnline;
        private boolean hasOnlineAccess;
        private String processDefinitionKey;
        private int appLogo;

        public AppModel() {
        }

        public AppModel(String itemId, String itemName, String appIcon, String isOnline, boolean hasOnlineAccess, String processDefinitionKey) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.appIcon = appIcon;
            this.isOnline = isOnline;
            this.hasOnlineAccess = hasOnlineAccess;
            this.processDefinitionKey = processDefinitionKey;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(String appIcon) {
            this.appIcon = appIcon;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        public boolean isHasOnlineAccess() {
            return hasOnlineAccess;
        }

        public void setHasOnlineAccess(boolean hasOnlineAccess) {
            this.hasOnlineAccess = hasOnlineAccess;
        }

        public String getProcessDefinitionKey() {
            return processDefinitionKey;
        }

        public void setProcessDefinitionKey(String processDefinitionKey) {
            this.processDefinitionKey = processDefinitionKey;
        }

        public int getAppLogo() {
            return appLogo;
        }

        public void setAppLogo(int appLogo) {
            this.appLogo = appLogo;
        }

        @Override
        public String toString() {
            return "AppModel{" +
                    "itemId='" + itemId + '\'' +
                    ", itemName='" + itemName + '\'' +
                    ", appIcon='" + appIcon + '\'' +
                    ", isOnline='" + isOnline + '\'' +
                    ", hasOnlineAccess=" + hasOnlineAccess +
                    ", processDefinitionKey='" + processDefinitionKey + '\'' +
                    '}';
        }
    }
}
