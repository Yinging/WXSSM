package com.yinging.ssm.module.weixin.pojo;

import lombok.Data;

/**
 * access_token实体类
 * Created by ZhaoJunYong on 2018/4/4.
 */
@Data
public class AccessToken {
    private String token;
    private int expiresIn;
}
