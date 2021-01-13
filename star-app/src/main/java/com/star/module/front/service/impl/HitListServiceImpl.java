package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.*;
import com.star.module.front.dto.RankDto;
import com.star.module.front.dto.StarFensRankDto;
import com.star.module.front.entity.Fens;
import com.star.module.front.entity.Guard;
import com.star.module.front.entity.HitList;
import com.star.module.front.service.IHitListService;
import com.star.module.front.vo.FensVigourLogVo;
import com.star.module.front.vo.FensVigourRankVo;
import com.star.module.operation.dto.FensMarkRankDto;
import com.star.module.operation.dto.FensMrgStarHiyDto;
import com.star.module.operation.dto.HitListDto;
import com.star.module.operation.util.DateUtils;
import com.star.module.operation.util.ListUtils;
import com.star.module.operation.vo.FensMarkVo;
import com.star.module.operation.vo.HitListVo;
import com.star.module.user.common.UserUtil;
import com.star.util.SnowflakeId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 打榜记录表 服务实现类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Service
@Slf4j
public class HitListServiceImpl extends ServiceImpl<HitListMapper, HitList> implements IHitListService {

    @Autowired
    private HitListMapper hitListMapper;
    @Autowired
    private StarMapper starMapper;
    @Autowired
    private ListUtils listUtils;

    @Autowired
    private FensMapper fensMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private GuardMapper guardMapper;

    @Override
    public int statisticsRankByTime(Long starId, Date startTime, Date endTime) {
        QueryWrapper<HitList> queryWrapper = new QueryWrapper<>();
        if (starId == null) {
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "明星id为空，无法统计榜单");
        }
        queryWrapper.lambda().eq(HitList::getStarId, starId);
        if (startTime != null) {
            queryWrapper.lambda().ge(HitList::getCreateTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.lambda().le(HitList::getCreateTime, endTime);
        }
        List<HitList> list = hitListMapper.selectList(queryWrapper);
        if (list.size() > 0) {
            return list.stream().mapToInt(HitList::getVigourVal).sum();
        } else {
            return NumberUtils.INTEGER_ZERO;
        }
    }

    @Override
    public PageSerializable<HitListVo> selectPage(HitListDto hitListDto) {
        PageSerializable<HitListVo> pageSerializable = null;

        //是否分页标识
        boolean needLimit = true;
        //根据姓名查询 不分页以便算排名
        if (StringUtils.isNotEmpty(hitListDto.getStarName())) {
            needLimit = false;
        }
        //根据id查询 不分页以便算排名
        if (hitListDto.getStarId() != null) {
            needLimit = false;
        }

        switch (hitListDto.getHitListType()) {
            //周榜
            case 0:
                if (hitListDto.getListType() != null) {
                    //本周&近三个月周时间段
                    if (hitListDto.getListType() != null) {
                        if (hitListDto.getStartTime() == null) {
                            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "周开始时间为空");
                        }
                        if (hitListDto.getEndTime() == null) {
                            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "周结束时间为空");
                        }
                    }
                }
                //查询条件都为空时，默认统计本周
                if (hitListDto.getListType() == null && StringUtils.isEmpty(hitListDto.getStarName()) && hitListDto.getStarId() != null) {
                    hitListDto.setStartTime(DateUtils.getTimeStampStr(DateUtils.getWeekStart(new Date())));
                    hitListDto.setEndTime(DateUtils.getTimeStampStr(DateUtils.getWeekEnd(new Date())));
                }
                //返回结果
                List<HitListVo> weekRankList = hitListMapper.selectHitRankByStar(hitListDto.getStartTime(), hitListDto.getEndTime(),
                        hitListDto.getPageNum(), hitListDto.getPageSize(), hitListDto.getSortType(), needLimit);


                for (int i = 0; i < weekRankList.size(); i++) {
                    weekRankList.get(i).setWeekTime(hitListDto.getStartTime().substring(0,10) + "~" + hitListDto.getEndTime().substring(0,10));
                    weekRankList.get(i).setRank(i + 1);
                }
                if(needLimit){//需要分页查询代表没带参数
                    int totalCount = hitListMapper.totalCount(hitListDto.getStartTime(), hitListDto.getEndTime());
                    pageSerializable = new PageSerializable<>(weekRankList);
                    pageSerializable.setTotal(totalCount);
                    break;
                }else {
                    pageSerializable = this.pageResult(hitListDto, weekRankList);
                    break;
                }
            //月榜
            case 1:
                if (hitListDto.getListType() != null) {
                    //具体月份
                    if (hitListDto.getListType() != null) {
                        if (hitListDto.getStartTime() == null) {
                            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "月开始时间为空");
                        }
                        if (hitListDto.getEndTime() == null) {
                            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "月结束时间为空");
                        }
                    }

                    //查询条件都为空时，默认统计本月
                    if (hitListDto.getListType() == null && StringUtils.isEmpty(hitListDto.getStarName()) && hitListDto.getStarId() != null) {
                        hitListDto.setStartTime(DateUtils.getTimeStampStr(DateUtils.getMonthStart(new Date())));
                        hitListDto.setEndTime(DateUtils.getTimeStampStr(DateUtils.getMonthEnd(new Date())));
                    }
                    //返回结果
                    List<HitListVo> monthRankList = hitListMapper.selectHitRankByStar(hitListDto.getStartTime(), hitListDto.getEndTime(),
                            hitListDto.getPageNum(), hitListDto.getPageSize(), hitListDto.getSortType(), needLimit);

                    for (int i = 0; i < monthRankList.size(); i++) {
                        monthRankList.get(i).setWeekTime(hitListDto.getStartTime().substring(0,7));
                        monthRankList.get(i).setRank(i + 1);
                    }
                    if(needLimit){//需要分页查询代表没带明星参数
                        int totalCount = hitListMapper.totalCount(hitListDto.getStartTime(), hitListDto.getEndTime());
                        pageSerializable = new PageSerializable<>(monthRankList);
                        pageSerializable.setTotal(totalCount);
                        break;
                    }else{
                        pageSerializable = this.pageResult(hitListDto, monthRankList);
                        break;
                    }
                }
            //总榜
            case 2:
                //返回结果
                List<HitListVo> totalRankList = hitListMapper.selectHitRankByStar(null,null,
                        hitListDto.getPageNum(), hitListDto.getPageSize(), hitListDto.getSortType(), needLimit);

                for (int i = 0; i < totalRankList.size(); i++) {
                    //totalRankList.get(i).setWeekTime(DateUtils.formatDate(hitListDto.getStartTime()));
                    totalRankList.get(i).setRank(i + 1);
                }
                if(needLimit){//需要分页查询代表没带明星参数
                    int totalCount = hitListMapper.totalCount(null,null);
                    pageSerializable = new PageSerializable<>(totalRankList);
                    pageSerializable.setTotal(totalCount);
                    break;
                }else{
                    pageSerializable = this.pageResult(hitListDto, totalRankList);
                    break;
                }
        }
        return pageSerializable;
    }

    private PageSerializable<HitListVo> pageResult(HitListDto hitListDto, List<HitListVo> list) {
        PageSerializable<HitListVo> pageSerializable = null;
        if (list.size() > 0) {
            //根据姓名查询，返回一条数据及排名
            if (StringUtils.isNotEmpty(hitListDto.getStarName())) {
                List<HitListVo> resultList = list.stream().filter(li -> li.getStarName().equals(hitListDto.getStarName())).collect(Collectors.toList());
                pageSerializable = new PageSerializable<>(resultList);
                pageSerializable.setTotal(resultList.size());
            }
            //根据id查询，返回一条数据及排名
            if (hitListDto.getStarId() != null && hitListDto.getStarId()>0) {
                List<HitListVo> resultList = list.stream().filter(li -> li.getStarId().equals(hitListDto.getStarId())).collect(Collectors.toList());
                pageSerializable = new PageSerializable<>(resultList);
                pageSerializable.setTotal(resultList.size());
            }
        } else {
            pageSerializable = new PageSerializable<>();
        }
        return pageSerializable;
    }


    @Override
    public PageSerializable<FensMarkVo> selectFensRankPage(FensMarkRankDto fensMarkRankDto) {
        PageSerializable<FensMarkVo> pageSerializable = null;

        /*if (fensMarkRankDto.getStarId() == null) {
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "明星id为空，无法统计榜单");
        }*/
        //是否分页标识
        boolean needLimit = true;
        //根据姓名查询 不分页以便算排名
        if (StringUtils.isNotEmpty(fensMarkRankDto.getFensName())) {
            needLimit = false;
        }
        //根据id查询 不分页以便算排名
        if (fensMarkRankDto.getFensId() != null) {
            needLimit = false;
        }

        //返回结果
        List<FensMarkVo> fensMarkRankList = hitListMapper.selectFensMarkRankByFens(fensMarkRankDto.getStarId(), fensMarkRankDto.getPageNum(), fensMarkRankDto.getPageSize(),
                fensMarkRankDto.getStartTime(), fensMarkRankDto.getEndTime(), fensMarkRankDto.getSortType(), needLimit);

        for (int i = 0; i < fensMarkRankList.size(); i++) {
            if(fensMarkRankDto.getStartTime()!=null && fensMarkRankDto.getEndTime()!=null){
                fensMarkRankList.get(i).setWeekTime(fensMarkRankDto.getStartTime() + "~" + fensMarkRankDto.getEndTime());
            }
            fensMarkRankList.get(i).setRank(i + 1);
        }

        if(needLimit){//需要分页查询代表没带明星参数
            int totalCount = hitListMapper.totalCountFensMark(fensMarkRankDto.getStartTime(), fensMarkRankDto.getEndTime());
            pageSerializable = new PageSerializable<>(fensMarkRankList);
            pageSerializable.setTotal(totalCount);
        }else {
            if (fensMarkRankList.size() > 0) {
                //根据姓名查询，返回一条数据及排名
                if (StringUtils.isNotEmpty(fensMarkRankDto.getFensName())) {
                    List<FensMarkVo> resultList = fensMarkRankList.stream().filter(li -> li.getFensName().equals(fensMarkRankDto.getFensName())).collect(Collectors.toList());
                    pageSerializable = new PageSerializable<>(resultList);
                    pageSerializable.setTotal(resultList.size());
                }
                //根据id查询，返回一条数据及排名
                if (fensMarkRankDto.getFensId() != null) {
                    List<FensMarkVo> resultList = fensMarkRankList.stream().filter(li -> li.getFensId().equals(fensMarkRankDto.getFensId())).collect(Collectors.toList());
                    pageSerializable = new PageSerializable<>(resultList);
                    pageSerializable.setTotal(resultList.size());
                }
            } else {
                pageSerializable = new PageSerializable<>();
            }
        }
        return pageSerializable;
    }

    @Override
    public PageSerializable<HitListVo> pageListRank(RankDto rankDto) {
        String startTime = null;
        String endTime = null;
        switch (rankDto.getRankType()){
            case 0:
                //查询条件都为空时，默认统计本周
                startTime = DateUtils.getTimeStampStr(DateUtils.getWeekStart(new Date()));
                endTime = DateUtils.getTimeStampStr(DateUtils.getWeekEnd(new Date()));
                break;
            case 1:
                //查询条件都为空时，默认统计本月
                startTime = DateUtils.getTimeStampStr(DateUtils.getMonthStart(new Date()));
                endTime = DateUtils.getTimeStampStr(DateUtils.getMonthEnd(new Date()));
                break;
        }

        //返回结果
        List<HitListVo> weekRankList = hitListMapper.selectHitRankByStar(startTime, endTime, rankDto.getPageNum(), rankDto.getPageSize(), NumberUtils.INTEGER_ONE, true);
        for (int i = 0; i < weekRankList.size(); i++) {
            weekRankList.get(i).setRank(i + 1);
        }
        int totalCount = hitListMapper.totalCount(startTime, endTime);
        PageSerializable<HitListVo> pageSerializable = new PageSerializable<>(weekRankList);
        pageSerializable.setTotal(totalCount);
        return pageSerializable;
    }

    @Override
    public PageSerializable<FensVigourRankVo> selectFensRank(StarFensRankDto rankDto) {
        Long id = UserUtil.getCurrentUserId(request);
        String star =null ,end =null;
        if(rankDto.getRankType() == 0){
            star = DateUtils.getTimeStampStr(DateUtils.getWeekStart(new Date()));
            end = DateUtils.getTimeStampStr(DateUtils.getWeekEnd(new Date()));
        }else if(rankDto.getRankType() == 1){
            star = DateUtils.getTimeStampStr(DateUtils.getMonthStart(new Date()));
            end = DateUtils.getTimeStampStr(DateUtils.getMonthEnd(new Date()));
        }else {

        }
        PageHelper.startPage(rankDto.getPageNum(),rankDto.getPageSize());
        Page<FensVigourRankVo> fensThisRank = hitListMapper.getFensThisRank(rankDto.getId(), star, end);
        for (FensVigourRankVo fensVigourRankVo:fensThisRank.getResult()){
            Fens fens = fensMapper.selectById(fensVigourRankVo.getFensId());
            fensVigourRankVo.setAvatarUrl(fens.getAvatarUrl());
            fensVigourRankVo.setNickName(fens.getNickName());
            if(id!= null && fensVigourRankVo.getFensId().longValue() == id.longValue()){
                fensVigourRankVo.setFlag(true);
            }else {
                fensVigourRankVo.setFlag(false);
            }

        }
        PageSerializable<FensVigourRankVo> pageSerializable = new PageSerializable<>(fensThisRank.getResult());
        pageSerializable.setTotal(fensThisRank.getTotal());
        return pageSerializable;
    }


    @Override
    public void hit(Long starId, Integer vigourVal) {
        HitList hitList = new HitList();
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        hitList.setCreateTime(localDateTimeOfNow);
        Long id = UserUtil.getCurrentUserId(request);

        Fens fens = fensMapper.selectById(id);
        if(vigourVal > fens.getVigourVal()){
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "热力值不够哦");
        }
        hitList.setFensId(id);
        hitList.setStarId(starId);
        hitList.setVigourVal(vigourVal);
        hitListMapper.insert(hitList);

        QueryWrapper<Guard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Guard::getStarId,starId).eq(Guard::getFensId,id);
        Guard guard = guardMapper.selectOne(queryWrapper);
        boolean flag = false;
        if(vigourVal >= 999){
            flag = true;
        }
        if(guard == null){
            Guard guardDo = new Guard();
            guardDo.setId(SnowflakeId.getInstance().nextId());
            guardDo.setAddTime(localDateTimeOfNow);
            guardDo.setFensId(id);
            guardDo.setStarId(starId);
            guardDo.setStatus(flag ? 1:0);
            guardMapper.insert(guardDo);
        }else {
            if(flag){
                Guard guardDo = new Guard();
                guardDo.setId(guard.getId());
                guardDo.setStatus(1);
                guardMapper.updateById(guardDo);
            }

        }
        fensMapper.updateReduceVigour(id,vigourVal,null);

    }


    @Override
    public void bulidFensHit(FensMrgStarHiyDto fensMrgStarHiyDto) {
        HitList hitList = new HitList();
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        hitList.setCreateTime(localDateTimeOfNow);
        Long id = fensMrgStarHiyDto.getId();

        Fens fens = fensMapper.selectById(id);
        if(fensMrgStarHiyDto.getVigourVal() > fens.getVigourVal()){
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "热力值不够哦");
        }
        hitList.setFensId(id);
        hitList.setStarId(fensMrgStarHiyDto.getStarId());
        hitList.setVigourVal(fensMrgStarHiyDto.getVigourVal());
        hitListMapper.insert(hitList);

        QueryWrapper<Guard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Guard::getStarId,fensMrgStarHiyDto.getStarId()).eq(Guard::getFensId,id);
        Guard guard = guardMapper.selectOne(queryWrapper);
        boolean flag = false;
        if(fensMrgStarHiyDto.getVigourVal() >= 999){
            flag = true;
        }
        if(guard == null){
            Guard guardDo = new Guard();
            guardDo.setId(SnowflakeId.getInstance().nextId());
            guardDo.setAddTime(localDateTimeOfNow);
            guardDo.setFensId(id);
            guardDo.setStarId(fensMrgStarHiyDto.getStarId());
            guardDo.setStatus(flag ? 1:0);
            guardMapper.insert(guardDo);
        }else {
            if(flag){
                Guard guardDo = new Guard();
                guardDo.setId(guard.getId());
                guardDo.setStatus(1);
                guardMapper.updateById(guardDo);
            }

        }
        fensMapper.updateReduceVigour(id,fensMrgStarHiyDto.getVigourVal(),fensMrgStarHiyDto.getStarName());
    }
}

