package com.yao.spring;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yaojian
 * @date 2020/7/2 20:47
 */

@XmlRootElement
public class User {


    private long id;

    private  String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
