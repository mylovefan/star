package com.star.module.operation.service;

import com.star.module.operation.entity.StarTags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 明星标签表 服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
public interface IStarTagsService extends IService<StarTags> {

    /**
     * 删除明星所属标签
     * @param id
     */
    void deleteByStarId(Long id);
}
