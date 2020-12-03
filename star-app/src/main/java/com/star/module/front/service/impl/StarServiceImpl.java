package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageSerializable;
import com.github.pagehelper.util.StringUtil;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Star;
import com.star.module.front.service.IStarService;
import com.star.module.operation.entity.StarTags;
import com.star.module.operation.service.IStarTagsService;
import com.star.module.operation.util.ListUtils;
import com.star.module.operation.util.RandomUtils;
import com.star.module.operation.dto.StarDto;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.vo.StartVo;
import com.star.util.SnowflakeId;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private IStarTagsService iStarTagsService;


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
        star = new Star();
        BeanUtils.copyProperties(dto,star);
        if(StringUtils.isNotEmpty(star.getTags())){
            if (star.getTags().contains(HOTSEARCH)) {
                star.setHotSearch(NumberUtils.INTEGER_ONE);
            } else {
                star.setHotSearch(NumberUtils.INTEGER_ZERO);
            }
        }

        QueryWrapper<Star> query = new QueryWrapper<>();
        List<Star> starList = starMapper.selectList(query);
        List<Long> starIds = starList.stream().map(Star::getStarId).collect(Collectors.toList());
        Long newStarId = RandomUtils.randomNumber6(starIds);

        star.setStarId(newStarId);
        star.setId(SnowflakeId.getInstance().nextId());
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        star.setCreateTime(localDateTimeOfNow);
        starMapper.insert(star);
        this.tagsSet(dto, star);
    }

    @Override
    public void updateStar(StarDto dto) {
        if(dto.getId()==null){
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),ErrorCodeEnum.PARAM_ERROR.getValue());
        }
        Star star = starMapper.selectById(dto.getId());
        if(star==null){
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"明星信息不存在");
        }
        BeanUtils.copyProperties(dto,star);
        if(StringUtils.isNotEmpty(star.getTags())){
            if (star.getTags().contains(HOTSEARCH)) {
                star.setHotSearch(NumberUtils.INTEGER_ONE);
            } else {
                star.setHotSearch(NumberUtils.INTEGER_ZERO);
            }
        }
        starMapper.updateById(star);
        this.tagsSet(dto, star);
    }

    private void tagsSet(StarDto dto, Star star){
        /** 标签关联标签处理 */
        if(dto.getTags()!=null && !dto.getTags().isEmpty()){
            iStarTagsService.deleteByStarId(star.getId());

            List<StarTags> starTagsList = new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            Map<Long, String> tags = dto.getTags();
            for (Map.Entry<Long, String> m : tags.entrySet()) {
                if(m.getKey()==null){
                    throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"标签id错误");
                }
                if(StringUtil.isEmpty(m.getValue())){
                    throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"标签为空");
                }
                StarTags tag = new StarTags();
                tag.setStarId(star.getId());
                tag.setTagsId(m.getKey());
                tag.setTagsName(m.getValue());
                starTagsList.add(tag);
                sb.append(m.getValue()).append(",");
            }
            iStarTagsService.saveBatch(starTagsList);

            star.setTags(sb.toString());
            starMapper.updateById(star);
        }
    }
    //eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6IjEiLCJpYXQiOjE2MDY5NjE2MTMsInVzZXJfbmFtZSI6IueuoeeQhuWRmCIsInVwZGF0ZVNlY29uZHMiOjE2MDY5NjUyMTMzMTMsImV4cCI6MTYwNjk3MjQxM30.t7RdBqwW0ozuS_SfBo_sCQZGqKN825x3o6SAqxIvb8bN_YYHwmQUNnMvtZwBy9KynY51z3nvOlHxUkza4Pr9xw
}
