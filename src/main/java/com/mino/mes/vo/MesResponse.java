package com.mino.mes.vo;

/**
 * @author fuxg
 * @create 2018-05-25 21:19
 **/
public class MesResponse<T> {

    public final static Integer SUCCESS_CODE = 200;
    public final static Integer ERROR_CODE = 400;

    public final static String SUCCESS_MSG = "操作成功";
    public final static String ERROR_MSG = "操作失败";

    Integer code;
    String msg;
    T data;

    public MesResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public MesResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
