package com.star.module.operation.controller;


import com.star.module.operation.service.ICarouselService;
import com.star.module.user.dto.CarouselDto;
import com.star.module.user.facade.CarouselFacade;
import com.star.module.user.vo.CarouselVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@RestController
public class CarouselController implements CarouselFacade {

    @Autowired
    private ICarouselService carouselService;

    @Override
    public void addOrUpdateCarousel(@RequestBody CarouselDto carouselDto) {
        carouselService.addOrUpdateCarousel(carouselDto);
    }

    @Override
    public List<CarouselVo> selectCarousel() {
        return carouselService.selectCarousel();
    }
}
