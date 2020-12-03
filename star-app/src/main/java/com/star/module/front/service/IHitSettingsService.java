package com.star.module.front.service;

import com.star.module.front.entity.HitSettings;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.operation.dto.HitSettingsDto;
import com.star.module.operation.vo.HitSettingsVo;

/**
 * <p>
 * 热力设置表 服务类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
public interface IHitSettingsService extends IService<HitSettings> {

    void setHitSettings(HitSettingsDto dto);

    HitSettingsVo selectHisSettings();
}
