package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageSerializable;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.FensMarkLogMapper;
import com.star.module.front.entity.FensMarkLog;
import com.star.module.front.entity.HitList;
import com.star.module.front.service.IFensMarkLogService;
import com.star.module.user.dto.FensMarkRankDto;
import com.star.module.user.vo.FensMarkVo;
import com.star.module.user.vo.HitListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 粉丝打榜记录表 服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Service
public class FensMarkLogServiceImpl extends ServiceImpl<FensMarkLogMapper, FensMarkLog> implements IFensMarkLogService {

    @Autowired
    private FensMarkLogMapper fensMarkLogMapper;
    @Override
    public PageSerializable<FensMarkVo> selectPage(FensMarkRankDto fensMarkRankDto) {
        PageSerializable<FensMarkVo> pageSerializable = null;

        if (fensMarkRankDto.getStarId() == null) {
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "明星id为空，无法统计榜单");
        }
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
        List<FensMarkVo> fensMarkRankList = fensMarkLogMapper.selectMarkRankByFens(fensMarkRankDto.getFensId(), fensMarkRankDto.getStarId(), fensMarkRankDto.getStartTime(), fensMarkRankDto.getEndTime());

        for (int i = 0; i < fensMarkRankList.size(); i++) {
            fensMarkRankList.get(i).setWeekTime(fensMarkRankDto.getStartTime() + "-" + fensMarkRankDto.getEndTime());
            fensMarkRankList.get(i).setRank(i + 1);
        }

        /*if(needLimit){//需要分页查询代表没带明星参数
            int totalCount = fensMarkLogMapper.totalCount(fensMarkRankDto.getStartTime(), fensMarkRankDto.getEndTime());
            pageSerializable = new PageSerializable<>(fensMarkRankList);
            pageSerializable.setTotal(totalCount);
        }else {
            if (list.size() > 0) {
                //根据姓名查询，返回一条数据及排名
                if (StringUtils.isNotEmpty(hitListDto.getStarName())) {
                    List<HitListVo> resultList = list.stream().filter(li -> li.getStarName().equals(hitListDto.getStarName())).collect(Collectors.toList());
                    pageSerializable = new PageSerializable<>(resultList);
                    pageSerializable.setTotal(resultList.size());
                }
                //根据id查询，返回一条数据及排名
                if (hitListDto.getStarId() != null) {
                    List<HitListVo> resultList = list.stream().filter(li -> li.getStarId().equals(hitListDto.getStarId())).collect(Collectors.toList());
                    pageSerializable = new PageSerializable<>(resultList);
                    pageSerializable.setTotal(resultList.size());
                }
            } else {
                pageSerializable = new PageSerializable<>();
            }
        }*/
        return null;
    }
}
