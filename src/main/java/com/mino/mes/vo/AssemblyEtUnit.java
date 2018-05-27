package com.mino.mes.vo;

/**
 * 装配单元信息
 * @author fuxg
 * @create 2018-05-27 11:50
 **/
public class AssemblyEtUnit {
    /**
     * 生产工单
     */
   String aufnr;

    /**
     *单元号
     */
   String unumb;

    /**
     *单元描述
     */
   String uname;

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getUnumb() {
        return unumb;
    }

    public void setUnumb(String unumb) {
        this.unumb = unumb;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
