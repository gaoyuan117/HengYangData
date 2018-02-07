package com.jzbwlkj.hengyangdata.ui.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gaoyuan on 2018/1/16.
 */

public class AboutBean implements Serializable {

    /**
     * id : 1
     * phone : 13639447983
     * logo : admin/20180110/8ecf625e89648cf2f478cba959daeb79.png
     * content : 关于我们关于我们关于我们关于我们关于我们
     * version : V1.0.1
     * uptime : 1515583229
     * switch : 1
     */

    private int id;
    private String phone;
    private String logo;
    private String content;
    private String version;
    private int uptime;
    @SerializedName("switch")
    private int switchX;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getUptime() {
        return uptime;
    }

    public void setUptime(int uptime) {
        this.uptime = uptime;
    }

    public int getSwitchX() {
        return switchX;
    }

    public void setSwitchX(int switchX) {
        this.switchX = switchX;
    }
}
