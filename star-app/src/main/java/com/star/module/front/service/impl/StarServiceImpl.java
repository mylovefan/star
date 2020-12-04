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
import com.star.module.front.dao.HitListMapper;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Star;
import com.star.module.front.service.IHitListService;
import com.star.module.front.service.IStarService;
import com.star.module.front.vo.StarInfoVo;
import com.star.module.operation.entity.StarTags;
import com.star.module.operation.entity.Tags;
import com.star.module.operation.model.StatModel;
import com.star.module.operation.service.IStarTagsService;
import com.star.module.operation.service.ITagsService;
import com.star.module.operation.util.DateUtils;
import com.star.module.operation.util.ListUtils;
import com.star.module.operation.util.RandomUtils;
import com.star.module.operation.dto.StarDto;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.vo.StartVo;
import com.star.util.SnowflakeId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
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
@Slf4j
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements IStarService {

    private static final String HOTSEARCH = "热门搜索";

    @Autowired
    private StarMapper starMapper;
    @Autowired
    private ListUtils listUtils;
    @Autowired
    private IStarTagsService iStarTagsService;
    @Autowired
    private ITagsService iTagsService;
    @Autowired
    private IHitListService iHitListService;
    @Autowired
    private HitListMapper hitListMapper;

    @Override
    public PageSerializable<StartVo> selectPage(StarPageDto starPageDto) {

        getStarRank(0, DateUtils.getWeekStart(new Date()), DateUtils.getWeekEnd(new Date()));
        getStarRank(1, DateUtils.getMonthStart(new Date()), DateUtils.getMonthEnd(new Date()));

        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(starPageDto.getName())) {
            queryWrapper.lambda().like(Star::getName, starPageDto.getName());
        }
        if (starPageDto.getId() != null) {
            queryWrapper.lambda().like(Star::getId, starPageDto.getId());
        }
        IPage page = new Page(starPageDto.getPageNum(), starPageDto.getPageSize());
        IPage<Star> pageList = starMapper.selectPage(page, queryWrapper);
        List<StartVo> list = new ArrayList<>();
        listUtils.copyList(pageList.getRecords(), list, StartVo.class);

        PageSerializable<StartVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(pageList.getTotal());
        return pageSerializable;
    }

    @Override
    public void addStar(StarDto dto) {
        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Star::getName, dto.getName());
        Star star = starMapper.selectOne(queryWrapper);
        if (star != null) {
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(), "该明星已存在");
        }
        star = new Star();
        BeanUtils.copyProperties(dto, star);
        if (StringUtils.isNotEmpty(star.getTags())) {
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
        if (dto.getId() == null) {
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getValue());
        }
        Star star = starMapper.selectById(dto.getId());
        if (star == null) {
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "明星信息不存在");
        }
        BeanUtils.copyProperties(dto, star);
        if (StringUtils.isNotEmpty(star.getTags())) {
            if (star.getTags().contains(HOTSEARCH)) {
                star.setHotSearch(NumberUtils.INTEGER_ONE);
            } else {
                star.setHotSearch(NumberUtils.INTEGER_ZERO);
            }
        }
        starMapper.updateById(star);
        this.tagsSet(dto, star);
    }

    private void tagsSet(StarDto dto, Star star) {
        /** 标签关联标签处理 */
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            iStarTagsService.deleteByStarId(star.getId());

            List<StarTags> starTagsList = new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            Map<Long, String> tags = dto.getTags();
            for (Map.Entry<Long, String> m : tags.entrySet()) {
                if (m.getKey() == null) {
                    throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "标签id错误");
                }
                if (StringUtil.isEmpty(m.getValue())) {
                    throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "标签为空");
                }
                QueryWrapper<Tags> wrapper = new QueryWrapper();
                wrapper.lambda().eq(Tags::getId, m.getValue());
                Tags t = iTagsService.getOne(wrapper);
                if (t == null) {
                    throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "标签不存在，请先添加标签再关联明星");
                }
                StarTags tag = new StarTags();
                tag.setStarId(star.getId());
                tag.setTagsId(m.getKey());
                tag.setTagsName(m.getValue());
                starTagsList.add(tag);
                sb.append(m.getValue()).append(",");
            }
            iStarTagsService.saveBatch(starTagsList);
            star.setTags(sb.toString().substring(0, sb.length() - 1));
            starMapper.updateById(star);
        }
    }

    public void getStarRank(int type, Date startTime, Date endTime){
        List<Star> starList = starMapper.selectList(new QueryWrapper<>());
        log.info("==============被统计明星数："+starList.size()+"==============");
        if(starList.size()>0) {
            starList.stream().forEach(sl -> {
                if(type==1)sl.setThisMonthRank(NumberUtils.INTEGER_ZERO);
                if(type==0)sl.setThisWeekRank(NumberUtils.INTEGER_ZERO);
            });

            List<StatModel> modelList = new ArrayList<>();
            listUtils.copyList(starList, modelList, StatModel.class);
            modelList.stream().forEach(item ->{
                int vigourVal = iHitListService.statisticsRankByTime(item.getId(), startTime, endTime);
                item.setVigourVal(vigourVal);
            });
            modelList.sort(Comparator.comparing(StatModel::getVigourVal).reversed());

            for (int i = 0; i < modelList.size() ; i++) {
                Star star = new Star();
                BeanUtils.copyProperties(modelList.get(i), star);

                if(type ==0) {
                    star.setThisWeekRank(i+1);
                }else{
                    star.setThisMonthRank(i+1);
                }
                starMapper.updateById(star);
            }
        }
    }

    @Override
    public List<String> hotSearch() {
        QueryWrapper<Star> wrapper = new QueryWrapper();
        wrapper.lambda().eq(Star::getHotSearch, NumberUtils.INTEGER_ONE);
        List<Star> list = starMapper.selectList(wrapper);
        List<String> strList = list.stream().map(Star::getName).collect(Collectors.toList());
        return strList;
    }

    @Override
    public StarInfoVo selectStarInfo(Long id) {
        Star star = starMapper.selectById(id);
        StarInfoVo starInfoVo = new StarInfoVo();
        BeanUtils.copyProperties(star, starInfoVo);

        //查询周榜名词
        String weekStart = DateUtils.getTimeStampStr(DateUtils.getWeekStart(new Date()));
        String weekEnd = DateUtils.getTimeStampStr(DateUtils.getWeekEnd(new Date()));
        int weekRank = hitListMapper.getThisRank(id,weekStart,weekEnd);
        starInfoVo.setThisWeekRank(weekRank);
        //查询月榜名称
        String monthStart = DateUtils.getTimeStampStr(DateUtils.getMonthStart(new Date()));
        String monthEnd = DateUtils.getTimeStampStr(DateUtils.getMonthEnd(new Date()));
        hitListMapper.getThisRank(id,monthStart,monthEnd);
        starInfoVo.setThisMonthRank(weekRank);
        return starInfoVo;
    }
}
