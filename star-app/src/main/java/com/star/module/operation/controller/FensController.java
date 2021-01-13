package com.star.module.operation.controller;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.service.IHitListService;
import com.star.module.front.service.IStarService;
import com.star.module.front.vo.HotStarVo;
import com.star.module.operation.dto.BulidFensDto;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.dto.FensMrgStarHiyDto;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.facade.FensFacade;
import com.star.module.operation.service.FensMrgService;
import com.star.module.operation.vo.FensVo;
import com.star.module.operation.vo.GiveVo;
import com.star.module.operation.vo.ImportGiveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class FensController implements FensFacade {

    @Autowired
    private FensMrgService fensMrgService;

    @Autowired
    private IHitListService hitListService;

    @Autowired
    private IStarService starService;

    @Override
    public PageSerializable<FensVo> selectFensPage(@RequestBody FensDto fensDto) {
        return fensMrgService.selectFensPage(fensDto);
    }

    @Override
    public PageSerializable<GiveVo> selectGivePage(@RequestBody GiveDto giveDto) {
        return fensMrgService.selectGivePage(giveDto);
    }

    @Override
    public void giveVigourVal(@RequestParam("id") Long id, @RequestParam("vigourVal") Integer vigourVal) {
        fensMrgService.giveVigourVal(id, vigourVal);
    }

    @Override
    public ImportGiveVo importVigourVal(@RequestParam("file") MultipartFile file) {
        return fensMrgService.importVigourVal(file);
    }

    @Override
    public void downModel(HttpServletResponse response) {
        fensMrgService.downModel(response);
    }


    @Override
    public void addBulidFens(@RequestBody BulidFensDto bulidFensDto) {
        fensMrgService.addBulidFens(bulidFensDto);
    }


    @Override
    public void bulidFensHit(@RequestBody FensMrgStarHiyDto fensMrgStarHiyDto) {
        hitListService.bulidFensHit(fensMrgStarHiyDto);
    }


    @Override
    public void downStarHitModel(HttpServletResponse response) {
        fensMrgService.downStarHitModel(response);
    }

    @Override
    public ImportGiveVo importHitSatrVigourVal(@RequestParam("file") MultipartFile file) {
        return fensMrgService.importHitSatrVigourVal(file);
    }

    @Override
    public List<HotStarVo> selectStarByName(String starName) {
        return starService.selectStarByName(starName);
    }
}
