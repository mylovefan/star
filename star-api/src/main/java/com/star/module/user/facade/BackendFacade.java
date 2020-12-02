package com.star.module.user.facade;

import com.github.pagehelper.PageSerializable;
import com.star.module.user.dto.FensMarkRankDto;
import com.star.module.user.dto.HitListDto;
import com.star.module.user.dto.StarDto;
import com.star.module.user.dto.StarPageDto;
import com.star.module.user.vo.HitListVo;
import com.star.module.user.vo.StartVo;
import com.star.module.user.vo.TagsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(value = "明星管理", tags = "明星管理")
@RequestMapping("backend/")
public interface BackendFacade {


    @ApiOperation(value = "明星列表")
    @PostMapping("star/list")
    PageSerializable<StartVo> getStars(@RequestBody StarPageDto starPageDto);

    @ApiOperation(value = "新增明星")
    @PostMapping("star/add")
    void addStar(@RequestBody StarDto dto);

    @ApiOperation(value = "修改明星")
    @PostMapping("star/update")
    void updateStar(@RequestBody StarDto dto);

    @ApiOperation(value = "标签列表")
    @PostMapping("tags/list")
    List<TagsVo> getTagsList();

    @ApiOperation(value = "新增标签")
    @PostMapping("tags/add")
    void addTag(@RequestParam("name") String name);

    @ApiOperation(value = "榜单排行榜列表")
    @PostMapping("hitList/rankList")
    PageSerializable<HitListVo> hilListRankList(@RequestBody HitListDto hitListDto);

    @ApiOperation(value = "粉丝打榜排行榜列表")
    @PostMapping("fensMark/rankList")
    PageSerializable<HitListVo> fensMarkRankList(@RequestBody FensMarkRankDto fensMarkRankDto);

}
