package com.jzbwlkj.hengyangdata.ui.bean;

import java.util.List;

/**
 * Created by gaoyuan on 2018/1/25.
 */

public class CheckAlonePwdBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"order_message":[],"paymessage":{"id":27,"title":"asdfa","img":"","describe":"<p>asfas<\/p>","money":"0.01","status":1,"addtime":"2018-01-25","deadline":"2018-01-27 19:55"}}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_message : []
         * paymessage : {"id":27,"title":"asdfa","img":"","describe":"<p>asfas<\/p>","money":"0.01","status":1,"addtime":"2018-01-25","deadline":"2018-01-27 19:55"}
         */

        private PaymessageBean paymessage;
        private List<?> order_message;

        public PaymessageBean getPaymessage() {
            return paymessage;
        }

        public void setPaymessage(PaymessageBean paymessage) {
            this.paymessage = paymessage;
        }

        public List<?> getOrder_message() {
            return order_message;
        }

        public void setOrder_message(List<?> order_message) {
            this.order_message = order_message;
        }

        public static class PaymessageBean {
            /**
             * id : 27
             * title : asdfa
             * img :
             * describe : <p>asfas</p>
             * money : 0.01
             * status : 1
             * addtime : 2018-01-25
             * deadline : 2018-01-27 19:55
             */

            private int id;
            private String title;
            private String img;
            private String describe;
            private String money;
            private int status;
            private String addtime;
            private String deadline;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
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

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }
        }
    }
}
