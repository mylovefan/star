package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.FensMapper;
import com.star.module.front.dao.FensVigourLogMapper;
import com.star.module.front.dao.StarMapper;
import com.star.module.front.entity.Fens;
import com.star.module.front.entity.FensVigourLog;
import com.star.module.front.entity.Star;
import com.star.module.front.enums.VigourTypeEnums;
import com.star.module.front.service.IFensVigourLogService;
import com.star.module.front.service.IHitListService;
import com.star.module.operation.dto.BulidFensDto;
import com.star.module.operation.dto.FensDto;
import com.star.module.operation.dto.FensMrgStarHiyDto;
import com.star.module.operation.dto.GiveDto;
import com.star.module.operation.service.FensMrgService;
import com.star.module.operation.util.RandomUtils;
import com.star.module.operation.vo.FensVo;
import com.star.module.operation.vo.GiveVo;
import com.star.module.operation.vo.ImportGiveVo;
import com.star.module.user.common.UserUtil;
import com.star.util.SnowflakeId;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Autowired
    private StarMapper starMapper;

    @Autowired
    private IHitListService hitListService;

    @Autowired
    private FensVigourLogMapper fensVigourLogMapper;

    @Override
    public PageSerializable<FensVo> selectFensPage(FensDto fensDto) {
        IPage<Fens> page = new Page<>(fensDto.getPageNum(),fensDto.getPageSize());
        QueryWrapper<Fens> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Fens::getBuild,fensDto.getBulid());
        if(fensDto.getId() != null){
            queryWrapper.lambda().like(Fens::getFensId,fensDto.getId());
        }
        queryWrapper.orderByDesc("add_time");
        IPage<Fens> fensIPage = fensMapper.selectPage(page, queryWrapper);
        List<FensVo> list = new ArrayList<>();
        for (Fens fens : fensIPage.getRecords()){
            FensVo fensVo = new FensVo();
            BeanUtils.copyProperties(fens,fensVo);

            //查询分享总次数
            QueryWrapper<FensVigourLog> fqueryWrapper = new QueryWrapper<>();
            fqueryWrapper.lambda().eq(FensVigourLog::getFensId,fens.getId());
            Integer count = fensVigourLogMapper.selectCount(fqueryWrapper);
            fensVo.setShareCount(count);
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
        fensVigourLog.setVigourVal(vigourVal);
        fensVigourLogService.addVigour(fensVigourLog);
    }


    @Override
    public ImportGiveVo importVigourVal(MultipartFile file)  {
        Workbook wookbook = null;
        String originalFilename = file.getOriginalFilename();
        // 判断是否为excel类型文件
        if(!originalFilename.endsWith(".xls") && !originalFilename.endsWith(".xlsx")) {
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"文件不是excle类型");
        }
        // 得到工作簿
        try {
            wookbook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception ex) {
            try {
                wookbook = new HSSFWorkbook(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);
        // 获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        List<String> list = new ArrayList<>();
        ImportGiveVo importGiveVo = new ImportGiveVo();
        int succ = 0;
        int fail = 0;
        for (int i = 1; i <= totalRowNum; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);
            Cell c0 = row.getCell(0);
            Long fensId = null;
            if(c0.getCellType() == 0){
                Integer fensIdStr =new Integer((int) c0.getNumericCellValue());
                fensId = Long.parseLong(fensIdStr.toString());
            }else {
                String fensIdStr = c0.getStringCellValue();
                fensId = Long.parseLong(fensIdStr);
            }

            Cell c1 = row.getCell(1);
            String vigourVal = "";
            if(c0.getCellType() == 0){
                Integer cellValue = new Integer((int) c1.getNumericCellValue());
                vigourVal = cellValue.toString();
            }else {
                vigourVal = c1.getStringCellValue();
            }

            QueryWrapper<Fens> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Fens::getFensId, fensId);
            Fens fens = fensMapper.selectOne(queryWrapper);
            if(fens == null){
                fail ++ ;
                list.add(fensId.toString());
                continue;
            }
            FensVigourLog fensVigourLog = new FensVigourLog();
            fensVigourLog.setAddUser(UserUtil.getCurrentUserName(request));
            fensVigourLog.setFensId(fens.getId());
            fensVigourLog.setVigourVal(Integer.parseInt(vigourVal));
            fensVigourLog.setType(VigourTypeEnums.GIVE.getCode());
            fensVigourLogService.addVigour(fensVigourLog);
            succ ++;

        }
        importGiveVo.setFensIds(list);
        importGiveVo.setFail(fail);
        importGiveVo.setSucc(succ);
        return importGiveVo;
    }


    @Override
    public void downModel(HttpServletResponse response) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("批量赠送活力值");
        sheet.setDefaultColumnWidth(30);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);

        HSSFCell cell0 = row.createCell((short) 0);
        cell0.setCellValue("粉丝ID");
        HSSFCell cell1 = row.createCell((short) 1);
        cell1.setCellValue("活力值");

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode("批量赠送活力值.xls", "UTF-8"));
            wb.write(out);
        }catch (Exception e){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"下载模板失败");
        }  finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void addBulidFens(BulidFensDto bulidFensDto) {
        Fens fens = new Fens();
        fens.setId(SnowflakeId.getInstance().nextId());
        LocalDateTime localDateTimeOfNow = LocalDateTime.now();
        fens.setAddTime(localDateTimeOfNow);
        fens.setAvatarUrl(bulidFensDto.getAvatarUrl());
        fens.setNickName(bulidFensDto.getNickName());
        fens.setConsumeVigourVal(0);
        fens.setTotalVigourVal(1000000);
        fens.setVigourVal(1000000);
        List<Long> longs = fensMapper.selectFensIds();
        Long fensId = RandomUtils.randomNumber6(longs);
        fens.setFensId(fensId);
        fens.setBuild(1);
        fensMapper.insert(fens);
    }


    @Override
    public void downStarHitModel(HttpServletResponse response) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("批量赠送活力值");
        sheet.setDefaultColumnWidth(30);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);

        HSSFCell cell0 = row.createCell((short) 0);
        cell0.setCellValue("粉丝ID");
        HSSFCell cell1 = row.createCell((short) 1);
        cell1.setCellValue("打榜热力值");
        HSSFCell cell2 = row.createCell((short) 2);
        cell2.setCellValue("明星ID");

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode("批量打榜.xls", "UTF-8"));
            wb.write(out);
        }catch (Exception e){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"下载模板失败");
        }  finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    @Transactional
    public ImportGiveVo importHitSatrVigourVal(MultipartFile file) {
        Workbook wookbook = null;
        String originalFilename = file.getOriginalFilename();
        // 判断是否为excel类型文件
        if(!originalFilename.endsWith(".xls") && !originalFilename.endsWith(".xlsx")) {
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"文件不是excle类型");
        }
        // 得到工作簿
        try {
            wookbook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception ex) {
            try {
                wookbook = new HSSFWorkbook(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);
        // 获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        List<String> list = new ArrayList<>();
        ImportGiveVo importGiveVo = new ImportGiveVo();
        int succ = 0;
        int fail = 0;
        for (int i = 1; i <= totalRowNum; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);
            if(row == null){
                continue;
            }
            Cell c0 = row.getCell(0);
            Long fensId = null;
            Long starId = null;
            if(c0.getCellType() == 0){
                Integer fensIdStr =new Integer((int) c0.getNumericCellValue());
                fensId = Long.parseLong(fensIdStr.toString());
            }else {
                String fensIdStr = c0.getStringCellValue();
                if(StringUtils.isBlank(fensIdStr)){
                    continue;
                }
                fensId = Long.parseLong(fensIdStr);
            }

            Cell c1 = row.getCell(1);
            String vigourVal = "";
            if(c1.getCellType() == 0){
                Integer cellValue = new Integer((int) c1.getNumericCellValue());
                vigourVal = cellValue.toString();
            }else {
                vigourVal = c1.getStringCellValue();
            }

            Cell c2 = row.getCell(2);
            if(c2.getCellType() == 2){
                Integer starIdStr =new Integer((int) c2.getNumericCellValue());
                starId = Long.parseLong(starIdStr.toString());
            }else {
                String starIdStr = c2.getStringCellValue();
                starId = Long.parseLong(starIdStr);
            }

            QueryWrapper<Fens> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Fens::getFensId, fensId);
            Fens fens = fensMapper.selectOne(queryWrapper);
            if(fens == null){
                fail ++ ;
                list.add("粉丝Id:"+fensId.toString()+"不存在");
                continue;
            }
            QueryWrapper<Star> starqueryWrapper = new QueryWrapper<>();
            starqueryWrapper.lambda().eq(Star::getStarId, starId);
            Star star = starMapper.selectOne(starqueryWrapper);
            if(star == null){
                fail ++ ;
                list.add("明星Id:"+starId.toString()+"不存在");
                continue;
            }

            FensMrgStarHiyDto fensMrgStarHiyDto = new FensMrgStarHiyDto();
            fensMrgStarHiyDto.setId(fens.getId());
            fensMrgStarHiyDto.setStarId(star.getId());
            fensMrgStarHiyDto.setVigourVal(Integer.parseInt(vigourVal));
            fensMrgStarHiyDto.setStarName(star.getName());
            try {
                hitListService.bulidFensHit(fensMrgStarHiyDto);
            } catch (ServiceException e) {
                fail ++ ;
                list.add("粉丝Id:"+fensId.toString()+"热力值不够");
                continue;
            }
            succ ++;

        }
        importGiveVo.setFensIds(list);
        importGiveVo.setFail(fail);
        importGiveVo.setSucc(succ);
        return importGiveVo;
    }
}
