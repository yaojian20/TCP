package com.yao.springCXF;

/**
 * @author yaojian
 * @date 2020/7/5 16:49
 */
public class Response {

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
