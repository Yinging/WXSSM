package com.yinging.ssm.module.weixin.pojo;

import lombok.Data;

/**
 * 微信单个图文消息体
 * Created by ZhaoJunYong on 2018/4/4.
 */
@Data
public class ImgText {
    private String Title;       //图文消息标题
    private String Description; //图文消息描述
    private String PicUrl;      //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    private String Url;         //点击图文消息跳转链接
}
