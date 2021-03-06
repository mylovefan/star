package com.star.module.user.controller;


import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.operation.dto.UploadDto;
import com.star.module.user.common.IgnoreSecurity;
import com.star.module.operation.dto.LoginDto;
import com.star.module.user.dto.ModifyPassCodeDTO;
import com.star.module.user.dto.WeixinLongDto;
import com.star.module.user.facade.CommonFacade;
import com.star.module.user.service.CommonService;
import com.star.module.user.service.WeixinAuthService;
import com.star.module.user.vo.UserLoginVo;
import com.star.module.user.vo.UserMenuVo;
import com.star.util.TencentUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 运营后台用户表 前端控制器
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-11-29
 */
@RestController
@Slf4j
public class CommonController implements CommonFacade {

    @Autowired
    private CommonService commonService;

    @Autowired
    private WeixinAuthService weixinAuthService;

    @Override
    @IgnoreSecurity
    public UserLoginVo login(@RequestBody LoginDto loginDto) {
        return commonService.login(loginDto.getAccount(), loginDto.getPwd());
    }

    @Override
    public void loginout() {

    }

    @Override
    public UserMenuVo getMenus() {
        UserMenuVo userMenuVo = new UserMenuVo();
        userMenuVo.setMenus(commonService.getMenus());
        return userMenuVo;
    }

    @Override
    public Boolean modifyPassCode(ModifyPassCodeDTO modifyPassCodeDTO) {
        return null;
    }


    @Override
    @IgnoreSecurity
    public UserLoginVo weiXinLong(@RequestBody WeixinLongDto weixinLongDto) {
        return weixinAuthService.weiXinLong(weixinLongDto.getCode(), weixinLongDto.getRawData(), weixinLongDto.getSignature(), weixinLongDto.getEncrypteData(), weixinLongDto.getIv());

    }

    @Override
    public String upload(@RequestParam(value = "file") MultipartFile file) {
        File temFile = transferToFile(file);
        String path = TencentUploadUtil.upload(temFile);
        temFile.delete();
        return path;
    }


    @Override
    public String uploadBase64(@RequestBody UploadDto uploadDto) {
        BASE64Decoder decode = new BASE64Decoder();
        byte[] bytes = new byte[0];
        try {
            String str = uploadDto.getBaseStr().split(",")[1];
            bytes = decode.decodeBuffer(str);
        } catch (IOException e) {
            log.info("Base64转数组失败："+e.toString());
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"Base64转数组失败");
        }catch (Exception e) {
            log.info("Base64转数组失败："+e.toString());
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"上传失败");
        }
        String path = TencentUploadUtil.upload(bytes);
        return path;
    }

    @Override
    @IgnoreSecurity
    public UserLoginVo testLogin(Long id) {
        return weixinAuthService.testLogin(id);
    }


    private File transferToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file=File.createTempFile(filename[0], ".jpg");
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
