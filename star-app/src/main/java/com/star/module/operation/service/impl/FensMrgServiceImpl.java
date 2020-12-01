package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.star.module.front.dao.FensMapper;
import com.star.module.front.entity.Fens;
import com.star.module.front.entity.FensVigourLog;
import com.star.module.front.enums.VigourTypeEnums;
import com.star.module.front.service.IFensVigourLogService;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.service.FensMrgService;
import com.star.module.operation.vo.FensVo;
import com.star.module.operation.vo.GiveVo;
import com.star.module.operation.vo.ImportGiveVo;
import com.star.module.user.common.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FensMrgServiceImpl implements FensMrgService {

    @Autowired
    private FensMapper fensMapper;

    @Autowired
    private IFensVigourLogService fensVigourLogService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public PageSerializable<FensVo> selectFensPage(FensDto fensDto) {
        IPage<Fens> page = new Page<>(fensDto.getPageNum(),fensDto.getPageSize());
        QueryWrapper<Fens> queryWrapper = new QueryWrapper<>();
        if(fensDto.getId() != null){
            queryWrapper.lambda().like(Fens::getId,fensDto.getId());
        }
        IPage<Fens> fensIPage = fensMapper.selectPage(page, queryWrapper);
        List<FensVo> list = new ArrayList<>();
        for (Fens fens : fensIPage.getRecords()){
            FensVo fensVo = new FensVo();
            BeanUtils.copyProperties(fens,fensVo);
            list.add(fensVo);
        }
        PageSerializable<FensVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(fensIPage.getTotal());
        return pageSerializable;
    }


    @Override
    public PageSerializable<GiveVo> selectGivePage(GiveDto giveDto) {
        PageHelper.startPage(giveDto.getPageNum(),giveDto.getPageSize());
        com.github.pagehelper.Page<GiveVo> page = fensMapper.selectGivePage(giveDto);
        PageSerializable<GiveVo> pageSerializable = new PageSerializable<>(page.getResult());
        pageSerializable.setTotal(page.getTotal());
        return pageSerializable;
    }

    @Override
    public void giveVigourVal(Long id,Integer vigourVal) {
        FensVigourLog fensVigourLog = new FensVigourLog();
        fensVigourLog.setAddUser(UserUtil.getCurrentUserName(request));
        fensVigourLog.setFensId(id);
        fensVigourLog.setType(VigourTypeEnums.GIVE.getCode());
        fensVigourLogService.addVigour(fensVigourLog);
    }


    @Override
    public ImportGiveVo importVigourVal(MultipartFile file) {
        return null;
    }
}