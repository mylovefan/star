package com.star.module.operation.facade;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.vo.HotStarVo;
import com.star.module.operation.dto.BulidFensDto;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.dto.FensMrgStarHiyDto;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.vo.FensVo;
import com.star.module.operation.vo.GiveVo;
import com.star.module.operation.vo.ImportGiveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "粉丝管理", tags = "粉丝管理")
@RequestMapping("fens/")
public interface FensFacade {

    @ApiOperation(value = "粉丝列表")
    @PostMapping("selectFensPage")
    PageSerializable<FensVo> selectFensPage(@RequestBody FensDto fensDto);


    @ApiOperation(value = "赠送记录列表")
    @PostMapping("selectGivePage")
    PageSerializable<GiveVo> selectGivePage(@RequestBody GiveDto giveDto);


    @ApiOperation(value = "赠送活力值")
    @PostMapping("giveVigourVal")
    void giveVigourVal(@RequestParam("id") Long id,@RequestParam("vigourVal") Integer vigourVal);


    @ApiOperation(value = "导入")
    @PostMapping("importVigourVal")
    ImportGiveVo importVigourVal(@RequestParam("file") MultipartFile file);

    @ApiOperation(value = "下载模板")
    @GetMapping("downModel")
    void downModel(HttpServletResponse response);

    @ApiOperation(value = "新增自建粉丝")
    @PostMapping("addBulidFens")
    void addBulidFens(@RequestBody BulidFensDto bulidFensDto);

    @ApiOperation(value = "自建粉丝打榜")
    @PostMapping("bulidFensHit")
    void bulidFensHit(@RequestBody FensMrgStarHiyDto fensMrgStarHiyDto);


    @ApiOperation(value = "下载自建粉丝打榜模板")
    @GetMapping("downStarHitModel")
    void downStarHitModel(HttpServletResponse response);


    @ApiOperation(value = "打榜导入")
    @PostMapping("importHitSatrVigourVal")
    ImportGiveVo importHitSatrVigourVal(@RequestParam("file") MultipartFile file);

    @ApiOperation(value = "根据明星名称查询名称")
    @GetMapping("selectStarByName")
    List<HotStarVo> selectStarByName(@RequestParam("starName") String starName);

}
