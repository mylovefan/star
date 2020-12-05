package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.FensVigourLogMapper;
import com.star.module.front.entity.FensVigourLog;
import com.star.module.front.entity.HitSettings;
import com.star.module.front.dao.HitSettingsMapper;
import com.star.module.front.entity.Star;
import com.star.module.front.enums.VigourTypeEnums;
import com.star.module.front.service.IHitSettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.module.front.vo.StarHitSettingsVo;
import com.star.module.operation.dto.HitSettingsDto;
import com.star.module.operation.vo.HitSettingsVo;
import com.star.module.user.common.UserUtil;
import com.star.util.SnowflakeId;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 热力设置表 服务实现类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Service
public class HitSettingsServiceImpl extends ServiceImpl<HitSettingsMapper, HitSettings> implements IHitSettingsService {

    @Autowired
    private HitSettingsMapper hitSettingsMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FensVigourLogMapper fensVigourLogMapper;

    @Override
    public void setHitSettings(HitSettingsDto dto) {
        if(dto.getId()==null){
            List<HitSettings> settingsList = hitSettingsMapper.selectList(new QueryWrapper<>());
            if(settingsList.size()>1){
                throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"热力信息已存在，无法重复添加");
            }
            HitSettings settings = new HitSettings();
            BeanUtils.copyProperties(dto, settings);
            settings.setId(SnowflakeId.getInstance().nextId());
            LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
            settings.setCreateTime(localDateTimeOfNow);
            settings.setUpdateTime(localDateTimeOfNow);
            hitSettingsMapper.insert(settings);
        }else{
            HitSettings settings = hitSettingsMapper.selectById(dto.getId());
            if(settings==null){
                throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"热力信息不存在");
            }
            BeanUtils.copyProperties(dto,settings);
            LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
            settings.setUpdateTime(localDateTimeOfNow);
            hitSettingsMapper.updateById(settings);
        }
    }

    @Override
    public HitSettingsVo selectHisSettings() {
        HitSettingsVo vo = new HitSettingsVo();
        QueryWrapper<HitSettings> query = new QueryWrapper<>();
        HitSettings settings = hitSettingsMapper.selectOne(query);
        if(settings!=null){
            String drawFieldNumsStr = settings.getDrawFieldNums();
            String [] str = drawFieldNumsStr.split(",");
            BeanUtils.copyProperties(settings, vo);
            vo.setDrawFieldNums(Arrays.asList(str));
        }
        return vo;
    }


    @Override
    public StarHitSettingsVo selectHitSettings(Long starId) {
        HitSettings hitSettings = hitSettingsMapper.selectOne(new QueryWrapper<HitSettings>());
        StarHitSettingsVo starHitSettingsVo = new StarHitSettingsVo();
        if(hitSettings == null){
            return starHitSettingsVo;
        }
        BeanUtils.copyProperties(hitSettings,starHitSettingsVo);
        Long id = UserUtil.getCurrentUserId(request);
        if(id == null){
            starHitSettingsVo.setSignFlag(false);
            return starHitSettingsVo;
        }
        QueryWrapper<FensVigourLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FensVigourLog::getFensId,id).eq(FensVigourLog::getStarId,starId)
                .eq(FensVigourLog::getType, VigourTypeEnums.SIGN.getCode())
                .eq(FensVigourLog::getVigTime, LocalDate.now());
        Integer count = fensVigourLogMapper.selectCount(queryWrapper);
        if(count !=null && count>0){
            starHitSettingsVo.setSignFlag(true);
        }else {
            starHitSettingsVo.setSignFlag(false);
        }
        return starHitSettingsVo;
    }
}
