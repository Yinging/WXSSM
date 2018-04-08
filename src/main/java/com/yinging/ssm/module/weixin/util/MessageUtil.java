package com.yinging.ssm.module.weixin.util;


import com.thoughtworks.xstream.XStream;
import com.yinging.ssm.module.weixin.pojo.ImgText;
import com.yinging.ssm.module.weixin.pojo.ImgTextMessage;
import com.yinging.ssm.module.weixin.pojo.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by ZhaoJunYong on 2018/4/3.
 */
public class MessageUtil {
    /**
     * XML转换为map
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map = new HashMap<String,String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();

        Document doc= reader.read(ins);
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for(Element e:list){
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }

    /**
     * 将文本消息对象类型转换为XML类型,将XML格式数据的根节点改为<xml></xml>
     */
    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream= new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 将图文消息对象类型转换为XML类型,将XML格式数据的根节点改为<xml></xml>,消息根节点为<item></item>
     */
    public static String imgTextMessageToXml(ImgTextMessage imgTextMessage){
        XStream xstream= new XStream();
        xstream.alias("xml", imgTextMessage.getClass());
        xstream.alias("item", new ImgText().getClass());
        return xstream.toXML(imgTextMessage);
    }
    /**
     * 发送文本消息
     */
    public static String sendText(String toUserName,String fromUserName,String content){
        TextMessage text= new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType("text");
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return MessageUtil.textMessageToXml(text);
    }

    /**
     * 发送图文消息
     */
    public static String sendImgText(String toUserName,String fromUserName,ImgText imgText){
        String message =null;
        List<ImgText> imgTextList = new ArrayList<ImgText>();
        imgTextList.add(imgText);

        ImgTextMessage imgTextMessage = new ImgTextMessage();
        imgTextMessage.setToUserName(fromUserName);
        imgTextMessage.setFromUserName(toUserName);
        imgTextMessage.setCreateTime(new Date().getTime());
        imgTextMessage.setMsgType("news");
        imgTextMessage.setArticles(imgTextList);
        imgTextMessage.setArticleCount(imgTextList.size());
        message = imgTextMessageToXml(imgTextMessage);
        return message;
    }
}
