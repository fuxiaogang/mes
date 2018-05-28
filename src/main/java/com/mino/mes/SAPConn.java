package com.mino.mes;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author fuxg
 * @create 2018-05-25 0:06
 **/
public class SAPConn {

    static JCoDestination destination = null;

    /**
     * 获取SAP连接
     *
     * @return SAP连接对象
     */
    public static JCoDestination getConnect() {
        if (destination == null) {
            synchronized (SAPConn.class) {
                if (destination == null) {
                    try {
                        destination = JCoDestinationManager.getDestination("sapconnect");
                    } catch (JCoException e) {
                        log.error("Connect SAP fault, error msg: " + e.toString());
                    }
                }
            }
        }
        return destination;
    }

    public static void main(String[] args) throws Exception {
        getConnect().getRepository();
    }

    private static Logger log = Logger.getLogger(SAPConn.class); // 初始化日志对象
}
