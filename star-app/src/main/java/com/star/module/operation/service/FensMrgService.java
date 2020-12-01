package com.star.module.operation.service;

import com.github.pagehelper.PageSerializable;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.vo.FensVo;

/**
 * @description :粉丝管理
 * @author zhangrc
 * @date 2020/12/1 16:54
 * @version 1.0
 */
public interface FensMrgService {

    /**
     * 粉丝列表
     *
     * @param fensDto
     * @return
     */
    PageSerializable<FensVo> selectFensPage(FensDto fensDto);
}
