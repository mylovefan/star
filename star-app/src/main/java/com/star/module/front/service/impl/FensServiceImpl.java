package com.star.module.front.service.impl;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dao.FensMapper;
import com.star.module.front.entity.Fens;
import com.star.module.front.service.IFensService;
import com.star.module.front.vo.MyGuardVo;
import com.star.module.front.vo.PersonalVo;
import com.star.module.user.common.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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

}

