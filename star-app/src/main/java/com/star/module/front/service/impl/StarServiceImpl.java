package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageSerializable;
import com.github.pagehelper.util.StringUtil;
import com.star.commen.dto.PageDTO;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.HitListMapper;
import com.star.module.front.entity.Star;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.service.IHitListService;
import com.star.module.front.service.IStarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.module.operation.util.DateUtils;
import com.star.module.operation.util.ListUtils;
import com.star.module.user.dto.StarDto;
import com.star.module.user.dto.StarPageDto;
import com.star.module.user.entity.User;
import com.star.module.user.vo.StartVo;
import com.star.module.user.vo.UserVo;
import com.star.util.SnowflakeId;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static final String HOTSEARCH = "热门搜索";
    @Autowired
    private StarMapper starMapper;

    @Autowired
    private ListUtils listUtils;


    @Override
    public PageSerializable<StartVo> selectPage(StarPageDto starPageDto) {
        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        if(StringUtil.isNotEmpty(starPageDto.getName())){
            queryWrapper.lambda().like(Star::getName,starPageDto.getName());
        }
        if(starPageDto.getId()!=null){
            queryWrapper.lambda().like(Star::getId,starPageDto.getId());
        }
        IPage page = new Page(starPageDto.getPageNum(), starPageDto.getPageSize());
        IPage<Star> pageList = starMapper.selectPage(page, queryWrapper);
        List<StartVo> list = new ArrayList<>();
        listUtils.copyList(pageList.getRecords(),list, StartVo.class);

        PageSerializable<StartVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(pageList.getTotal());
        return pageSerializable;
    }

    @Override
    public void addStar(StarDto dto) {
        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Star::getName, dto.getName());
        Star star = starMapper.selectOne(queryWrapper);
        if(star!=null){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"该明星已存在");
        }
        BeanUtils.copyProperties(dto,star);
        if(StringUtils.isNotEmpty(star.getTags())){
            if (star.getTags().contains(HOTSEARCH)) {
                star.setHotSearch(NumberUtils.INTEGER_ONE);
            } else {
                star.setHotSearch(NumberUtils.INTEGER_ZERO);
            }
        }
        star.setId(SnowflakeId.getInstance().nextId());
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        star.setCreateTime(localDateTimeOfNow);
        starMapper.insert(star);
    }

    @Override
    public void updateStar(StarDto dto) {
        if(dto.getId()!=null){
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),ErrorCodeEnum.PARAM_ERROR.getValue());
        }
        Star star = new Star();
        BeanUtils.copyProperties(dto,star);
        if(StringUtils.isNotEmpty(star.getTags())){
            if (star.getTags().contains(HOTSEARCH)) {
                star.setHotSearch(NumberUtils.INTEGER_ONE);
            } else {
                star.setHotSearch(NumberUtils.INTEGER_ZERO);
            }
        }

        starMapper.updateById(star);
    }
}
