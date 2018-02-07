package com.jzbwlkj.hengyangdata.ui.bean;

import java.util.List;

/**
 * Created by gaoyuan on 2018/1/31.
 */

public class GroupMemBean {

    /**
     * data : [{"nickname":"哈哈","headsmall":"http://oqyku65x1.bkt.clouddn.com/LsYtqkWzJvgPKZrFwwYG.png","area":"历下区"},{"nickname":"哈哈","headsmall":"http://oqyku65x1.bkt.clouddn.com/LsYtqkWzJvgPKZrFwwYG.png","area":"历下区"}]
     * state : {"code":0,"msg":""}
     */

    private StateBean state;
    private List<DataBean> data;

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class StateBean {
        /**
         * code : 0
         * msg :
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class DataBean {
        /**
         * nickname : 哈哈
         * headsmall : http://oqyku65x1.bkt.clouddn.com/LsYtqkWzJvgPKZrFwwYG.png
         * area : 历下区
         */

        private String nickname;
        private String headsmall;
        private String area;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadsmall() {
            return headsmall;
        }

        public void setHeadsmall(String headsmall) {
            this.headsmall = headsmall;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }
}
