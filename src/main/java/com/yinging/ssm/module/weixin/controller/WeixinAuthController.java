package com.yinging.ssm.module.weixin.controller;

import com.yinging.ssm.module.weixin.util.WXUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * 微信授权接口
 * Created by ZhaoJunYong on 2018/4/4.
 */
@Controller
@RequestMapping("/auth")
public class WeixinAuthController {
    private static final Logger logger = LoggerFactory.getLogger(WeixinAuthController.class);


    /**
     * 网页授权地址
     */
    @RequestMapping(value = "/authUrl", method = RequestMethod.GET)
    @ResponseBody
    public void getAuthUrl(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException {
        //回调地址
        String backUrl = WXUtil.DOMAIN + "/auth/authCallback?pageUrl=/info.jsp";
        //授权地址
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize"
                + "?appid="+WXUtil.APPID
                + "&redirect_uri=" + backUrl
                + "&response_type=code"
                + "&scope=snsapi_userinfo" //snsapi_userinfo
                + "#wechat_redirect";
        resp.sendRedirect(url);
    }

    /**
     * 微信授权回调接口
     */
    @RequestMapping(value = "/authCallback", method = RequestMethod.GET)
    @ResponseBody
    public void authCallback(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException, ServletException {
        //访问地址后调用回调函数,并且传递code
        String code = req.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token"
                + "?appid=" + WXUtil.APPID
                + "&secret=" + WXUtil.APPSECRET
                + "&code=" + code
                + "&grant_type=authorization_code";
        JSONObject jsonObject = WXUtil.doGetStr(url);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo" + "?access_token=" + token + "&openid=" + openid + "&lang=zh_CN";

        //获取用户信息
        JSONObject basicInfo = WXUtil.doGetStr(infoUrl);
        String info = basicInfo.toString();

        String pageUrl = req.getParameter("pageUrl");
        System.out.println("pageUrl:" + pageUrl);
        //将用户信息保留在session中
        req.setAttribute("info", info);
        req.getRequestDispatcher(pageUrl).forward(req, resp);
    }
}
