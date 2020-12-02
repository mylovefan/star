package com.star.module.operation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageSerializable;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Star;
import com.star.module.operation.dao.ResourcesMapper;
import com.star.module.operation.dao.StarResourcesRelMapper;
import com.star.module.operation.dao.StarTagsMapper;
import com.star.module.operation.dto.ResourcesDto;
import com.star.module.operation.dto.ResourcesPageDto;
import com.star.module.operation.dto.TagsDto;
import com.star.module.operation.entity.Resources;
import com.star.module.operation.entity.StarResourcesRel;
import com.star.module.operation.entity.StarTags;
import com.star.module.operation.service.IResourcesService;
import com.star.module.operation.util.DateUtils;
import com.star.module.operation.vo.ResourcesDetailDto;
import com.star.module.operation.vo.ResourcesVo;
import com.star.util.SnowflakeId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements IResourcesService {

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private StarResourcesRelMapper starResourcesRelMapper;

    @Autowired
    private StarTagsMapper starTagsMapper;

    @Autowired
    private StarMapper starMapper;

    @Override
    @Transactional
    public void addOrUpdateResources(ResourcesDto resourcesDto) {

        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        Resources resources = new Resources();
        BeanUtils.copyProperties(resourcesDto,resources);
        resources.setTags(JSON.toJSONString(resourcesDto.getTags()));
        List<Long> starIds = resourcesDto.getTags().stream().map(TagsDto::getId).collect(Collectors.toList());
        QueryWrapper<StarTags> starTagsQueryWrapper = new QueryWrapper<>();
        starTagsQueryWrapper.lambda().in(StarTags::getTagsId,starIds);
        List<StarTags> starTags = starTagsMapper.selectList(starTagsQueryWrapper);
        List<Long> starList = new ArrayList<>();
        for (StarTags tags : starTags){
            starList.add(tags.getStarId());
        }
        for (Long starId : resourcesDto.getStarIds()){
            QueryWrapper<Star> starWrapper = new QueryWrapper<>();
            starWrapper.lambda().in(Star::getId,starId);
            Integer count = starMapper.selectCount(starWrapper);
            if(count != null && count > 0){
                starList.add(starId);
            }
        }

        if(resourcesDto.getId() != null){
            Resources record = resourcesMapper.selectById(resourcesDto.getId());
            if(record == null){
                throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"id错误");
            }

            //判断状态是否进行中
            if(localDateTimeOfNow.isAfter(record.getBeginTime()) || localDateTimeOfNow.isBefore(record.getEndTime())) {
                throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"资源状态进行中，不允许修改");
            }
            if(localDateTimeOfNow.isAfter(record.getEndTime())) {
                throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"资源状态已结束，不允许修改");
            }

            resources.setId(resources.getId());
            resources.setUpdateTime(localDateTimeOfNow);
            resourcesMapper.updateById(resources);

            QueryWrapper<StarResourcesRel> wrapper = new QueryWrapper<>();
            wrapper.lambda().in(StarResourcesRel::getResourcesId,record.getId());
            starResourcesRelMapper.delete(wrapper);
        }else {
            resources.setAddTime(localDateTimeOfNow);
            resourcesMapper.insert(resources);
        }

        for (Long starId : starList){
            StarResourcesRel starResourcesRel = new StarResourcesRel();
            starResourcesRel.setId(SnowflakeId.getInstance().nextId());
            starResourcesRel.setResourcesId(resources.getId());
            starResourcesRel.setStarId(starId);
            starResourcesRel.setAddTime(localDateTimeOfNow);
            starResourcesRelMapper.insert(starResourcesRel);
        }
    }

    @Override
    public PageSerializable<ResourcesVo> selectResourcesPage(ResourcesPageDto resourcesPageDto) {
        return null;
    }

    @Override
    public ResourcesDetailDto selectResources(Long id) {
        return null;
    }
}
