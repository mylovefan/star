package com.star.module.user.service.impl;

import com.star.module.user.entity.HitList;
import com.star.module.user.dao.HitListMapper;
import com.star.module.user.service.IHitListService;
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
