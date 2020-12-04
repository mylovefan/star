package com.star.module.front.service;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.entity.Guard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.front.vo.MyGuardVo;
import com.star.module.front.vo.MyHitListVo;

/**
 * <p>
 * 守护表 服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-03
 */
public interface IGuardService extends IService<Guard> {

    /**
     * 我的守护
     *
     * @param pageDTO
     * @return
     */
    PageSerializable<MyGuardVo> selectMyGuard(PageDTO pageDTO);

    /**
     * 我的打榜记录
     *
     * @param pageDTO
     * @return
     */
    PageSerializable<MyHitListVo> selectHitList(PageDTO pageDTO);

}
