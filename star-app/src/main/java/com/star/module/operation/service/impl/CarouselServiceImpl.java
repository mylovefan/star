package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.common.CommonConstants;
import com.star.module.operation.dao.CarouselMapper;
import com.star.module.operation.entity.Carousel;
import com.star.module.operation.service.ICarouselService;
import com.star.module.user.dto.CarouselDto;
import com.star.module.user.vo.CarouselVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public void addOrUpdateCarousel(CarouselDto carouselDto) {
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(carouselDto,carousel);
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        if(carouselDto.getId() != null){
            carousel.setUpdateTime(localDateTimeOfNow);
            carouselMapper.updateById(carousel);
        }else {
            carousel.setAddTime(localDateTimeOfNow);
            carouselMapper.insert(carousel);
        }
    }

    @Override
    public CarouselVo selectCarousel() {
        Carousel carousel = carouselMapper.selectOne(new QueryWrapper<>());
        CarouselVo carouselVo = new CarouselVo();
        if (carousel == null){
            return carouselVo;
        }
        BeanUtils.copyProperties(carousel,carouselVo);
        return carouselVo;
    }
}
