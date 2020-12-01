package com.star.module.front.service.impl;

import com.star.module.front.entity.HitList;
import com.star.module.front.dao.HitListMapper;
import com.star.module.front.service.IHitListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 打榜记录表 服务实现类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Service
public class HitListServiceImpl extends ServiceImpl<HitListMapper, HitList> implements IHitListService {
}
