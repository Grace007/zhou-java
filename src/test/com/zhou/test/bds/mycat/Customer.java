package com.zhou.test.bds.mycat;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author eli
 * @date 2017/12/21 17:56
 */
@Table("customer")
public class Customer {

    @Column
    private int id;
    @Column
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
