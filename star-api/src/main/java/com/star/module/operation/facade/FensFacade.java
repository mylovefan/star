package com.star.module.operation.facade;

import com.github.pagehelper.PageSerializable;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.vo.FensVo;
import com.star.module.operation.vo.GiveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "粉丝管理", tags = "粉丝管理")
@RequestMapping("fens/")
public interface FensFacade {

    @ApiOperation(value = "粉丝列表")
    @PostMapping("selectFensPage")
    PageSerializable<FensVo> selectFensPage(@RequestBody FensDto fensDto);


    @ApiOperation(value = "赠送记录列表")
    @PostMapping("selectGivePage")
    PageSerializable<GiveVo> selectGivePage(@RequestBody GiveDto giveDto);

}
