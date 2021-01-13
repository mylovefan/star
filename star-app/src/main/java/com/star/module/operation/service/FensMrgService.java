package com.star.module.operation.service;

import com.github.pagehelper.PageSerializable;
import com.star.module.operation.dto.BulidFensDto;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.vo.FensVo;
import com.star.module.operation.vo.GiveVo;
import com.star.module.operation.vo.ImportGiveVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @description :粉丝管理
 * @author zhangrc
 * @date 2020/12/1 16:54
 * @version 1.0
 */
public interface FensMrgService {

    /**
     * 粉丝列表
     *
     * @param fensDto
     * @return
     */
    PageSerializable<FensVo> selectFensPage(FensDto fensDto);

    /**
     * 赠送记录列表
     *
     * @param giveDto
     * @return
     */
    PageSerializable<GiveVo> selectGivePage(@RequestBody GiveDto giveDto);

    /**
     * 赠送活力值
     *
     * @param id
     * @param vigourVal
     */
    void giveVigourVal(@RequestParam("id") Long id ,@RequestParam("vigourVal") Integer vigourVal);

    /**
     * 导入
     * @param file
     * @return
     */
    ImportGiveVo importVigourVal(MultipartFile file);

    /**
     * 下载模板
     *
     * @param response
     * @throws Exception
     */
    void downModel(HttpServletResponse response);

    /**
     * 新增自建粉丝
     *
     * @param bulidFensDto
     */
    void addBulidFens(BulidFensDto bulidFensDto);

    /**
     * 自建粉丝打榜模板
     *
     * @param response
     */
    void downStarHitModel(HttpServletResponse response);

    /**
     * 打榜导入
     *
     * @param file
     * @return
     */
    ImportGiveVo importHitSatrVigourVal(MultipartFile file);
}
