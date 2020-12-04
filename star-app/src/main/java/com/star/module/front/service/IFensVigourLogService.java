package com.star.module.front.service;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.entity.FensVigourLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.front.vo.FensVigourLogVo;

/**
 * <p>
 * 粉丝热力获取记录 服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
public interface IFensVigourLogService extends IService<FensVigourLog> {

    /**
     * 新增活力记录
     *
     * @param fensVigourLog
     */
    void addVigour(FensVigourLog fensVigourLog);

    /**
     * 我的热力获取记录
     *
     * @param pageDTO
     * @return
     */
    PageSerializable<FensVigourLogVo> selectVigourLog(PageDTO pageDTO);


}
