package com.star.module.operation.service.impl;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.dao.FensMapper;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.service.FensMrgService;
import com.star.module.operation.vo.FensVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FensMrgServiceImpl implements FensMrgService {

    @Autowired
    private FensMapper fensMapper;

    @Override
    public PageSerializable<FensVo> selectFensPage(FensDto fensDto) {
        return null;
    }
}
