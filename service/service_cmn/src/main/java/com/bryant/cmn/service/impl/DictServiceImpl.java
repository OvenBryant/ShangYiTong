package com.bryant.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bryant.cmn.listener.DictListener;
import com.bryant.cmn.mapper.DictMapper;
import com.bryant.cmn.service.IDictService;
import com.bryant.yygh.model.cmn.Dict;
import com.bryant.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {


    public String show() {
        return "你好";
    }

    /**
     * 进行了多次数据库查询,造成较高的资源消耗和延迟。
     */
//    @Override
//    public List<Dict> findChildData(Long id) {
//        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
//        //创建条件：根据id查询子数据  id与parent_id相同的数据
//        queryWrapper.eq("parent_id",id);
//
//        List<Dict> dictList = baseMapper.selectList(queryWrapper);
//
//        for (Dict dict : dictList) {
//            Long dictId = dict.getId();
//            boolean hasChild = this.hasChild(dictId);
//            //向dictList集合中设置是否有子节点
//            dict.setHasChildren(hasChild);
//        }
//        return dictList;
//    }
//
//
//    /**
//     * 封装是否有子节点方法
//     *
//     * @param id
//     * @return
//     */
//    //判断id是否是子节点
//    private boolean hasChild(Long id) {
//        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
//
//        //创建条件：根据id查询子数据
//        queryWrapper.eq("parent_id",id);
//        Long count = baseMapper.selectCount(queryWrapper);
//        //如果大于0就是true，反之false
//        return count > 0;
//    }
    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);

        List<Dict> dictList = baseMapper.selectList(queryWrapper);// 展开市 第二层

        // 获取所有子节点的ID
        List<Long> childIds = dictList.stream().map(Dict::getId).collect(Collectors.toList());

        if (!childIds.isEmpty()) {
            QueryWrapper<Dict> childQueryWrapper = new QueryWrapper<>();
            childQueryWrapper.in("parent_id", childIds);
            List<Dict> childDictList = baseMapper.selectList(childQueryWrapper);  // 展开对应 市 以下的列表区

            // 将子节点信息添加到父节点对象中
            for (Dict dict : dictList) {
                Long dictId = dict.getId();
                boolean hasChild = childDictList.stream().anyMatch(d -> d.getParentId().equals(dictId));
                dict.setHasChildren(hasChild);
            }
        }

        return dictList;
    }

    /**
     * 导出数据词典数据
     *
     * @param response
     */
    @Override
    public void exportDictData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("数据字典", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");


            List<Dict> dictList = baseMapper.selectList(null);

            List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
            for (Dict dict : dictList) {
                DictEeVo dictVo = new DictEeVo();
                BeanUtils.copyProperties(dict, dictVo);
                dictVoList.add(dictVo);
            }
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典").doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 上传数据
     *
     * @param file
     */
    @Override
    public void uploadData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
