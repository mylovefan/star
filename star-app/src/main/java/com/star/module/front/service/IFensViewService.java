package com.star.module.front.service;

import com.star.module.front.entity.FensView;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2021-04-02
 */
public interface IFensViewService extends IService<FensView> {

    Boolean selectViewLimit();
}
