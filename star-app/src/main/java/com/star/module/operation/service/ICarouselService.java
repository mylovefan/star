package com.star.module.operation.service;

import com.star.module.operation.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.user.dto.CarouselDto;
import com.star.module.user.vo.CarouselVo;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
public interface ICarouselService extends IService<Carousel> {

    /**
     * 新增修改轮播图
     *
     * @param carouselDto
     */
    void addOrUpdateCarousel(CarouselDto carouselDto);

    /**
     * 查询轮播图
     *
     * @return
     */
    CarouselVo selectCarousel();
}
