package com.bryant.cmn;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bryant.cmn.controller.DictController;
import com.bryant.yygh.common.result.Result;
import com.bryant.yygh.model.cmn.Dict;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class logTest {

    Logger logger = LoggerFactory.getLogger(DictController.class);

    @Test
    public void test(){
        log.info("==============");
        log.debug("=============");
        log.error("==============");
        log.trace("================");
        log.warn("================");

        logger.info("===============");
        logger.debug("=============");
        logger.error("==============");
        logger.trace("================");
        logger.warn("================");

    }
}



