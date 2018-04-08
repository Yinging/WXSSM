package com.yinging.ssm.module.weixin.menu.menuevent;

import com.yinging.ssm.module.weixin.menu.MyMenu;
import com.yinging.ssm.module.weixin.pojo.AccessToken;
import com.yinging.ssm.module.weixin.util.WXUtil;
import net.sf.json.JSONObject;

/**
 * 查询菜单
 * Created by ZhaoJunYong on 2018/4/4.
 */
public class QueryMenu {
    public static void main(String[] args){
        //1.获取access_token
        AccessToken accessToken = WXUtil.getAccessToken();
        //2.将menu实体类转换为json，在转换为string
        String menu = JSONObject.fromObject(MyMenu.initMenu()).toString();
        //3.创建菜单
        JSONObject result = WXUtil.queryMenu(accessToken.getToken());
        if(null!=result){
            System.out.println("菜单："+ result.toString());
        }else{
            System.out.println("菜单不存在");
        }
    }
}
