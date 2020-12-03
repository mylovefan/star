package com.star.module.operation.service;

import com.star.module.operation.entity.Tags;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.operation.vo.TagsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
public interface ITagsService extends IService<Tags> {

    List<TagsVo> tagsList();

    void addTags(String name);
}
