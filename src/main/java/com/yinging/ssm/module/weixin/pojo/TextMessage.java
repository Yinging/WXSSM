package com.yinging.ssm.module.weixin.pojo;

import lombok.Data;

/**
 * 微信文本消息对象
 * Created by ZhaoJunYong on 2018/4/3.
 */

@Data
public class TextMessage {
    private String ToUserName;   //开发者微信号id
    private String FromUserName; //用户openid
    private long CreateTime;     //消息发送时间
    private String MsgType;      //消息类型
    private String Content;      //消息内容
    private String MsgId;        //消息id，64位整型
}
