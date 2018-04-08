package com.yinging.ssm.module.weixin.pojo;

import lombok.Data;

/**
 * 一级菜单实体类
 * Created by ZhaoJunYong on 2018/4/4.
 */
@Data
public class Button {
	private String name;
	private String type;
	private Button[] sub_button;
	}
