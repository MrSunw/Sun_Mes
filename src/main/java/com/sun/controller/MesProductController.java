package com.sun.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.beans.PageQuery;
import com.sun.beans.PageResult;
import com.sun.common.JsonData;
import com.sun.model.MesProduct;
import com.sun.param.MesProductVo;
import com.sun.param.SearchProductIronParam;
import com.sun.param.SearchProductParam;
import com.sun.service.ProductService;

@Controller
@RequestMapping("/product")
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
	
	//跳转到钢锭查询页面
	@RequestMapping("/productIron.page")
	public String productIron() {
		return PFATH+"productIron";
	}
	
	//跳转到到库查询页面
	@RequestMapping("/productCome.page")
	public String productCome() {
		return PFATH+"productCome";
	}
	
	//跳转到材料绑定页面
	@RequestMapping("/productBindList.page")
	public String productBindList() {
		return PFATH+"productBindList";
	}
	
	//跳转到材料点击绑定页面
	@RequestMapping("/productBind.page")

	public String productBind(String id,Model model) {
		MesProduct product=productServive.seletById(id);
		if(product!=null) {
			model.addAttribute("product",product);
			return PFATH+"productBind";
		}else {
		  return PFATH+"productBindList";
		}
	
	}
	
	//添加
	@RequestMapping("/insert.json")
	public String insert(MesProductVo mesProductVo) {
		productServive.productBatchInserts(mesProductVo);
        if(mesProductVo!=null&&mesProductVo.getProductMaterialsource().equals("钢锭")) {
        	return PFATH+"productIron";
        }else {
        	return PFATH+"product";
        }	
        
	}
	
	//修改
	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData updateProduct(MesProductVo mesProductVo) {
		System.out.println("----->"+mesProductVo);
     	productServive.update(mesProductVo);
		return JsonData.success();
	}
	
	//查询批量到库页面 分页 到库查询分页 材料绑定分页
	@RequestMapping("/product.json")
	@ResponseBody
	public JsonData productSelect(SearchProductParam param,PageQuery page) {
	PageResult<MesProduct> pd=	(PageResult<MesProduct>) productServive.productSelect(param,page);
	return JsonData.success(pd);
		
	}
	
	//查询钢锭分页
	
	@RequestMapping("/productIron.json")
	@ResponseBody
	public JsonData productIronSelect(SearchProductIronParam param,PageQuery page) {
		PageResult<MesProduct> pdi=	(PageResult<MesProduct>) productServive.productIronSelect(param,page);
		return JsonData.success(pdi);
	}
	
	//材料批量到库
	@RequestMapping("/productBatchStart.json")
	@ResponseBody
	public JsonData productBatchStart(String ids) {
		productServive.productBatchStart(ids);
		return JsonData.success();
	}
	
}
