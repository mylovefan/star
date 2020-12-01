package com.star.module.operation.controller;

import com.github.pagehelper.PageSerializable;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.facade.FensFacade;
import com.star.module.operation.service.FensMrgService;
import com.star.module.operation.vo.FensVo;
import com.star.module.operation.vo.GiveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FensController implements FensFacade {

    @Autowired
    private FensMrgService fensMrgService;

    @Override
    public PageSerializable<FensVo> selectFensPage(@RequestBody FensDto fensDto) {
        return fensMrgService.selectFensPage(fensDto);
    }

    @Override
    public PageSerializable<GiveVo> selectGivePage(@RequestBody GiveDto giveDto) {
        return null;
    }
}
