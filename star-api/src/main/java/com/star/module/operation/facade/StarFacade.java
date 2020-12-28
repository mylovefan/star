package com.star.module.operation.facade;

import com.github.pagehelper.PageSerializable;
import com.star.module.operation.dto.FensMarkRankDto;
import com.star.module.operation.dto.HitListDto;
import com.star.module.operation.dto.StarDto;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "明星管理", tags = "明星管理")
@RequestMapping("star/")
public interface StarFacade {


    @ApiOperation(value = "明星列表")
    @PostMapping("star/list")
    PageSerializable<StartVo> getStars(@RequestBody StarPageDto starPageDto);

    @ApiOperation(value = "新增明星")
    @PostMapping("star/add")
    void addStar(@RequestBody StarDto dto);

    @ApiOperation(value = "修改明星")
    @PostMapping("star/update")
    void updateStar(@RequestBody StarDto dto);

    @ApiOperation(value = "明星详情")
    @GetMapping("star/selectStatById")
    StarDetailVo selectStatById(@RequestParam("id") Long id);

    @ApiOperation(value = "标签列表")
    @PostMapping("tags/list")
    List<TagsVo> getTagsList();

    @ApiOperation(value = "新增标签")
    @GetMapping("tags/add")
    void addTag(@RequestParam("name") String name);

    @ApiOperation(value = "删除标签")
    @GetMapping("tags/deleteTags")
    void deleteTags(@RequestParam("id") Long id);

    @ApiOperation(value = "榜单排行榜列表")
    @PostMapping("hitList/rankList")
    PageSerializable<HitListVo> hilListRankList(@RequestBody HitListDto hitListDto);

    @ApiOperation(value = "粉丝贡献榜列表")
    @PostMapping("fensMark/rankList")
    PageSerializable<FensMarkVo> fensMarkRankList(@RequestBody FensMarkRankDto fensMarkRankDto);

}
