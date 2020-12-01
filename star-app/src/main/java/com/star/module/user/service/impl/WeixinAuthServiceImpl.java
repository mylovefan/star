package com.star.module.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.front.dao.FensMapper;
import com.star.module.front.entity.Fens;
import com.star.module.user.common.TokenManager;
import com.star.module.user.service.WeixinAuthService;
import com.star.module.user.util.HttpClientUtil;
import com.star.module.user.vo.UserLoginVo;
import com.star.util.SnowflakeId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WeixinAuthServiceImpl implements WeixinAuthService {

    public String appId ="";

    public String secret ="";

    @Autowired
    private FensMapper fensMapper;

    @Autowired
    private TokenManager tokenManager;


    @Override
    public UserLoginVo weiXinLong(String code, String rawData, String signature, String encrypteData, String iv) {

        JSONObject rawDataJson = JSON.parseObject( rawData );
        JSONObject SessionKeyOpenId = getSessionKeyOrOpenId( code );
        String openid = SessionKeyOpenId.getString("openid" );
        String sessionKey = SessionKeyOpenId.getString( "session_key" );

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        if (!signature.equals(signature2)) {
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"签名校验失败");
        }
        //查询用户是否存在
        QueryWrapper<Fens> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Fens::getOpenId, openid);
        Fens fens = fensMapper.selectOne(queryWrapper);
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        if(fens == null){
            // 用户信息入库
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            String gender = rawDataJson.getString("gender");
            String city = rawDataJson.getString("city");
            String country = rawDataJson.getString("country");
            String province = rawDataJson.getString("province");
            fens = new Fens();
            fens.setId(SnowflakeId.getInstance().nextId());
            fens.setOpenId(openid);
            fens.setAddTime(localDateTimeOfNow);
            fens.setLastVisitTime(localDateTimeOfNow);
            fens.setSessionKey(sessionKey);
            fens.setCity(city);
            fens.setProvince(province);
            fens.setCountry(country);
            fens.setAvatarUrl(avatarUrl);
            fens.setGender(Integer.parseInt(gender));
            fens.setNickName(nickName);
            //解密敏感信息
            JSONObject jsonObject = getUserInfo(encrypteData,sessionKey,iv);
            fens.setPhone(jsonObject.getString("phoneNumber"));
            fensMapper.insert(fens);
        }else {
            fens.setLastVisitTime(localDateTimeOfNow);
            fens.setUpdateTime(localDateTimeOfNow);
            fensMapper.updateById(fens);
        }

        String token = tokenManager.createToken(fens.getId(),openid,fens.getNickName());

        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setToken(token);
        userLoginVo.setAccount(openid);
        userLoginVo.setUserId(fens.getId());
        return userLoginVo;

    }


    @Override
    public UserLoginVo testLogin(Long id) {
        Fens fens = fensMapper.selectById(id);
        String token = tokenManager.createToken(fens.getId(),fens.getOpenId(),fens.getNickName());

        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setToken(token);
        userLoginVo.setAccount(fens.getOpenId());
        userLoginVo.setUserId(fens.getId());
        return userLoginVo;
    }

    private JSONObject getSessionKeyOrOpenId(String code){
        //微信端登录code
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String,String> requestUrlParam = new HashMap<String, String>(  );
        requestUrlParam.put( "appid",appId);//小程序appId
        requestUrlParam.put( "secret",secret);
        requestUrlParam.put( "js_code",code );//小程序端返回的code
        requestUrlParam.put( "grant_type","authorization_code" );//默认参数

        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject( HttpClientUtil.doPost( requestUrl,requestUrlParam ));
        return jsonObject;
    }


    private JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                log.info("解密用户数据："+result);
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
