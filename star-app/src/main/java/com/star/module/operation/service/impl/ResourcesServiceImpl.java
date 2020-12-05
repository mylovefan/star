package com.star.module.operation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.github.pagehelper.util.StringUtil;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.FensJoinMapper;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.dto.ResourcesRankDto;
import com.star.module.front.entity.Star;
import com.star.module.front.vo.FensJoinResVo;
import com.star.module.front.vo.StarResourcesVo;
import com.star.module.operation.dao.ListAwardMapper;
import com.star.module.operation.dao.ResourcesMapper;
import com.star.module.operation.dao.StarResourcesRelMapper;
import com.star.module.operation.dao.StarTagsMapper;
import com.star.module.operation.dto.*;
import com.star.module.operation.entity.ListAward;
import com.star.module.operation.entity.Resources;
import com.star.module.operation.entity.StarResourcesRel;
import com.star.module.operation.entity.StarTags;
import com.star.module.operation.service.IResourcesService;
import com.star.module.operation.vo.ListAwardVo;
import com.star.module.operation.vo.ResourcesDetailVo;
import com.star.module.operation.vo.ResourcesVo;
import com.star.module.user.common.UserUtil;
import com.star.util.SnowflakeId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private ListAwardMapper listAwardMapper;

    @Autowired
    private FensJoinMapper fensJoinMapper;

    @Autowired
    private HttpServletRequest request;

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
        IPage<Resources> page = new Page<>(resourcesPageDto.getPageNum(),resourcesPageDto.getPageSize());
        QueryWrapper<Resources> queryWrapper = new QueryWrapper<>();
        if(StringUtil.isNotEmpty(resourcesPageDto.getBeginTime())){
            queryWrapper.lambda().ge(Resources::getAddTime,resourcesPageDto.getBeginTime());
        }
        if(StringUtil.isNotEmpty(resourcesPageDto.getEndTime())){
            queryWrapper.lambda().le(Resources::getAddTime,resourcesPageDto.getEndTime());
        }
        if(resourcesPageDto.getType() != null){
            queryWrapper.lambda().eq(Resources::getType,resourcesPageDto.getType());
        }
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        if(resourcesPageDto.getStatus() != null){
            if(resourcesPageDto.getStatus() == 1){
                queryWrapper.lambda().le(Resources::getBeginTime,localDateTimeOfNow);
            }else if(resourcesPageDto.getStatus() == 2){
                queryWrapper.lambda().ge(Resources::getBeginTime,localDateTimeOfNow);
                queryWrapper.lambda().le(Resources::getEndTime,localDateTimeOfNow);
            }else {
                queryWrapper.lambda().ge(Resources::getEndTime,localDateTimeOfNow);
            }

        }
        queryWrapper.orderByDesc("add_time");
        IPage<Resources> resourcesIPage = resourcesMapper.selectPage(page, queryWrapper);
        List<ResourcesVo> list = new ArrayList<>();
        for (Resources resources : resourcesIPage.getRecords()){
            ResourcesVo resourcesVo = new ResourcesVo();
            BeanUtils.copyProperties(resources,resourcesVo);
            if(localDateTimeOfNow.isAfter(resources.getBeginTime()) || localDateTimeOfNow.isBefore(resources.getEndTime())) {
                resourcesVo.setStatus(2);
            }
            if(localDateTimeOfNow.isAfter(resources.getEndTime())) {
                resourcesVo.setStatus(3);
            }
            if(localDateTimeOfNow.isBefore(resources.getBeginTime())) {
                resourcesVo.setStatus(1);
            }
            list.add(resourcesVo);
        }
        PageSerializable<ResourcesVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(resourcesIPage.getTotal());
        return pageSerializable;
    }

    @Override
    public ResourcesDetailVo selectResources(Long id) {
        Resources resources = resourcesMapper.selectById(id);
        ResourcesDetailVo resourcesVo = new ResourcesDetailVo();
        BeanUtils.copyProperties(resources,resourcesVo);
        resourcesVo.setTags(JSONArray.parseArray(resources.getTags(),TagsDto.class));

        //查询明星
        QueryWrapper<StarResourcesRel> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(StarResourcesRel::getResourcesId,id);
        List<StarResourcesRel> starResourcesRels = starResourcesRelMapper.selectList(wrapper);
        List<Long> starIds = starResourcesRels.stream().map(StarResourcesRel::getStarId).collect(Collectors.toList());
        resourcesVo.setStarIds(starIds);
        return resourcesVo;
    }

    @Override
    public void addOrUpdateListAward(ListAwardDto listAwardDto) {
        if(!"WEEK".equals(listAwardDto.getCode()) && !"MONTH".equals(listAwardDto.getCode())){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"CODE错误");
        }
        ListAward listAward = new ListAward();
        BeanUtils.copyProperties(listAwardDto,listAward);

        QueryWrapper<ListAward> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(ListAward::getCode,listAwardDto.getCode());
        Integer count = listAwardMapper.selectCount(wrapper);
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        if(count != null && count > 0){
            listAward.setAddTime(localDateTimeOfNow);
            listAwardMapper.update(listAward,wrapper);
        }else {
            listAward.setId(SnowflakeId.getInstance().nextId());
            listAward.setAddTime(localDateTimeOfNow);
            listAwardMapper.insert(listAward);
        }
    }


    @Override
    public ListAwardVo selectListAward(String code) {
        QueryWrapper<ListAward> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(ListAward::getCode,code);
        ListAward listAward = listAwardMapper.selectOne(wrapper);
        ListAwardVo listAwardVo = new ListAwardVo();
        if(listAward == null){
            return listAwardVo;
        }
        BeanUtils.copyProperties(listAward,listAwardVo);
        return listAwardVo;
    }

    @Override
    public PageSerializable<StarResourcesVo> selectResources(StarPageDto starPageDto) {

        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PageHelper.startPage(starPageDto.getPageNum(),starPageDto.getPageSize());
        com.github.pagehelper.Page<StarResourcesVo> resourcesIPage = resourcesMapper.selectStarResources(starPageDto.getId(), localDateTimeOfNow.format(df));
        for (StarResourcesVo starResourcesVo : resourcesIPage.getResult()){
            List<String> fens = fensJoinMapper.selectFens(starResourcesVo.getResourcesRelId());
            starResourcesVo.setFensList(fens);
        }
        PageSerializable<StarResourcesVo> pageSerializable = new PageSerializable<>(resourcesIPage.getResult());
        pageSerializable.setTotal(resourcesIPage.getTotal());
        return pageSerializable;
    }

    @Override
    public PageSerializable<FensJoinResVo> selectResourcesRank(ResourcesRankDto resourcesRankDto) {
        Long id = UserUtil.getCurrentUserId(request);
        PageHelper.startPage(resourcesRankDto.getPageNum(),resourcesRankDto.getPageSize());
        com.github.pagehelper.Page<FensJoinResVo> resourcesIPage = fensJoinMapper.selectFensResources(resourcesRankDto.getResourcesRelId());
        for (FensJoinResVo fensJoinResVo : resourcesIPage.getResult()){
            if(id != null && id.longValue() == fensJoinResVo.getFensId().longValue()){
                fensJoinResVo.setFlag(true);
            }else {
                fensJoinResVo.setFlag(false);
            }
        }
        PageSerializable<FensJoinResVo> pageSerializable = new PageSerializable<>(resourcesIPage.getResult());
        pageSerializable.setTotal(resourcesIPage.getTotal());
        return pageSerializable;
    }
}
