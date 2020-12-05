package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dao.GuardMapper;
import com.star.module.front.dao.HitListMapper;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Guard;
import com.star.module.front.entity.HitList;
import com.star.module.front.entity.Star;
import com.star.module.front.service.IGuardService;
import com.star.module.front.vo.MyGuardVo;
import com.star.module.front.vo.MyHitListVo;
import com.star.module.front.vo.StarGuardVo;
import com.star.module.user.common.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 守护表 服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-03
 */
@Service
public class GuardServiceImpl extends ServiceImpl<GuardMapper, Guard> implements IGuardService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StarMapper starMapper;

    @Autowired
    private GuardMapper guardMapper;

    @Autowired
    private HitListMapper hitListMapper;


    @Override
    public PageSerializable<MyGuardVo> selectMyGuard(PageDTO pageDTO) {
        Long id = UserUtil.getCurrentUserId(request);
        IPage<Guard> page = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        QueryWrapper<Guard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Guard::getFensId,id);
        queryWrapper.orderByDesc("add_time");
        IPage<Guard> guardIPage = guardMapper.selectPage(page, queryWrapper);
        List<MyGuardVo> list = new ArrayList<>();
        for (Guard guard : guardIPage.getRecords()){
            MyGuardVo myGuardVo = new MyGuardVo();
            myGuardVo.setId(guard.getStarId());
            Star star = starMapper.selectById(guard.getStarId());
            myGuardVo.setName(star.getName());
            myGuardVo.setAvatar(star.getAvatar());
            myGuardVo.setHotNums(star.getHotNums());
            list.add(myGuardVo);
        }
        PageSerializable<MyGuardVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(guardIPage.getTotal());
        return pageSerializable;
    }

    @Override
    public PageSerializable<MyHitListVo> selectHitList(PageDTO pageDTO) {
        Long id = UserUtil.getCurrentUserId(request);
        IPage<HitList> page = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        QueryWrapper<HitList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HitList::getFensId,id);
        queryWrapper.orderByDesc("add_time");
        IPage<HitList> hitListIPage = hitListMapper.selectPage(page, queryWrapper);
        List<MyHitListVo> list = new ArrayList<>();
        for (HitList hitList : hitListIPage.getRecords()){
            MyHitListVo myHitListVo = new MyHitListVo();
            myHitListVo.setId(hitList.getStarId());
            Star star = starMapper.selectById(hitList.getStarId());
            myHitListVo.setName(star.getName());
            myHitListVo.setAvatar(star.getAvatar());
            myHitListVo.setVigourVal(hitList.getVigourVal());
            myHitListVo.setAddTime(hitList.getCreateTime());
            list.add(myHitListVo);
        }
        PageSerializable<MyHitListVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(hitListIPage.getTotal());
        return pageSerializable;
    }


    @Override
    public List<StarGuardVo> selectStarGuardList(Long starId) {
        List<StarGuardVo> starGuardVos = guardMapper.selectStarGuardList(starId);
        return starGuardVos;
    }
}
