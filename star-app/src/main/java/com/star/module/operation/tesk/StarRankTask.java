package com.star.module.operation.tesk;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Star;
import com.star.module.front.service.IHitListService;
import com.star.module.front.service.IStarService;
import com.star.module.operation.model.StatModel;
import com.star.module.operation.util.DateUtils;
import com.star.module.operation.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 明星排行榜统计定时任务
 */

@Component
@Slf4j
public class StarRankTask {

    @Autowired
    private StarMapper starMapper;
    @Autowired
    private IHitListService iHitListService;
    @Autowired
    private ListUtils listUtils;

    /**
     * 每周1早上8点执行
     */
    @Scheduled(cron = "0 0 8 ? 0 1 *")
    public void starRankByWeek() {
        long beginTime = System.currentTimeMillis();
        log.info("==============周一统计明星榜单开始，运行时间："+DateUtils.getTimeStampStr(new Date())+"==============");
        log.info("==============统计开始时间："+DateUtils.getTimeStampStr(DateUtils.getDayStart(DateUtils.getLastWeekMonday()))+"==============");
        log.info("==============统计结束时间："+DateUtils.getTimeStampStr(DateUtils.getDayEnd(DateUtils.getLastSundayEndDay()))+"==============");
        statisticsStarRank(NumberUtils.INTEGER_ZERO, DateUtils.getDayStart(DateUtils.getLastWeekMonday()), DateUtils.getDayEnd(DateUtils.getLastSundayEndDay()));

        long endTime = System.currentTimeMillis();
        log.info("==============统计结束，耗时："+(endTime - beginTime) / 1000+"s ==============");
    }

    /**
     * 每月1号早上8点执行
     */
    @Scheduled(cron = "0 0 8 1 * ?")
    public void starRankByMonth() {
        long beginTime = System.currentTimeMillis();
        log.info("==============一号统计明星榜单开始，运行时间："+DateUtils.getTimeStampStr(new Date())+"==============");
        log.info("==============统计开始时间："+DateUtils.getTimeStampStr(DateUtils.getDayStart(DateUtils.getPreviousMonthFirstDay()))+"==============");
        log.info("==============统计结束时间："+DateUtils.getTimeStampStr(DateUtils.getDayEnd(DateUtils.getPreviousMonthLastDay()))+"==============");
        statisticsStarRank(NumberUtils.INTEGER_ONE, DateUtils.getDayStart(DateUtils.getPreviousMonthFirstDay()), DateUtils.getDayEnd(DateUtils.getPreviousMonthLastDay()));

        long endTime = System.currentTimeMillis();
        log.info("==============统计结束，耗时："+(endTime - beginTime) / 1000+"s ==============");

    }

    private void statisticsStarRank(int timeType, Date startTime, Date endTime){
        List<Star> starList = starMapper.selectList(new QueryWrapper<>());
        log.info("==============被统计明星数："+starList.size()+"==============");
        if(starList.size()>0){
            List<StatModel> modelList = new ArrayList<>();
            listUtils.copyList(starList, modelList, StatModel.class);
            modelList.stream().forEach(item ->{
                int vigourVal = iHitListService.statisticsRankByTime(item.getId(), startTime, endTime);
                item.setVigourVal(vigourVal);
            });
            modelList.sort(Comparator.comparing(StatModel::getVigourVal).reversed());
            //取前三名存库
            for (int i = 0; i < modelList.size() ; i++) {
                Star star = new Star();
                BeanUtils.copyProperties(modelList.get(i), star);
                if(star.getId()!=null && modelList.get(i).getVigourVal()>0){
                    switch (i){
                        case 0:
                            if(timeType ==0) {
                                star.setRankWeekChampionNum(star.getRankWeekChampionNum()+1);
                                break;
                            }else {
                                star.setRankMonthChampionNum(star.getRankMonthChampionNum()+1);
                                break;
                            }
                        case 1:
                            if(timeType ==0) {
                                star.setRankWeekSecondNum(star.getRankWeekSecondNum()+1);
                                break;
                            }else{
                                star.setRankMonthSecondNum(star.getRankMonthSecondNum()+1);
                                break;
                            }
                        case 2:
                            if(timeType ==0) {
                                star.setRankWeekThirdNum(star.getRankWeekThirdNum()+1);
                                break;
                            }else{
                                star.setRankMonthThirdNum(star.getRankMonthThirdNum()+1);
                                break;
                            }
                    }
                    if(timeType ==0) {
                        star.setThisWeekRank(i+1);
                    }else{
                        star.setThisMonthRank(i+1);
                    }

                    starMapper.updateById(star);
                }
            }
        }
    }
}
