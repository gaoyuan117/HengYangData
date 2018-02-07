package com.jzbwlkj.hengyangdata.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by gaoyuan on 2018/1/16.
 */
@Entity
public class History {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "key")
    @NotNull
    @Unique
    private String key;
    @Generated(hash = 644782702)
    public History(Long id, @NotNull String key) {
        this.id = id;
        this.key = key;
    }
    @Generated(hash = 869423138)
    public History() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
}
