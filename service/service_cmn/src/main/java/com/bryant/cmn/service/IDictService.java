package com.bryant.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bryant.yygh.model.cmn.Dict;

import java.util.List;

public interface IDictService extends IService<Dict> {
    List<Dict> findChildData(Long id);
}
