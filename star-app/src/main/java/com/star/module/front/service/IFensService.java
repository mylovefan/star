package com.star.module.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.entity.Fens;
import com.star.module.front.vo.MyGuardVo;
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


}
