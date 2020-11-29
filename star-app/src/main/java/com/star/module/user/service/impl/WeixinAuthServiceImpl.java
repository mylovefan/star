package com.star.module.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.user.service.WeixinAuthService;
import com.star.module.user.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WeixinAuthServiceImpl implements WeixinAuthService {

    public String appId ="";

    public String secret ="";

    @Override
    public void weiXinLong(String code, String rawData, String signature, String encrypteData, String iv) {

        JSONObject rawDataJson = JSON.parseObject( rawData );
        JSONObject SessionKeyOpenId = getSessionKeyOrOpenId( code );
        String openid = SessionKeyOpenId.getString("openid" );
        String sessionKey = SessionKeyOpenId.getString( "session_key" );

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        if (!signature.equals(signature2)) {
            throw new ServiceException(ErrorCodeEnum.PARAM_ERROR.getCode(),"签名校验失败");
        }

        //解密敏感信息
        /*String decrypt = WXCore.decrypt(appId, encrypteData, sessionKey, iv);
        JSONObject userInfo = JSON.parseObject(decrypt);*/


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


    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
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
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
