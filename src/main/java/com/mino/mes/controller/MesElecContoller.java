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
     * @param IN_DATE
     * @return
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/listBoard")
    public MesResponse<ElecBoard> listBoard(String IN_DATE) {
        if (StringUtils.isEmpty(IN_DATE)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            IN_DATE = sdf.format(new Date());
        }
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

}

