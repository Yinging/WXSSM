package com.yinging.ssm.module.weixin.menu;

import com.yinging.ssm.module.weixin.pojo.Button;
import com.yinging.ssm.module.weixin.pojo.ClickButton;
import com.yinging.ssm.module.weixin.pojo.Menu;
import com.yinging.ssm.module.weixin.pojo.ViewButton;

/**
 * 我的菜单
 * Created by ZhaoJunYong on 2018/4/4.
 */
public class MyMenu {
    /**
     * 初始化菜单
     */
    public static Menu initMenu(){
        Menu menu = new Menu();

        ClickButton clkButton01 = new ClickButton();
        clkButton01.setName("click菜单");
        clkButton01.setType("click");
        clkButton01.setKey("01");

        ViewButton viwButton = new ViewButton();
        viwButton.setName("view菜单");
        viwButton.setType("view");
        viwButton.setUrl("http://2bkgcb.natappfree.cc/index.jsp");


        ClickButton clkButton02 = new ClickButton();
        clkButton02.setName("扫码事件");
        clkButton02.setType("scancode_push");
        clkButton02.setKey("02");

        ClickButton clkButton03 = new ClickButton();
        clkButton03.setName("地理位置");
        clkButton03.setType("location_select");
        clkButton03.setKey("03");

        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[] { clkButton02, clkButton03 });

        menu.setButton(new Button[] {clkButton01,viwButton,button });
        return menu;
    }
}
