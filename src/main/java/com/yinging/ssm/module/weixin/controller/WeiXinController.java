package com.yinging.ssm.module.weixin.controller;

import com.yinging.ssm.module.weixin.pojo.AccessToken;
import com.yinging.ssm.module.weixin.pojo.ImgText;
import com.yinging.ssm.module.weixin.pojo.ShareUrl;
import com.yinging.ssm.module.weixin.pojo.TicketInfo;
import com.yinging.ssm.module.weixin.util.MessageUtil;
import com.yinging.ssm.module.weixin.util.CheckUtil;
import com.yinging.ssm.module.weixin.util.WXUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.Map;

import static com.yinging.ssm.module.weixin.util.WXUtil.APPID;
import static com.yinging.ssm.module.weixin.util.WXUtil.APPSECRET;

/**
 * 微信开发入门 -- 个人学习总结
 * Created by ZhaoJunYong on 2018/4/3.
 */
@Controller
@RequestMapping("/weiXinController")
public class WeiXinController {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinController.class);

    @RequestMapping(value = "/wxRest", method = RequestMethod.GET)
    @ResponseBody
    public String wxGet(@Context HttpServletRequest request, @Context HttpServletResponse response){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //校验参数
        logger.debug("| 开始校验参数 |");
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            logger.debug("| 校验成功 |");
            //将字符串返回给微信
            return echostr;
        }
        logger.debug("| 校验失败 |");
        return "";
    }

    @RequestMapping(value = "wxRest",method = RequestMethod.POST)
    @ResponseBody
    public String wxPost(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException, DocumentException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> map = MessageUtil.xmlToMap(request);
        String fromUserName = map.get("FromUserName"); //发送方帐号（用户OpenID）
        String toUserName = map.get("ToUserName"); //开发者微信号原始ID
        String msgType = map.get("MsgType"); //text
        String content = map.get("Content"); //文本消息内容

        String message = null;
        if("text".equals(msgType)){
            //发送的消息为文本消息
            if("1".equals(content)){
                StringBuffer sb = new StringBuffer();
                sb.append("这是一个测试项目");
                message = MessageUtil.sendText(toUserName,fromUserName,sb.toString());
            }else if ("2".equals(content)){
                ImgText imgText = new ImgText();
                imgText.setTitle("百度");
                imgText.setDescription("这是一个百度网址");
                imgText.setPicUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2920084749,2018893236&fm=58&s=39C718720E8EBE011B398BAC0300F024&bpow=121&bpoh=75");
                imgText.setUrl("www.baidu.com");
                message = MessageUtil.sendImgText(toUserName,fromUserName,imgText);
            }else if("3".equals(content)){
                StringBuffer sb = new StringBuffer();
                sb.append("用于学习微信项目的开发");
                message = MessageUtil.sendText(toUserName,fromUserName,sb.toString());
            }else if("?".equals(content) || "？".equals(content)){
                StringBuffer sb = new StringBuffer();
                sb.append("欢迎您的关注,请按照菜单提示操作:\n");
                sb.append("1:介绍\n");
                sb.append("2:百度网址\n");
                sb.append("3:关于本项目\n");
                sb.append("?:帮助");
                message = MessageUtil.sendText(toUserName,fromUserName,sb.toString());
            }else {
                StringBuffer sb = new StringBuffer();
                sb.append("您发送的消息是:" + content);
                message = MessageUtil.sendText(toUserName, fromUserName, sb.toString());
            }
        }else if("event".equals(msgType)){
            //发送的消息是一个事件
            //1.获取事件类型
            String eventType = map.get("Event");
            if("subscribe".equals(eventType)){
                //关注事件
                return MessageUtil.sendText(toUserName,fromUserName,"?:帮助");
            }else if("CLICK".equals(eventType)){
                //菜单click事件
                message = MessageUtil.sendText(toUserName,fromUserName,"您点击了Click");
            }else if("VIEW".equals(eventType)){
                //菜单view事件
                String url = map.get("EventKey");
                message = MessageUtil.sendText(toUserName,fromUserName,url);
            }else if("scancode_push".equals(eventType)){
                String key=map.get("EventKey");
                message = MessageUtil.sendText(toUserName,fromUserName,key);
            }
        }else if("location".equals(msgType)){
            String label = map.get("Label");
            message = MessageUtil.sendText(toUserName,fromUserName,label);
        }
        return message;
    }

    /**
     * 微信自定义分享
     * @throws Exception
     */
    @RequestMapping(value = "shareWX",method = RequestMethod.POST)
    @ResponseBody
    public Map shareWX(ShareUrl url,@Context HttpServletRequest req, @Context HttpServletResponse resp) throws Exception {
        System.out.println("url:"+url.getShareurl());
        AccessToken accessToken = WXUtil.getAccessToken();
        TicketInfo ticketInfo = WXUtil.getTicket(accessToken.getToken());
        Map<String,String> wechatParam = WXUtil.getWechatParam(ticketInfo.getTicket(),url.getShareurl());
        return wechatParam;
    }
}
