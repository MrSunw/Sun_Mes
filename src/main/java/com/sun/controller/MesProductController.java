package com.sun.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.param.MesProductVo;
import com.sun.service.ProductService;

@Controller
@RequestMapping("product")
public class MesProductController {
 
	private static String PFATH="product/";
	
	@Resource
	private ProductService productServive;
	
	//跳转到材料管理页面
	@RequestMapping("/productinsert.page")
	public String productInsert() {
		return PFATH+"productinsert";
	}
	
	//跳转到批量到库页面
	@RequestMapping("/product.page")
	public String product() {
		return PFATH+"product";
	}
	
	//添加
	@RequestMapping("/insert.json")
	@ResponseBody
	public void insert(MesProductVo mesProductVo) {
		productServive.productBatchInserts(mesProductVo);
	}
}
