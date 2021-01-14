package com.star.module.operation.controller;


import com.github.pagehelper.PageSerializable;
import com.star.module.front.service.IHitListService;
import com.star.module.operation.service.ITagsService;
import com.star.module.operation.dto.FensMarkRankDto;
import com.star.module.operation.dto.HitListDto;
import com.star.module.operation.dto.StarDto;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.facade.StarFacade;
import com.star.module.front.service.IStarService;
import com.star.module.operation.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
public class StarController implements StarFacade {

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
    public StarDetailVo selectStatById(Long id) {
        return iStarService.selectStatById(id);
    }

    @Override
    public List<TagsVo> getTagsList() {
        return iTagsService.tagsList();
    }

    @Override
    public void addTag(@RequestParam(value = "signature") String name) {
        iTagsService.addTags(name);
    }

    @Override
    public void deleteTags(@RequestParam(value = "id") Long id) {
        iTagsService.deleteTags(id);
    }

    /**
     * 榜单列表
     * @param hitListDto
     * @return
     */
    @Override
    public PageSerializable<HitListVo> hilListRankList(@RequestBody HitListDto hitListDto) {
        return hitListService.selectPage(hitListDto);
    }

    @Override
    public PageSerializable<FensMarkVo> fensMarkRankList(@RequestBody FensMarkRankDto fensMarkRankDto) {
        return hitListService.selectFensRankPage(fensMarkRankDto);
    }


    @Override
    public void downStarList(HttpServletResponse response,@RequestParam("name") String name,@RequestParam("starId")  Long starId) {
        iStarService.downStarList(response,name,starId);
    }
}
