package com.star.module.user.service.impl;

import com.star.module.user.entity.Star;
import com.star.module.user.dao.StarMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.module.user.service.StarService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 明星表 服务实现类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements StarService {

}
