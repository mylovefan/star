package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.module.front.dao.FensViewMapper;
import com.star.module.front.dao.ViewLimitMapper;
import com.star.module.front.entity.FensView;
import com.star.module.front.entity.HitList;
import com.star.module.front.entity.ViewLimit;
import com.star.module.front.service.IFensViewService;
import com.star.module.user.common.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2021-04-02
 */
@Service
public class FensViewServiceImpl extends ServiceImpl<FensViewMapper, FensView> implements IFensViewService {

    @Autowired
    private ViewLimitMapper viewLimitMapper;

    @Autowired
    private FensViewMapper fensViewMapper;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Boolean selectViewLimit() {
        Long id = UserUtil.getCurrentUserId(request);
        if(id == null){
            return Boolean.FALSE;
        }
        QueryWrapper<FensView> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FensView::getAddTime, LocalDate.now()).eq(FensView::getFensId,id);
        FensView fensView = fensViewMapper.selectOne(queryWrapper);
        if(fensView == null){
            return Boolean.TRUE;
        }
        QueryWrapper<ViewLimit> wrapper = new QueryWrapper<>();
        ViewLimit viewLimitDo = viewLimitMapper.selectOne(wrapper);
        if(viewLimitDo == null){
            return Boolean.TRUE;
        }
        if(fensView.getViewNum() >= viewLimitDo.getViewLimit()){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
