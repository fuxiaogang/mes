package com.mino.mes;

import com.sap.conn.jco.*;

import java.util.HashMap;

/**
 * @author fuxg
 * @create 2018-05-24 下午11:09
 **/
public class Test {

    public static void main(String[] args) {
//        JCoFunction function = null;
//        JCoDestination destination = SAPConn.getConnect();
//        String sum="";//求和
//        try {
//            //调用Z_WS_TEST函数
//            function = destination.getRepository().getFunction("ZMES_IF001");
//            JCoParameterList input = function.getImportParameterList();
//            //NUM1
//        input.setValue("IN_ECODE", "ME010");
//
//            function.execute(destination);
//            String msg = function.getExportParameterList().getString("OUT_MESSAGE");//调用接口返回值
//            String type= function.getExportParameterList().getString("OUT_TYPE");//调用接口返回值
//            JCoParameterList exportTable= function.getTableParameterList();
//
//            JCoTable getTable1 = exportTable.getTable("ET_PLIST");// 这是调用后 RFC 返回的表名
//
//            boolean loopFlag1 = !getTable1.isEmpty(); //判断 这张表中有木有数据
//
//            while(loopFlag1){ //循环获取数据
//              String getAreaNum = getTable1.getString("ECODE");  //根据表字段来获取值
//                System.out.println(getAreaNum);
//                loopFlag1 = getTable1.nextRow(); // 移动到下一行
//            }
//
//            System.out.println("message:" + msg);
//            System.out.println("type:" + type);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
////        connnect();
        System.out.println(100%70);
        System.out.println(7300%60);
    }

    public static JCoDestination connect() {
        JCoDestination destination = null;
        try {
            destination = JCoDestinationManager.getDestination("E:\\myworkspace\\mes\\src\\main\\resources\\ABAP_AS_WITH_POOL.jcoDestination");
            System.out.println(destination);

        } catch (JCoException e) {
//            log.error("Connect SAP fault, error msg: " + e.toString());
            e.printStackTrace();
        }
        return destination;
    }
}
