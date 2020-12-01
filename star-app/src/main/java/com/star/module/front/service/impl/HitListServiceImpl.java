package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.entity.HitList;
import com.star.module.front.dao.HitListMapper;
import com.star.module.front.service.IHitListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.utility.NumberUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 打榜记录表 服务实现类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Service
public class HitListServiceImpl extends ServiceImpl<HitListMapper, HitList> implements IHitListService {

    @Autowired
    private HitListMapper hitListMapper;

    @Override
    public int statisticsRankByTime(Long starId, Date startTime, Date endTime) {
        QueryWrapper<HitList> queryWrapper = new QueryWrapper<>();
        if(starId ==null){
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"明星id为空，无法统计榜单");
        }
        queryWrapper.lambda().eq(HitList::getStarId, starId);
        if(startTime!=null){
            queryWrapper.lambda().ge(HitList::getCreateTime, startTime);
        }
        if(endTime!=null){
            queryWrapper.lambda().le(HitList::getCreateTime, endTime);
        }
        List<HitList> list = hitListMapper.selectList(queryWrapper);
        if(list.size()>0){
            return list.stream().mapToInt(HitList::getVigourVal).sum();
        }else{
            return NumberUtils.INTEGER_ZERO;
        }
    }
}
