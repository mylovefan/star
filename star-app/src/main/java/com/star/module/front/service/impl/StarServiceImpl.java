package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageSerializable;
import com.github.pagehelper.util.StringUtil;
import com.star.commen.dto.PageDTO;
import com.star.module.front.entity.Star;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.service.IStarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.module.user.vo.StartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 明星表 服务实现类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements IStarService {

    @Autowired
    private StarMapper starMapper;

    @Override
    public PageSerializable<StartVo> selectPage(PageDTO pageDTO, String name, Long id) {
        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        if(StringUtil.isNotEmpty(name)){
            queryWrapper.lambda().like(Star::getName,name);
        }
        if(id!=null){
            queryWrapper.lambda().like(Star::getId,id);
        }
        IPage page = new Page(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<Star> pageList = starMapper.selectPage(page, queryWrapper);


        return null;

    }
}
