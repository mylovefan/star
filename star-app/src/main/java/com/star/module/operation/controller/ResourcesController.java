package com.star.module.operation.controller;


import com.github.pagehelper.PageSerializable;
import com.star.module.front.dto.OpenImgDto;
import com.star.module.front.vo.OpenImgVo;
import com.star.module.operation.dto.ListAwardDto;
import com.star.module.operation.dto.ResourcesDto;
import com.star.module.operation.dto.ResourcesPageDto;
import com.star.module.operation.facade.ResourcesFacade;
import com.star.module.operation.service.IResourcesService;
import com.star.module.operation.vo.ListAwardVo;
import com.star.module.operation.vo.ResourcesDetailVo;
import com.star.module.operation.vo.ResourcesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private IResourcesService resourcesService;

    @Override
    public void addOrUpdateResources(@RequestBody ResourcesDto resourcesDto) {
        resourcesService.addOrUpdateResources(resourcesDto);
    }

    @Override
    public PageSerializable<ResourcesVo> selectResourcesPage(@RequestBody ResourcesPageDto resourcesPageDto) {
        return resourcesService.selectResourcesPage(resourcesPageDto);
    }

    @Override
    public ResourcesDetailVo selectResources(@RequestParam("id") Long id) {
        return resourcesService.selectResources(id);
    }

    @Override
    public void addOrUpdateListAward(@RequestBody ListAwardDto listAwardDto) {
        resourcesService.addOrUpdateListAward(listAwardDto);
    }

    @Override
    public ListAwardVo selectListAward(@RequestParam("code") String code) {
        return resourcesService.selectListAward(code);
    }


    @Override
    public void addOrUpdatOpenImg(@RequestBody OpenImgDto openImgDto) {
        resourcesService.addOrUpdatOpenImg(openImgDto);
    }

    @Override
    public OpenImgVo selectOpenImg() {

        return resourcesService.selectOpenImg();
    }


    @Override
    public void saveOrUpdateViewLimit(@RequestParam("viewLimit") int viewLimit) {
        resourcesService.saveOrUpdateViewLimit(viewLimit);
    }

    @Override
    public Integer selectViewLimit() {
        return resourcesService.selectViewLimit();
    }
}
