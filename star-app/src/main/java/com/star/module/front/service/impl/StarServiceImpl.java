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
import com.star.module.front.dao.FensMapper;
import com.star.module.front.dao.HitListMapper;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.dto.RankDto;
import com.star.module.front.entity.Fens;
import com.star.module.front.entity.HitList;
import com.star.module.front.entity.Star;
import com.star.module.front.service.IHitListService;
import com.star.module.front.service.IStarService;
import com.star.module.front.vo.HitDetailVo;
import com.star.module.front.vo.HotStarVo;
import com.star.module.front.vo.StarInfoVo;
import com.star.module.operation.dto.TagsDto;
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
import com.star.module.operation.vo.HitListVo;
import com.star.module.operation.vo.StarDetailVo;
import com.star.module.operation.vo.StartVo;
import com.star.module.user.common.UserUtil;
import com.star.util.SnowflakeId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
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

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FensMapper fensMapper;

    @Override
    public PageSerializable<StartVo> selectPage(StarPageDto starPageDto) {

        getStarRank(0, DateUtils.getWeekStart(new Date()), DateUtils.getWeekEnd(new Date()));
        getStarRank(1, DateUtils.getMonthStart(new Date()), DateUtils.getMonthEnd(new Date()));

        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(starPageDto.getName())) {
            List<String> nameList = Arrays.asList(starPageDto.getName().split("[,， \n\r]"));
            queryWrapper.lambda().in(Star::getName, nameList);
        }
        if (starPageDto.getId() != null) {
            queryWrapper.lambda().like(Star::getStarId, starPageDto.getId());
        }
        if(starPageDto.getMonthSort() != null){
            if(starPageDto.getMonthSort() == 0){
                queryWrapper.lambda().orderByAsc(Star::getThisMonthRank);
            }else {
                queryWrapper.lambda().orderByDesc(Star::getThisMonthRank);
            }
        }
        if(starPageDto.getWeekSort() != null){
            if(starPageDto.getWeekSort() == 0){
                queryWrapper.lambda().orderByAsc(Star::getThisWeekRank);
            }else {
                queryWrapper.lambda().orderByDesc(Star::getThisWeekRank);
            }
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
        if (dto.getTags() != null && dto.getTags().size() > 0) {
            List<String> list = new ArrayList<>();
            String tagsStr = "";
            for (TagsDto s : dto.getTags()){
                list.add(s.getName());
                tagsStr = tagsStr+s.getName()+"、";
            }
            if (list.contains(HOTSEARCH)) {
                star.setHotSearch(NumberUtils.INTEGER_ONE);
            } else {
                star.setHotSearch(NumberUtils.INTEGER_ZERO);
            }
            tagsStr =tagsStr.substring(0, tagsStr.length() - 1);
            star.setTags(tagsStr);
        }else {
            star.setTags("");
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
        if (dto.getTags() != null && dto.getTags().size() > 0) {
            List<String> list = new ArrayList<>();
            String tagsStr = "";
            for (TagsDto s : dto.getTags()){
                list.add(s.getName());
                tagsStr = tagsStr+s.getName()+"、";
            }
            if (list.contains(HOTSEARCH)) {
                star.setHotSearch(NumberUtils.INTEGER_ONE);
            } else {
                star.setHotSearch(NumberUtils.INTEGER_ZERO);
            }
            tagsStr =tagsStr.substring(0, tagsStr.length() - 1);
            star.setTags(tagsStr);
        }else {
            star.setTags("");
        }
        starMapper.updateById(star);
        this.tagsSet(dto, star);
    }


    @Override
    public PageSerializable<HitListVo> pageListRank(RankDto rankDto) {
        String startTime = null;
        String endTime = null;
        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        if(rankDto.getRankType() == 0){
            queryWrapper.orderByAsc("this_week_rank");

            Date starDate = DateUtils.getWeekStart(new Date());
            Date endDate = DateUtils.getWeekEnd(new Date());
            startTime = DateUtils.formatDate(starDate,DateUtils.DATE_FORMAT_DATETIME);
            endTime = DateUtils.formatDate(endDate,DateUtils.DATE_FORMAT_DATETIME);
        }else if(rankDto.getRankType() == 1){
            queryWrapper.orderByAsc("this_month_rank");

            Date starMonthDate = DateUtils.getMonthStart(new Date());
            Date endMonthDate = DateUtils.getMonthEnd(new Date());
            startTime = DateUtils.formatDate(starMonthDate,DateUtils.DATE_FORMAT_DATETIME);
            endTime = DateUtils.formatDate(endMonthDate,DateUtils.DATE_FORMAT_DATETIME);
        }else {
            queryWrapper.orderByDesc("hot_nums");
        }
        queryWrapper.orderByAsc("create_time");
        IPage page = new Page(rankDto.getPageNum(), rankDto.getPageSize());
        IPage<Star> pageList = starMapper.selectPage(page, queryWrapper);

        List<Long> starIds = new ArrayList<>();
        for (Star star : pageList.getRecords()) {
            starIds.add(star.getId());
        }
        List<HitList> list = hitListMapper.statisticsRankByTimeAndStar(starIds,startTime,endTime);

        //返回结果
        List<HitListVo> weekRankList = new ArrayList<>();
        int i = rankDto.getPageSize()*(rankDto.getPageNum()-1)+1 ;
        for (Star star : pageList.getRecords()) {
            HitListVo hitListVo = new HitListVo();
            hitListVo.setId(star.getId());
            hitListVo.setStarAvatar(star.getAvatar());
            hitListVo.setStarId(star.getStarId());
            hitListVo.setStarName(star.getName());
            hitListVo.setRank(i);
            if(rankDto.getRankType() != 2){
                for (HitList statModel : list){
                    if(star.getId().longValue() == statModel.getStarId()){
                        if(statModel.getVigourVal() == null){
                            hitListVo.setTotalVigourVal(0);
                        }else {
                            hitListVo.setTotalVigourVal(statModel.getVigourVal());
                        }
                        break;
                    }
                }
            }else {
                hitListVo.setTotalVigourVal(star.getHotNums());
            }
            i++;
            weekRankList.add(hitListVo);
        }
        //int totalCount = hitListMapper.totalCount(startTime, endTime);
        PageSerializable<HitListVo> pageSerializable = new PageSerializable<>(weekRankList);
        pageSerializable.setTotal(pageList.getTotal());
        return pageSerializable;
    }

    private void tagsSet(StarDto dto, Star star) {
        /** 标签关联标签处理 */
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            iStarTagsService.deleteByStarId(star.getId());

            List<StarTags> starTagsList = new ArrayList<>();
            List<TagsDto> tags = dto.getTags();
            for (TagsDto m : tags) {

                StarTags tag = new StarTags();
                tag.setStarId(star.getId());
                tag.setTagsId(m.getId());
                tag.setTagsName(m.getName());
                starTagsList.add(tag);
            }
            iStarTagsService.saveBatch(starTagsList);
        }
    }

    public List<StatModel> getStarRank(int type, Date startTime, Date endTime){
        List<Star> starList = starMapper.selectList(new QueryWrapper<>());
        log.info("==============被统计明星数："+starList.size()+"==============");
        List<StatModel> modelList = new ArrayList<>();
        if(starList.size()>0) {
            starList.stream().forEach(sl -> {
                if(type==1)sl.setThisMonthRank(NumberUtils.INTEGER_ZERO);
                if(type==0)sl.setThisWeekRank(NumberUtils.INTEGER_ZERO);
            });
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
        return modelList;
    }


    private List<StatModel> getStatisticsStarRank(int type, String startTime, String endTime){
        List<Star> starList = starMapper.selectList(new QueryWrapper<>());
        log.info("==============被统计明星数："+starList.size()+"==============");
        List<StatModel> modelList = new ArrayList<>();
        if(starList.size()>0) {
            starList.stream().forEach(sl -> {
                if(type==1)sl.setThisMonthRank(NumberUtils.INTEGER_ZERO);
                if(type==0)sl.setThisWeekRank(NumberUtils.INTEGER_ZERO);
            });
            listUtils.copyList(starList, modelList, StatModel.class);
            QueryWrapper<HitList> queryWrapper = new QueryWrapper<>();
            if (startTime != null) {
                queryWrapper.lambda().ge(HitList::getCreateTime, startTime);
            }
            if (endTime != null) {
                queryWrapper.lambda().le(HitList::getCreateTime, endTime);
            }
            List<HitList> list = hitListMapper.statisticsRankByTime(startTime,endTime);
            modelList.stream().forEach(item ->{
                boolean flag = true;
                for (HitList hitList : list){
                    if (item.getId().longValue() == hitList.getStarId().longValue()) {
                        item.setVigourVal(hitList.getVigourVal());
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    item.setVigourVal(0);
                }
            });
           /* modelList.sort(Comparator.comparing(StatModel::getVigourVal).reversed());

            List<Star> updateList = new ArrayList<>();
            for (int i = 0; i < modelList.size() ; i++) {
                Star star = new Star();
                star.setId(modelList.get(i).getId());
                if(type ==0) {
                    star.setThisWeekRank(i+1);
                }else{
                    star.setThisMonthRank(i+1);
                }
                updateList.add(star);
            }
            starMapper.batchUpdate(updateList);*/
        }
        return modelList;
    }

    public List<StatModel> getStatisticsStarRankTask(int type, String startTime, String endTime){
        List<Star> starList = starMapper.selectList(new QueryWrapper<>());
        log.info("==============被统计明星数："+starList.size()+"==============");
        List<StatModel> modelList = new ArrayList<>();
        if(starList.size()>0) {
            starList.stream().forEach(sl -> {
                if(type==1)sl.setThisMonthRank(NumberUtils.INTEGER_ZERO);
                if(type==0)sl.setThisWeekRank(NumberUtils.INTEGER_ZERO);
            });
            listUtils.copyList(starList, modelList, StatModel.class);
            QueryWrapper<HitList> queryWrapper = new QueryWrapper<>();
            if (startTime != null) {
                queryWrapper.lambda().ge(HitList::getCreateTime, startTime);
            }
            if (endTime != null) {
                queryWrapper.lambda().le(HitList::getCreateTime, endTime);
            }
            List<HitList> list = hitListMapper.statisticsRankByTime(startTime,endTime);
            modelList.stream().forEach(item ->{
                boolean flag = true;
                for (HitList hitList : list){
                    if (item.getId().longValue() == hitList.getStarId().longValue()) {
                        item.setVigourVal(hitList.getVigourVal());
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    item.setVigourVal(0);
                }
            });
            modelList.sort(Comparator.comparing(StatModel::getVigourVal).reversed());

            List<Star> updateList = new ArrayList<>();
            for (int i = 0; i < modelList.size() ; i++) {
                Star star = new Star();
                star.setId(modelList.get(i).getId());
                if(type ==0) {
                    star.setThisWeekRank(i+1);
                }else{
                    star.setThisMonthRank(i+1);
                }
                updateList.add(star);
            }
            starMapper.batchUpdate(updateList);
        }
        return modelList;
    }

    @Override
    public List<HotStarVo> hotSearch() {
        QueryWrapper<Star> wrapper = new QueryWrapper();
        wrapper.lambda().eq(Star::getHotSearch, NumberUtils.INTEGER_ONE);
        List<Star> list = starMapper.selectList(wrapper);
        List<HotStarVo> map = new ArrayList<>();
        for (Star star:list) {
            HotStarVo starVo = new HotStarVo();
            starVo.setId(star.getId().toString());
            starVo.setName(star.getName());
            map.add(starVo);
        }
        return map;
    }

    @Override
    public List<StarInfoVo> selectStar(String name) {
        List<StarInfoVo> list = new ArrayList<>();
        QueryWrapper<Star> wrapper = new QueryWrapper();
        wrapper.lambda().like(Star::getName, name);
        List<Star> star = starMapper.selectList(wrapper);
        listUtils.copyList(star, list, StarInfoVo.class);
        return list;
    }

    @Override
    public StarInfoVo selectStarInfo(Long id) {
        Star star = starMapper.selectById(id);
        StarInfoVo starInfoVo = new StarInfoVo();
        BeanUtils.copyProperties(star, starInfoVo);
        starInfoVo.setThisWeekRank(star.getThisWeekRank());
        starInfoVo.setThisMonthRank(star.getThisMonthRank());
        return starInfoVo;
    }


    @Override
    public HitDetailVo selectHitDetail(Long starId) {
        Long id = UserUtil.getCurrentUserId(request);
        Fens fens = fensMapper.selectById(id);
        HitDetailVo hitDetailVo = new HitDetailVo();
        hitDetailVo.setVigourVal(fens.getVigourVal());
        Star star = starMapper.selectById(starId);
        hitDetailVo.setHitPopupImg(star.getHitPopupImg());
        return hitDetailVo;
    }


    @Override
    public StarDetailVo selectStatById(Long id) {
        Star star = starMapper.selectById(id);
        StarDetailVo starDetailVo = new StarDetailVo();
        BeanUtils.copyProperties(star,starDetailVo);
        QueryWrapper<StarTags> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StarTags::getStarId, id);
        List<StarTags> list = iStarTagsService.list(queryWrapper);
        List<TagsDto> tagsDtos = new ArrayList<>();
        for (StarTags starTags : list){
            TagsDto tagsDto = new TagsDto();
            tagsDto.setId(starTags.getId());
            tagsDto.setTagsId(starTags.getTagsId());
            tagsDto.setName(starTags.getTagsName());
            tagsDtos.add(tagsDto);
        }
        starDetailVo.setTags(tagsDtos);
        return starDetailVo;
    }

    @Override
    public List<HotStarVo> selectStarByName(String starName) {
        QueryWrapper<Star> wrapper = new QueryWrapper();
        wrapper.lambda().like(Star::getName, starName);
        List<Star> starList = starMapper.selectList(wrapper);
        List<HotStarVo> list = new ArrayList<>();
        for (Star star :starList){
            HotStarVo hotStarVo = new HotStarVo();
            hotStarVo.setId(star.getId().toString());
            hotStarVo.setName(star.getName());
            list.add(hotStarVo);
        }
        return list;
    }


    @Override
    public void downStarList(HttpServletResponse response,String name,Long starId) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("明星记录");
        sheet.setDefaultColumnWidth(30);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);

        HSSFCell cell0 = row.createCell((short) 0);
        cell0.setCellValue("明星姓名");
        HSSFCell cell1 = row.createCell((short) 1);
        cell1.setCellValue("明星ID");

        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(name)) {
            List<String> nameList = Arrays.asList(name.split("[,， \n\r]"));
            queryWrapper.lambda().in(Star::getName, nameList);
        }
        if (starId != null) {
            queryWrapper.lambda().like(Star::getStarId, starId);
        }
        List<Star> stars = starMapper.selectList(queryWrapper);

        int i = 1;
        for (Star startVo : stars){
            HSSFRow rowData = sheet.createRow((int) i);
            HSSFCell celldata0 = rowData.createCell((short) 0);
            celldata0.setCellValue(startVo.getName());
            HSSFCell celldata1 = rowData.createCell((short) 1);
            celldata1.setCellValue(startVo.getStarId().toString());
            i++;
        }

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode("明星记录.xls", "UTF-8"));
            wb.write(out);
        }catch (Exception e){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"下载明星记录失败");
        }  finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
