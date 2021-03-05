package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.star.common.CommonConstants;
import com.star.module.front.dao.HitListMapper;
import com.star.module.front.dao.OpenImgMapper;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Star;
import com.star.module.front.vo.CarouselDeatilVo;
import com.star.module.front.vo.HomeCarouselVo;
import com.star.module.front.vo.OpenImgVo;
import com.star.module.operation.dao.CarouselMapper;
import com.star.module.operation.dao.ListAwardMapper;
import com.star.module.operation.entity.Carousel;
import com.star.module.operation.entity.ListAward;
import com.star.module.operation.entity.OpenImg;
import com.star.module.operation.service.ICarouselService;
import com.star.module.operation.util.DateUtils;
import com.star.module.operation.vo.OpenVo;
import com.star.module.user.dto.CarouselDto;
import com.star.module.user.vo.CarouselVo;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private HitListMapper hitListMapper;

    @Autowired
    private OpenImgMapper openImgMapper;

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
    public List<CarouselVo> selectCarousel() {
        List<Carousel> carousels = carouselMapper.selectList(new QueryWrapper<>());
        List<CarouselVo> carouselVos = new ArrayList<>();
        for (Carousel carousel : carousels){
           CarouselVo carouselVo = new CarouselVo();
           BeanUtils.copyProperties(carousel,carouselVo);
           carouselVos.add(carouselVo);
        }
        return carouselVos;
    }


    @Override
    public List<HomeCarouselVo> carouselList() {
        List<HomeCarouselVo> list = new ArrayList<>();
        QueryWrapper<Carousel> cwrapper = new QueryWrapper<>();
        cwrapper.lambda().eq(Carousel::getOpen,1).orderByAsc(Carousel::getSort);
        List<Carousel> carousels = carouselMapper.selectList(cwrapper);
        for (Carousel carousel : carousels){
            HomeCarouselVo vo1 = new HomeCarouselVo();
            vo1.setImg(carousel.getHome());
            vo1.setLevelImg(carousel.getLevel());
            list.add(vo1);
        }

        QueryWrapper<ListAward> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ListAward::getCode,"MONTH").eq(ListAward::getOpen,1);
        ListAward listAward = listAwardMapper.selectOne(wrapper);
        if(listAward != null && listAward.getType().indexOf("3") != -1){
            String startTime = DateUtils.getLastMonthStart();
            String endTime = DateUtils.getLastMonthEnd();

            HomeCarouselVo lastRank = hitListMapper.getLastRank(startTime, endTime);
            if(lastRank !=null){
                lastRank.setCode("MONTH");
                list.add(lastRank);
            }
        }

        QueryWrapper<ListAward> weekwrapper = new QueryWrapper<>();
        weekwrapper.lambda().eq(ListAward::getCode,"WEEK").eq(ListAward::getOpen,1);
        ListAward listAward2 = listAwardMapper.selectOne(weekwrapper);
        if(listAward2 != null && listAward2.getType().indexOf("3") != -1){
            Date starDate = DateUtils.getLastWeekMonday();
            Date endDate = DateUtils.getLastSundayEndDay();
            String startTime = DateUtils.formatDate(starDate,DateUtils.DATE_FORMAT_DATETIME);
            String endTime = DateUtils.formatDate(endDate,DateUtils.DATE_FORMAT_DATETIME);
            HomeCarouselVo lastRank = hitListMapper.getLastRank(startTime, endTime);
            if(lastRank !=null){
                lastRank.setCode("WEEK");
                list.add(lastRank);
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
        //carouselVo.setLevel1(carousel.getLevel1());
        //carouselVo.setLevel2(carousel.getLevel2());
        return carouselVo;
    }

    @Override
    public OpenVo selectOpenImg() {
        QueryWrapper<OpenImg> wrapper = new QueryWrapper<>();
        OpenImg openImg = openImgMapper.selectOne(wrapper);
        OpenVo openImgVo = new OpenVo();
        if(openImg == null || openImg.getOpen() == 0){
            return openImgVo;
        }
        openImgVo.setImg(openImg.getImg());
        return openImgVo;
    }
}
