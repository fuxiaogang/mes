package com.mino.mes.service;

import com.mino.mes.SAPConn;
import com.mino.mes.utils.Constant;
import com.mino.mes.utils.MesException;
import com.mino.mes.vo.*;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fuxg
 * @create 2018-05-27 11:28
 **/
@Service
public class AssemblyServiceImpl {
    /**
     * 生产信息
     *
     * @param inEcode
     * @return
     * @throws Exception
     */
    public List<AssemblyEtPlist> findEtPlist(final String inEcode) throws Exception {
        final String functionName = Constant.FUN_ASSEMBLY_STATION_ZMES_IF012;
        final List<AssemblyEtPlist> datas = new LinkedList<>();

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_ECODE", inEcode);
        function.execute(destination);

        checkSapResponse(function);

        JCoParameterList exportTable = function.getTableParameterList();
        JCoTable etPlistTable = exportTable.getTable("ET_PLIST");

        boolean loopFlag1 = !etPlistTable.isEmpty();
        while (loopFlag1) {
            AssemblyEtPlist assemblyEtPlist = new AssemblyEtPlist();
            String ECODE = etPlistTable.getString("ECODE");
            String ECODE_TEXT = etPlistTable.getString("ECODE_TEXT");
            String AUFNR = etPlistTable.getString("AUFNR");
            Long PERNR = etPlistTable.getLong("PERNR");
            String SNAME = etPlistTable.getString("SNAME");
            String UNUMB = etPlistTable.getString("UNUMB");
            String EQUIP = etPlistTable.getString("EQUIP");
            String RDATE = etPlistTable.getString("RDATE");
            String RTIME = etPlistTable.getString("RTIME");
            Integer PROCT = etPlistTable.getInt("PROCT");
            String BEGIN_TR = etPlistTable.getString("BEGIN_TR");
            String PROCT_TR = etPlistTable.getString("PROCT_TR");
            String ZSTAT = etPlistTable.getString("ZSTAT");
            Long EVEID = etPlistTable.getLong("EVEID");

            assemblyEtPlist.setEcode(ECODE);
            assemblyEtPlist.setEcodeText(ECODE_TEXT);
            assemblyEtPlist.setAufnr(AUFNR);
            assemblyEtPlist.setPernr(PERNR);
            assemblyEtPlist.setSname(SNAME);
            assemblyEtPlist.setUnumb(UNUMB);
            assemblyEtPlist.setEquip(EQUIP);
            assemblyEtPlist.setRdate(RDATE);
            assemblyEtPlist.setRtime(RTIME);
            assemblyEtPlist.setProct(PROCT);
            assemblyEtPlist.setBeginTr(BEGIN_TR);
            assemblyEtPlist.setProctTr(PROCT_TR);
            assemblyEtPlist.setZstat(ZSTAT);
            assemblyEtPlist.setEveid(EVEID);
            datas.add(assemblyEtPlist);
            loopFlag1 = etPlistTable.nextRow();
        }
        return datas;
    }

    /**
     * 状态信息
     *
     * @param inEcode
     * @return
     * @throws Exception
     */
    public List<Stats> findEtStats(final String inEcode) throws Exception {
        final String functionName = Constant.FUN_ASSEMBLY_STATION_ZMES_IF012;
        final List<Stats> datas = new LinkedList<>();

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_ECODE", inEcode);
        function.execute(destination);

        checkSapResponse(function);

        JCoParameterList exportTable = function.getTableParameterList();
        JCoTable table = exportTable.getTable("ET_STATS");

        boolean loopFlag1 = !table.isEmpty();
        while (loopFlag1) {
            Stats stats = new Stats();
            String ECODE = table.getString("ECODE");
            String ZSTAT = table.getString("ZSTAT");

            stats.setEcode(ECODE);
            stats.setZstat(ZSTAT);

            datas.add(stats);
            loopFlag1 = table.nextRow();
        }
        return datas;
    }

    /**
     * 单元信息
     *
     * @param inEquip 设备号
     * @return
     * @throws Exception
     */
    public List<AssemblyEtUnit> findEtUnit(final String inEquip) throws Exception {
        final String functionName = Constant.FUN_ASSEMBLY_EQUIP_ZMES_IF005;
        final List<AssemblyEtUnit> datas = new LinkedList<>();

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_EQUIP", inEquip);
        function.execute(destination);

        checkSapResponse(function);

        JCoParameterList exportTable = function.getTableParameterList();
        JCoTable table = exportTable.getTable("ET_UNITS");

        boolean loopFlag1 = !table.isEmpty();
        while (loopFlag1) {
            AssemblyEtUnit assemblyEtUnit = new AssemblyEtUnit();
            String AUFNR = table.getString("AUFNR");
            String UNUMB = table.getString("UNUMB");
            String UNAME = table.getString("UNAME");

            assemblyEtUnit.setAufnr(AUFNR);
            assemblyEtUnit.setUnumb(UNUMB);
            assemblyEtUnit.setUname(UNAME);

            datas.add(assemblyEtUnit);
            loopFlag1 = table.nextRow();
        }
        return datas;
    }

    /**
     * 订单信息
     *
     * @param inEquip
     * @return
     * @throws Exception
     */
    public AssemblyEsOrder findEsOrder(final String inEquip) throws Exception {
        final String functionName = Constant.FUN_ASSEMBLY_EQUIP_ZMES_IF005;

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_EQUIP", inEquip);
        function.execute(destination);

        checkSapResponse(function);

        JCoStructure structure = function.getExportParameterList().getStructure("ES_ORDER");
        String AUFNR = structure.getString("AUFNR");
        String PSPID = structure.getString("PSPID");
        String MATNR = structure.getString("MATNR");
        String MAKTX = structure.getString("MAKTX");
        String PSMNG = structure.getString("PSMNG");
        String USR08 = structure.getString("USR08");
        String USR09 = structure.getString("USR09");
        AssemblyEsOrder assemblyEsOrder = new AssemblyEsOrder();
        assemblyEsOrder.setAufnr(AUFNR);
        assemblyEsOrder.setPspid(PSPID);
        assemblyEsOrder.setMatnr(MATNR);
        assemblyEsOrder.setMaktx(MAKTX);
        assemblyEsOrder.setPsmng(PSMNG);
        assemblyEsOrder.setUsr08(USR08);
        assemblyEsOrder.setUsr09(USR09);

        return assemblyEsOrder;
    }


    /**
     * 上机
     *
     * @param IN_ECODE 机台号
     * @param IN_ZSTAT 状态
     * @param IN_PERNR 员工编号
     * @param IN_EQUIP 设备号
     * @param  IN_UNUMB 单元号
     * @throws Exception
     */
    public void start(final String IN_ECODE, final String IN_ZSTAT, final Long IN_PERNR, final String IN_EQUIP, final String IN_UNUMB) throws Exception {
        final String functionName = Constant.FUN_ASSEMBLY_START_ZMES_IF006;

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_ECODE", IN_ECODE);
        input.setValue("IN_ZSTAT", IN_ZSTAT);
        input.setValue("IN_PERNR", IN_PERNR);
        input.setValue("IN_EQUIP", IN_EQUIP);
        input.setValue("IN_UNUMB", IN_UNUMB);
        function.execute(destination);

        checkSapResponse(function);
    }

    /**
     * 结束
     *
     * @param IN_EVEID 事件id
     * @throws Exception
     */
    public void stop(final String IN_EVEID) throws Exception {
        final String functionName = Constant.FUN_ASSEMBLY_STOP_ZMES_IF007;

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_EVEID", IN_EVEID);
        function.execute(destination);

        checkSapResponse(function);
    }

    private void checkSapResponse(JCoFunction function) throws MesException {
        String msg = function.getExportParameterList().getString("OUT_MESSAGE");
        String type = function.getExportParameterList().getString("OUT_TYPE");
        if ("E".equals(type)) {
            throw new MesException(msg);
        }
    }
}
