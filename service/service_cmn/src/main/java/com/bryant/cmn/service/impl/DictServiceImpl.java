package com.bryant.cmn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bryant.cmn.mapper.DictMapper;
import com.bryant.cmn.service.IDictService;
import com.bryant.yygh.model.cmn.Dict;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
}
