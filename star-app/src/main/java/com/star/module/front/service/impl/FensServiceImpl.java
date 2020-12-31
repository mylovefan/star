package com.star.module.front.service.impl;

import com.star.common.CommonConstants;
import com.star.module.front.dao.FensMapper;
import com.star.module.front.dto.UpdatePersonalCenterInfoDto;
import com.star.module.front.entity.Fens;
import com.star.module.front.service.IFensService;
import com.star.module.front.vo.PersonalVo;
import com.star.module.user.common.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
}

