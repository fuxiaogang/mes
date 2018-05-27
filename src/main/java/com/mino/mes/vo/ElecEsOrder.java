package com.mino.mes.vo;

public class ElecEsOrder {
    /**
     *生产工单
     */
    String aufnr;
    /**
     *项目号
     */
    String pspid;
    /**
     *物料编号
     */
    String matnr;
    /**
     *物料描述
     */
    String maktx;
    /**
     *  加工数量
     */
    String psmng;
    /**
     *计划开始日期
     */
    String usr08;
    /**
     *计划完成日期
     */
    String usr09;

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getPspid() {
        return pspid;
    }

    public void setPspid(String pspid) {
        this.pspid = pspid;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public String getPsmng() {
        return psmng;
    }

    public void setPsmng(String psmng) {
        this.psmng = psmng;
    }

    public String getUsr08() {
        return usr08;
    }

    public void setUsr08(String usr08) {
        this.usr08 = usr08;
    }

    public String getUsr09() {
        return usr09;
    }

    public void setUsr09(String usr09) {
        this.usr09 = usr09;
    }
}
