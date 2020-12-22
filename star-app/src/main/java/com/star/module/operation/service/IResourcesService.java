package com.star.module.operation.service;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dto.ResourcesRankDto;
import com.star.module.front.vo.FensJoinResVo;
import com.star.module.front.vo.ListAwardPersionVo;
import com.star.module.front.vo.StarResourcesVo;
import com.star.module.operation.dto.ListAwardDto;
import com.star.module.operation.dto.ResourcesDto;
import com.star.module.operation.dto.ResourcesPageDto;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.entity.Resources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.operation.vo.ListAwardVo;
import com.star.module.operation.vo.ResourcesDetailVo;
import com.star.module.operation.vo.ResourcesVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
public interface IResourcesService extends IService<Resources> {

    /**
     * 新增修改资源
     *
     * @param resourcesDto
     */
    void addOrUpdateResources(ResourcesDto resourcesDto);

    /**
     * 资源列表
     *
     * @param resourcesPageDto
     * @return
     */
    PageSerializable<ResourcesVo> selectResourcesPage(ResourcesPageDto resourcesPageDto);

    /**
     * 资源详情
     *
     * @param id
     * @return
     */
    ResourcesDetailVo selectResources(Long id);

    /**
     * 新增修改周榜月榜
     *
     * @param listAwardDto
     */
    void addOrUpdateListAward(ListAwardDto listAwardDto);

    /**
     * 周榜月榜详情
     *
     * @param code
     * @return
     */
    ListAwardVo selectListAward(String code);

    /**
     * 明星详情页资源列表
     *
     * @param starPageDto
     * @return
     */
    PageSerializable<StarResourcesVo> selectResources(StarPageDto starPageDto);

    /**
     * 粉丝资源排名
     *
     * @param resourcesRankDto
     * @return
     */
    PageSerializable<FensJoinResVo> selectResourcesRank(ResourcesRankDto resourcesRankDto);


    /**
     * 参与活动
     * @param resourcesRelId
     */
    void joinResources( Long resourcesRelId,Integer status);

    /**
     * 榜单
     *
     * @return
     */
    List<ListAwardPersionVo> listAward();

}
