package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.module.operation.dao.StarTagsMapper;
import com.star.module.operation.entity.StarTags;
import com.star.module.operation.service.IStarTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 明星标签表 服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Service
public class StarTagsServiceImpl extends ServiceImpl<StarTagsMapper, StarTags> implements IStarTagsService {

    @Autowired
    private StarTagsMapper starTagsMapper;

    @Override
    public void deleteByStarId(Long id) {
        QueryWrapper<StarTags> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StarTags::getStarId, id);
        starTagsMapper.delete(queryWrapper);
    }
}
