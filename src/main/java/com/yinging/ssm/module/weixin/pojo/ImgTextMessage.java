package com.yinging.ssm.module.weixin.pojo;

import lombok.Data;

import java.util.List;

/**
 * 微信图文消息对象
 * Created by ZhaoJunYong on 2018/4/4.
 */
@Data
public class ImgTextMessage {
    private String ToUserName;     //开发者微信号id
    private String FromUserName;   //用户openid
    private long CreateTime;       //消息发送时间
    private String MsgType;        //消息类型
    private int ArticleCount;      //数量
    private List<ImgText> Articles;//消息体
}
