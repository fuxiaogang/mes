package com.mino.mes.vo;

/**
 * @author fuxg
 * @create 2018-05-25 21:25
 **/
public class ElecEtPlist {
    /**
     * 机台号
     */
    String ecode;
    /**
     * 机台描述
     */
    String ecodeText;
    /**
     * 生产工单
     */
    String aufnr;
    /**
     * 员工编号
     */
    Long pernr;
    /**
     * 员工姓名
     */
    String sname;
    /**
     * 图号
     */
    String zzext;
    /**
     * 开始日期
     */
    String rdate;
    /**
     * 开始时间
     */
    String rtime;
    /**
     * 持续时间（秒）
     */
    Integer proct;
    /**
     * 开始时间
     */
    String beginTr;
    /**
     * 持续时间
     */
    String proctTr;
    /**
     * 状态
     */
    String zstat;
    /**
     * 事件ID号
     */
    Long eveid;

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEcodeText() {
        return ecodeText;
    }

    public void setEcodeText(String ecodeText) {
        this.ecodeText = ecodeText;
    }

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public Long getPernr() {
        return pernr;
    }

    public void setPernr(Long pernr) {
        this.pernr = pernr;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getZzext() {
        return zzext;
    }

    public void setZzext(String zzext) {
        this.zzext = zzext;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public Integer getProct() {
        return proct;
    }

    public void setProct(Integer proct) {
        this.proct = proct;
    }

    public String getBeginTr() {
        return beginTr;
    }

    public void setBeginTr(String beginTr) {
        this.beginTr = beginTr;
    }

    public String getProctTr() {
        return proctTr;
    }

    public void setProctTr(String proctTr) {
        this.proctTr = proctTr;
    }

    public String getZstat() {
        return zstat;
    }

    public void setZstat(String zstat) {
        this.zstat = zstat;
    }

    public Long getEveid() {
        return eveid;
    }

    public void setEveid(Long eveid) {
        this.eveid = eveid;
    }
}
