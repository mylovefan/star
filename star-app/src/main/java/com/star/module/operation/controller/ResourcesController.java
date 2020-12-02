package com.star.module.operation.controller;


import com.github.pagehelper.PageSerializable;
import com.star.module.operation.dto.ResourcesDto;
import com.star.module.operation.dto.ResourcesPageDto;
import com.star.module.operation.facade.ResourcesFacade;
import com.star.module.operation.vo.ResourcesDetailDto;
import com.star.module.operation.vo.ResourcesVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@RestController
public class ResourcesController implements ResourcesFacade {


    @Override
    public void addOrUpdateResources(@RequestBody ResourcesDto resourcesDto) {

    }

    @Override
    public PageSerializable<ResourcesVo> selectResourcesPage(@RequestBody ResourcesPageDto resourcesPageDto) {
        return null;
    }

    @Override
    public ResourcesDetailDto selectResources(@PathVariable("id") Long id) {
        return null;
    }
}
