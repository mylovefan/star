package com.star.module.front.dao;

import com.star.module.front.entity.Star;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 明星表 Mapper 接口
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
public interface StarMapper extends BaseMapper<Star> {

    /**
     * 修改活力值
     * @param vigourVal
     */
    void updateVigour(@Param("id") Long id, @Param("vigourVal") int vigourVal);


    void batchUpdate(@Param("list") List<Star> list);

}
