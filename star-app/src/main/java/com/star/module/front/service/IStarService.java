package com.star.module.front.service;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.dto.RankDto;
import com.star.module.front.entity.Star;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.front.vo.HitDetailVo;
import com.star.module.front.vo.HotStarVo;
import com.star.module.front.vo.StarInfoVo;
import com.star.module.operation.dto.StarDto;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.model.StatModel;
import com.star.module.operation.vo.HitListVo;
import com.star.module.operation.vo.StarDetailVo;
import com.star.module.operation.vo.StartVo;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 明星表 服务类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
public interface IStarService extends IService<Star> {

    PageSerializable<StartVo> selectPage(StarPageDto starPageDto);

    void addStar(StarDto dto);

    void updateStar(StarDto dto);

    /**
     * 前端明星详情页
     *
     * @param id
     * @return
     */
    StarInfoVo selectStarInfo(Long id);

    /**
     * 更新明星本周排名/本月排名
     * @param type 0本周；1本月
     * @param startTime
     * @param endTime
     */
    List<StatModel> getStarRank(int type, Date startTime, Date endTime);

    /**
     * 热门搜索的明星
     * @return
     */
    List<HotStarVo> hotSearch();

    /**
     * 明星信息
     * @param name
     * @return
     */
    List<StarInfoVo> selectStar(String name);

    /**
     * 打榜详情
     *
     * @param starId
     * @return
     */
    HitDetailVo selectHitDetail(@RequestParam("starId") Long starId);


    /**
     * 明星详情
     *
     * @param id
     * @return
     */
    StarDetailVo selectStatById(Long id);

    /**
     * 根据明星名称查询明星
     *
     * @param starName
     * @return
     */
    List<HotStarVo> selectStarByName(String starName);

    /**
     *
     * @param response
     */
    void downStarList(HttpServletResponse response,String name,Long starId);


    /**
     * 首页榜单列表
     * @param rankDto
     * @return
     */
    PageSerializable<HitListVo> pageListRank(RankDto rankDto);

    /**
     * 定时任务更新
     *
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    List<StatModel> getStatisticsStarRankTask(int type, String startTime, String endTime);
}
