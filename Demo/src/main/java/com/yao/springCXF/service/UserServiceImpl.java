package com.yao.springCXF.service;

import com.yao.springCXF.Response;
import com.yao.springCXF.Storage;
import com.yao.springCXF.entity.User;
import com.yao.springCXF.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaojian
 * @date 2020/7/5 16:32
 */

@Service
public class UserServiceImpl implements UserService {

    public static List<User> users = Storage.users();


    @Override
    public List<User> getUsers() {
       return users;
    }

    @Override
    public Response delete(long id) {
        for (User user : users){
            if (user.getId() == id){
                users.remove(user);
                break;
            }
        }
        Response response = new Response();
        response.setCode(200);
        response.setMessage("successful!");
        return response;
    }

    @Override
    public Response add(User user) {
        users.add(user);
        Response response = new Response();
        response.setCode(200);
        response.setMessage("successful!");
        return response;
    }

    @Override
    public User getUser(long id) {
        for (User user : users){
            if (user.getId() == id){
                users.remove(user);
                return  user;
            }
        }
        return  null;
    }


    @Override
    public Response update(long id) {
        return null;
    }
}
