package com.mino.mes.vo;

import java.util.List;

/**
 * @author fuxg
 * @create 2018-05-27 20:10
 **/
public class AssemblyBoard {
    List<AssemblyBoardEtPlist> etPlists;
    List<AssemblyBoardEtStatus> etStatuss;

    public AssemblyBoard(List<AssemblyBoardEtPlist> etPlists, List<AssemblyBoardEtStatus> etStatuss) {
        this.etPlists = etPlists;
        this.etStatuss = etStatuss;
    }

    public List<AssemblyBoardEtPlist> getEtPlists() {
        return etPlists;
    }

    public void setEtPlists(List<AssemblyBoardEtPlist> etPlists) {
        this.etPlists = etPlists;
    }

    public List<AssemblyBoardEtStatus> getEtStatuss() {
        return etStatuss;
    }

    public void setEtStatuss(List<AssemblyBoardEtStatus> etStatuss) {
        this.etStatuss = etStatuss;
    }
}
