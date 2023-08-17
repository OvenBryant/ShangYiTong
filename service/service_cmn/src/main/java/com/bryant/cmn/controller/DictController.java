package com.bryant.cmn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bryant.cmn.service.IDictService;
import com.bryant.yygh.common.result.Result;
import com.bryant.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Autowired
    private IDictService dictService;


    /**
     * 根据id查看数据词典信息，再根据parent_id查看是否有子节点,有则return
     *
     * @param id
     * @return
     */
    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("/findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }



    /**
     * 设置响应头格式，并执行EasyExcel的导出操作
     *
     * @param response
     * @return
     */
    //导出数据词典接口
    @ApiOperation(value = "下载数据词典")
    @GetMapping("/exportDict")
    public Result exportDict(HttpServletResponse response) {

        dictService.exportDictData(response);
        return Result.ok();
    }




}