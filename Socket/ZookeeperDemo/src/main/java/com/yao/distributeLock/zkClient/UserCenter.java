package com.yao.distributeLock.zkClient;

import java.io.Serializable;

/**
 * @author yaojian
 * @date 2020/7/28 16:17
 */
public class UserCenter implements Serializable {
    private static final long serialVersionUID = 8723921702315752311L;


    //用户中心

    /**
     *
     */


    private String center_id;

    private String center_name;

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }






}
