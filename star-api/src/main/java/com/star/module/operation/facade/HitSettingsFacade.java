package com.star.module.operation.facade;

import com.star.module.operation.dto.HitSettingsDto;
import com.star.module.operation.vo.HitSettingsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "热力管理", tags = "热力管理")
@RequestMapping(value = "hitSettings/")
public interface HitSettingsFacade {

    @ApiOperation(value = "热力设置")
    @PostMapping(value = "edit")
    void setHitSettings(@RequestBody HitSettingsDto dto);

    @ApiOperation(value = "热力设置查询")
    @GetMapping(value = "select")
    HitSettingsVo selectHisSettings();
}
