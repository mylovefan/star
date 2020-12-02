package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.module.operation.dao.ResourcesMapper;
import com.star.module.operation.entity.Resources;
import com.star.module.operation.service.IResourcesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements IResourcesService {

}
