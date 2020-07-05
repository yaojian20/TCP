package com.yao;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author yaojian
 * @date 2020/6/30 22:32
 */

@WebService
//@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface IsayHello {

    @WebMethod
    String sayHello(String name);
}
