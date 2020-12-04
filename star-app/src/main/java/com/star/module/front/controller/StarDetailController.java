package com.star.module.front.controller;

import com.star.module.front.facade.StarDetailFacade;
import com.star.module.front.service.IStarService;
import com.star.module.front.vo.StarInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StarDetailController implements StarDetailFacade {

    @Autowired
    private IStarService starService;

    @Override
    public StarInfoVo selectStarInfo(@RequestParam("id") Long id) {
        return null;
    }
}
