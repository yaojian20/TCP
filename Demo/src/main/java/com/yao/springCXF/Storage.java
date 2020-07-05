package com.yao.springCXF;

import com.yao.springCXF.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaojian
 * @date 2020/7/5 16:45
 */
public class Storage {

    public static List<User> users(){
        List<User> users = new ArrayList<>();
        users.add(new User(1,"jack"));
        users.add(new User(2,"tom"));
        users.add(new User(3,"james"));
        users.add(new User(4,"yao"));
        users.add(new User(5,"bob"));
        return  users;
    }
}
