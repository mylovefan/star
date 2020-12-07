package com.star.module.front.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.star.module.front.entity.Fens;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.vo.GiveVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 修改活力值
     * @param vigourVal
     */
    void updateVigour(@Param("id") Long id,@Param("vigourVal") int vigourVal);


    void updateReduceVigour(@Param("id") Long id,@Param("vigourVal") int vigourVal);

    /**
     * 查询所有粉丝id
     *
     * @return
     */
    List<Long> selectFensIds();

}
