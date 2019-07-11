package com.sun.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.beans.PageQuery;
import com.sun.beans.PageResult;
import com.sun.common.JsonData;
import com.sun.model.MesPlan;
import com.sun.param.MesPlanVo;
import com.sun.param.SearchPlanParam;
import com.sun.service.PlanService;

@Controller
@RequestMapping("/plan")
public class MesPlanController {

	private static String FPATH="plan/";
	
	@Resource
	private PlanService planService;
	
	//跳转到待启动计划页面
	@RequestMapping("/plan.page")
	public String planPage() {
		return FPATH+"plan";
	} 
	
	//跳转到已启动计划页面
	@RequestMapping("/planStarted.page")
	public String planStartedPage() {
		return FPATH+"planStarted";
	}
	
	//分页显示
	@RequestMapping("/plan.json")
	@ResponseBody
	public JsonData searchPage(SearchPlanParam param,PageQuery page) {
		PageResult<MesPlan> pr=(PageResult<MesPlan>) planService.searchPageList(param, page);
		return JsonData.success(pr);
	}
	
	//修改
	@RequestMapping("update.json")
	@ResponseBody
	 public JsonData updatePlan(MesPlanVo mesPlanVo) {
    	planService.update(mesPlanVo);
    	return JsonData.success();
    }
}
