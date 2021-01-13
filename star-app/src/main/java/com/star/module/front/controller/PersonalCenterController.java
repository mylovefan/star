package com.star.module.front.controller;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dto.UpdatePersonalCenterInfoDto;
import com.star.module.front.facade.PersonalCenterFacade;
import com.star.module.front.service.IFensService;
import com.star.module.front.service.IFensVigourLogService;
import com.star.module.front.service.IGuardService;
import com.star.module.front.vo.*;
import com.star.module.operation.service.IResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonalCenterController implements PersonalCenterFacade {

    @Autowired
    private IFensService fensService;

    @Autowired
    private IGuardService guardService;

    @Autowired
    private IFensVigourLogService fensVigourLogService;

    @Autowired
    private IResourcesService resourcesService;

    @Override
    public PersonalVo personalCenterInfo() {
        return fensService.personalCenterInfo();
    }

    @Override
    public PageSerializable<MyGuardVo> selectMyGuard(@RequestBody PageDTO pageDTO) {
        return guardService.selectMyGuard(pageDTO);
    }

    @Override
    public PageSerializable<FensVigourLogVo> selectVigourLog(@RequestBody PageDTO pageDTO) {
        return fensVigourLogService.selectVigourLog(pageDTO);
    }

    @Override
    public PageSerializable<MyHitListVo> selectHitList(@RequestBody PageDTO pageDTO) {
        return guardService.selectHitList(pageDTO);
    }

    @Override
    public void updatePersonalCenterInfo(@RequestBody UpdatePersonalCenterInfoDto updatePersonalCenterInfoDto) {
        fensService.updatePersonalCenterInfo(updatePersonalCenterInfoDto);
    }

    @Override
    public List<ListAwardPersionVo> listAward() {
        return resourcesService.listAward();
    }


    @Override
    public void removeMyGuard(@RequestParam("starId") Long starId) {

    }
}
