package com.star.module.operation.tesk;

import com.star.module.front.service.IStarService;
import com.star.module.operation.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 明星排行榜统计定时任务
 * 统计冠亚季军
 */
@Component
@EnableAsync
@Slf4j
public class StarHomeRankTask {

    @Autowired
    private IStarService starService;

    /**
     * 周排名
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void starRankByWeek() {
        Date starDate = DateUtils.getWeekStart(new Date());
        Date endDate = DateUtils.getWeekEnd(new Date());
        String startTime = DateUtils.formatDate(starDate,DateUtils.DATE_FORMAT_DATETIME);
        String endTime = DateUtils.formatDate(endDate,DateUtils.DATE_FORMAT_DATETIME);
        starService.getStatisticsStarRankTask(NumberUtils.INTEGER_ZERO, startTime, endTime);
    }

    /**
     * 月排名
     */
    @Scheduled(cron = "5 0/2 * * * ?")
    public void starRankByMonth() throws Exception{
        Date starMonthDate = DateUtils.getMonthStart(new Date());
        Date endMonthDate = DateUtils.getMonthEnd(new Date());
        String startTime = DateUtils.formatDate(starMonthDate,DateUtils.DATE_FORMAT_DATETIME);
        String endTime = DateUtils.formatDate(endMonthDate,DateUtils.DATE_FORMAT_DATETIME);
        starService.getStatisticsStarRankTask(NumberUtils.INTEGER_ONE, startTime, endTime);
    }
}
