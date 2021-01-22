package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageSerializable;
import com.star.common.CommonConstants;
import com.star.module.front.dao.FensMapper;
import com.star.module.front.dto.FensRankDto;
import com.star.module.front.dto.UpdatePersonalCenterInfoDto;
import com.star.module.front.entity.Fens;
import com.star.module.front.service.IFensService;
import com.star.module.front.vo.FensVigourRankVo;
import com.star.module.front.vo.PersonalVo;
import com.star.module.operation.vo.FensVo;
import com.star.module.user.common.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FensServiceImpl implements IFensService {

    @Autowired
    private FensMapper fensMapper;
    @Autowired
    private HttpServletRequest request;

    @Override
    public PersonalVo personalCenterInfo() {
        Long id = UserUtil.getCurrentUserId(request);
        Fens fens = fensMapper.selectById(id);
        PersonalVo personalVo = new PersonalVo();
        BeanUtils.copyProperties(fens, personalVo);
        return personalVo;
    }


    @Override
    public void updatePersonalCenterInfo(UpdatePersonalCenterInfoDto updatePersonalCenterInfoDto) {
        Long id = UserUtil.getCurrentUserId(request);
        Fens fens = new Fens();
        fens.setId(id);
        fens.setAvatarUrl(updatePersonalCenterInfoDto.getAvatarUrl());
        fens.setNickName(updatePersonalCenterInfoDto.getNickName());
        fens.setSlogan(updatePersonalCenterInfoDto.getSlogan());
        fens.setSloganOpen(updatePersonalCenterInfoDto.getSloganOpen());
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        fens.setUpdateTime(localDateTimeOfNow);
        fensMapper.updateById(fens);
    }


    @Override
    public PageSerializable<FensVigourRankVo> selectHomeFensRank(FensRankDto rankDto) {
        Long id = UserUtil.getCurrentUserId(request);
        IPage<Fens> page = new Page<>(rankDto.getPageNum(),rankDto.getPageSize());
        QueryWrapper<Fens> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("consume_vigour_val");
        IPage<Fens> fensIPage = fensMapper.selectPage(page, queryWrapper);
        List<FensVigourRankVo> list = new ArrayList<>();
        for (Fens fens : fensIPage.getRecords()){
            FensVigourRankVo fensVo = new FensVigourRankVo();
            fensVo.setAvatarUrl(fens.getAvatarUrl());
            fensVo.setNickName(fens.getNickName());
            fensVo.setFensId(fens.getFensId());
            fensVo.setVigourVal(fens.getVigourVal());
            if(id!= null && fens.getId().longValue() == id.longValue()){
                fensVo.setFlag(true);
            }else {
                fensVo.setFlag(false);
            }
            list.add(fensVo);
        }
        PageSerializable<FensVigourRankVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(fensIPage.getTotal());
        return pageSerializable;
    }
}

