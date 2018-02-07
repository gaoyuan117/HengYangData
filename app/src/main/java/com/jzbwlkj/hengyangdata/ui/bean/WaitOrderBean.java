package com.jzbwlkj.hengyangdata.ui.bean;

/**
 * Created by gaoyuan on 2018/1/16.
 */

public class WaitOrderBean {


    /**
     * id : 9
     * msg_id : 4
     * order_id : 3
     * group_id : 7
     * money : 15
     * order_no : 201801161745012655
     * uid : 8
     * status : 0
     * pay_type : 1
     * createtime : 2018-01-16 17:45:01
     * uptime : 1516095901
     * nums : 0
     * title :  白马股强势不改 多股频创历史新高
     * img : admin/20180110/66eb4556ed0191edf1ee1f8fc143ed0f.png
     */

    private int id;
    private int msg_id;
    private int order_id;
    private int group_id;
    private String money;
    private String order_no;
    private int uid;
    private int status;
    private int pay_type;
    private int is_read;
    private String createtime;
    private int uptime;
    private int nums;
    private String title;
    private String img;

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getUptime() {
        return uptime;
    }

    public void setUptime(int uptime) {
        this.uptime = uptime;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
