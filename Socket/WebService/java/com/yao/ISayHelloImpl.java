package com.yao;

import javax.jws.WebService;

/**
 * @author yaojian
 * @date 2020/6/30 22:33
 */
@WebService
public class ISayHelloImpl implements  IsayHello {
    @Override
    public String sayHello(String name) {
        System.out.println("call sayHello");
        return "hello, I am " + name;
    }
}
