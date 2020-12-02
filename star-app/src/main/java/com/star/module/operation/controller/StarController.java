package com.star.module.operation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.service.IHitListService;
import com.star.module.operation.entity.Tags;
import com.star.module.operation.service.ITagsService;
import com.star.module.user.dto.HitListDto;
import com.star.module.user.dto.StarDto;
import com.star.module.user.dto.StarPageDto;
import com.star.module.user.facade.BackendFacade;
import com.star.module.front.service.IStarService;
import com.star.module.user.vo.HitListVo;
import com.star.module.user.vo.StartVo;
import com.star.module.user.vo.TagsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private ITagsService iTagsService;
    @Autowired
    private IHitListService hitListService;


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

    @Override
    public List<TagsVo> getTagsList() {
        return iTagsService.tagsList();
    }

    @Override
    public void addTag(@RequestParam(value = "signature") String name) {
        iTagsService.addTags(name);
    }

    /**
     * 榜单列表
     * @param hitListDto
     * @return
     */
    @Override
    public PageSerializable<HitListVo> getStars(HitListDto hitListDto) {
        return hitListService.selectPage(hitListDto);
    }


}
