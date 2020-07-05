package com.yao.spring;

import javax.jws.WebService;
import java.util.List;

/**
 * @author yaojian
 * @date 2020/7/2 20:50
 */
@WebService

public interface Userservice {

    List<User> getUsers();


}
