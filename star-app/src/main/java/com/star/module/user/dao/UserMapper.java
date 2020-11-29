package com.star.module.user.dao;

import com.star.module.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 运营后台用户表 Mapper 接口
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-11-29
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
