package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.common.CommonConstants;
import com.star.module.front.dao.FensMapper;
import com.star.module.front.dao.FensVigourLogMapper;
import com.star.module.front.entity.Fens;
import com.star.module.front.entity.FensVigourLog;
import com.star.module.front.entity.Guard;
import com.star.module.front.entity.Star;
import com.star.module.front.service.IFensVigourLogService;
import com.star.module.front.vo.FensVigourLogVo;
import com.star.module.front.vo.MyGuardVo;
import com.star.module.user.common.UserUtil;
import com.star.util.SnowflakeId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 粉丝热力获取记录 服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Service
public class FensVigourLogServiceImpl extends ServiceImpl<FensVigourLogMapper, FensVigourLog> implements IFensVigourLogService {

    @Autowired
    private FensVigourLogMapper fensVigourLogMapper;

    @Autowired
    private FensMapper fensMapper;

    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional
    public void addVigour(FensVigourLog fensVigourLog) {
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        fensVigourLog.setId(SnowflakeId.getInstance().nextId());
        fensVigourLog.setAddTime(localDateTimeOfNow);
        fensVigourLog.setVigTime(LocalDate.now());
        fensVigourLogMapper.insert(fensVigourLog);

        //修改粉丝表活力值记录
        fensMapper.updateVigour(fensVigourLog.getFensId(),fensVigourLog.getVigourVal());
    }

    @Override
    public PageSerializable<FensVigourLogVo> selectVigourLog(PageDTO pageDTO) {
        Long id = UserUtil.getCurrentUserId(request);
        IPage<FensVigourLog> page = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        QueryWrapper<FensVigourLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FensVigourLog::getFensId,id);
        queryWrapper.orderByDesc("add_time");
        IPage<FensVigourLog> fensVigourLogIPage = fensVigourLogMapper.selectPage(page, queryWrapper);
        List<FensVigourLogVo> list = new ArrayList<>();
        for (FensVigourLog fensVigourLog : fensVigourLogIPage.getRecords()){
            FensVigourLogVo fensVigourLogVo = new FensVigourLogVo();
            BeanUtils.copyProperties(fensVigourLog,fensVigourLogVo);
            list.add(fensVigourLogVo);
        }
        PageSerializable<FensVigourLogVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(fensVigourLogIPage.getTotal());
        return pageSerializable;
    }
}
