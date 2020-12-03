package com.star.module.front.controller;

import com.star.module.front.facade.PersonalCenterFacade;
import com.star.module.front.service.IFensService;
import com.star.module.front.vo.PersonalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonalCenterController implements PersonalCenterFacade {

    @Autowired
    private IFensService fensService;

    @Override
    public PersonalVo personalCenterInfo() {
        return null;
    }
}
