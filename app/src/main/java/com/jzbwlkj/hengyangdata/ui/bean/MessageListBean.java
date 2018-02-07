package com.jzbwlkj.hengyangdata.ui.bean;

import java.io.Serializable;

/**
 * Created by gaoyuan on 2018/1/16.
 */

public class MessageListBean implements Serializable {

    /**
     * id : 2
     * img : admin/20180108/4f0872462f6fd195ccb6ae605c6a8cef.jpg
     * title : 刚刚获得国家最高科学技术奖的王泽山和侯云德有多牛
     * money : 21
     * status : 1
     * deadline : 2018-01-15 00:00
     */

    private int id;
    private String img;
    private String title;
    private String money;
    private int status;
    private long deadline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}
