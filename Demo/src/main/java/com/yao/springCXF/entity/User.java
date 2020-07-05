package com.yao.springCXF.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yaojian
 * @date 2020/7/4 23:30
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    public User() {
    }

    private long id ;

    private String name;

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
