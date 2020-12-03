package com.star.module.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dao.GuardMapper;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Guard;
import com.star.module.front.service.IGuardService;
import com.star.module.front.vo.MyGuardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 守护表 服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-03
 */
@Service
public class GuardServiceImpl extends ServiceImpl<GuardMapper, Guard> implements IGuardService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StarMapper starMapper;


    @Override
    public PageSerializable<MyGuardVo> selectMyGuard(PageDTO pageDTO) {
       /* Long id = UserUtil.getCurrentUserId(request);
        IPage<Guard> page = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        QueryWrapper<Fens> queryWrapper = new QueryWrapper<>();
        if(fensDto.getId() != null){
            queryWrapper.lambda().like(Fens::getId,fensDto.getId());
        }
        queryWrapper.orderByDesc("add_time");
        IPage<Fens> fensIPage = fensMapper.selectPage(page, queryWrapper);
        List<FensVo> list = new ArrayList<>();
        for (Fens fens : fensIPage.getRecords()){
            FensVo fensVo = new FensVo();
            BeanUtils.copyProperties(fens,fensVo);
            list.add(fensVo);
        }
        PageSerializable<FensVo> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(fensIPage.getTotal());
        return pageSerializable;*/
        return null;
    }

}
