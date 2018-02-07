package com.jzbwlkj.hengyangdata.ui.bean;

/**
 * Created by gaoyuan on 2018/1/16.
 */

public class NewsDetailBean {


    /**
     * id : 3
     * uid : 2
     * msg_id : 2
     * uptime : 2018-01-06 13:39:11
     * is_read : 0
     * content : 欢迎注册恒阳数据APP，感谢您的信任，我们一定不辜负您的期望~
     */

    private int id;
    private int uid;
    private int msg_id;
    private String uptime;
    private int is_read;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
