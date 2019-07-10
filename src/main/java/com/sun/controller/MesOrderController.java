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

	//ת������������ҳ��
	@RequestMapping("/orderBatch.page")
	public String orderBatch() {
		return  PHATH+"orderBatch";
	}
	
	//��Ӷ���
	//��ӽ���json���ݵ�ע��
	@ResponseBody
	@RequestMapping("insert.json")
	public JsonData insertAjax(MesOrderVo mesOrderVo) {
		System.out.println(mesOrderVo);
		orderService.addOrder(mesOrderVo);
		return JsonData.successs();
	}	
	
}
