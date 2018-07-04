package com.mino.mes.controller;

import com.mino.mes.service.ElecServiceImpl;
import com.mino.mes.utils.MesException;
import com.mino.mes.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fuxg
 * @create 2018-05-25 下午6:43
 **/
@RequestMapping("/elec")
@Controller
public class MesElecContoller {

    Logger logger = LoggerFactory.getLogger(MesElecContoller.class);

    @Autowired
    ElecServiceImpl elecService;


    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listEtPlist")
    public List<ElecEtPlist> listEtPlist(String ECODE) {
        List<ElecEtPlist> elecEtPlists = new LinkedList<>();
        if (!StringUtils.isEmpty(ECODE)) {
            try {
                elecEtPlists = elecService.findEtPlist(ECODE);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("param:ECODE required!");
        }
        return elecEtPlists;
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listEtStats")
    public List<Stats> listEtStats(String ECODE) {
        List<Stats> statsList = new LinkedList<>();
        if (!StringUtils.isEmpty(ECODE)) {
            try {
                statsList = elecService.findEtStats(ECODE);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("param:ECODE required!");
        }
        return statsList;
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listEtHistory")
    public List<ElecEtHistory> listEtHistory(String IN_ZZEXT) {
        List<ElecEtHistory> elecEtHistories = new LinkedList<>();
        if (!StringUtils.isEmpty(IN_ZZEXT)) {
            try {
                elecEtHistories = elecService.findEtHistory(IN_ZZEXT);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("param:IN_ZZEXT required!");
        }
        return elecEtHistories;
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listEsOrder")
    public ElecEsOrder listEsOrder(String IN_ZZEXT) {
        ElecEsOrder elecEsOrder = new ElecEsOrder();
        if (!StringUtils.isEmpty(IN_ZZEXT)) {
            try {
                elecEsOrder = elecService.findEsOrder(IN_ZZEXT);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("param:IN_ZZEXT required!");
        }
        return elecEsOrder;
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/start")
    public String start(String IN_ECODE, String IN_ZSTAT, Long IN_PERNR, String IN_ZZEXT) {
        if (StringUtils.isEmpty(IN_ECODE)) {
            return "机台号不能为空";
        }
        if (StringUtils.isEmpty(IN_ZSTAT)) {
            return "状态不能为空";
        }
        if (StringUtils.isEmpty(IN_PERNR)) {
            return "员工编号不能为空";
        }
        if (StringUtils.isEmpty(IN_ZZEXT)) {
            return "图号不能为空";
        }
        try {
            elecService.start(IN_ECODE, IN_ZSTAT, IN_PERNR, IN_ZZEXT);
            return "success";
        } catch (MesException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "fail";
        }
    }

    /**
     * 結束
     *
     * @param IN_EVEID
     * @return
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/stop")
    public String stop(String IN_EVEID) {
        if (StringUtils.isEmpty(IN_EVEID)) {
            return "事件id不能为空";
        }
        try {
            elecService.stop(IN_EVEID);
            return "success";
        } catch (MesException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "fail";
        }
    }

    /**
     * 生产看板
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listBoard")
    public MesResponse<ElecBoard> listBoard() {
        String IN_DATE = "1990-01-01";
        Integer code = null;
        String msg = null;
        ElecBoard elecBoard = null;
        try {
            elecBoard = elecService.findBoard(IN_DATE);
            code = MesResponse.SUCCESS_CODE;
            msg = MesResponse.SUCCESS_MSG;
        } catch (MesException e) {
            logger.error(e.getMessage());
            code = MesResponse.ERROR_CODE;
            msg = e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage());
            code = MesResponse.ERROR_CODE;
            msg = MesResponse.ERROR_MSG;
        }
        return new MesResponse<>(code, msg, elecBoard);
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/viewImg")
    public void viewImg(String IN_ZZEXT, String IN_MODE, HttpServletResponse response) throws Exception {

        if (StringUtils.isEmpty(IN_ZZEXT)) {
            output(null, "图号不能为空", response);
            return;
        }
        try {
            String localFilePath = elecService.viewImg(IN_ZZEXT, IN_MODE);
            output(localFilePath, null, response);
        } catch (MesException e) {
            logger.error(e.getMessage());
            output(null, e.getMessage(), response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            output(null, "打开图纸错误", response);
        }
    }

    private void output(String localFilePath, String errorMsg, HttpServletResponse response) throws Exception {
        File file = null;
        String fileName = null;
        if (localFilePath == null) {
            errorMsg = "找不到图纸";
        } else {
            file = new File(localFilePath);
            fileName = localFilePath.substring(localFilePath.lastIndexOf("//") + 1);
        }

        if (errorMsg == null && !file.exists()) {
            errorMsg = "找不到图纸";
        }
        if (errorMsg == null) {
            //重置response
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //解决中文文件名显示问题
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            //设置文件长度
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);

            if (fileLength != 0) {
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];

                //创建输出流
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;

                //读取文件内容并写入到response的输出流当中
                while ((readLength = inStream.read(buf)) != -1) {
                    servletOS.write(buf, 0, readLength);
                }
                //关闭输入流
                inStream.close();

                //刷新输出流缓冲
                servletOS.flush();

                //关闭输出流
                servletOS.close();
            }
        } else {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(errorMsg);
        }

    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/serverDate")
    public String serverDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}

