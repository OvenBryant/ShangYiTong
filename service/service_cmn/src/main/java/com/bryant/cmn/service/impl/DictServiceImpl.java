package com.bryant.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bryant.cmn.mapper.DictMapper;
import com.bryant.cmn.service.IDictService;
import com.bryant.yygh.model.cmn.Dict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

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
}
