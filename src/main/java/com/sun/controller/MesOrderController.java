package com.sun.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.common.JsonData;
import com.sun.param.MesOrderVo;
import com.sun.service.OrderService;

@Controller
@RequestMapping("/order")
public class MesOrderController {
	
	private static String PHATH="order/";
	
	@Resource
	private OrderService orderService;

	//转发到创建启动页面
	@RequestMapping("/orderBatch.page")
	public String orderBatch() {
		return  PHATH+"orderBatch";
	}
	
	//添加订单
	//添加接收json数据的注解
	@ResponseBody
	@RequestMapping("insert.json")
	public JsonData insertAjax(MesOrderVo mesOrderVo) {
		System.out.println(mesOrderVo);
		orderService.addOrder(mesOrderVo);
		return JsonData.successs();
	}	
	
}
