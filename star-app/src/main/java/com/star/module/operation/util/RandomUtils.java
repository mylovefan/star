package com.star.module.operation.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class RandomUtils {

    private final static String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    private final static String NUMBERS = "0123456789";

    /**
     * 6位随机数字
     * @param existList 已存在的数字
     * @return
     */
    public static Long randomNumber6 (List<Long> existList){
        Long numbers = null;
        int s= 0;//防止无限循环 限定10次
        while (s<10){
            //6位随机小写字母
            String code = RandomStringUtils.random(6,NUMBERS);
            boolean existFlag = false;
            if(existList!=null && existList.size()>0){
                existFlag = existList.stream().anyMatch(t -> t.equals(code));
            }
            if (!existFlag) {
                numbers = Long.valueOf(code);
                break;
            }
            s++;
        }
        return numbers;
    }

    public static void main(String[] args) {
        System.out.println(randomNumber6 (new ArrayList<>()));;
    }
}
