package com.mino.mes.controller;

import com.mino.mes.service.AssemblyServiceImpl;
import com.mino.mes.utils.MesException;
import com.mino.mes.vo.*;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fuxg
 * @create 2018-05-25 下午6:43
 **/
@RequestMapping("/assembly")
@Controller
public class MesAssemblyContoller {

    Logger logger = LoggerFactory.getLogger(MesAssemblyContoller.class);

    @Autowired
    AssemblyServiceImpl assemblyService;


    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listEtPlist")
    public MesResponse<List<AssemblyEtPlist>> listEtPlist(String ECODE) {
        List<AssemblyEtPlist> assemblyEtPlists = new LinkedList<>();
        Integer code;
        String msg;
        if (!StringUtils.isEmpty(ECODE)) {
            try {
                assemblyEtPlists = assemblyService.findEtPlist(ECODE);
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
        } else {
            logger.error("param:ECODE required!");
            code = MesResponse.ERROR_CODE;
            msg = "机台号必填";
        }
        return new MesResponse<>(code, msg, assemblyEtPlists);
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listEtStats")
    public MesResponse<List<Stats>> listEtStats(String ECODE) {
        List<Stats> statsList = new LinkedList<>();
        Integer code;
        String msg;
        if (!StringUtils.isEmpty(ECODE)) {
            try {
                statsList = assemblyService.findEtStats(ECODE);
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
        } else {
            logger.error("param:ECODE required!");
            code = MesResponse.ERROR_CODE;
            msg = "机台号必填";
        }
        return new MesResponse<>(code, msg, statsList);
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listEtUnit")
    public MesResponse<List<AssemblyEtUnit>> listEtUnit(String IN_EQUIP) {
        List<AssemblyEtUnit> assemblyEtUnits = new LinkedList<>();
        Integer code;
        String msg;
        if (!StringUtils.isEmpty(IN_EQUIP)) {
            try {
                assemblyEtUnits = assemblyService.findEtUnit(IN_EQUIP);
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
        } else {
            logger.error("param:IN_EQUIP required!");
            code = MesResponse.ERROR_CODE;
            msg = "设备号必填";
        }
        return new MesResponse<>(code, msg, assemblyEtUnits);
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listEsOrder")
    public MesResponse<AssemblyEsOrder> listEsOrder(String IN_EQUIP) {
        AssemblyEsOrder assemblyEsOrder = new AssemblyEsOrder();
        Integer code;
        String msg;
        if (!StringUtils.isEmpty(IN_EQUIP)) {
            try {
                assemblyEsOrder = assemblyService.findEsOrder(IN_EQUIP);
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
        } else {
            logger.error("param:IN_EQUIP required!");
            code = MesResponse.ERROR_CODE;
            msg = "设备号必填";
        }
        return new MesResponse<>(code, msg, assemblyEsOrder);
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/start")
    public MesResponse<String> start(final String IN_ECODE, final String IN_ZSTAT, final Long IN_PERNR, final String IN_EQUIP, final String IN_UNUMB) {
        Integer code = null;
        String msg = null;
        if (StringUtils.isEmpty(IN_ECODE)) {
            msg = "机台号不能为空";
            code = MesResponse.ERROR_CODE;
        }
        if (StringUtils.isEmpty(IN_ZSTAT)) {
            msg = "状态不能为空";
            code = MesResponse.ERROR_CODE;
        }
        if (StringUtils.isEmpty(IN_PERNR)) {
            msg = "员工编号不能为空";
            code = MesResponse.ERROR_CODE;
        }
        if (StringUtils.isEmpty(IN_EQUIP)) {
            msg = "设备号不能为空";
            code = MesResponse.ERROR_CODE;
        }

        if (msg == null) {
            try {
                assemblyService.start(IN_ECODE, IN_ZSTAT, IN_PERNR, IN_EQUIP, IN_UNUMB);
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
        }
        return new MesResponse<>(code, msg);
    }

    /**
     * 結束
     *
     * @param IN_EVEID
     * @return
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/stop")
    public MesResponse<String> stop(String IN_EVEID) {
        Integer code = null;
        String msg = null;
        if (StringUtils.isEmpty(IN_EVEID)) {
            msg = "事件id不能为空";
            code = MesResponse.ERROR_CODE;
        }
        if (msg == null) {
            try {
                assemblyService.stop(IN_EVEID);
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
        }
        return new MesResponse<>(code, msg);
    }

    /**
     * 生产看板
     *
     * @param IN_DATE
     * @return
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listBoard")
    public MesResponse<AssemblyBoard> listBoard(String IN_DATE) {
        if (StringUtils.isEmpty(IN_DATE)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            IN_DATE = sdf.format(new Date());
        }
        Integer code = null;
        String msg = null;
        AssemblyBoard assemblyBoard = null;
        try {
            assemblyBoard = assemblyService.findBoard(IN_DATE);
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
        return new MesResponse<>(code, msg, assemblyBoard);
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/serverDate")
    public String serverDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

}

