package com.star.module.operation.controller;


import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.user.dto.StarDto;
import com.star.module.user.dto.StarPageDto;
import com.star.module.user.facade.BackendFacade;
import com.star.module.front.service.IStarService;
import com.star.module.user.vo.StartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 明星表 前端控制器
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@RestController
public class StarController implements BackendFacade {

    @Autowired
    private IStarService iStarService;

    @Override
    public PageSerializable<StartVo> getStars(@RequestBody StarPageDto starPageDto) {
        return iStarService.selectPage(starPageDto);
    }

    @Override
    public void addStar(@RequestBody StarDto dto) {
        iStarService.addStar(dto);
    }

    @Override
    public void updateStar(@RequestBody StarDto dto) {
        iStarService.updateStar(dto);
    }
}
