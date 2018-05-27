package com.mino.mes.vo;

import java.util.List;

/**
 * @author fuxg
 * @create 2018-05-27 18:13
 **/
public class ElecBoard {
    List<ElecBoardEtPlist> etPlists;
    List<ElecBoardEtStatus> etStatuss;

    public ElecBoard(List<ElecBoardEtPlist> etPlists, List<ElecBoardEtStatus> etStatuss) {
        this.etPlists = etPlists;
        this.etStatuss = etStatuss;
    }

    public List<ElecBoardEtPlist> getEtPlists() {
        return etPlists;
    }

    public void setEtPlists(List<ElecBoardEtPlist> etPlists) {
        this.etPlists = etPlists;
    }

    public List<ElecBoardEtStatus> getEtStatuss() {
        return etStatuss;
    }

    public void setEtStatuss(List<ElecBoardEtStatus> etStatuss) {
        this.etStatuss = etStatuss;
    }
}
