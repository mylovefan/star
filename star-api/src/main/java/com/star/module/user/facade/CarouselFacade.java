package com.star.module.user.facade;

import com.star.module.user.dto.CarouselDto;
import com.star.module.user.vo.CarouselVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "轮播图设置", tags = "轮播图设置")
@RequestMapping("carousel/")
public interface CarouselFacade {

    @ApiOperation(value = "轮播图设置")
    @PostMapping("addOrUpdateCarousel")
    void addOrUpdateCarousel(@RequestBody CarouselDto carouselDto);


    @ApiOperation(value = "轮播图查询")
    @PostMapping("selectCarousel")
    CarouselVo selectCarousel();
}
