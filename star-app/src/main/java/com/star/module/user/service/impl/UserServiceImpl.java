package com.star.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageSerializable;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.user.dao.UserMapper;
import com.star.module.user.dto.UserDto;
import com.star.module.user.dto.UserPageDto;
import com.star.module.user.entity.User;
import com.star.module.user.service.UserService;
import com.star.module.user.vo.UserVo;
import com.star.util.SnowflakeId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getAccount, userDto.getAccount());
        User user = userMapper.selectOne(queryWrapper);
        if(user != null){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"账号已存在");
        }
        User userDo = new User();
        BeanUtils.copyProperties(userDto,userDo);
        userDo.setStatus(1);
        userDo.setId(SnowflakeId.getInstance().nextId());
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        userDo.setAddTime(localDateTimeOfNow);
        userMapper.insert(userDo);
    }


    @Override
    public void delUser(Long id) {
        User user = userMapper.selectById(id);
        if(user.getAccount().equals("admin")){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"超级管理员不允许删除");
        }
        userMapper.deleteById(id);
    }

    @Override
    public void updateUser(Long id,Integer status) {
        User user = new User();
        user.setStatus(status);
        user.setId(id);
        userMapper.updateById(user);
    }


    @Override
    public PageSerializable<UserVo> selectPage(UserPageDto userPageDto) {
        IPage<User> page = new Page<>(userPageDto.getPageNum(),userPageDto.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(userPageDto.getAccount())){
            queryWrapper.lambda().like(User::getAccount,userPageDto.getAccount());
        }
        if(StringUtils.isNotBlank(userPageDto.getName())){
            queryWrapper.lambda().like(User::getName,userPageDto.getName());
        }
        if(userPageDto.getStatus() != null){
            queryWrapper.lambda().eq(User::getStatus,userPageDto.getStatus());
        }
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        List<UserVo> list = new ArrayList<>();
        for (User user : userIPage.getRecords()){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            list.add(userVo);
        }
        PageSerializable<UserVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(userIPage.getTotal());
        return pageSerializable;
    }
}
