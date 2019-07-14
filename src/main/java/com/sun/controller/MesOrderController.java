package com.sun.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.beans.PageQuery;
import com.sun.beans.PageResult;
import com.sun.common.JsonData;
import com.sun.model.MesOrder;
import com.sun.param.MesOrderVo;
import com.sun.param.SearchOrderParam;
import com.sun.service.OrderService;

@Controller
@RequestMapping("/order")
public class MesOrderController {
	
	private static String PHATH="order/";
	
	@Resource
	private OrderService orderService;

	//跳转订单创建页面
	@RequestMapping("/orderBatch.page")
	public String orderBatch() {
		return  PHATH+"orderBatch";
	}
	//跳转订单查询
	@RequestMapping("/order.page")
	public String orderPage() {
		return PHATH+"order";
	}
	
	
	//添加订单(批量增加)
	//添加接收json数据的注解
	@ResponseBody
	@RequestMapping("/insert.json")
	public JsonData insertAjax(MesOrderVo mesOrderVo) {
		orderService.orderBatchInserts(mesOrderVo);//批量
		return JsonData.success();
	}	
	
	@RequestMapping("/order.json")
	@ResponseBody
	//SearchOrderParam 查询关键词  PageQuery 页码相关值
	public JsonData searchPage(SearchOrderParam param,PageQuery page) {
		PageResult<MesOrder> pr=(PageResult<MesOrder>) orderService.searchPageList(param, page);
		return JsonData.success(pr);   
		
	}
	
	@RequestMapping("/update.json")
	@ResponseBody
	//修改
	public JsonData updateOrder(MesOrderVo mesOrderVo) {
		orderService.update(mesOrderVo);
    	return JsonData.success();
	}
	
	//批量启动处理
	@ResponseBody
	@RequestMapping("orderBatchStart.json")
	public JsonData orderBatchStart(String ids) {
		orderService.batchStart(ids);
		return JsonData.success();
	}
}
