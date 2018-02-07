package com.jzbwlkj.hengyangdata.ui.bean;

import java.io.Serializable;

/**
 * Created by gaoyuan on 2018/1/16.
 */

public class MessageDetailBean implements Serializable{

    /**
     * id : 2
     * title : 刚刚获得国家最高科学技术奖的王泽山和侯云德有多牛
     * img : admin/20180108/4f0872462f6fd195ccb6ae605c6a8cef.jpg
     * describe : <p>原标题:刚刚获得国家最高科学技术奖的王泽山和侯云德有多牛？</p><p>今天，2017年度国家科学技术奖励大会在人民大会堂举行，南京理工大学教授、中国工程院院士王泽山和中国疾病预防控制中心病毒病所研究员、中国工程院院士侯云德，获国家最高科学技术奖。</p><p>这两位科学家到底书写了怎样的传奇？</p><p>本文综编自新华网、《中国科学报》、人民日报客户端、央视新闻移动网等，不代表瞭望智库观点。</p><p>一</p><p>王泽山院士：“以身相许”火炸药</p><p><img class="normal" data-loadfunc="0" src="https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2085556507,4011970658&fm=173&s=12387984502290AEA23190930300D093&w=500&h=323&img.JPEG" data-loaded="0" width="500px"/></p><p>2017年3月10日王泽山院士在辽阳试验场朱志飞摄</p><p><img class="normal" data-loadfunc="0" src="https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=471441809,3786376298&fm=173&s=A52840B3C4A338B52300D48A03007043&w=500&h=328&img.JPEG" data-loaded="0" width="500px"/></p><p>2017年3月15日王泽山院士在检测自动装置系统朱志飞摄</p><p>“学术会议参加不少，但对这种采访不太适应。”眼前这位三次获得国家最高科技大奖的“80后”院士并不太习惯面对媒体，有点腼腆地说：“自己只是个一辈子只能做好一件事情的人。”</p><p>中国工程院院士、南京理工大学教授王泽山在火炸药这个“不起眼”的国防领域，整整奋斗了64个年头，为我国火炸药事业从跟踪仿制到进入创新发展作出了重要贡献，书写了一段带领我国火炸药整体实力进入世界前列的传奇。</p><p>1.投身“不起眼”的火炸药领域</p><p><br/></p>
     * money : 21
     * status : 3
     * addtime : 1515407342
     * deadline : 1515407304
     */

    private int id;
    private String title;
    private String img;
    private String describe;
    private String money;
    private int status;
    private long addtime;
    private int is_pur;
    private long deadline;

    public int getIs_pur() {
        return is_pur;
    }

    public void setIs_pur(int is_pur) {
        this.is_pur = is_pur;
    }

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

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}
