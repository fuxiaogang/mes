package com.mino.mes.service;

import com.mino.mes.SAPConn;
import com.mino.mes.utils.Constant;
import com.mino.mes.utils.MesException;
import com.mino.mes.vo.ElecEsOrder;
import com.mino.mes.vo.ElecEtHistory;
import com.mino.mes.vo.ElecEtPlist;
import com.mino.mes.vo.Stats;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fuxg
 * @create 2018-05-25 21:34
 **/
@Service
public class ElecServiceImpl {

    public static void main(String[] args) throws Exception {
        //  findEtPlist("ME010");
    }

    /**
     * 生产信息
     *
     * @param inEcode
     * @return
     * @throws Exception
     */
    public List<ElecEtPlist> findEtPlist(final String inEcode) throws Exception {
        final String functionName = Constant.FUN_ELEC_STATION_ZMES_IF001;
        final List<ElecEtPlist> datas = new LinkedList<>();

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
            ElecEtPlist elecEtPlist = new ElecEtPlist();
            String ECODE = etPlistTable.getString("ECODE");
            String ECODE_TEXT = etPlistTable.getString("ECODE_TEXT");
            String AUFNR = etPlistTable.getString("AUFNR");
            Long PERNR = etPlistTable.getLong("PERNR");
            String SNAME = etPlistTable.getString("SNAME");
            String ZZEXT = etPlistTable.getString("ZZEXT");
            String RDATE = etPlistTable.getString("RDATE");
            String RTIME = etPlistTable.getString("RTIME");
            Integer PROCT = etPlistTable.getInt("PROCT");
            String BEGIN_TR = etPlistTable.getString("BEGIN_TR");
            String PROCT_TR = etPlistTable.getString("PROCT_TR");
            String ZSTAT = etPlistTable.getString("ZSTAT");
            Long EVEID = etPlistTable.getLong("EVEID");

            elecEtPlist.setEcode(ECODE);
            elecEtPlist.setEcodeText(ECODE_TEXT);
            elecEtPlist.setAufnr(AUFNR);
            elecEtPlist.setPernr(PERNR);
            elecEtPlist.setSname(SNAME);
            elecEtPlist.setZzext(ZZEXT);
            elecEtPlist.setRdate(RDATE);
            elecEtPlist.setRtime(RTIME);
            elecEtPlist.setProct(PROCT);
            elecEtPlist.setBeginTr(BEGIN_TR);
            elecEtPlist.setProctTr(PROCT_TR);
            elecEtPlist.setZstat(ZSTAT);
            elecEtPlist.setEveid(EVEID);
            datas.add(elecEtPlist);
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
        final String functionName = Constant.FUN_ELEC_STATION_ZMES_IF001;
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
     * 历史信息
     *
     * @param IN_ZZEXT
     * @return
     * @throws Exception
     */
    public List<ElecEtHistory> findEtHistory(final String IN_ZZEXT) throws Exception {
        final String functionName = Constant.FUN_ELEC_DRW_ZMES_IF013;
        final List<ElecEtHistory> datas = new LinkedList<>();

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_ZZEXT", IN_ZZEXT);
        function.execute(destination);

        checkSapResponse(function);

        JCoParameterList exportTable = function.getTableParameterList();
        JCoTable table = exportTable.getTable("ET_HISTORY");

        boolean loopFlag1 = !table.isEmpty();
        while (loopFlag1) {
            ElecEtHistory elecEtHistory = new ElecEtHistory();
            String AUFNR = table.getString("AUFNR");
            Long PERNR = table.getLong("PERNR");
            String SNAME = table.getString("SNAME");
            String LTXA1 = table.getString("LTXA1");
            String RDATE = table.getString("RDATE");

            elecEtHistory.setAufnr(AUFNR);
            elecEtHistory.setPernr(PERNR);
            elecEtHistory.setSname(SNAME);
            elecEtHistory.setLtxa1(LTXA1);
            elecEtHistory.setRdate(RDATE);

            datas.add(elecEtHistory);
            loopFlag1 = table.nextRow();
        }
        return datas;
    }

    /**
     * 订单信息
     *
     * @param IN_ZZEXT
     * @return
     * @throws Exception
     */
    public ElecEsOrder findEsOrder(final String IN_ZZEXT) throws Exception {
        final String functionName = Constant.FUN_ELEC_DRW_ZMES_IF013;
        final List<ElecEsOrder> datas = new LinkedList<>();

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_ZZEXT", IN_ZZEXT);
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
        ElecEsOrder elecEsOrder = new ElecEsOrder();
        elecEsOrder.setAufnr(AUFNR);
        elecEsOrder.setPspid(PSPID);
        elecEsOrder.setMatnr(MATNR);
        elecEsOrder.setMaktx(MAKTX);
        elecEsOrder.setPsmng(PSMNG);
        elecEsOrder.setUsr08(USR08);
        elecEsOrder.setUsr09(USR09);

        return elecEsOrder;
    }

    /**
     * 上机
     *
     * @param IN_ECODE 机台号
     * @param IN_ZSTAT 状态
     * @param IN_PERNR 员工编号
     * @param IN_ZZEXT 图号
     * @throws Exception
     */
    public void start(final String IN_ECODE, final String IN_ZSTAT, final Long IN_PERNR, final String IN_ZZEXT) throws Exception {
        final String functionName = Constant.FUN_ELEC_START_ZMES_IF002;

        JCoDestination destination = SAPConn.getConnect();
        JCoFunction function = destination.getRepository().getFunction(functionName);
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IN_ECODE", IN_ECODE);
        input.setValue("IN_ZSTAT", IN_ZSTAT);
        input.setValue("IN_PERNR", IN_PERNR);
        input.setValue("IN_ZZEXT", IN_ZZEXT);
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
        final String functionName = Constant.FUN_ELEC_STOP_ZMES_IF003;

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
