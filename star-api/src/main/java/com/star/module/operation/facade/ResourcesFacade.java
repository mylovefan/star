package com.star.module.operation.facade;

import com.github.pagehelper.PageSerializable;
import com.star.module.operation.dto.ListAwardDto;
import com.star.module.operation.dto.ResourcesDto;
import com.star.module.operation.dto.ResourcesPageDto;
import com.star.module.operation.vo.ListAwardVo;
import com.star.module.operation.vo.ResourcesDetailVo;
import com.star.module.operation.vo.ResourcesVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value = "资源管理", tags = "资源管理")
@RequestMapping("resources/")
public interface ResourcesFacade {

    @ApiOperation(value = "新增修改资源")
    @PostMapping("addOrUpdateResources")
    void addOrUpdateResources(@RequestBody ResourcesDto resourcesDto);

    @ApiOperation(value = "资源列表")
    @PostMapping("selectResourcesPage")
    PageSerializable<ResourcesVo> selectResourcesPage(@RequestBody ResourcesPageDto resourcesPageDto);

    @ApiOperation(value = "资源详情")
    @GetMapping("selectResources")
    ResourcesDetailVo selectResources(@PathVariable("id") Long id);

    @ApiOperation(value = "周榜月榜新增修改")
    @PostMapping("addOrUpdateListAward")
    void addOrUpdateListAward(@RequestBody ListAwardDto listAwardDto);

    @ApiOperation(value = "查询周榜月榜 code(WEEK | MONTH)")
    @GetMapping("selectListAward")
    ListAwardVo selectListAward(@RequestParam("code") String code);
}
