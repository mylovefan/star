package com.star.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import com.star.module.operation.util.DateUtils;

import java.io.*;
import java.net.URL;
import java.util.Date;

public class TencentUploadUtil {


    //腾讯云的SecretId
    public static String secretId = "xxx";
    //腾讯云的SecretKey
    public static String secretKey ="xxx";
    //腾讯云的bucket (存储桶)
    public static String bucket ="xxx";
    //腾讯云的region(bucket所在地区)
    public static String region ="xxx";
    //腾讯云的allowPrefix(允许上传的路径)
    public static String allowPrefix;
    //腾讯云的临时密钥时长(单位秒)
    public static String durationSeconds;
    //腾讯云的访问基础链接:
    public static String baseUrl = "";



    /**
     * 上传腾讯云
     *
     * @param bytes    文件字节
     * @return 腾讯云访问路径
     */
    public static String upload(byte[] bytes) {
        String filePath = String.valueOf(SnowflakeId.getInstance().nextId()).concat(".jpg");
        // 1 初始化秘钥信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 处理文件路径
        filePath = filePath.startsWith("/") ? filePath : "/" + filePath;

        // 判断文件大小（小文件上传建议不超过20M）
        int length = bytes.length;
        // 获取文件流
        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(length);
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        // objectMetadata.setContentType("image/jpeg");

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filePath, byteArrayInputStream, objectMetadata);
        // 设置 Content type, 默认是 application/octet-stream
        putObjectRequest.setMetadata(objectMetadata);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        String eTag = putObjectResult.getETag();
        // 关闭客户端
        cosClient.shutdown();
        // http://{buckname}-{appid}.cosgz.myqcloud.com/image/1545012027692.jpg
        return findFile(filePath);
    }

    /**
     * 上传腾讯云
     *
     * @param file     文件
     * @return 腾讯云访问路径
     */
    public static String upload(File file) {
        String filePath = String.valueOf(SnowflakeId.getInstance().nextId()).concat(".jpg");
        // 1 初始化秘钥信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 处理文件路径
        filePath = filePath.startsWith("/") ? filePath : "/" + filePath;

        // 判断文件大小（小文件上传建议不超过20M）
        byte[] bytes = File2byte(file);
        int length = bytes.length;
        // 获取文件流
        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(length);
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        // objectMetadata.setContentType("image/jpeg");

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filePath, byteArrayInputStream, objectMetadata);
        // 设置 Content type, 默认是 application/octet-stream
        putObjectRequest.setMetadata(objectMetadata);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        String eTag = putObjectResult.getETag();
        // 关闭客户端
        cosClient.shutdown();
        // http://{buckname}-{appid}.cosgz.myqcloud.com/image/1545012027692.jpg
        return findFile(filePath);
    }

    public static byte[] File2byte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void del(String key) {
        // 1 初始化秘钥信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        cosClient.deleteObject(bucket,key);
        // 关闭客户端
        cosClient.shutdown();
    }

    public static String findFile(String key) {
        // 1 初始化秘钥信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        Date expiration = DateUtils.adYear(new Date(),50);
        URL url = cosClient.generatePresignedUrl(bucket, key, expiration);
        // 关闭客户端
        cosClient.shutdown();
        // http://{buckname}-{appid}.cosgz.myqcloud.com/image/1545012027692.jpg
        return url.toString();
    }

   /* public static void main(String[] args) {
        System.out.println(DateUtils.adYear(new Date(),100));
    }*/

}
