package com.mino.mes.controller;

import com.mino.mes.service.ElecServiceImpl;
import com.mino.mes.utils.MesException;
import com.mino.mes.vo.ElecEtPlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fuxg
 * @create 2018-05-25 下午6:43
 **/
@Controller
public class MesContoller {

    Logger logger = LoggerFactory.getLogger(MesContoller.class);

    @Autowired
    ElecServiceImpl elecService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/elec/listEtPlist" )
    public List<ElecEtPlist> listEtPlist(String ECODE) {
        List<ElecEtPlist> elecEtPlists = new LinkedList<>();
        if(!StringUtils.isEmpty(ECODE)) {
            try {
                elecEtPlists = elecService.findEtPlist(ECODE);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }else{
            logger.error("param:ECODE required!");
        }
        return elecEtPlists;
    }

}

