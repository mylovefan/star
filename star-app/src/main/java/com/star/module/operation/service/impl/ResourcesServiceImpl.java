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
import com.star.module.front.dao.*;
import com.star.module.front.dto.OpenImgDto;
import com.star.module.front.dto.ResourcesRankDto;
import com.star.module.front.entity.FensJoin;
import com.star.module.front.entity.FensView;
import com.star.module.front.entity.Star;
import com.star.module.front.entity.ViewLimit;
import com.star.module.front.vo.FensJoinResVo;
import com.star.module.front.vo.ListAwardPersionVo;
import com.star.module.front.vo.OpenImgVo;
import com.star.module.front.vo.StarResourcesVo;
import com.star.module.operation.dao.ListAwardMapper;
import com.star.module.operation.dao.ResourcesMapper;
import com.star.module.operation.dao.StarResourcesRelMapper;
import com.star.module.operation.dao.StarTagsMapper;
import com.star.module.operation.dto.*;
import com.star.module.operation.entity.*;
import com.star.module.operation.service.IResourcesService;
import com.star.module.operation.util.DateUtils;
import com.star.module.operation.vo.ListAwardVo;
import com.star.module.operation.vo.ResourceStarVo;
import com.star.module.operation.vo.ResourcesDetailVo;
import com.star.module.operation.vo.ResourcesVo;
import com.star.module.user.common.UserUtil;
import com.star.util.SnowflakeId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Autowired
    private OpenImgMapper openImgMapper;

    @Autowired
    private ViewLimitMapper viewLimitMapper;

    @Autowired
    private FensViewMapper fensViewMapper;

    @Override
    @Transactional
    public void addOrUpdateResources(ResourcesDto resourcesDto) {

        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        Resources resources = new Resources();
        BeanUtils.copyProperties(resourcesDto,resources);
        resources.setBeginTime(DateUtils.parseLocalDateStr(resourcesDto.getBeginTime()));
        resources.setEndTime(DateUtils.parseLocalDateStr(resourcesDto.getEndTime()));
        resources.setTags(JSON.toJSONString(resourcesDto.getTags()));
        List<Long> starIds = resourcesDto.getTags().stream().map(TagsDto::getId).collect(Collectors.toList());
        QueryWrapper<StarTags> starTagsQueryWrapper = new QueryWrapper<>();
        starTagsQueryWrapper.lambda().in(StarTags::getTagsId,starIds);
        List<StarTags> starTags = starTagsMapper.selectList(starTagsQueryWrapper);
        Set<Long> starList = new HashSet<>();
        for (StarTags tags : starTags){
            starList.add(tags.getStarId());
        }
        List<Long> starIds2 = new ArrayList<>();
        if(resourcesDto.getStarIds() != null){
            for (Long starId : resourcesDto.getStarIds()){
                QueryWrapper<Star> starWrapper = new QueryWrapper<>();
                starWrapper.lambda().eq(Star::getStarId,starId);
                Star star = starMapper.selectOne(starWrapper);
                if(star == null){
                    continue;
                }
                if(starList.contains(star.getId())){
                    continue;
                }
                starIds2.add(starId);
                starList.add(star.getId());
            }
            resources.setStarIds(JSON.toJSONString(starIds2));
        }

        if(resourcesDto.getId() != null && resourcesDto.getId().longValue() > 0){
            Resources record = resourcesMapper.selectById(resourcesDto.getId());
            if(record == null){
                throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"id错误");
            }

            //判断状态是否进行中
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
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        String localDateTimeOfNow = DateUtils.parseLocalDateToStr(localDateTime,DateUtils.DATE_FORMAT_DATETIME);
        if(resourcesPageDto.getStatus() != null){
            if(resourcesPageDto.getStatus() == 1){
                queryWrapper.lambda().gt(Resources::getBeginTime,localDateTimeOfNow);
            }else if(resourcesPageDto.getStatus() == 2){
                queryWrapper.lambda().le(Resources::getBeginTime,localDateTimeOfNow);
                queryWrapper.lambda().ge(Resources::getEndTime,localDateTimeOfNow);
            }else {
                queryWrapper.lambda().le(Resources::getEndTime,localDateTimeOfNow);
            }

        }
        queryWrapper.orderByDesc("add_time");
        IPage<Resources> page = new Page<>(resourcesPageDto.getPageNum(),resourcesPageDto.getPageSize());
        IPage<Resources> resourcesIPage = resourcesMapper.selectPage(page, queryWrapper);
        List<ResourcesVo> list = new ArrayList<>();
        for (Resources resources : resourcesIPage.getRecords()){
            ResourcesVo resourcesVo = new ResourcesVo();
            BeanUtils.copyProperties(resources,resourcesVo);
            if(localDateTime.isAfter(resources.getBeginTime()) || localDateTime.isBefore(resources.getEndTime())) {
                resourcesVo.setStatus(2);
            }
            if(localDateTime.isAfter(resources.getEndTime())) {
                resourcesVo.setStatus(3);
            }
            if(localDateTime.isBefore(resources.getBeginTime())) {
                resourcesVo.setStatus(1);
            }
            List<ResourceStarVo> resourceStarVos = starResourcesRelMapper.selectStar(resources.getId());
            List<String> completeStar = new ArrayList<>();
             List<String> relationStar = new ArrayList<>();
            for (ResourceStarVo resourceStarVo : resourceStarVos){
                relationStar.add(resourceStarVo.getName());
                if(resourceStarVo.getStatus() == 1){
                    completeStar.add(resourceStarVo.getName());
                }
            }
            resourcesVo.setRelationStar(relationStar);
            resourcesVo.setCompleteStar(completeStar);
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
        if(StringUtils.isNotBlank(resources.getTags())){
            resourcesVo.setTags(JSONArray.parseArray(resources.getTags(),TagsDto.class));
        }
        //查询明星
        if(StringUtils.isNotBlank(resources.getStarIds())){
            resourcesVo.setStarIds(JSONArray.parseArray(resources.getStarIds(),Long.class));
        }
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


    @Override
    public void joinResources(Long resourcesRelId,Integer status) {
        StarResourcesRel starResourcesRelDo = starResourcesRelMapper.selectById(resourcesRelId);
        if(starResourcesRelDo == null){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"数据不存在，请检查Id是否错误");
        }

        Long id = UserUtil.getCurrentUserId(request);

        QueryWrapper<FensJoin> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(FensJoin::getFensId,id).eq(FensJoin::getResourcesRelId,resourcesRelId);
        FensJoin fensJoin = fensJoinMapper.selectOne(wrapper);
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));

        StarResourcesRel starResourcesRel = new StarResourcesRel();
        starResourcesRel.setId(resourcesRelId);
        int joinNum = 0;
        int reachNum =0;
        int rstatus = 0;
        if(fensJoin == null){
            FensJoin fensJoinDo = new FensJoin();
            fensJoinDo.setAddTime(localDateTimeOfNow);
            fensJoinDo.setId(SnowflakeId.getInstance().nextId());
            fensJoinDo.setCompleteNum(status);
            fensJoinDo.setFensId(id);
            fensJoinDo.setResourcesRelId(resourcesRelId);
            fensJoinMapper.insert(fensJoinDo);
            joinNum = 1;
            if(status == 1){
                reachNum = 1;
            }
        }else {
            if(status == 1){
                FensJoin fensJoinDo = new FensJoin();
                fensJoinDo.setId(fensJoin.getId());
                fensJoinDo.setUpdateTime(localDateTimeOfNow);
                fensJoinDo.setCompleteNum(fensJoin.getCompleteNum()+1);
                fensJoinMapper.updateById(fensJoinDo);
                reachNum = 1;
            }
            joinNum = 1;
        }
        int count = starResourcesRelDo.getReachNum() + reachNum;

        Resources resources = resourcesMapper.selectById(starResourcesRelDo.getResourcesId());
        if(count == resources.getTarget()){
            rstatus = 1;
        }
        starResourcesRel.setUpdateTime(localDateTimeOfNow);

        starResourcesRelMapper.updateNum(resourcesRelId,joinNum,reachNum,rstatus);

        //增加观看次数
        QueryWrapper<FensView> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FensView::getAddTime, LocalDate.now()).eq(FensView::getFensId,id);
        FensView fensView = fensViewMapper.selectOne(queryWrapper);
        if(fensView == null){
            fensView = new FensView();
            fensView.setAddTime(LocalDate.now());
            fensView.setFensId(id);
            fensView.setViewNum(1);
            fensViewMapper.insert(fensView);
        }else {
            fensView.setViewNum(fensView.getViewNum()+1);
            fensViewMapper.updateById(fensView);
        }
    }


    @Override
    public List<ListAwardPersionVo> listAward() {
        QueryWrapper<ListAward> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ListAward::getOpen,1);
        List<ListAward> list = listAwardMapper.selectList(wrapper);
        List<ListAwardPersionVo> listAwardPersionVos = new ArrayList<>();
        for (ListAward listAward:list){
            ListAwardPersionVo listAwardPersionVo = new ListAwardPersionVo();
            listAwardPersionVo.setCode(listAward.getCode());
            listAwardPersionVo.setImg(listAward.getImg());
            listAwardPersionVos.add(listAwardPersionVo);
        }
        return listAwardPersionVos;
    }


    @Override
    public void addOrUpdatOpenImg(OpenImgDto openImgDto) {
        QueryWrapper<OpenImg> wrapper = new QueryWrapper<>();
        OpenImg openImg = openImgMapper.selectOne(wrapper);
        if(openImg == null){
            openImg = new OpenImg();
            openImg.setId(SnowflakeId.getInstance().nextId());
            openImg.setImg(openImgDto.getImg());
            openImg.setOpen(openImgDto.getOpen());
            openImgMapper.insert(openImg);
        }else {
            openImg.setImg(openImgDto.getImg());
            openImg.setOpen(openImgDto.getOpen());
            openImgMapper.updateById(openImg);
        }

    }

    @Override
    public OpenImgVo selectOpenImg() {
        QueryWrapper<OpenImg> wrapper = new QueryWrapper<>();
        OpenImg openImg = openImgMapper.selectOne(wrapper);
        OpenImgVo openImgVo = new OpenImgVo();
        if(openImg == null){
            return openImgVo;
        }
        BeanUtils.copyProperties(openImg,openImgVo);
        return openImgVo;
    }


    @Override
    public void saveOrUpdateViewLimit(int viewLimit) {
        QueryWrapper<ViewLimit> wrapper = new QueryWrapper<>();
        ViewLimit viewLimitDo = viewLimitMapper.selectOne(wrapper);
        if(viewLimitDo == null){
            viewLimitDo = new ViewLimit();
            viewLimitDo.setViewLimit(viewLimit);
            viewLimitMapper.insert(viewLimitDo);
        }else {
            viewLimitDo.setViewLimit(viewLimit);
            viewLimitMapper.update(viewLimitDo,wrapper);
        }
    }


    @Override
    public Integer selectViewLimit() {
        QueryWrapper<ViewLimit> wrapper = new QueryWrapper<>();
        ViewLimit viewLimitDo = viewLimitMapper.selectOne(wrapper);
        if(viewLimitDo != null){
            return viewLimitDo.getViewLimit();
        }
        return null;
    }
}
