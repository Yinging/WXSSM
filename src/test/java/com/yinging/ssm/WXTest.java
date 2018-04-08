package com.yinging.ssm;

import com.yinging.ssm.module.weixin.pojo.AccessToken;
import com.yinging.ssm.module.weixin.util.WXUtil;

/**
 * 微信测试类
 * Created by ZhaoJunYong on 2018/4/4.
 */
public class WXTest {
    public static void main(String[] args){
        AccessToken accessToken = WXUtil.getAccessToken();
        System.out.println("票据："+accessToken.getToken());
        System.out.println("有效时间："+accessToken.getExpiresIn());
    }
}
