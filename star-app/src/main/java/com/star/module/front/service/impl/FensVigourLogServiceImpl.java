package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.common.CommonConstants;
import com.star.module.front.dao.FensMapper;
import com.star.module.front.dao.FensVigourLogMapper;
import com.star.module.front.entity.Fens;
import com.star.module.front.entity.FensVigourLog;
import com.star.module.front.service.IFensVigourLogService;
import com.star.util.SnowflakeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    @Override
    public void addVigour(FensVigourLog fensVigourLog) {
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        fensVigourLog.setId(SnowflakeId.getInstance().nextId());
        fensVigourLog.setAddTime(localDateTimeOfNow);
        fensVigourLog.setVigTime(LocalDate.now());
        fensVigourLogMapper.insert(fensVigourLog);

        //修改粉丝表活力值记录
        fensMapper.updateVigour(fensVigourLog.getFensId(),fensVigourLog.getVigourVal());
    }
}
