package com.yinging.ssm.module.weixin.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 检验工具类, 验证消息的确来自微信服务器.
 * Created by ZhaoJunYong on 2018/4/3.
 */
public class CheckUtil {
    private static final String token = "yinging";
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 排序
        Arrays.sort(arr);
        // 生成字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        //sha1加密
        String temp = getSha1(content.toString());
        //与微信传过来的signature进行比较
        System.out.println("校验中");
        System.out.println("signature:"+signature);
        System.out.println("temp     :"+temp);
        System.out.println("校验结束");
        return temp.equals(signature);
    }

    //sha1加密算法
    public static String getSha1(String str){
        if (null == str || 0 == str.length()){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
