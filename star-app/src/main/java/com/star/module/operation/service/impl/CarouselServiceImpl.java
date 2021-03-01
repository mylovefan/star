package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.star.common.CommonConstants;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Star;
import com.star.module.front.vo.CarouselDeatilVo;
import com.star.module.front.vo.HomeCarouselVo;
import com.star.module.operation.dao.CarouselMapper;
import com.star.module.operation.dao.ListAwardMapper;
import com.star.module.operation.entity.Carousel;
import com.star.module.operation.entity.ListAward;
import com.star.module.operation.service.ICarouselService;
import com.star.module.user.dto.CarouselDto;
import com.star.module.user.vo.CarouselVo;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    @Autowired
    private StarMapper starMapper;

    @Autowired
    private ListAwardMapper listAwardMapper;

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


    @Override
    public List<HomeCarouselVo> carouselList() {
        List<HomeCarouselVo> list = new ArrayList<>();
        Carousel carousel = carouselMapper.selectOne(new QueryWrapper<>());
        if(carousel!=null){
            if(carousel.getOpen().equals(NumberUtils.INTEGER_ONE)){
                if(StringUtil.isNotEmpty(carousel.getHome1())){
                    HomeCarouselVo vo1 = new HomeCarouselVo();
                    vo1.setImg(carousel.getHome1());
                    vo1.setLevel(1);
                    list.add(vo1);
                }
            }
            if(carousel.getOpen2().equals(NumberUtils.INTEGER_ONE)){
                if(StringUtil.isNotEmpty(carousel.getHome2())){
                    HomeCarouselVo vo2 = new HomeCarouselVo();
                    vo2.setImg(carousel.getHome2());
                    vo2.setLevel(2);
                    list.add(vo2);
                }
            }
        }

        QueryWrapper<ListAward> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(ListAward::getCode,"MONTH");
        ListAward listAward = listAwardMapper.selectOne(wrapper);
        if(listAward != null && listAward.getType() == 3){
            QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Star::getThisMonthRank, 1);
            Star star = starMapper.selectOne(queryWrapper);
            if(star !=null){
                HomeCarouselVo vo3 = new HomeCarouselVo();
                vo3.setImg(star.getHomeImg());
                vo3.setStarName(star.getName());
                vo3.setStarId(star.getId());
                vo3.setCode("MONTH");
                list.add(vo3);
            }
        }

        QueryWrapper<ListAward> weekwrapper = new QueryWrapper<>();
        weekwrapper.lambda().in(ListAward::getCode,"WEEK");
        ListAward listAward2 = listAwardMapper.selectOne(weekwrapper);
        if(listAward2 != null && listAward2.getType() == 3){
            QueryWrapper<Star> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.lambda().eq(Star::getThisWeekRank, 1);
            Star star1 = starMapper.selectOne(queryWrapper1);
            if(star1 !=null){
                HomeCarouselVo vo4 = new HomeCarouselVo();
                vo4.setImg(star1.getHomeImg());
                vo4.setStarName(star1.getName());
                vo4.setStarId(star1.getId());
                vo4.setCode("WEEK");
                list.add(vo4);
            }
        }
        return list;
    }

    @Override
    public CarouselDeatilVo seleclCarouseDeatil() {
        Carousel carousel = carouselMapper.selectOne(new QueryWrapper<>());
        CarouselDeatilVo carouselVo = new CarouselDeatilVo();
        if (carousel == null){
            return carouselVo;
        }
        carouselVo.setLevel1(carousel.getLevel1());
        carouselVo.setLevel2(carousel.getLevel2());
        return carouselVo;
    }

    @Override
    public String selectOpenImg() {
        List<ListAward> list = listAwardMapper.selectList(new QueryWrapper<>());
        for (ListAward listAward : list){
            if("WEEK".equals(listAward.getCode()) && listAward.getType() == 2){
                QueryWrapper<Star> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.lambda().eq(Star::getThisWeekRank, 1);
                Star star1 = starMapper.selectOne(queryWrapper1);
                if(star1 != null){
                    return star1.getOpenImg();
                }
            }

            if("MONTH".equals(listAward.getCode()) && listAward.getType() == 2){
                QueryWrapper<Star> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.lambda().eq(Star::getThisMonthRank, 1);
                Star star1 = starMapper.selectOne(queryWrapper1);
                if(star1 != null){
                    return star1.getOpenImg();
                }
            }
        }
        return null;
    }
}
