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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PageSerializable<HitListVo> selectPage(FensMarkRankDto fensMarkRankDto) {
        if (fensMarkRankDto.getStarId() == null) {
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(), "明星id为空，无法统计榜单");
        }

        List<FensMarkVo> list = fensMarkLogMapper.selectMarkRankByFens(fensMarkRankDto.getFensId(), fensMarkRankDto.getStarId(), fensMarkRankDto.getStartTime(), fensMarkRankDto.getEndTime());
        return null;
    }
}
