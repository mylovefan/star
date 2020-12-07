package com.star.module.front.service;

import com.star.module.front.dto.UpdatePersonalCenterInfoDto;
import com.star.module.front.vo.PersonalVo;

/**
 * <p>
 * 微信用户信息 服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-11-30
 */
public interface IFensService{

    /**
     * 个人中心 我的信息
     *
     * @return
     */
    PersonalVo personalCenterInfo();

    /**
     * 修改信息
     *
     * @param updatePersonalCenterInfoDto
     */
    void updatePersonalCenterInfo(UpdatePersonalCenterInfoDto updatePersonalCenterInfoDto);

}
