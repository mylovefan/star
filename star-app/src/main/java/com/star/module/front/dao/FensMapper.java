package com.star.module.front.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.star.module.front.entity.Fens;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.vo.GiveVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 微信用户信息 Mapper 接口
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-11-30
 */
public interface FensMapper extends BaseMapper<Fens> {

    /**
     * 赠送记录
     *
     * @param giveDto
     * @return
     */
    Page<GiveVo> selectGivePage(GiveDto giveDto);

}
