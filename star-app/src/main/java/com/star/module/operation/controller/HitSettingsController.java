package com.star.module.operation.controller;


import com.star.module.front.service.IHitSettingsService;
import com.star.module.operation.dto.HitSettingsDto;
import com.star.module.operation.facade.HitSettingsFacade;
import com.star.module.operation.vo.HitSettingsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 热力设置表 前端控制器
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@RestController
public class HitSettingsController implements HitSettingsFacade {

    @Autowired
    private IHitSettingsService iHitSettingsService;
    @Override
    public void setHitSettings(@RequestBody HitSettingsDto dto) {
        iHitSettingsService.setHitSettings(dto);
    }

    @Override
    public HitSettingsVo selectHisSettings() {
        return iHitSettingsService.selectHisSettings();
    }
}
