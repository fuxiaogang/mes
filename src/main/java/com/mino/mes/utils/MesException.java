package com.mino.mes.utils;

/**
 * @author fuxg
 * @create 2018-05-25 22:13
 **/
public class MesException extends Exception {

    String msg;

    Integer code;

    public MesException() {
        super();
    }

    public MesException(String message) {
        super(message);
    }

    public MesException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
