package com.star.util;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ：minghang<henry@gvt861.com>
 * @date ：Created in 2020/1/17 16:57
 * @description：
 * @since： JDK8, gms-api2.0.1
 */
@Component
public class ImageToBase64 {

    /**
     * 本地图片转换Base64的方法
     *
     * @param imgPath     
     */


     public  String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
         String imgFile = imgPath;// 待处理的图片
         InputStream in = null;
         byte[] data = null;
         String encode = null; // 返回Base64编码过的字节数组字符串
         // 对字节数组Base64编码
         BASE64Encoder encoder = new BASE64Encoder();
         try {
             // 读取图片字节数组
             in = new FileInputStream(imgFile);
             data = new byte[in.available()];
             in.read(data);
             encode = encoder.encode(data);
         } catch (IOException e) {
            e.printStackTrace();
        } finally {
             try {
                 if (in != null){
                     in.close();
                 }
             } catch (IOException e) {
                 // TODO Auto-generated catch block
                   e.printStackTrace();
             }
         }
         return encode;
     }
//
//    public static void main(String[] args) {
//        String s = GetImageStr("C:\\Users\\GVT\\Desktop\\bgi\\abc.jpg");
//        System.out.println(s);
//    }
}
