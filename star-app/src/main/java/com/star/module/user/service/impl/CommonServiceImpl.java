
package com.star.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.user.common.TokenManager;
import com.star.module.user.dao.FunctionMapper;
import com.star.module.user.dao.UserMapper;
import com.star.module.user.dto.ModifyPassCodeDTO;
import com.star.module.user.entity.Function;
import com.star.module.user.entity.User;
import com.star.module.user.service.CommonService;
import com.star.module.user.vo.MenusVo;
import com.star.module.user.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private FunctionMapper functionMapper;


    @Override
    public UserLoginVo login(String account, String pwd) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getAccount, account);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw new ServiceException(ErrorCodeEnum.USER_NOT_EXIST.getCode(),ErrorCodeEnum.USER_NOT_EXIST.getValue());
        }
        if(!pwd.equals(user.getPwd())){
            throw new ServiceException(ErrorCodeEnum.USER_NOT_EXIST.getCode(),ErrorCodeEnum.USER_NOT_EXIST.getValue());
        }
        if (user.getStatus().equals(0)){
            throw new ServiceException(ErrorCodeEnum.USER_FORBID_ERROR.getCode(),ErrorCodeEnum.USER_FORBID_ERROR.getValue());
        }
        String token = tokenManager.createToken(user.getId(),account,user.getName());

        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setToken(token);
        userLoginVo.setAccount(account);
        userLoginVo.setUserId(user.getId());

        //查询角色
        log.info(token);
        return userLoginVo;
    }


    @Override
    public List<MenusVo> getMenus(String userId){
        List<MenusVo> menusVos = new ArrayList<>();
        QueryWrapper<Function> queryWrapper = new QueryWrapper<>();
        List<Function> list = functionMapper.selectList(queryWrapper);
        List<Function> first = new ArrayList<>();
        first = list.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
        first.sort(Comparator.comparing(Function::getSortVal));
        for (Function function : first){
            MenusVo menusVo = new MenusVo();
            menusVo.setId(function.getId());
            menusVo.setName(function.getName());
            menusVo.setUri(function.getUri());
            menusVo.setIcon(function.getIcon());
            menusVo.setMenus(getNodeMenus(function.getId(),list));
            menusVos.add(menusVo);
        }
        return menusVos;
    }

    @Override
    public Boolean modifyPassCode(ModifyPassCodeDTO modifyPassCodeDTO) {
        if(StringUtils.isEmpty(modifyPassCodeDTO.getAccount())
                || StringUtils.isEmpty(modifyPassCodeDTO.getOldPassword())
                || StringUtils.isEmpty(modifyPassCodeDTO.getNewPassword())){
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),ErrorCodeEnum.PARAM_ERROR.getValue());
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getAccount, modifyPassCodeDTO.getAccount());
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw new ServiceException(ErrorCodeEnum.USER_NOT_EXIST.getCode(),ErrorCodeEnum.USER_NOT_EXIST.getValue());
        }
        if(!modifyPassCodeDTO.getOldPassword().equals(user.getPwd())){
            throw new ServiceException(ErrorCodeEnum.USER_NOT_EXIST.getCode(),ErrorCodeEnum.USER_NOT_EXIST.getValue());
        }
        if (user.getStatus().equals(0)){
            throw new ServiceException(ErrorCodeEnum.USER_FORBID_ERROR.getCode(),ErrorCodeEnum.USER_FORBID_ERROR.getValue());
        }
        user.setPwd(modifyPassCodeDTO.getNewPassword());
        int res = this.userMapper.updateById(user);
        return res == 1;
    }

    ;

    private List<MenusVo> getNodeMenus(Long parentId,List<Function> list){

        List<Function> functions = new ArrayList<>();
        functions = list.stream().filter(item -> item.getParentId().longValue() == parentId.longValue()).collect(Collectors.toList());
        functions.sort(Comparator.comparing(Function::getSortVal));
        List<MenusVo> menusVos = new ArrayList<>();
        for (Function function : functions){
            MenusVo menusVo = new MenusVo();
            menusVo.setId(function.getId());
            menusVo.setName(function.getName());
            menusVo.setUri(function.getUri());
            menusVo.setIcon(function.getIcon());
            menusVo.setMenus(getNodeMenus(function.getId(),list));
            menusVos.add(menusVo);

        }
        return menusVos;
    }
}

