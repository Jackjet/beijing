package com.ysbd.beijing.ui.bean;

import java.io.Serializable;

/**
 * Created by lcjing on 2018/8/17.
 */

public class MenuBean implements Serializable{

    /**
     * name : 起草结余资金
     * actionguid : {5A292830-D942-4249-8D81-885F2AB9253D}
     * action_status : 1
     */

    private String name;
    private String actionguid;
    private String action_status;//状态 0表示结束动作，1正常发送动作

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionguid() {
        return actionguid;
    }

    public void setActionguid(String actionguid) {
        this.actionguid = actionguid;
    }

    public String getAction_status() {
        return action_status;
    }

    public void setAction_status(String action_status) {
        this.action_status = action_status;
    }
}
