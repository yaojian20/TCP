package com.yao;

import javax.xml.ws.Endpoint;

/**
 * @author yaojian
 * @date 2020/6/30 22:34
 */
public class BootStrap {
    public static void main(String[] args) {


        //http://localhost:8888/sayHello?wsdl浏览器访问wsdl文件
        Endpoint.publish("http://localhost:8888/sayHello", new ISayHelloImpl());

        System.out.println("bootstrap success!");
    }
}
