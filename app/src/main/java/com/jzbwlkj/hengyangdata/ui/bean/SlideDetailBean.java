package com.jzbwlkj.hengyangdata.ui.bean;

/**
 * Created by gaoyuan on 2018/1/16.
 */

public class SlideDetailBean {

    /**
     * id : 1
     * status : 1
     * delete_time : 0
     * name : 轮播图
     * remark :
     * img : admin/20180106/fa0cf73b62251b85c3c825be014c16b6.jpg
     * content : <p>海纳百川，精选全球的高品质软件与服务</p><p><br/></p>
     * uptime : 2018-01-06 15:35:29
     */

    private int id;
    private int status;
    private int delete_time;
    private String name;
    private String remark;
    private String img;
    private String content;
    private String uptime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(int delete_time) {
        this.delete_time = delete_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}
