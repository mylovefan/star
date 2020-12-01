package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.module.operation.dao.TagsMapper;
import com.star.module.operation.entity.Tags;
import com.star.module.operation.service.ITagsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

}
